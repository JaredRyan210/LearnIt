package com.hfad.finalproject_team_temp
import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
class alarm : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            createNotificationChannel(it)
            showNotification(it)
        }
    }
    private fun createNotificationChannel(context: Context) {
        val channelName = "alarm"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("alarm", channelName, importance).apply {
            description = "Take a quiz!"
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification(context: Context) {
        val notificationBuilder = NotificationCompat.Builder(context, "alarm")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Don't forget to learn today!")
            .setContentText("It's been 24 hours since you've opened the app. Try taking a quiz quiz!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission if not already granted
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )

        } else {
            // if permission has been granted, show notification
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.notify(1, notificationBuilder.build())
        }
    }
}