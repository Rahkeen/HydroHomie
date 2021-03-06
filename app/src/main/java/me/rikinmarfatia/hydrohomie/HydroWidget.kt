package me.rikinmarfatia.hydrohomie

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import me.rikinmarfatia.hydrohomie.domain.WaterKrate

class HydroWidget : AppWidgetProvider() {

    companion object {
        const val TAG = "HydroHomieWidget"
        const val ACTION_UPDATE_COUNT = "ACTION_UPDATE_COUNT"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d(TAG, "Updated")
        appWidgetIds.forEach { appWidgetId ->
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    // We also override onReceive to update since just onUpdate() isn't very reliable
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d(TAG, "Received: ${intent.action}")

        if (ACTION_UPDATE_COUNT == intent.action) {

            val views: RemoteViews = renderWidgetViews(context)

            val appWidget = ComponentName(context, HydroWidget::class.java)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            appWidgetManager.updateAppWidget(appWidget, views)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val pendingIntent: PendingIntent = Intent(context, MainActivity::class.java).let { intent ->
            PendingIntent.getActivity(context, 0, intent, 0)
        }

        val views = renderWidgetViews(context).also {
            setWidgetClickAction(it, pendingIntent)
        }

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun renderWidgetViews(
        context: Context,
        waterStore: WaterKrate = WaterKrate(context)
    ): RemoteViews {
        val count = waterStore.count
        val goal = waterStore.goal

        val display = waterDisplay(count, goal, context)
        val fillDrawable = waterFillDrawable(count, goal)

        return RemoteViews(
            context.packageName,
            R.layout.hydro_widget
        ).apply {
            setTextViewText(R.id.widget_goal_display, display)
            setImageViewResource(R.id.widget_water_fill_level, fillDrawable)
        }
    }

    private fun setWidgetClickAction(widgetViews: RemoteViews, pendingIntent: PendingIntent) {
        widgetViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent)
    }

    private fun waterDisplay(count: Int, goal: Int, context: Context): String {
        return when (count) {
            0 -> context.resources.getString(R.string.widget_count_empty)
            goal -> context.resources.getString(R.string.widget_count_full)
            else -> {
                String.format(
                    context.resources.getString(R.string.widget_count_display),
                    count,
                    goal
                )
            }
        }
    }

    private fun waterFillDrawable(count: Int, goal: Int): Int {
        val percentCompletion = count.toFloat() / goal
        return when {
            percentCompletion == 0F -> R.drawable.widget_water_fill_none
            percentCompletion <= .25 -> R.drawable.widget_water_fill_some
            percentCompletion <= .5 -> R.drawable.widget_water_fill_half
            percentCompletion < 1F -> R.drawable.widget_water_fill_most
            else -> R.drawable.widget_water_fill_full
        }
    }
}
