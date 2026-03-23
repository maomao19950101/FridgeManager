package com.fridge.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.fridge.app.notification.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FridgeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // 过期提醒通道
            val expiryChannel = NotificationChannel(
                NotificationHelper.CHANNEL_EXPIRED,
                getString(R.string.channel_expired_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.channel_expired_description)
                enableVibration(true)
                enableLights(true)
            }

            // 一般通知通道
            val generalChannel = NotificationChannel(
                NotificationHelper.CHANNEL_GENERAL,
                getString(R.string.channel_general_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.channel_general_description)
            }

            notificationManager.createNotificationChannels(listOf(expiryChannel, generalChannel))
        }
    }

    companion object {
        lateinit var instance: FridgeApplication
            private set
    }
}
