package com.example.s_kim.memoproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "memoDatabase.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException{
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    // Insert DB
    public long insertColumn(int memoNumber, String title, String message){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.MEMONUMBER, memoNumber);
        values.put(DataBases.CreateDB.TITLE, title);
        values.put(DataBases.CreateDB.MESSAGE, message);
        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
    }

    // Update DB
    public boolean updateColumn(int memoNumber, String title, String message){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.TITLE,title);
        values.put(DataBases.CreateDB.MESSAGE,message);
        return mDB.update(DataBases.CreateDB._TABLENAME0, values, "memoNumber=" + memoNumber, null) > 0;
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(DataBases.CreateDB._TABLENAME0, null, null);
    }

    // Delete DB
    public boolean deleteColumn(int memoNumber){
        return mDB.delete(DataBases.CreateDB._TABLENAME0, "memoNumber="+memoNumber, null) > 0;
    }
    // Select DB
    public Cursor selectColumns(){

        return mDB.query(DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }

    // sort by column
    public Cursor sortColumn(){
        Cursor c = mDB.rawQuery( "SELECT * FROM "+DataBases.CreateDB._TABLENAME0+";", null);
        return c;
    }
}
