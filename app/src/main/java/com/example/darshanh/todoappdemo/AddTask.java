package com.example.darshanh.todoappdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import db.TodoTaskHelper;

public class AddTask extends AppCompatActivity {
    EditText task,taskTitle;
    Button addTaskBtn;
    TodoTaskHelper todoTaskHelper;
    SQLiteDatabase sqLiteDatabase;
    TimePicker timePicker;
    int hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        task=(EditText)findViewById(R.id.taskDetails);
        taskTitle=(EditText)findViewById(R.id.taskTitle);
        addTaskBtn=(Button)findViewById(R.id.addTaskBtn);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String input=task.getText().toString().trim();
                String title=taskTitle.getText().toString();
                if(TextUtils.isEmpty(input)){
                    Toast.makeText(getApplicationContext(),"Task Description Empty!!",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(title)){
                    Toast.makeText(getApplicationContext(),"Task Title Empty!!",Toast.LENGTH_LONG).show();
                }
                else{
                    addToDB(title,input);
                    timePicker=(TimePicker)findViewById(R.id.time);
                    hour=timePicker.getHour();
                    minute=timePicker.getMinute();
                    Calendar alarmTime= Calendar.getInstance();
                    alarmTime.set(Calendar.HOUR_OF_DAY,hour);
                    alarmTime.set(Calendar.MINUTE,minute);
                    alarmTime.set(Calendar.SECOND,0);
                    Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                    final int _id = (int) System.currentTimeMillis();
                    intent.putExtra("task_title",taskTitle.getText().toString());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),_id , intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC,alarmTime.getTimeInMillis(), pendingIntent);
                    Intent intent1=new Intent(AddTask.this,MainActivity.class);
                    startActivity(intent1);
                }

            }
        });

    }

    private void addToDB(String title,String input) {
            todoTaskHelper=new TodoTaskHelper(AddTask.this);
            sqLiteDatabase = todoTaskHelper.getWritableDatabase();
            ContentValues task = new ContentValues();
            task.clear();
            task.put(TodoTaskHelper.TASK_TITLE,title);
            task.put(TodoTaskHelper.TASK_DESCRIPTION,input);
            sqLiteDatabase.insert(TodoTaskHelper.TABLE_NAME,null,task);
            Toast.makeText(this,"Task Added Sucessfully!!",Toast.LENGTH_LONG).show();

        }

}
