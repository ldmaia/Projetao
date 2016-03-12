package br.com.helpdev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import br.com.helpdev.Entity.Foto;

/**
 * Created by Lucas on 06/03/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "database_name";

    // Table Names
    public static final String DB_TABLE = "table_image";

    // column names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "image_name";
    public static final String KEY_IMAGE = "image_data";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY,"+
            KEY_NAME + " TEXT," +
            KEY_IMAGE + " BLOB);";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating table
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        // create new table
        onCreate(db);
    }


    /**
     * Created by Lucas on 06/03/2016.
     */
    public static class FotoDAO {
        private SQLiteDatabase db;
        private DataBase banco;

        public FotoDAO(Context context) {
            banco = new DataBase(context);
        }

        public void close()
        {
            db.close();
        }


        public void inserirImagem(Foto foto) {

            // Convert the image into byte array
            Bitmap bitmap = foto.getImagem();
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
                values.put(KEY_NAME, foto.getNome());
                values.put(KEY_IMAGE, buffer);
                db.insert(DB_TABLE, null, values);
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
        }

        public ArrayList<Foto> carregarFotos() {
            ArrayList<Foto> fotoList = new ArrayList<Foto>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + DB_TABLE;

            SQLiteDatabase db = banco.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Foto foto = new Foto();
                    foto.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("KEY_ID"))));
                    foto.setNome(cursor.getString(1));
                    byte[] imgByte = cursor.getBlob(2);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                    foto.setImagem(bitmap);


                    // Adding contact to list
                    fotoList.add(foto);
                } while (cursor.moveToNext());
            }

            // return contact list
            return fotoList;
        }
    }
}



