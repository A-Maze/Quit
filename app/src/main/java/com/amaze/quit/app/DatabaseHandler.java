package com.amaze.quit.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rik on 20-5-2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "QuitSmokeDatabase";

    // User table name
    private static final String TABLE_USER = "users";

    // User Table Columns names
    private static final String USER_UID = "uID";
    private static final String USER_SID = "sID";
    private static final String USER_PERDAG = "per_dag";
    private static final String USER_LEVEL = "level";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* creating Tables */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* User table */
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + USER_UID + " INTEGER PRIMARY KEY," + USER_SID + " INTEGER,"
                + USER_PERDAG + " INTEGER," + USER_LEVEL + " INTEGER" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        /*Drop older table if existed*/
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        /* Create tables again*/
        onCreate(db);
    }

    /* Adding new User*/
    public void addUser(User user) {}

   /* Getting single User*/
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { USER_UID,
                        USER_SID, USER_PERDAG, USER_LEVEL }, USER_UID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                             Integer.parseInt(cursor.getString(1)),
                             Integer.parseInt(cursor.getString(2)),
                             Integer.parseInt(cursor.getString(3)));
        /* return contact */
        return user;
    }

    /* Updating single User */
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_SID, user.getsID());
        values.put(USER_PERDAG, user.getPerDag());
        values.put(USER_LEVEL, user.getLevel());

        /* updating row */
        return db.update(TABLE_USER, values, USER_UID + " = ?",
                new String[] { String.valueOf(user.getuID()) });
    }

    // Deleting single User
    public void deleteUser(User user) {}
}
