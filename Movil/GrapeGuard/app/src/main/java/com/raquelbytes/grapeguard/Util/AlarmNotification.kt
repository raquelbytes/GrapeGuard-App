package com.raquelbytes.grapeguard.Util

import com.raquelbytes.grapeguard.Controller.AddTaskDialogFragment
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.raquelbytes.grapeguard.Controller.AddTaskDialogFragment.Companion.MY_CHANNEL_ID
import com.raquelbytes.grapeguard.R
import java.util.*

class AlarmNotification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val tarea = intent?.getStringExtra("TAREA") ?: return
        createSimpleNotification(context, tarea)
        Log.d("AlarmNotification", "Notificación recibida con tarea: $tarea")
    }

    private fun createSimpleNotification(context: Context, tarea: String) {
        val intent = Intent(context, AddTaskDialogFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val notificationId = generateNotificationId() // Genera un ID de notificación único

        // Obtén el texto desde los recursos de strings
        val content = context.getString(R.string.notification_content)

        val notification = NotificationCompat.Builder(context, MY_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle(tarea)
            .setContentText(tarea)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(content + tarea)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification) // Utiliza el ID de notificación generado
    }


    // Genera un ID de notificación único basado en la hora actual
    private fun generateNotificationId(): Int {
        return Calendar.getInstance().timeInMillis.toInt()
    }
}
