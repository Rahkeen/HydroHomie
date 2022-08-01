package com.rikin.hydrohomie.app.jobs

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rikin.hydrohomie.R
import com.rikin.hydrohomie.app.platform.CHANNEL_ID
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
        .notify(DRINK_NOTIF_ID, buildNotification(applicationContext))
    }
    return Result.success()
  }

  private fun buildNotification(context: Context): Notification {
    return NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_drink_water)
      .setContentTitle("Time to drink")
      .setContentText("Chug chug chug chug")
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .build()
  }
}

private const val DRINK_NOTIF_ID = 1
