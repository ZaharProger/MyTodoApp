package com.example.mytodoapp.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.mytodoapp.constants.IntentKeys
import com.example.mytodoapp.entities.db.Task
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.random.Random

class Alarm(context: Context) {
    private val _context = context
    private val alarmManager = context
        .getSystemService(Context.ALARM_SERVICE) as? AlarmManager?

    fun set(data: Task, fireTime: Long) {
        val alarmIntent = PendingIntent.getBroadcast(
            _context,
            Random.nextInt(1, Int.MAX_VALUE),
            Intent(_context, AlarmReceiver::class.java).apply {
                putExtra(IntentKeys.ALARM_TASK.stringValue, Json.encodeToString(data))
                action = "com.example.mytodoapp.alarm"
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            fireTime,
            alarmIntent
        )
    }
}