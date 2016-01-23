package com.example.garret_m_aymond.garretsblackjack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Garret-M-Aymond on 1/20/16.
 */
public class ScoreDbHelper extends SQLiteOpenHelper {
    private static ScoreDbHelper mInstance = null;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS  " + "Scoreboard" + " (" +
                    "_ID" + " INTEGER PRIMARY KEY," +
                    "winner" + TEXT_TYPE + COMMA_SEP +
                    "dealerscore" + TEXT_TYPE + COMMA_SEP +
                    "playerscore" + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + "Scoreboard";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BlackJack";

    public ScoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("DbUpgrade", "DB is version " + DATABASE_VERSION);
    }

    public static ScoreDbHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new ScoreDbHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public void onCreate(SQLiteDatabase db) {
        synchronized (db) {
            db.execSQL(SQL_CREATE_ENTRIES);
            Log.i("DbUpgrade", "Creating Session DB ");
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DbUpgrade", "Upgrading Sessions DB from version " + oldVersion + " to " + newVersion);
        DropTable(db);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DbUpgrade", "Upgrading Sessions DB from version " + oldVersion + " to " + newVersion);
        DropTable(db);
        onCreate(db);
    }

    public void DropTable(SQLiteDatabase db) {
        synchronized (db) {
            db.execSQL(SQL_DELETE_ENTRIES);
        }
    }

    public static SQLiteDatabase openDatabase(Context ctx, String className, String methodName) {
        ScoreDbHelper sDbHelper = ScoreDbHelper.getInstance(ctx);
        SQLiteDatabase db = sDbHelper.getWritableDatabase();
        boolean isWal = db.enableWriteAheadLogging();
        if (isWal) {
            Log.d("SQLiteDatabase", "Database is in Write Ahead Logging mode. " +
                    "Location: " + className + "." + methodName);
        } else {
            Log.d("SQLiteDatabase", "Database is most likely Read-Only. " +
                    "Location: " + className + "." + methodName);
        }
        return db;
    }
}