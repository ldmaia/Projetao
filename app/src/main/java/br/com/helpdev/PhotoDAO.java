package br.com.helpdev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import br.com.helpdev.Entity.Photo;

/**
 * Created by Lucas on 15/03/2016.
 */
public class PhotoDAO {
    public SQLiteDatabase db;
    private DataBase banco;
    private Context context;


    public PhotoDAO(Context context) {
        this.context = context;
        banco = new DataBase(context);
    }

    public void close() {
        db.close();
    }


    public void updateReportPicture(long reportId, Bitmap picture) {
        String filename = "projetinho.png";
        String picturePath = "MyFileStorage";
        File myInternalFile;


        File directory = context.getDir(picturePath, context.MODE_PRIVATE);
        myInternalFile = new File(directory, reportId + "" + filename);
        picturePath = myInternalFile.toString();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myInternalFile);
            picture.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception ex) {
            Log.i("DATABASE", "Problem updating picture", ex);
            picturePath = "";
        }

        SQLiteDatabase db = banco.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(banco.KEY_NAME, picturePath);
            db.insert(banco.DB_TABLE, null, values);
            db.update(banco.DB_TABLE, values, banco.KEY_ID + "=?", new String[]{String.valueOf(reportId)});
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();

            db.close();

        }
    }

    String picturePath = null;
    public String getReportPicturePath(Photo reportId) {
        SQLiteDatabase db = banco.getReadableDatabase();
            Cursor cursor = db.query(banco.DB_TABLE, null, banco.KEY_ID + "=?", new String[]{String.valueOf(reportId.getId())}, null, null, null);
            if(cursor != null){
                cursor.moveToFirst();
                picturePath = cursor.getString(cursor.
                        getColumnIndex(banco.KEY_NAME));

            }
        return picturePath;
    }





   /* public void inserirImagem(Photo photo) {

        // Convert the image into byte array
        Bitmap bitmap = photo.getImagem();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] buffer = out.toByteArray();
        // Open the database for writing
        SQLiteDatabase db = banco.getWritableDatabase();
         // Start the transaction.
        db.beginTransaction();
        ContentValues values;

        try{
            values = new ContentValues();
            values.put(banco.KEY_NAME, photo.getNome());
            values.put(banco.KEY_IMAGE, buffer);
            db.insert(banco.DB_TABLE, null, values);
            // Insert into database successfully.
            db.setTransactionSuccessful();
        }catch (SQLiteException e){
            e.printStackTrace();

        }finally {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }*/

    public ArrayList<Photo> carregarFotos() {
        ArrayList<Photo> photoList = new ArrayList<Photo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + banco.DB_TABLE;

        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Photo photo = new Photo();
                photo.setId(Integer.parseInt(cursor.getString(0)));
                photo.setNome(cursor.getString(1));
                //photo.setImagem(cursor.getBlob(2));
                //byte[] imgByte = cursor.getBlob(2);
                //Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                //ByteArrayInputStream inputStream = new ByteArrayInputStream(imgByte);
                //Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //bitmap = null;

                photoList.add(photo);
            } while (cursor.moveToNext());
        }

        // return foto list
        return photoList;
    }
}

   /* public void deletaFoto(Foto foto) {
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete(DB_TABLE, KEY_ID + " = ?",
                new String[]{String.valueOf(foto.getId())});
        db.close();
    }*/
