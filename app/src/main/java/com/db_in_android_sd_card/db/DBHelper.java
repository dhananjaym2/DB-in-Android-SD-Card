package com.db_in_android_sd_card.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.db_in_android_sd_card.models.NameModel;
import com.db_in_android_sd_card.util.AppLog;

import java.io.File;
import java.util.ArrayList;

/**
 * Database interaction class
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = DBHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;

    private final String LOG_TAG = this.getClass().getSimpleName();
    private final String TABLE_NAME = "TABLE_NAME";
    private final String KEY_ID = "KEY_ID";
    private final String KEY_NAME = "KEY_NAME";
    private final String KEY_APP_PKG = "KEY_APP_PKG";
    private String currentAppPkg;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public DBHelper(Context context) {
        super(context
                /* DB file name and location is the second parameter. */
                , Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "DB" + File.separator + DATABASE_NAME /*name*/
                , null /*factory*/
                , DATABASE_VERSION /*version*/);
        currentAppPkg = context.getPackageName();/* OR = com.db_in_android_sd_card.BuildConfig.APPLICATION_ID*/
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NAME_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_APP_PKG + " TEXT" + ")";
        AppLog.v(LOG_TAG, "in onCreate() CREATE_NAME_TABLE:" + CREATE_NAME_TABLE);
        db.execSQL(CREATE_NAME_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * Insert a row in TABLE_NAME for a NameModel object
     *
     * @param nameModel NameModel object with the name field set.
     * @return result of DB insert
     */
    public long insertNameValueInDB(NameModel nameModel) {
        long result;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();// android.database.sqlite.SQLiteCantOpenDatabaseException: unknown error (code 14): Could not open database

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, nameModel.getName()); // Contact Name
        values.put(KEY_APP_PKG, currentAppPkg);
        AppLog.v(LOG_TAG, "values:" + values.toString());

        // Inserting Row
        result = sqLiteDatabase.insert(TABLE_NAME, null, values);
        AppLog.v(LOG_TAG, "insert query result:" + result);
        sqLiteDatabase.close(); // Closing database connection
        return result;
    }

    /**
     * Fetch and return all name values for current app package.
     *
     * @return ArrayList of NameModel objects.
     */
    public ArrayList<NameModel> fetchAllNameValuesForCurrentAppFromDB() {
        ArrayList<NameModel> nameArrayList = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_APP_PKG},
                KEY_APP_PKG + "=?", new String[]{currentAppPkg}, null, null, null, null);

        if (cursor != null) {
            nameArrayList = new ArrayList<>();
            while (cursor.moveToNext()) {

                NameModel nameModel = new NameModel();
                nameModel.setId_NameModel(cursor.getString(0));
                nameModel.setName(cursor.getString(1));
                nameModel.setAppPkg(cursor.getString(2));

                nameArrayList.add(nameModel);
            }

            cursor.close();
        } else {
            AppLog.v(LOG_TAG, "cursor null in fetchAllNameValueInDB()");
        }
        sqLiteDatabase.close();
        return nameArrayList;
    }
}
//reference: http://stackoverflow.com/questions/4806181/sqlite-database-on-sd-card