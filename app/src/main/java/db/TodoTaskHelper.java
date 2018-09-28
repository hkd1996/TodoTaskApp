package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TodoTaskHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="todo_db";
    public static final int DB_VERSION=1;
    public static final String TABLE_NAME="todo_task";
    public static final String TASK_ID= BaseColumns._ID;
    public static final String TASK_DESCRIPTION="task";
    String CREATE_TABLE="create table todo_task(id integer primary key autoincrement,task text not null)";


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
