package com.example.darshanh.todoappdemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import db.TodoTaskHelper;

import static db.TodoTaskHelper.TABLE_NAME;
import static db.TodoTaskHelper.TASK_DESCRIPTION;
import static db.TodoTaskHelper.TASK_ID;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskViewHolder> implements ItemTouchHelperAdapter,View.OnClickListener {

    Cursor cursor;
    Context context;
    private OnItemClickListener clickListener;



    public RecyclerAdapter(Cursor cursor,Context context) {
        this.cursor = cursor;
        this.context=context;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_with_delete,viewGroup,false);
        TaskViewHolder taskViewHolder=new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, final int i) {
        cursor.moveToPosition(i);
        final String title=cursor.getString(1);
        final String text=cursor.getString(2);
        taskViewHolder.task_Title.setText(title);
        taskViewHolder.task_list.setText(text);
        taskViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,UpdateTask.class);
                intent.putExtra("position",i);
                intent.putExtra("task",text);
                intent.putExtra("title",title);
                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public void swipeToDelete(int position) {
        cursor.moveToPosition(position);
        String task=cursor.getString(0);
        TodoTaskHelper todoTaskHelper=new TodoTaskHelper(context);
        SQLiteDatabase sqLiteDatabase = todoTaskHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+TASK_ID+"='"+task+"'");
        cursor=sqLiteDatabase.rawQuery("select * from "+TodoTaskHelper.TABLE_NAME,null);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,cursor.getCount());
        sqLiteDatabase.close();
    }

    @Override
    public void onClick(View view) {
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView task_list,task_Title;

        public LinearLayout parentLayout;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task_list=itemView.findViewById(R.id.task_list);
            task_Title=itemView.findViewById(R.id.task_title);
            parentLayout=itemView.findViewById(R.id.view_foreground);
        }
    }
}
