package com.apps.nijinpan.todolistapp;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.apps.nijinpan.todolistapp.models.Todo;
import com.apps.nijinpan.todolistapp.utils.UIUtils;

import java.util.List;

public class TodoListAdapter extends BaseAdapter {
    private MainActivity activity;
    private List<Todo> data;

    public TodoListAdapter(@NonNull MainActivity activity, List<Todo> data) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.main_list_item, parent, false);

            vh = new ViewHolder();
            vh.todoText = (TextView) convertView.findViewById(R.id.main_list_item_text);
            vh.doneCheckBox = (CheckBox) convertView.findViewById(R.id.main_list_item_check);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        final Todo todo = (Todo) getItem(position);

        vh.todoText.setText(todo.text);
        vh.doneCheckBox.setChecked(todo.done);
        //UIUtils.setTextViewStrikeThrough(vh.todoText, todo.done);

        convertView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, TodoEditActivity.class);
                intent.putExtra(TodoEditActivity.KEY_TODO, todo);
                activity.startActivityForResult(intent, MainActivity.REQ_CODE_TODO_EDIT);

            }
        });
        return convertView;
    }
    private static class ViewHolder{
        TextView todoText;
        CheckBox doneCheckBox;
    }
}
