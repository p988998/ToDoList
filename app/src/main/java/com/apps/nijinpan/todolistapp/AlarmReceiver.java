package com.apps.nijinpan.todolistapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.apps.nijinpan.todolistapp.models.Todo;

import java.util.Calendar;

import static android.provider.Settings.Global.getString;


public class AlarmReceiver extends BroadcastReceiver {
    public static final String NOTIFY_TODO = "notification_todo";
    @Override
    public void onReceive(Context context, Intent intent) {
        final int notificationId = 100;
        Todo todo = intent.getParcelableExtra(TodoEditActivity.KEY_TODO);

        if (todo ==null){
            todo = new Todo("test intent", Calendar.getInstance().getTime());
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, TodoEditActivity.KEY_TODO)
                .setSmallIcon(R.drawable.ic_adb_white_24dp)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(todo.text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                ;

        Intent resultIntent = new Intent(context, TodoEditActivity.class);
        resultIntent.putExtra(NOTIFY_TODO, todo);
        //resultIntent.putExtra(TodoEditActivity.KEY_NOTIFICATION_ID, notificationId);

//        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
//                0,
//                resultIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);
       // NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       // nm.notify(notificationId, builder.build());

        createNotificationChannel(context);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());

    }
    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(TodoEditActivity.KEY_TODO, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
