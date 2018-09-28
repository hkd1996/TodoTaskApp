package com.example.darshanh.todoappdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import db.TodoTaskHelper;

public class AddTask extends AppCompatActivity {
    EditText task;
    Button addTaskBtn;
    TodoTaskHelper todoTaskHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        task=(EditText)findViewById(R.id.taskDetails);
        addTaskBtn=(Button)findViewById(R.id.addTaskBtn);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDB();
            }
        });

    }

    private void addToDB() {
        String input=task.getText().toString();
        if(TextUtils.isEmpty(input)){
            Toast.makeText(getApplicationContext(),"Task Details Empty!!",Toast.LENGTH_LONG).show();
        }
        else{
            todoTaskHelper=new TodoTaskHelper(AddTask.this);
            sqLiteDatabase = todoTaskHelper.getWritableDatabase();
            ContentValues task = new ContentValues();
            task.clear();
            task.put(TodoTaskHelper.TASK_DESCRIPTION,input);
            sqLiteDatabase.insert(TodoTaskHelper.TABLE_NAME,null,task);
            Toast.makeText(this,"Task Added Sucessfully!!",Toast.LENGTH_LONG);

        }
    }
}
