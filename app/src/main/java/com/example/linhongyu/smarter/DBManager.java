package com.example.linhongyu.smarter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by linhongyu on 29/4/18.
 */

public class DBManager {
    private static String[] columns = {
            DBStructure.tableEntry.COLUMN_USAGEID,
            DBStructure.tableEntry.COLUMN_RESID,
            DBStructure.tableEntry.COLUMN_USEDATE,
            DBStructure.tableEntry.COLUMN_USEHOUR,
            DBStructure.tableEntry.COLUMN_FRIDGEUSAGE,
            DBStructure.tableEntry.COLUMN_CONDITINERUSAGE,
            DBStructure.tableEntry.COLUMN_WASHINGUSAGE,
            DBStructure.tableEntry.COLUMN_TEMPERATURE,
    };

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ELECUSAGE.db";
    private final Context context;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            DBStructure.tableEntry.TABLE_NAME + " (" +
            DBStructure.tableEntry._ID + " INTEGER PRIMARY KEY," +
            DBStructure.tableEntry.COLUMN_USAGEID + TEXT_TYPE + COMMA_SEP +
            DBStructure.tableEntry.COLUMN_RESID + TEXT_TYPE + COMMA_SEP +
            DBStructure.tableEntry.COLUMN_USEDATE  + TEXT_TYPE + COMMA_SEP +
            DBStructure.tableEntry.COLUMN_USEHOUR + TEXT_TYPE + COMMA_SEP +
            DBStructure.tableEntry.COLUMN_FRIDGEUSAGE + TEXT_TYPE + COMMA_SEP +
            DBStructure.tableEntry.COLUMN_CONDITINERUSAGE + TEXT_TYPE + COMMA_SEP +
            DBStructure.tableEntry.COLUMN_WASHINGUSAGE + TEXT_TYPE + COMMA_SEP +
            DBStructure.tableEntry.COLUMN_TEMPERATURE + TEXT_TYPE + ");";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
            DBStructure.tableEntry.TABLE_NAME;


    private MySQLiteOpenHelper myDBHelper;
    private static SQLiteDatabase db;

    public DBManager(Context ctx) {
        this.context = ctx;
        myDBHelper = new MySQLiteOpenHelper(context);
    }
    public DBManager open() throws SQLException {
        db = myDBHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        myDBHelper.close();
    }


    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {
        public MySQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }

    public static long insertUsage(int usageid, int resid, String date, int time, double fridgeusage,
                                   double airusage, double washingusage, double temperature) {
        Log.i("Temperature is:","generate: "+ usageid);
        ContentValues values = new ContentValues();
        values.put(DBStructure.tableEntry.COLUMN_USAGEID, usageid);
        values.put(DBStructure.tableEntry.COLUMN_RESID, resid);
        values.put(DBStructure.tableEntry.COLUMN_USEDATE, date);
        values.put(DBStructure.tableEntry.COLUMN_USEHOUR, time);
        values.put(DBStructure.tableEntry.COLUMN_FRIDGEUSAGE, fridgeusage);
        values.put(DBStructure.tableEntry.COLUMN_CONDITINERUSAGE, airusage);
        values.put(DBStructure.tableEntry.COLUMN_WASHINGUSAGE, washingusage);
        values.put(DBStructure.tableEntry.COLUMN_TEMPERATURE, temperature);
        return db.insert(DBStructure.tableEntry.TABLE_NAME, null, values);
    }

    public static Cursor getUsage() {
        return db.query(DBStructure.tableEntry.TABLE_NAME, columns, null, null, null, null, null);
    }

    public void deleteAllUsages() {
        db.delete(DBStructure.tableEntry.TABLE_NAME, null, null);
    }
}