package com.kadyrova.count2exam.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

object NotificationHelper {

    const val CHANNEL_ID = "exam_notifications"

    fun createNotificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "Prüfungsbenachrichtigungen",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Erinnerungen für bevorstehende Prüfungen"
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleTestAlarm(context: Context) {
        val intent = Intent(context, ExamNotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 10_000,
            pendingIntent
        )
    }
}