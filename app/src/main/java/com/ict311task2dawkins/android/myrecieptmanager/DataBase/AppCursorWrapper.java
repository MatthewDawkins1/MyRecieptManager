package com.ict311task2dawkins.android.myrecieptmanager.DataBase;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ict311task2dawkins.android.myrecieptmanager.model.Reciept;

import java.util.Date;
import java.util.UUID;

public class AppCursorWrapper extends CursorWrapper {
    public AppCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Reciept getRecipt(){
        String uuidString = getString(getColumnIndex(AppDbSchema.AppTable.Cols.UUID));
        String titleString = getString(getColumnIndex(AppDbSchema.AppTable.Cols.TITLE));
        String shopnameString = getString(getColumnIndex(AppDbSchema.AppTable.Cols.SHOPNAME));
        String commentString = getString(getColumnIndex(AppDbSchema.AppTable.Cols.COMMENT));
        Long dateString = getLong(getColumnIndex(AppDbSchema.AppTable.Cols.DATE));
        String imageString = getString(getColumnIndex(AppDbSchema.AppTable.Cols.IMAGE));

        Reciept reciept = new Reciept(UUID.fromString(uuidString));
        reciept.setTitle(titleString);
        reciept.setShopeName(shopnameString);
        reciept.setComment(commentString);
        reciept.setDate(new Date(dateString));
        reciept.setImage(imageString);

        return reciept;
    }
}
