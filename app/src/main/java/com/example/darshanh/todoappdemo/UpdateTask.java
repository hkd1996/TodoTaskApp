package com.example.darshanh.todoappdemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import db.TodoTaskHelper;

public class UpdateTask extends AppCompatActivity {
    EditText newTaskDesc,newTaskTitle;
    Button update;
    TodoTaskHelper todoTaskHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        newTaskDesc=(EditText)findViewById(R.id.update_task);
        newTaskTitle=(EditText)findViewById(R.id.update_title);
        Bundle task=getIntent().getExtras();

        final int taskId=task.getInt("position");
        final String task_desc=task.getString("task");
        final String task_title=task.getString("title");
        newTaskDesc.setText(task_desc);
        newTaskTitle.setText(task_title);
        update=(Button)findViewById(R.id.update_taskBtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newDesc=newTaskDesc.getText().toString().trim();
                String newTitle=newTaskTitle.getText().toString().trim();
                    todoTaskHelper=new TodoTaskHelper(UpdateTask.this);
                    sqLiteDatabase = todoTaskHelper.getWritableDatabase();
                    ContentValues cv=new ContentValues();
                    cv.put(TodoTaskHelper.TASK_DESCRIPTION,newDesc);
                    cv.put(TodoTaskHelper.TASK_TITLE,newTitle);
                    sqLiteDatabase.update(TodoTaskHelper.TABLE_NAME,cv,TodoTaskHelper.TASK_DESCRIPTION+" =?"+" and "+TodoTaskHelper.TASK_TITLE+" =?",new String[]{String.valueOf(task_desc),String.valueOf(task_title)});
                    sqLiteDatabase.close();
                    Toast.makeText(getApplicationContext(),"Updated successfully!!",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

            });

    }
}
