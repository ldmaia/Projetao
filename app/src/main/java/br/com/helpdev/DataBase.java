package br.com.helpdev;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lucas on 06/03/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 12;

    // Database Name
    private static final String DATABASE_NAME = "database_name";

    // Table Names
    public static final String DB_TABLE = "table_image";

    // column names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "image_name";
    //public static final String KEY_IMAGE = "image_data";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_NAME + " TEXT);";
    //+
      //      KEY_IMAGE + " BLOB);";

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

}



