package com.apps.nijinpan.todolistapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.apps.nijinpan.todolistapp.AlarmReceiver;
import com.apps.nijinpan.todolistapp.TodoEditActivity;
import com.apps.nijinpan.todolistapp.models.Todo;

import java.util.Calendar;
import java.util.Date;

public class AlarmUtils {
    public static void setAlarm(Context context, Todo todo){
        Calendar c = Calendar.getInstance();
        if(todo.remindDate.compareTo(c.getTime()) < 0){
            return;
        }
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(TodoEditActivity.KEY_TODO, todo);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, todo.remindDate.getTime(), alarmIntent);
    }
}
