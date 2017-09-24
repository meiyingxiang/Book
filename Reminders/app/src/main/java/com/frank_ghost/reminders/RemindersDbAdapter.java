package com.frank_ghost.reminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 2017/9/24.
 */

public class RemindersDbAdapter {
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_IMPORTANT = "important";
    public static final int INDEX_ID = 0;
    public static final int INDEX_CONTENT = INDEX_ID + 1;
    public static final int INDEX_IMPORTANT = INDEX_ID + 2;
    private static final String TAG = "RemindersDbAdapter";
    private static DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "dba_remdrs";
    private static final String TABLE_NAME = "tbl_remdrs";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;
    private static final String DATABASE_CREATE = "create table if not exists " + TABLE_NAME + " ( "
            + COL_ID + " integer primary key autoincrement, " + COL_CONTENT + " text, " + COL_IMPORTANT + " integer );";

    public RemindersDbAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void open() {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDb = mDatabaseHelper.getWritableDatabase();
    }

    public void close() {
        if (mDatabaseHelper != null) {
            mDatabaseHelper.close();
        }
    }

    public void createReminder(String name, boolean important) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CONTENT, name);
        contentValues.put(COL_IMPORTANT, important ? 1 : 0);
        mDb.insert(TABLE_NAME, null, contentValues);
    }

    public long createReminder(Reminder reminder) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CONTENT, reminder.getmContent());
        contentValues.put(COL_IMPORTANT, reminder.getmImportant());
        return mDb.insert(TABLE_NAME, null, contentValues);
    }

    public Reminder fetchReminderById(int id) {
        Cursor query = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_CONTENT, COL_IMPORTANT}, COL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (query != null) {
            query.moveToFirst();
        }
        return new Reminder(query.getInt(INDEX_ID), query.getString(INDEX_CONTENT), query.getInt(INDEX_IMPORTANT));
    }

    public Cursor fetchAllReminder() {
        Cursor query = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_CONTENT, COL_IMPORTANT}, null, null, null, null, null);
        if (query != null) {
            query.moveToFirst();
        }
        return query;
    }

    public void updateReminder(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, reminder.getmContent());
        values.put(COL_IMPORTANT, reminder.getmImportant());
        mDb.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(reminder.getmId())});
    }

    public void deleteReminder(Reminder reminder) {
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(reminder.getmId())});
    }

    public void deleteAllReminders() {
        mDb.delete(TABLE_NAME, null, null);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, RemindersDbAdapter.DATABASE_NAME, null, DATABASE_VERSION);
        }

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.e(TAG, "Upgrade database from version " + oldVersion + " to " +
                    newVersion + " ,which will destroy all old data");
            db.execSQL("drop table if exists " + TABLE_NAME);
            onCreate(db);
        }
    }
}
