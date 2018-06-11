package com.apps.nijinpan.todolistapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.apps.nijinpan.todolistapp.AlarmReceiver;
import com.apps.nijinpan.todolistapp.TodoEditActivity;
import com.apps.nijinpan.todolistapp.models.Todo;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlarmUtils {
    public static void setAlarm(Context context, Todo todo){
        Calendar c = Calendar.getInstance();
        if(todo.remindDate.compareTo(c.getTime()) < 0){
            return;
        }
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        //intent.putExtra(TodoEditActivity.KEY_TODO, todo); this has bug, Parcelable intent will lose in alarmMgr

        String jString = ModelUtils.toString(todo, new TypeToken<Todo>(){});
        intent.putExtra(TodoEditActivity.KEY_TODO, jString);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, todo.remindDate.getTime(), alarmIntent);
    }
}
