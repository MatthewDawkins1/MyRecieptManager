package com.ict311task2dawkins.android.myrecieptmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ict311task2dawkins.android.myrecieptmanager.DataBase.AppBaseHelper;
import com.ict311task2dawkins.android.myrecieptmanager.DataBase.AppCursorWrapper;
import com.ict311task2dawkins.android.myrecieptmanager.DataBase.AppDbSchema;
import com.ict311task2dawkins.android.myrecieptmanager.model.Reciept;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemLab {
    private static ItemLab sItemLab;

    private Context mcontext;
    private SQLiteDatabase appDataBase;

    public void addRecipt(Reciept r){
        ContentValues values = getContentValues(r);

        appDataBase.insert(AppDbSchema.AppTable.NAME, null, values);
    }

    public void removeRecipt(Reciept r){
        ContentValues values = getContentValues(r);
        appDataBase.execSQL("DELETE FROM "+ AppDbSchema.AppTable.NAME+" WHERE "+AppDbSchema.AppTable.Cols.UUID +"= '"+r.getUuid().toString()+"'");
    }

    public static ItemLab get(Context context){
        if(sItemLab == null){
            sItemLab = new ItemLab(context);
        }
        return sItemLab;
    }

    public List<Reciept> getmRecipts() {
        List<Reciept> reciepts = new ArrayList<>();

        AppCursorWrapper cursor = queryRecipts(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                reciepts.add(cursor.getRecipt());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return reciepts;
    }

    public void updateRecipt (Reciept reciept){
        String uuidString = reciept.getUuid().toString();
        ContentValues values = getContentValues(reciept);

        appDataBase.update(AppDbSchema.AppTable.NAME, values, AppDbSchema.AppTable.Cols.UUID + " = ?", new String[]{ uuidString });
    }

    private AppCursorWrapper queryRecipts(String whereClause, String[] whereArgs){
        Cursor cursor = appDataBase.query(
                AppDbSchema.AppTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new AppCursorWrapper(cursor);
    }

    public Reciept getRecipt(UUID id){
        AppCursorWrapper cursor = queryRecipts(
                AppDbSchema.AppTable.Cols.UUID+" = ?",new String[]{id.toString()}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getRecipt();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(Reciept reciept){
        ContentValues values = new ContentValues();
        values.put(AppDbSchema.AppTable.Cols.UUID, reciept.getUuid().toString());
        values.put(AppDbSchema.AppTable.Cols.TITLE, reciept.getTitle());
        values.put(AppDbSchema.AppTable.Cols.COMMENT, reciept.getComment());
        values.put(AppDbSchema.AppTable.Cols.SHOPNAME, reciept.getShopeName());
        values.put(AppDbSchema.AppTable.Cols.DATE, reciept.getDate().getTime());
        values.put(AppDbSchema.AppTable.Cols.LOCATION, reciept.getImage());

        return values;
    }

    private ItemLab(Context ccntext){
        mcontext = ccntext.getApplicationContext();
        appDataBase = new AppBaseHelper(mcontext).getWritableDatabase();
    }
}
