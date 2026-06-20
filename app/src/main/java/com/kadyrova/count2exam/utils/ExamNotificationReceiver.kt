package com.kadyrova.count2exam.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ExamNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(
            context,
            "Prüfungserinnerung ausgelöst",
            Toast.LENGTH_LONG
        ).show()
    }
}