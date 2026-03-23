package com.fridge.app.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.fridge.app.data.db.AppDatabase
import com.fridge.app.data.model.Ingredient
import com.fridge.app.data.model.IngredientCategory
import com.fridge.app.notification.NotificationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * 过期检查Worker
 * 后台定期检查食材过期情况并发送通知
 */
class ExpiryCheckWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val database = AppDatabase.getDatabase(context)
    private val ingredientDao = database.ingredientDao()
    private val notificationHelper = NotificationHelper(context)

    override suspend fun doWork(): Result {
        return try {
            checkAndNotifyExpired()
            checkAndNotifyExpiring()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    /**
     * 检查已过期食材
     */
    private suspend fun checkAndNotifyExpired() {
        val expiredIngredients = withContext(Dispatchers.IO) {
            ingredientDao.getAllIngredientsSync().filter {
                it.expireDate != null && it.expireDate.before(Date())
            }
        }

        if (expiredIngredients.isNotEmpty()) {
            // 只通知今日新过期的食材
            val todayExpired = expiredIngredients.filter { ingredient ->
                ingredient.expireDate?.let { isToday(it) } ?: false
            }

            if (todayExpired.isNotEmpty()) {
                notificationHelper.sendExpiredNotification(todayExpired)
            }

            // 发送单独通知给特别重要的食材（如肉类、海鲜）
            val importantExpired = expiredIngredients.filter { 
                it.category == IngredientCategory.MEAT ||
                it.category == IngredientCategory.SEAFOOD ||
                it.category == IngredientCategory.DAIRY
            }

            importantExpired.forEach { ingredient ->
                notificationHelper.sendSingleExpiredNotification(ingredient)
            }
        }
    }

    /**
     * 检查即将过期食材
     */
    private suspend fun checkAndNotifyExpiring() {
        // 获取3天内过期的食材
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 3)
        
        val expiringIngredients = withContext(Dispatchers.IO) {
            ingredientDao.getExpiringIngredients(calendar.time)
        }.filter { ingredient ->
            // 排除已过期
            ingredient.getDaysUntilExpire()?.let { it >= 0 } ?: false
        }

        // 按过期时间分组
        val groupedByDays = expiringIngredients.groupBy { ingredient ->
            ingredient.getDaysUntilExpire() ?: -1
        }

        // 发送通知（距离越近优先级越高）
        when {
            // 今天过期
            groupedByDays[0]?.isNotEmpty() == true -> {
                notificationHelper.sendExpiringSoonNotification(
                    groupedByDays[0]!!, 
                    0
                )
            }
            // 明天过期
            groupedByDays[1]?.isNotEmpty() == true -> {
                notificationHelper.sendExpiringSoonNotification(
                    groupedByDays[1]!!, 
                    1
                )
            }
            // 2-3天内过期
            else -> {
                val soonExpiring = expiringIngredients.filter { 
                    (it.getDaysUntilExpire() ?: 0) in 2..3 
                }
                if (soonExpiring.isNotEmpty()) {
                    notificationHelper.sendExpiringSoonNotification(soonExpiring, 3)
                }
            }
        }
    }

    /**
     * 判断是否为今天
     */
    private fun isToday(date: Date): Boolean {
        val today = Calendar.getInstance()
        val checkDate = Calendar.getInstance().apply { time = date }
        
        return today.get(Calendar.YEAR) == checkDate.get(Calendar.YEAR) &&
               today.get(Calendar.DAY_OF_YEAR) == checkDate.get(Calendar.DAY_OF_YEAR)
    }

    companion object {
        private const val WORK_NAME = "expiry_check_work"

        /**
         * 调度周期性检查
         * 每天上午9点检查一次
         */
        fun schedule(context: Context) {
            // 计算到下一个9点的时间
            val currentTime = Calendar.getInstance()
            val targetTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 9)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                
                // 如果今天9点已过，设置为明天
                if (before(currentTime)) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }

            val initialDelay = targetTime.timeInMillis - currentTime.timeInMillis

            // 创建每日检查任务
            val dailyWorkRequest = PeriodicWorkRequestBuilder<ExpiryCheckWorker>(
                1, TimeUnit.DAYS
            )
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .addTag(WORK_NAME)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                dailyWorkRequest
            )
        }

        /**
         * 立即执行一次检查
         */
        fun checkNow(context: Context) {
            val workRequest = androidx.work.OneTimeWorkRequestBuilder<ExpiryCheckWorker>()
                .addTag("immediate_check")
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }

        /**
         * 取消所有检查任务
         */
        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }

        /**
         * 检查是否已调度
         */
        fun isScheduled(context: Context): Boolean {
            val workManager = WorkManager.getInstance(context)
            val workInfos = workManager.getWorkInfosByTag(WORK_NAME).get()
            return workInfos.any { 
                it.state == androidx.work.WorkInfo.State.ENQUEUED || 
                it.state == androidx.work.WorkInfo.State.RUNNING 
            }
        }
    }
}
