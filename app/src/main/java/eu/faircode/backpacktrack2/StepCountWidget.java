package eu.faircode.backpacktrack2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Date;

public class StepCountWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        DatabaseHelper dh = null;
        try {
            dh = new DatabaseHelper(context);
            int count = dh.getStepCount(new Date().getTime());

            Intent riSettings = new Intent(context, ActivitySettings.class);
            riSettings.setAction(Intent.ACTION_MAIN);
            riSettings.addCategory(Intent.CATEGORY_LAUNCHER);
            riSettings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent piSettings = PendingIntent.getActivity(context, 1, riSettings, PendingIntent.FLAG_UPDATE_CURRENT);

            for (int i = 0; i < appWidgetIds.length; i++) {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stepcount_widget);
                views.setTextViewText(R.id.appwidget_text, Integer.toString(count));
                views.setOnClickPendingIntent(R.id.appwidget_text, piSettings);
                appWidgetManager.updateAppWidget(appWidgetIds[i], views);
            }
        } finally {
            if (dh != null)
                dh.close();
        }
    }
}
