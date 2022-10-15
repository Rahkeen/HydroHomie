package com.rikin.hydrohomie.app.jobs

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rikin.hydrohomie.R
import com.rikin.hydrohomie.app.platform.CHANNEL_ID
import com.rikin.hydrohomie.app.platform.MainActivity
import java.time.LocalDateTime

class NotificationWorker(
  appContext: Context,
  workerParameters: WorkerParameters
) : Worker(appContext, workerParameters) {

  override fun doWork(): Result {
    val hour = LocalDateTime.now().hour
    if (hour in 9..22) {
      NotificationManagerCompat
        .from(applicationContext)
        .notify(
          DRINK_NOTIF_ID,
          buildNotification(applicationContext)
        )
    }
    return Result.success()
  }

  private fun buildNotification(context: Context): Notification {
    val pendingIntent = PendingIntent.getActivity(
      context,
      0,
      Intent(context, MainActivity::class.java),
      PendingIntent.FLAG_IMMUTABLE
    )
    return NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_nalgene)
      .setContentTitle("💧Drink some water 💧")
      .setContentText("You looking kinda thirsty.")
      .setAutoCancel(true)
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .setContentIntent(pendingIntent)
      .build()
  }
}

private const val DRINK_NOTIF_ID = 1
