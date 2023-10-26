package kelompok7.msibfinalproject1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DB_TASK";
    private static final int DATABASE_VERSION = 1 ;
    private static final String TABLE_TASK = "TASK";
    private static final String KEY_ID = "id";
    private static final String TASK_NAME = "task_name";

    public SQLiteDatabaseHandler(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreateSql = " CREATE TABLE "+TABLE_TASK+" ( "+
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TASK_NAME + " TEXT) ";
        db.execSQL(tableCreateSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_TASK);
        onCreate(db);
    }
    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_NAME,task.getTaskName());

        db.insert(TABLE_TASK, null, values);
        db.close();

    }

    public List<Task> getAllTask(){
        List<Task> taskList = new ArrayList<>();
        //
        String queryAll = " SELECT * FROM "+TABLE_TASK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryAll, null);

        if(cursor.moveToFirst()){
            do{
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTaskName(cursor.getString(1));


                taskList.add(task);
            }
            while (cursor.moveToNext());
        }
        //
        return taskList;
    }
    public void deleteTask(Task task ){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, KEY_ID +" = ? ", new String[]{String.valueOf(task.getId())});
        db.close();
    }
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_NAME, task.getTaskName());

        return db.update(TABLE_TASK, contentValues, KEY_ID + " = ? ", new String[]{String.valueOf(task.getId())});
    }
}
