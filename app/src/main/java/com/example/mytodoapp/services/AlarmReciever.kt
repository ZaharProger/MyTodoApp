package com.example.mytodoapp.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mytodoapp.R
import com.example.mytodoapp.activities.NotificationActivity
import com.example.mytodoapp.constants.IntentKeys
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let { handleAlarm(context, it) }
    }

    private fun handleAlarm(context: Context?, intent: Intent) {
        context?.let {
            val task = intent.getStringExtra(IntentKeys.ALARM_TASK.stringValue)?.
                let { data -> Json.decodeFromString<Task>(data) }
            val category = intent.getStringExtra(IntentKeys.ALARM_CATEGORY.stringValue)?.
                let { data -> Json.decodeFromString<Category>(data) }

            if (task != null && category != null) {
                val channelId = createNotificationChannel(it, "${task.uId}")
                createNotification(
                    it,
                    channelId,
                    task,
                    category,
                    "Необходимо выполнить задачу ${task.title}!"
                )
            }
        }
    }

    private fun createNotification(
        context: Context,
        id: String,
        task: Task,
        category: Category,
        description: String
    ) {
        val intent = Intent(context, NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(IntentKeys.CURRENT_TASK.stringValue, Json.encodeToString(task))
            putExtra(IntentKeys.CURRENT_CATEGORY.stringValue, Json.encodeToString(category))
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            task.uId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(task.title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {

                notify(System.currentTimeMillis().toInt(), builder.build())
            }
        }
    }

    private fun createNotificationChannel(context: Context, channelId: String): String {
        val channel = NotificationChannel(
            channelId,
            "ALARM",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply { description = "Alarm channel" }

        val notificationManager: NotificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        return channelId
    }

}