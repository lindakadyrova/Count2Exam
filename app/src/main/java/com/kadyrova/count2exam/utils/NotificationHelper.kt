package com.kadyrova.count2exam.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.ZoneId

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
        scheduleAlarm(
            context = context,
            requestCode = 1234,
            triggerTime = System.currentTimeMillis() + 10_000,
            examSubject = "Test Prüfung",
            hoursBefore = 0
        )
    }

    private fun scheduleAlarm(
        context: Context,
        requestCode: Int,
        triggerTime: Long,
        examSubject: String,
        hoursBefore: Int
    ) {
        if (triggerTime <= System.currentTimeMillis()) return

        val intent = Intent(context, ExamNotificationReceiver::class.java).apply {
            putExtra("examSubject", examSubject)
            putExtra("hoursBefore", hoursBefore)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val alarmInfo = AlarmManager.AlarmClockInfo(triggerTime, pendingIntent)
                alarmManager.setAlarmClock(alarmInfo, pendingIntent)
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }

    fun scheduleExamReminder(
        context: Context,
        examId: String,
        examSubject: String,
        examDate: String
    ) {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val examLocalDate = LocalDate.parse(examDate, formatter)
        val examDateTime = examLocalDate
            .atTime(8, 0)
            .atZone(ZoneId.systemDefault())

        val reminder72h = examDateTime.minusHours(72).toInstant().toEpochMilli()
        val reminder24h = examDateTime.minusHours(24).toInstant().toEpochMilli()
        val testReminder = System.currentTimeMillis() + 10_000 // In 10 Sekunden zum Testen

        scheduleAlarm(
            context = context,
            requestCode = examId.hashCode() + 999,
            triggerTime = testReminder,
            examSubject = examSubject,
            hoursBefore = 0
        )

        scheduleAlarm(
            context = context,
            requestCode = examId.hashCode() + 72,
            triggerTime = reminder72h,
            examSubject = examSubject,
            hoursBefore = 72
        )

        scheduleAlarm(
            context = context,
            requestCode = examId.hashCode() + 24,
            triggerTime = reminder24h,
            examSubject = examSubject,
            hoursBefore = 24
        )
    }
}