package com.rikin.hydrohomie.app.jobs

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rikin.hydrohomie.R
import com.rikin.hydrohomie.app.platform.MainActivity
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

interface Notifier {
  fun start()
  fun cancel()
}

@ExperimentalAnimationApi
class RealNotifier(private val appContext: Context) : Notifier {

  init {
    createReminderChannel()
  }

  override fun start() {
    WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
      DRINK_WATER_JOB_NAME,
      ExistingPeriodicWorkPolicy.REPLACE,
      PeriodicWorkRequestBuilder<NotificationWorker>(
        DRINK_WATER_JOB_REPEAT,
        TimeUnit.HOURS
      )
        .addTag(DRINK_WATER_JOB_TAG)
        .build()
    )
  }

  override fun cancel() {
    WorkManager.getInstance(appContext).cancelAllWorkByTag(DRINK_WATER_JOB_TAG)
  }

  private fun createReminderChannel() {
    val name = "Reminders"
    val descriptionText = "Reminders to drink water"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
      description = descriptionText
    }
    val notificationManager = appContext.getSystemService(NotificationManager::class.java)
    notificationManager.createNotificationChannel(channel)
  }
}

class FakeNotifier : Notifier {
  override fun start() = Unit

  override fun cancel() = Unit
}

@ExperimentalAnimationApi
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
      .setContentTitle("💧 Drink some water 💧")
      .setContentText("You look thirsty right now.")
      .setAutoCancel(true)
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .setContentIntent(pendingIntent)
      .build()
  }
}

private const val DRINK_NOTIF_ID = 1
private const val DRINK_WATER_JOB_NAME = "drink_water"
private const val DRINK_WATER_JOB_TAG = "tag_drink_water"
private const val DRINK_WATER_JOB_REPEAT = 3L
private const val CHANNEL_ID = "com.rikin.hydrohomie.REMINDER_NOTIFICATIONS"
