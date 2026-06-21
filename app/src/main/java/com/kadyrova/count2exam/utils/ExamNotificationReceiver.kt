package com.kadyrova.count2exam.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class ExamNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val examSubject = intent.getStringExtra("examSubject") ?: "Prüfung"
        val hoursBefore = intent.getIntExtra("hoursBefore", 0)

        val contentText = if (hoursBefore > 0) {
            "Deine Prüfung '$examSubject' beginnt in $hoursBefore Stunden!"
        } else {
            "Deine Prüfung '$examSubject' steht an!"
        }

        val notification = NotificationCompat.Builder(
            context,
            NotificationHelper.CHANNEL_ID
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Prüfungserinnerung")
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        val notificationId = examSubject.hashCode() + hoursBefore
        notificationManager.notify(
            notificationId,
            notification
        )
    }
}
