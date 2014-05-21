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

    // User table names
    private static final String TABLE_USER = "users";
    private static final String TABLE_SIGARETTEN = "sigaretten";
    private static final String TABLE_CHALLENGES = "challenges";
    private static final String TABLE_USER_CHALLANGE = "user_challenge";
    private static final String TABLE_LEVELS = "levels";
    private static final String TABLE_GEZONDHEID = "gezondheid";

    // User Table Columns names
    private static final String USER_UID = "uID";
    private static final String USER_SID = "sID";
    private static final String USER_PERDAG = "per_dag";
    private static final String USER_LEVEL = "level";


    // Sigaretten Table Columns names
    private static final String SIGARETTEN_UID = "uID";
    private static final String SIGARETTEN_MERK = "merk";
    private static final String SIGARETTEN_AANTAL = "aantal";
    private static final String SIGARETTEN_TEER = "teer";
    private static final String SIGARETTEN_NICOTINE = "nicotine";
    private static final String SIGARETTEN_PRIJS = "prijs";

    // Chalenges Table Columns names
    private static final String CHALLENGES_CID = "cID";
    private static final String CHALLENGES_TITEL = "Titel";
    private static final String CHALLENGES_BESCHRIJVING = "beschrijving";


    // User_challenges Table Columns names
    private static final String USER_CHALLENGES_UID = "uID";
    private static final String USER_CHALLENGES_CID = "cID";
    private static final String USER_CHALLENGES_ucID = "ucID";


    // Levels Table Columns names
    private static final String LEVEL_LID = "lID";
    private static final String LEVEL_TITEL = "titel";
    private static final String LEVEL_BESCHRIJVING = "beschrijving";


    // Gezondheid Table Columns names
    private static final String GEZONDHEID_UID = "uID";
    private static final String GEZONDHEID_TEER = "sID";
    private static final String GEZONDHEID_NICOTINE = "per_dag";
    private static final String GEZONDHEID_LANGERTELEVEN = "level";
    private static final String GEZONDHEID_CO2 = "level";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* creating Tables */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* User table */
        String CREATE_USER_TABLE = "CREATE TABLE "
                + TABLE_USER + "("
                + USER_UID + " INTEGER PRIMARY KEY,"
                + USER_SID + " INTEGER,"
                + USER_PERDAG + " INTEGER,"
                + USER_LEVEL + " INTEGER" + ")";
        db.execSQL(CREATE_USER_TABLE);

          /* Sigaretten table */
        String CREATE_SIGARETTEN_TABLE = "CREATE TABLE "
                + TABLE_SIGARETTEN + "("
                + SIGARETTEN_UID + " INTEGER PRIMARY KEY,"
                + SIGARETTEN_MERK + " TEXT,"
                + SIGARETTEN_AANTAL + " INTEGER,"
                + SIGARETTEN_TEER + " REAL"
                + SIGARETTEN_NICOTINE + " REAL"
                + SIGARETTEN_PRIJS + " REAL"
                + ")";
        db.execSQL(CREATE_SIGARETTEN_TABLE);

          /* Challenges table */
        String CREATE_CHALLANGES_TABLE = "CREATE TABLE "
                + TABLE_CHALLENGES + "("
                + CHALLENGES_CID + " INTEGER PRIMARY KEY,"
                + CHALLENGES_TITEL + " TEXT,"
                + CHALLENGES_BESCHRIJVING + " TEXT,"
                + ")";
        db.execSQL(CREATE_CHALLANGES_TABLE);

          /* User_challenge table */
        String CREATE_USER_CHALLENGE_TABLE = "CREATE TABLE "
                + TABLE_USER_CHALLANGE + "("
                + USER_CHALLENGES_ucID + " INTEGER PRIMARY KEY,"
                + USER_CHALLENGES_UID + " INTEGER,"
                + USER_CHALLENGES_CID + " INTEGER,"
                + ")";
        db.execSQL(CREATE_USER_CHALLENGE_TABLE);

          /* Levels table */
        String CREATE_LEVELS_TABLE = "CREATE TABLE "
                + TABLE_LEVELS + "("
                + LEVEL_LID + " INTEGER PRIMARY KEY,"
                + LEVEL_TITEL + " TEXT,"
                + LEVEL_BESCHRIJVING + " TEXT,"
                + ")";
        db.execSQL(CREATE_LEVELS_TABLE);

          /* Gezondheid table */
        String CREATE_GEZONDHEID_TABLE = "CREATE TABLE "
                + TABLE_GEZONDHEID + "("
                + GEZONDHEID_UID + " INTEGER PRIMARY KEY,"
                + GEZONDHEID_TEER + " REAL,"
                + GEZONDHEID_NICOTINE + " REAL"
                + GEZONDHEID_CO2 + " REAL"
                + GEZONDHEID_LANGERTELEVEN + " REAL"
                + ")";
        db.execSQL(CREATE_GEZONDHEID_TABLE);
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
