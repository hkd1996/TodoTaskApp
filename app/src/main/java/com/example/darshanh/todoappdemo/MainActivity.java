package com.example.darshanh.todoappdemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import db.TodoTaskHelper;

public class MainActivity extends AppCompatActivity {
    Button addNewTask,viewTasks;
    Cursor cursor;
    RecyclerView taskListRecycler;
    RecyclerAdapter recyclerAdapter;
    TodoTaskHelper todoTaskHelper;
    SQLiteDatabase sqLiteDatabase;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewTask=(Button)findViewById(R.id.addTask);
        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddTask.class);
                startActivity(intent);
            }
        });
        taskListRecycler=(RecyclerView)findViewById(R.id.taskListRv);
        taskListRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);

            }
        });


        taskListRecycler=(RecyclerView)findViewById(R.id.taskListRv);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        taskListRecycler.setHasFixedSize(true);
        taskListRecycler.setLayoutManager(layoutManager);
        todoTaskHelper=new TodoTaskHelper(MainActivity.this);
        taskListRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        sqLiteDatabase = todoTaskHelper.getReadableDatabase();
        cursor=sqLiteDatabase.rawQuery("select * from "+TodoTaskHelper.TABLE_NAME,null);
        recyclerAdapter=new RecyclerAdapter(cursor,getApplicationContext());
        taskListRecycler.setAdapter(recyclerAdapter);
        ItemTouchHelper.Callback itemTouchCallback=new ItemSwipeHelper(Direction.LEFT,recyclerAdapter);
        ItemTouchHelper swipeToDismissTouchHelper =new ItemTouchHelper(itemTouchCallback);
        swipeToDismissTouchHelper.attachToRecyclerView(taskListRecycler);
        /*cursor=sqLiteDatabase.rawQuery("select * from "+TodoTaskHelper.TABLE_NAME,null);
        recyclerAdapter=new RecyclerAdapter(cursor,getApplicationContext());
        taskListRecycler.setAdapter(recyclerAdapter);*/

     }


}
