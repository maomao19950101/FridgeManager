package com.fridge.app.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.fridge.app.MainActivity;
import com.fridge.app.R;
import com.fridge.app.data.model.Ingredient;
import com.fridge.app.ui.activity.IngredientDetailActivity;

/**
 * 通知工具类
 * 管理应用通知渠道和发送通知
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 +2\u00020\u0001:\u0001+B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJD\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\f2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u0012J(\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u0012H\u0002J\b\u0010\u001f\u001a\u00020\u0017H\u0002J\u0018\u0010 \u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\nH\u0002J\u0014\u0010\"\u001a\u00020\u00172\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$J\u001c\u0010&\u001a\u00020\u00172\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010\'\u001a\u00020\u0012J\u0016\u0010(\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u000e\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020%R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/fridge/app/notification/NotificationHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "notificationManager", "Landroid/app/NotificationManager;", "areNotificationsEnabled", "", "buildNotification", "Landroid/app/Notification;", "channelId", "", "title", "content", "pendingIntent", "Landroid/app/PendingIntent;", "priority", "", "category", "style", "Landroidx/core/app/NotificationCompat$Style;", "cancelAllNotifications", "", "cancelNotification", "id", "createChannel", "Landroid/app/NotificationChannel;", "name", "description", "importance", "createNotificationChannels", "notify", "notification", "sendExpiredNotification", "ingredients", "", "Lcom/fridge/app/data/model/Ingredient;", "sendExpiringSoonNotification", "days", "sendGeneralNotification", "sendSingleExpiredNotification", "ingredient", "Companion", "app_debug"})
public final class NotificationHelper {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.app.NotificationManager notificationManager = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_EXPIRED = "channel_expired";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_EXPIRING_SOON = "channel_expiring_soon";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_GENERAL = "channel_general";
    public static final int NOTIFICATION_ID_EXPIRED = 1001;
    public static final int NOTIFICATION_ID_EXPIRING = 1002;
    public static final int NOTIFICATION_ID_GENERAL = 1003;
    public static final int REQUEST_CODE_EXPIRED = 2001;
    public static final int REQUEST_CODE_EXPIRING = 2002;
    public static final int REQUEST_CODE_GENERAL = 2003;
    @org.jetbrains.annotations.NotNull()
    public static final com.fridge.app.notification.NotificationHelper.Companion Companion = null;
    
    public NotificationHelper(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * 创建通知渠道（Android 8.0+）
     */
    private final void createNotificationChannels() {
    }
    
    private final android.app.NotificationChannel createChannel(java.lang.String channelId, java.lang.String name, java.lang.String description, int importance) {
        return null;
    }
    
    /**
     * 发送过期食材通知
     */
    public final void sendExpiredNotification(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.Ingredient> ingredients) {
    }
    
    /**
     * 发送即将过期通知
     */
    public final void sendExpiringSoonNotification(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.Ingredient> ingredients, int days) {
    }
    
    /**
     * 发送单个食材过期通知
     */
    public final void sendSingleExpiredNotification(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Ingredient ingredient) {
    }
    
    /**
     * 发送通用通知
     */
    public final void sendGeneralNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String content) {
    }
    
    /**
     * 构建通知
     */
    private final android.app.Notification buildNotification(java.lang.String channelId, java.lang.String title, java.lang.String content, android.app.PendingIntent pendingIntent, int priority, java.lang.String category, androidx.core.app.NotificationCompat.Style style) {
        return null;
    }
    
    /**
     * 显示通知
     */
    private final void notify(int id, android.app.Notification notification) {
    }
    
    /**
     * 取消指定ID的通知
     */
    public final void cancelNotification(int id) {
    }
    
    /**
     * 取消所有通知
     */
    public final void cancelAllNotifications() {
    }
    
    /**
     * 检查通知权限
     */
    public final boolean areNotificationsEnabled() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/fridge/app/notification/NotificationHelper$Companion;", "", "()V", "CHANNEL_EXPIRED", "", "CHANNEL_EXPIRING_SOON", "CHANNEL_GENERAL", "NOTIFICATION_ID_EXPIRED", "", "NOTIFICATION_ID_EXPIRING", "NOTIFICATION_ID_GENERAL", "REQUEST_CODE_EXPIRED", "REQUEST_CODE_EXPIRING", "REQUEST_CODE_GENERAL", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}