package br.com.helpdev.hardware.camera;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

import br.com.helpdev.DataBase;

/**
 * Created by Lucas on 06/03/2016.
 */
public class DAOdb {
    private SQLiteDatabase database;
    private DataBase dbHelper;

    public DAOdb() {

    }

    public DAOdb(Context context) {

        dbHelper = new DataBase(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addImage(Bitmap bitmap) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.KEY_NAME, "teste");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        values.put(dbHelper.KEY_IMAGE, stream.toByteArray());
        database.insert(dbHelper.DB_TABLE, null, values);
        database.close();
    }
}
