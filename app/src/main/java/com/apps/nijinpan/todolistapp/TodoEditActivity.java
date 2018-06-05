package com.apps.nijinpan.todolistapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.apps.nijinpan.todolistapp.models.Todo;
import com.apps.nijinpan.todolistapp.utils.DateUtils;
import com.apps.nijinpan.todolistapp.utils.UIUtils;

import java.util.Date;

public class TodoEditActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    public static final String KEY_TODO = "todo";
    public static final String KEY_TODO_ID = "todo_id";

    private EditText todoEdit;
    private TextView dateTv;
    private TextView timeTv;
    private CheckBox completeCb;

    private Todo todo;
    private Date remindDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        todo = getIntent().getParcelableExtra(KEY_TODO);
        remindDate = todo != null
                ? todo.remindDate
                : null;

        setupUI();
    }



    private void setupActionbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        setTitle(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
    private void setupUI() {
        setContentView(R.layout.activity_todo_edit);
        setupActionbar();

        todoEdit = (EditText) findViewById(R.id.toto_detail_todo_edit);
        dateTv = (TextView) findViewById(R.id.todo_detail_date);
        timeTv = (TextView) findViewById(R.id.todo_detail_time);
        completeCb = (CheckBox) findViewById(R.id.todo_detail_complete);

        if (todo != null) {
            todoEdit.setText(todo.text);
            //UIUtils.setTextViewStrikeThrough(todoEdit, todo.done);
            completeCb.setChecked(todo.done);

            findViewById(R.id.todo_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete();
                }
            });
        } else {
            findViewById(R.id.todo_delete).setVisibility(View.GONE);
        }

        if (remindDate != null) {
            dateTv.setText(DateUtils.dateToStringDate(remindDate));
            timeTv.setText(DateUtils.dateToStringTime(remindDate));
        } else {
            dateTv.setText(R.string.set_date);
            timeTv.setText(R.string.set_time);
        }

        setupSaveButton();

    }

    private void setupSaveButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.todo_detail_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAndExit();
            }
        });
    }

    private void saveAndExit() {
        if (todo == null){
            todo = new Todo(todoEdit.getText().toString(), remindDate);
        }else{
            todo.text = todoEdit.getText().toString();
            todo.remindDate = remindDate;
        }
        todo.done = completeCb.isChecked();

        Intent result = new Intent();
        result.putExtra(KEY_TODO, todo);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
