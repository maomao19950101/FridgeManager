package com.fridge.app.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.fridge.app.notification.NotificationHelper
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * 开机启动接收器
 * 设备重启后重新调度过期检查任务
 */
class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            "android.intent.action.QUICKBOOT_POWERON",
            "com.htc.intent.action.QUICKBOOT_POWERON" -> {
                Log.d(TAG, "Received ${intent.action}, rescheduling expiry checks")
                
                // 重新调度过期检查任务
                rescheduleExpiryChecks(context)
                
                // 发送重启完成通知（可选）
                // sendRestartNotification(context)
            }
        }
    }

    /**
     * 重新调度过期检查
     */
    private fun rescheduleExpiryChecks(context: Context) {
        try {
            // 使用WorkManager调度每日检查
            ExpiryCheckWorker.schedule(context)
            
            Log.d(TAG, "Expiry check work scheduled successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to reschedule expiry checks", e)
        }
    }

    /**
     * 发送重启完成通知（可选功能）
     */
    private fun sendRestartNotification(context: Context) {
        val notificationHelper = NotificationHelper(context)
        notificationHelper.sendGeneralNotification(
            context.getString(R.string.app_restart_title),
            context.getString(R.string.app_restart_message)
        )
    }

    companion object {
        private const val TAG = "BootCompletedReceiver"
    }
}
