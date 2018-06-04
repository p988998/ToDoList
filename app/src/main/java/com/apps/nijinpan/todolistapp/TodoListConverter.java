package com.apps.nijinpan.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.apps.nijinpan.todolistapp.models.Todo;

import java.util.List;

public class TodoListConverter {
    private Context context;
    private List<Todo> data;

    public TodoListConverter (Context context, List<Todo> data){
        this.context = context;
        this.data = data;
    }

    public View getView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.main_list_item, null);
        Todo todo = data.get(position);

        ((TextView) view.findViewById(R.id.main_list_item_text)).setText(todo.text);
        return view;
    }
}
