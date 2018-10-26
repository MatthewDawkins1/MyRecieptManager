package com.ict311task2dawkins.android.myrecieptmanager.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ict311task2dawkins.android.myrecieptmanager.DataBase.AppDbSchema.AppTable;

public class AppBaseHelper extends SQLiteOpenHelper {
    public static final int VERSON = 1;
    public static final String DATABASE_NAME = "appBase.db";

    public AppBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSON);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + AppTable.NAME + "(" + " _id integer primary key autoincrement, "+
        AppTable.Cols.COMMENT+", "+
        AppTable.Cols.DATE+", "+
        AppTable.Cols.IMAGE+", "+
        AppTable.Cols.LOCATION+", "+
        AppTable.Cols.REPORT+", "+
        AppTable.Cols.SHOPNAME+", "+
        AppTable.Cols.TITLE+", "+
        AppTable.Cols.UUID+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
