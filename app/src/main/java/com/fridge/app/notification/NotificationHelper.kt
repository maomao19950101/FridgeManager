package com.fridge.app.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.fridge.app.MainActivity
import com.fridge.app.R
import com.fridge.app.data.model.Ingredient
import com.fridge.app.ui.activity.IngredientDetailActivity

/**
 * 通知工具类
 * 管理应用通知渠道和发送通知
 */
class NotificationHelper(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannels()
    }

    /**
     * 创建通知渠道（Android 8.0+）
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                createChannel(
                    CHANNEL_EXPIRED,
                    context.getString(R.string.channel_expired_name),
                    context.getString(R.string.channel_expired_description),
                    NotificationManager.IMPORTANCE_HIGH
                ),
                createChannel(
                    CHANNEL_EXPIRING_SOON,
                    context.getString(R.string.channel_expiring_name),
                    context.getString(R.string.channel_expiring_description),
                    NotificationManager.IMPORTANCE_DEFAULT
                ),
                createChannel(
                    CHANNEL_GENERAL,
                    context.getString(R.string.channel_general_name),
                    context.getString(R.string.channel_general_description),
                    NotificationManager.IMPORTANCE_LOW
                )
            )

            channels.forEach { notificationManager.createNotificationChannel(it) }
        }
    }

    private fun createChannel(
        channelId: String,
        name: String,
        description: String,
        importance: Int
    ): NotificationChannel {
        return NotificationChannel(channelId, name, importance).apply {
            this.description = description
            
            // 设置声音
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            
            setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI, audioAttributes)
            
            // 设置震动模式
            if (importance == NotificationManager.IMPORTANCE_HIGH) {
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 200, 500)
            }
            
            // 设置LED灯
            enableLights(true)
            lightColor = context.getColor(R.color.primary)
        }
    }

    /**
     * 发送过期食材通知
     */
    fun sendExpiredNotification(ingredients: List<Ingredient>) {
        if (ingredients.isEmpty()) return

        val title = context.getString(R.string.notification_expired_title)
        val content = if (ingredients.size == 1) {
            context.getString(R.string.notification_expired_single, ingredients[0].name)
        } else {
            context.getString(R.string.notification_expired_multiple, ingredients.size)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MainActivity.EXTRA_TAB_INDEX, 0) // 打开食材页
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_CODE_EXPIRED,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = buildNotification(
            channelId = CHANNEL_EXPIRED,
            title = title,
            content = content,
            pendingIntent = pendingIntent,
            priority = NotificationCompat.PRIORITY_HIGH,
            category = NotificationCompat.CATEGORY_ALARM
        )

        notify(NOTIFICATION_ID_EXPIRED, notification)
    }

    /**
     * 发送即将过期通知
     */
    fun sendExpiringSoonNotification(ingredients: List<Ingredient>, days: Int) {
        if (ingredients.isEmpty()) return

        val title = context.getString(R.string.notification_expiring_title, days)
        val content = if (ingredients.size == 1) {
            val daysLeft = ingredients[0].getDaysUntilExpire() ?: 0
            context.getString(
                R.string.notification_expiring_single,
                ingredients[0].name,
                daysLeft
            )
        } else {
            context.getString(R.string.notification_expiring_multiple, ingredients.size)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MainActivity.EXTRA_TAB_INDEX, 0)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_CODE_EXPIRING,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // 构建展开式通知
        val inboxStyle = NotificationCompat.InboxStyle()
            .setBigContentTitle(title)
            .setSummaryText(content)

        ingredients.take(5).forEach { ingredient ->
            val daysLeft = ingredient.getDaysUntilExpire() ?: 0
            val line = if (daysLeft == 0) {
                "• ${ingredient.name} (${context.getString(R.string.expires_today)})"
            } else {
                "• ${ingredient.name} (${context.getString(R.string.days_left, daysLeft)})"
            }
            inboxStyle.addLine(line)
        }

        if (ingredients.size > 5) {
            inboxStyle.addLine(context.getString(R.string.and_more, ingredients.size - 5))
        }

        val notification = buildNotification(
            channelId = CHANNEL_EXPIRING_SOON,
            title = title,
            content = content,
            pendingIntent = pendingIntent,
            priority = NotificationCompat.PRIORITY_DEFAULT,
            category = NotificationCompat.CATEGORY_REMINDER,
            style = inboxStyle
        )

        notify(NOTIFICATION_ID_EXPIRING, notification)
    }

    /**
     * 发送单个食材过期通知
     */
    fun sendSingleExpiredNotification(ingredient: Ingredient) {
        val title = context.getString(R.string.notification_item_expired)
        val content = context.getString(R.string.notification_item_expired_content, ingredient.name)

        val intent = Intent(context, IngredientDetailActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(IngredientDetailActivity.EXTRA_INGREDIENT_ID, ingredient.id)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            ingredient.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = buildNotification(
            channelId = CHANNEL_EXPIRED,
            title = title,
            content = content,
            pendingIntent = pendingIntent,
            priority = NotificationCompat.PRIORITY_HIGH,
            category = NotificationCompat.CATEGORY_ALARM
        )

        notify(ingredient.id.toInt(), notification)
    }

    /**
     * 发送通用通知
     */
    fun sendGeneralNotification(title: String, content: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_CODE_GENERAL,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = buildNotification(
            channelId = CHANNEL_GENERAL,
            title = title,
            content = content,
            pendingIntent = pendingIntent,
            priority = NotificationCompat.PRIORITY_LOW,
            category = NotificationCompat.CATEGORY_STATUS
        )

        notify(NOTIFICATION_ID_GENERAL, notification)
    }

    /**
     * 构建通知
     */
    private fun buildNotification(
        channelId: String,
        title: String,
        content: String,
        pendingIntent: PendingIntent,
        priority: Int,
        category: String,
        style: NotificationCompat.Style? = null
    ): android.app.Notification {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(priority)
            .setCategory(category)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setColor(context.getColor(R.color.primary))
            .setStyle(style)
            .apply {
                if (priority == NotificationCompat.PRIORITY_HIGH) {
                    setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
                }
            }
            .build()
    }

    /**
     * 显示通知
     */
    private fun notify(id: Int, notification: android.app.Notification) {
        try {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                NotificationManagerCompat.from(context).notify(id, notification)
            }
        } catch (e: SecurityException) {
            // 通知权限被拒绝
            e.printStackTrace()
        }
    }

    /**
     * 取消指定ID的通知
     */
    fun cancelNotification(id: Int) {
        notificationManager.cancel(id)
    }

    /**
     * 取消所有通知
     */
    fun cancelAllNotifications() {
        notificationManager.cancelAll()
    }

    /**
     * 检查通知权限
     */
    fun areNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    companion object {
        // 通知渠道ID
        const val CHANNEL_EXPIRED = "channel_expired"
        const val CHANNEL_EXPIRING_SOON = "channel_expiring_soon"
        const val CHANNEL_GENERAL = "channel_general"

        // 通知ID
        const val NOTIFICATION_ID_EXPIRED = 1001
        const val NOTIFICATION_ID_EXPIRING = 1002
        const val NOTIFICATION_ID_GENERAL = 1003

        // PendingIntent Request Code
        const val REQUEST_CODE_EXPIRED = 2001
        const val REQUEST_CODE_EXPIRING = 2002
        const val REQUEST_CODE_GENERAL = 2003
    }
}
