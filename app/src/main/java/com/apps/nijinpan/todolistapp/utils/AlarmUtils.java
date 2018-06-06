package com.apps.nijinpan.todolistapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.apps.nijinpan.todolistapp.AlarmReceiver;

import java.util.Calendar;
import java.util.Date;

public class AlarmUtils {
    public static void setAlarm(Context context, Date date){
        Calendar c = Calendar.getInstance();
        if(date.compareTo(c.getTime()) < 0){
            return;
        }
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), alarmIntent);
    }
}
