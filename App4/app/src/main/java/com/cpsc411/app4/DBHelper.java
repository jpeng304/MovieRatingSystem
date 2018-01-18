package com.cpsc411.app4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 12/1/2017.
 */


public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "tasksDB.sqlite";
    public static final String DBLOCATION = "/data/data/com.cpsc411.app4/databases/"; //if cant open check the path
    public static final String MOVIE_TABLE_NAME = "MOVIE";

    public static final String COL_1 = "Name";
    public static final String COL_2 = "Date";
    public static final String COL_3 = "Rating";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    public void openDatabase(){
        String dbPath = mContext.getDatabasePath(DB_NAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()){
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase(){
        if(mDatabase != null)
        {
            mDatabase.close();
        }
    }

    //Get the name, date and rating into the list from the MOVIE table but in the order of date
    //from recent to oldest
    public List<Task> getMoviebyDate(){
        Task type = null;
        List<Task> typeList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM MOVIE ORDER BY Date DESC", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            type = new Task (cursor.getString(0),cursor.getString(1),cursor.getFloat(2));
            typeList.add(type);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return typeList;
    }
    //Get the name, date and rating into the list from the MOVIE table but in the order of rating
    //from highest to lowest
    public List<Task> getMoviebyRating(){
        Task type = null;
        List<Task> typeList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM MOVIE ORDER BY Rating DESC", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            type = new Task (cursor.getString(0),cursor.getString(1),cursor.getFloat(2));
            typeList.add(type);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return typeList;
    }

    //Insert new data into MOVIE table
    public void insertData(String movie_name, String movie_date, float movie_rating)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, movie_name);
        contentValues.put(COL_2, movie_date);
        contentValues.put(COL_3, movie_rating);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(MOVIE_TABLE_NAME, null,contentValues);
        db.close();
    }

    //Update the MOIVE Table
    public void updateData(String movie_name, String movie_date, float movie_rating, String original_name)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, movie_name);
        contentValues.put(COL_2, movie_date);
        contentValues.put(COL_3, movie_rating);
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COL_1 + "= ?";
        String[] whereArgs = { original_name };

        db.update(MOVIE_TABLE_NAME,contentValues,where,whereArgs);
        db.close();
    }

}
