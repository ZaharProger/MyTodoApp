package com.example.mytodoapp.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.mytodoapp.constants.IntentKeys
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Alarm(context: Context) {
    private val _context = context
    private val alarmManager = context
        .getSystemService(Context.ALARM_SERVICE) as? AlarmManager?

    fun set(task: Task, category: Category, fireTime: Long) {
        val alarmIntent = PendingIntent.getBroadcast(
            _context,
            task.uId.toInt(),
            Intent(_context, AlarmReceiver::class.java).apply {
                putExtra(IntentKeys.ALARM_TASK.stringValue, Json.encodeToString(task))
                putExtra(IntentKeys.ALARM_CATEGORY.stringValue, Json.encodeToString(category))

                action = "com.example.mytodoapp.alarm"
            },
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        alarmManager?.let {
            it.cancel(alarmIntent)
            it.set(
                AlarmManager.RTC_WAKEUP,
                fireTime,
                alarmIntent
            )
        }
    }
}