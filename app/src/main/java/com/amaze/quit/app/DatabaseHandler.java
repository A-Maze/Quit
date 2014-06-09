package com.amaze.quit.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rik on 20-5-2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private Sigaretten sigaret;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "QuitSmokeDatabase";

    //  table names
    private static final String TABLE_USER = "users";
    private static final String TABLE_SIGARETTEN = "sigaretten";
    private static final String TABLE_CHALLENGES = "challenges";
    private static final String TABLE_USER_CHALLANGE = "user_challenge";
    private static final String TABLE_LEVELS = "levels";
    private static final String TABLE_GEZONDHEID = "gezondheid";
    private static final String TABLE_PRODUCT = "product";

    // User Table Columns names
    private static final String USER_UID = "uID";
    private static final String USER_SID = "sID";
    private static final String USER_PERDAG = "per_dag";
    private static final String USER_LEVEL = "level";
    private static final String USER_QUIT_YEAR = "quit_year";
    private static final String USER_QUIT_MONTH = "quit_month";
    private static final String USER_QUIT_DAY = "quit_day";
    private static final String USER_QUIT_HOUR = "quit_hour";
    private static final String USER_QUIT_MINUTE = "quit_minute";
    private static final String USER_SPENT_AMOUNT = "spent_amount";

    // Sigaretten Table Columns namesss
    private static final String SIGARETTEN_SID = "sID";
    private static final String SIGARETTEN_MERK = "merk";
    private static final String SIGARETTEN_AANTAL = "aantal";
    private static final String SIGARETTEN_TEER = "teer";
    private static final String SIGARETTEN_NICOTINE = "nicotine";
    private static final String SIGARETTEN_PRIJS = "prijs";

    // Chalenges Table Columns names
    private static final String CHALLENGES_CID = "cID";
    private static final String CHALLENGES_TITEL = "Titel";
    private static final String CHALLENGES_BESCHRIJVING = "beschrijving";
    private static final String CHALLENGES_BEHAALD = "behaald";


    // User_challenges Table Columns names
    private static final String USER_CHALLENGES_UID = "uID";
    private static final String USER_CHALLENGES_CID = "cID";
    private static final String USER_CHALLENGES_UCID = "ucID";


    // Levels Table Columns names
    private static final String LEVEL_LID = "lID";
    private static final String LEVEL_TITEL = "titel";
    private static final String LEVEL_BESCHRIJVING = "beschrijving";
    private static final String LEVEL_MINDAYS = "min_days";

    // Gezondheid Table Columns names
    private static final String GEZONDHEID_UID = "uID";
    private static final String GEZONDHEID_TEER = "sID";
    private static final String GEZONDHEID_NICOTINE = "per_dag";
    private static final String GEZONDHEID_LANGERTELEVEN = "langer_te_leven";
    private static final String GEZONDHEID_CO2 = "co2";

    // Product Table Columns names
    private static final String PRODUCT_UID = "UID";
    private static final String PRODUCT_ID = "ID";
    private static final String PRODUCT_TITEL = "titel";
    private static final String PRODUCT_PRIJS = "prijs";
    private static final String PRODUCT_IMAGE = "image";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /* creating Tables */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* User table */
        String CREATE_USER_TABLE = "CREATE TABLE "
                + TABLE_USER + "("
                + USER_UID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + USER_SID + " INTEGER,"
                + USER_PERDAG + " INTEGER,"
                + USER_LEVEL + " INTEGER,"
                + USER_QUIT_YEAR + " INTEGER,"
                + USER_QUIT_MONTH + " INTEGER,"
                + USER_QUIT_DAY + " INTEGER,"
                + USER_QUIT_HOUR + " INTEGER,"
                + USER_QUIT_MINUTE + " INTEGER,"
                + USER_SPENT_AMOUNT + " INTEGER"
                + ")";
        db.execSQL(CREATE_USER_TABLE);

          /* Sigaretten table */
        String CREATE_SIGARETTEN_TABLE = "CREATE TABLE "
                + TABLE_SIGARETTEN + "("
                + SIGARETTEN_SID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SIGARETTEN_AANTAL + " INTEGER,"
                + SIGARETTEN_MERK + " TEXT,"
                + SIGARETTEN_TEER + " REAL,"
                + SIGARETTEN_NICOTINE + " REAL,"
                + SIGARETTEN_PRIJS + " REAL"
                + ")";
        db.execSQL(CREATE_SIGARETTEN_TABLE);

          /* Challenges table */
        String CREATE_CHALLANGES_TABLE = "CREATE TABLE "
                + TABLE_CHALLENGES + "("
                + CHALLENGES_CID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CHALLENGES_TITEL + " TEXT,"
                + CHALLENGES_BESCHRIJVING + " TEXT,"
                + CHALLENGES_BEHAALD + " INTEGER"
                + ")";
        db.execSQL(CREATE_CHALLANGES_TABLE);

          /* User_challenge table */
        String CREATE_USER_CHALLENGE_TABLE = "CREATE TABLE "
                + TABLE_USER_CHALLANGE + "("
                + USER_CHALLENGES_UCID + " INTEGER PRIMARY KEY ,"
                + USER_CHALLENGES_UID + " INTEGER,"
                + USER_CHALLENGES_CID + " INTEGER"
                + ")";
        db.execSQL(CREATE_USER_CHALLENGE_TABLE);

          /* Levels table */
        String CREATE_LEVELS_TABLE = "CREATE TABLE "
                + TABLE_LEVELS + "("
                + LEVEL_LID + " INTEGER PRIMARY KEY,"
                + LEVEL_TITEL + " TEXT,"
                + LEVEL_BESCHRIJVING + " TEXT,"
                + LEVEL_MINDAYS + " INTEGER"
                + ")";
        db.execSQL(CREATE_LEVELS_TABLE);

          /* Gezondheid table */
        String CREATE_GEZONDHEID_TABLE = "CREATE TABLE "
                + TABLE_GEZONDHEID + "("
                + GEZONDHEID_UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + GEZONDHEID_TEER + " REAL,"
                + GEZONDHEID_NICOTINE + " REAL,"
                + GEZONDHEID_CO2 + " REAL,"
                + GEZONDHEID_LANGERTELEVEN + " REAL"
                + ")";
        db.execSQL(CREATE_GEZONDHEID_TABLE);

         /* Prodcut info table */
        String CREATE_PRODUCT_TABLE = "CREATE TABLE "
                + TABLE_PRODUCT + "("
                + PRODUCT_UID + " INTEGER PRIMARY KEY,"
                + PRODUCT_ID + " TEXT,"
                + PRODUCT_TITEL + " TEXT,"
                + PRODUCT_PRIJS + " REAL,"
                + PRODUCT_IMAGE + " BLOB"
                + ")";
        db.execSQL(CREATE_PRODUCT_TABLE);

    }








    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        /*Drop older table if existed*/
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEZONDHEID);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGARETTEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_CHALLANGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        /* Create tables again*/
        onCreate(db);
    }

    /* Adding new User*/
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(USER_UID, user.getuID()); // user id
        values.put(USER_SID, user.getsID()); // user sigaretten id
        values.put(USER_PERDAG, user.getPerDag()); // hoeveel die smoked per dag
        values.put(USER_LEVEL, user.getLevel()); // level van user. bij nieuwe user gewoon 1.
        values.put(USER_QUIT_YEAR, user.getQuitYear());
        values.put(USER_QUIT_MONTH, user.getQuitMonth());
        values.put(USER_QUIT_DAY, user.getQuitDay());
        values.put(USER_QUIT_HOUR, user.getQuitHour());
        values.put(USER_QUIT_MINUTE, user.getQuitMinute());
        values.put(USER_SPENT_AMOUNT, user.getSpentAmount());

        // Inserting Row
        assert db != null;
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    public void addSigarette(Sigaretten sigaret) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(SIGARETTEN_SID, sigaret.getsID());
        values.put(SIGARETTEN_AANTAL, sigaret.getAantal());
        values.put(SIGARETTEN_MERK, sigaret.getMerk());
        values.put(SIGARETTEN_TEER, sigaret.getTeer());
        values.put(SIGARETTEN_NICOTINE, sigaret.getNicotine());
        values.put(SIGARETTEN_PRIJS, sigaret.getPrijs());
        // Inserting Row
        assert db != null;
        db.insert(TABLE_SIGARETTEN, null, values);
        db.close(); // Closing database connection
    }

    public void addChallenge(Challenges challenge) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CHALLENGES_TITEL, challenge.getTitel());
        values.put(CHALLENGES_BESCHRIJVING, challenge.getBeschrijving());
        values.put(CHALLENGES_BEHAALD,challenge.getBehaald());

        // Inserting Row
        assert db != null;
        db.insert(TABLE_CHALLENGES, null, values);
        db.close(); // Closing database connection
    }

    public void addLevel(Levels level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LEVEL_LID, level.getlID());
        values.put(LEVEL_TITEL, level.getTitel());
        values.put(LEVEL_BESCHRIJVING,level.getBeschrijving());
        values.put(LEVEL_MINDAYS, level.getMinDays());
        // Inserting Row
        assert db != null;
        db.insert(TABLE_LEVELS, null, values);
        db.close(); // Closing database connection
    }

    public void addProduct(Artikel artikel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PRODUCT_UID, artikel.getuId());
        values.put(PRODUCT_ID, artikel.getId());
        values.put(PRODUCT_TITEL, artikel.getTitel());
        values.put(PRODUCT_PRIJS, artikel.getPrijs());
        values.put(PRODUCT_IMAGE, artikel.getImage());

        // Inserting Row
        assert db != null;
        db.insert(TABLE_PRODUCT, null, values);
        db.close(); // Closing database connection
    }









    /* Getting single User*/
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        assert db != null;
        Cursor cursor = db.query(TABLE_USER, new String[] {
                        USER_UID,
                        USER_SID,
                        USER_PERDAG,
                        USER_LEVEL,
                        USER_QUIT_YEAR,
                        USER_QUIT_MONTH,
                        USER_QUIT_DAY,
                        USER_QUIT_HOUR,
                        USER_QUIT_MINUTE,
                        USER_SPENT_AMOUNT}, USER_UID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)),
                Integer.parseInt(cursor.getString(8)),
                Integer.parseInt(cursor.getString(9)));
        /* return contact */
        cursor.close();
        return user;

    }

    /* Getting single User*/
    public Sigaretten getSigaret(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        assert db != null;
        Cursor cursor = db.query(TABLE_SIGARETTEN, new String[] {
                        SIGARETTEN_SID ,
                        SIGARETTEN_NICOTINE,
                        SIGARETTEN_MERK,
                        SIGARETTEN_AANTAL,
                        SIGARETTEN_TEER,
                        SIGARETTEN_PRIJS}, SIGARETTEN_SID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Sigaretten sigaret = new Sigaretten(
                Integer.parseInt(cursor.getString(0)),
                Float.parseFloat(cursor.getString(1)),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                Float.parseFloat(cursor.getString(4)),
                Float.parseFloat(cursor.getString(5)));
        /* return sigaret */
        cursor.close();
        return sigaret;
    }



    /* Getting single challenge*/
    public Challenges getChallenge(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        assert db != null;
        Cursor cursor = db.query(TABLE_CHALLENGES, new String[] {
                        CHALLENGES_CID,
                        CHALLENGES_TITEL,
                        CHALLENGES_BESCHRIJVING,
                        CHALLENGES_BEHAALD}, CHALLENGES_CID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Challenges challenge = new Challenges(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3));
        /* return challenge */
        cursor.close();
        return challenge;
    }

    /* Getting single userChallenge*/
    public User_Challenge getUserChallenge(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        assert db != null;
        Cursor cursor = db.query(TABLE_USER_CHALLANGE, new String[] {
                        USER_CHALLENGES_UCID,
                        USER_CHALLENGES_UID,
                        USER_CHALLENGES_CID
                }, USER_CHALLENGES_UCID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User_Challenge user_challenge = new User_Challenge(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)));
        /* return contact */
        cursor.close();
        return user_challenge;
    }

    /* Getting single User*/
    public Gezondheid getGezondheid(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        assert db != null;
        Cursor cursor = db.query(TABLE_GEZONDHEID, new String[] {
                        GEZONDHEID_UID,
                        GEZONDHEID_TEER,
                        GEZONDHEID_NICOTINE,
                        GEZONDHEID_CO2,
                        GEZONDHEID_LANGERTELEVEN}, GEZONDHEID_UID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Gezondheid gezondheid = new Gezondheid(
                Integer.parseInt(cursor.getString(0)),
                Float.parseFloat(cursor.getString(1)),
                Float.parseFloat(cursor.getString(2)),
                Float.parseFloat(cursor.getString(3)),
                Float.parseFloat(cursor.getString(4)));
        /* return contact */
        cursor.close();
        return gezondheid;
    }

    /* Getting single Level*/
    public Levels getLevel(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        assert db != null;
        Cursor cursor = db.query(TABLE_LEVELS, new String[] {
                        LEVEL_LID,
                        LEVEL_TITEL,
                        LEVEL_BESCHRIJVING,
                        LEVEL_MINDAYS}, LEVEL_LID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Levels level = new Levels(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)));

        /* return contact */
        cursor.close();
        return level;
    }

    /* Getting single Product*/
    public Artikel getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        assert db != null;
        Cursor cursor = db.query(TABLE_PRODUCT, new String[] {
                        PRODUCT_UID,
                        PRODUCT_ID,
                        PRODUCT_TITEL,
                        PRODUCT_PRIJS,
                        PRODUCT_IMAGE}, PRODUCT_UID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Artikel artikel = new Artikel(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Float.parseFloat(cursor.getString(3)),
                cursor.getBlob(4));

        /* return contact */
        cursor.close();
        return artikel;
    }

    public boolean isProductThere(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM CAT_BUD_TAB", null);
        if (cur != null) {
            cur.moveToFirst();                       // Always one row returned.
            if (cur.getInt (0) == 0) {               // Zero count means empty table.
                return false;
            }
        }
        return true;
    }




    /* Updating single Userr */
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_SID, user.getsID());
        values.put(USER_PERDAG, user.getPerDag());
        values.put(USER_LEVEL, user.getLevel());
        values.put(USER_SPENT_AMOUNT, user.getSpentAmount());
        values.put(USER_QUIT_YEAR, user.getQuitYear());
        values.put(USER_QUIT_MONTH, user.getQuitMonth());
        values.put(USER_QUIT_DAY, user.getQuitDay());
        values.put(USER_QUIT_HOUR, user.getQuitHour());
        values.put(USER_QUIT_MINUTE, user.getQuitMinute());

        /* updating row */
        return db.update(TABLE_USER, values, USER_UID + " = ?",
                new String[] { String.valueOf(user.getuID()) });
    }

    /* Updating single Siaget */
    public int updateSigaretten(Sigaretten sigaretten) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SIGARETTEN_AANTAL, sigaretten.getAantal());

        /* updating row */
        return db.update(TABLE_SIGARETTEN, values, SIGARETTEN_SID + " = ?",
                new String[] { String.valueOf(sigaretten.getsID()) });
    }

    public int updateChallenge (Challenges challenge) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("id",String.valueOf(challenge.getcID()));
        Log.d("id",String.valueOf(challenge.getBeschrijving()));
        Log.d("id",String.valueOf(challenge.getBehaald()));
        values.put(CHALLENGES_TITEL, challenge.getTitel());
        values.put(CHALLENGES_BESCHRIJVING, challenge.getBeschrijving());
        values.put(CHALLENGES_BEHAALD,challenge.getBehaald());


         /* updating row */
        return db.update(TABLE_CHALLENGES, values, CHALLENGES_CID + " = ?",
                new String[] { String.valueOf(challenge.getcID()) });
    }

    /* Updating prodcuct */
    public int updateProduct(Artikel artikel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_ID, artikel.getId());
        values.put(PRODUCT_TITEL, artikel.getTitel());
        values.put(PRODUCT_PRIJS, artikel.getPrijs());
        values.put(PRODUCT_IMAGE, artikel.getImage());

        /* updating row */
        return db.update(TABLE_PRODUCT, values, PRODUCT_UID + " = ?",
                new String[] { String.valueOf(artikel.getuId()) });
    }





    public int getChallengesAmount(){
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_CHALLENGES);
        return numRows;
    }

    public int getLevelAmount(){
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_LEVELS);
        return numRows;
    }






}
