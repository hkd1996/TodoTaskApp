package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.darshanh.todoappdemo.MainActivity;
import com.example.darshanh.todoappdemo.R;
import com.example.darshanh.todoappdemo.RecyclerAdapter;

public class TodoTaskHelper extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;
    RecyclerView taskListRecycler;
    RecyclerAdapter recyclerAdapter;
    Cursor cursor;
    private static final String DB_NAME="todo_db";
    public static final int DB_VERSION=1;
    public static final String TABLE_NAME="todo_task";
    public static final String TASK_ID= "id";
    public static final String TASK_TITLE="task_title";
    public static final String TASK_DESCRIPTION="task";
    String CREATE_TABLE="create table todo_task(id integer primary key autoincrement,task_title text not null,task text not null)";


    public TodoTaskHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
