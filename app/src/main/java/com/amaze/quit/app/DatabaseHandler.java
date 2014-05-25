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

    // User Table Columns names
    private static final String USER_UID = "uID";
    private static final String USER_SID = "sID";
    private static final String USER_PERDAG = "per_dag";
    private static final String USER_LEVEL = "level";


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


    // User_challenges Table Columns names
    private static final String USER_CHALLENGES_UID = "uID";
    private static final String USER_CHALLENGES_CID = "cID";
    private static final String USER_CHALLENGES_UCID = "ucID";


    // Levels Table Columns names
    private static final String LEVEL_LID = "lID";
    private static final String LEVEL_TITEL = "titel";
    private static final String LEVEL_BESCHRIJVING = "beschrijving";


    // Gezondheid Table Columns names
    private static final String GEZONDHEID_UID = "uID";
    private static final String GEZONDHEID_TEER = "sID";
    private static final String GEZONDHEID_NICOTINE = "per_dag";
    private static final String GEZONDHEID_LANGERTELEVEN = "langer_te_leven";
    private static final String GEZONDHEID_CO2 = "co2";



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
                + SIGARETTEN_SID + " INTEGER PRIMARY KEY,"
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
                + CHALLENGES_CID + " INTEGER PRIMARY KEY,"
                + CHALLENGES_TITEL + " TEXT,"
                + CHALLENGES_BESCHRIJVING + " TEXT"
                + ")";
        db.execSQL(CREATE_CHALLANGES_TABLE);

          /* User_challenge table */
        String CREATE_USER_CHALLENGE_TABLE = "CREATE TABLE "
                + TABLE_USER_CHALLANGE + "("
                + USER_CHALLENGES_UCID + " INTEGER PRIMARY KEY,"
                + USER_CHALLENGES_UID + " INTEGER,"
                + USER_CHALLENGES_CID + " INTEGER"
                + ")";
        db.execSQL(CREATE_USER_CHALLENGE_TABLE);

          /* Levels table */
        String CREATE_LEVELS_TABLE = "CREATE TABLE "
                + TABLE_LEVELS + "("
                + LEVEL_LID + " INTEGER PRIMARY KEY,"
                + LEVEL_TITEL + " TEXT,"
                + LEVEL_BESCHRIJVING + " TEXT"
                + ")";
        db.execSQL(CREATE_LEVELS_TABLE);

          /* Gezondheid table */
        String CREATE_GEZONDHEID_TABLE = "CREATE TABLE "
                + TABLE_GEZONDHEID + "("
                + GEZONDHEID_UID + " INTEGER PRIMARY KEY,"
                + GEZONDHEID_TEER + " REAL,"
                + GEZONDHEID_NICOTINE + " REAL,"
                + GEZONDHEID_CO2 + " REAL,"
                + GEZONDHEID_LANGERTELEVEN + " REAL"
                + ")";
        db.execSQL(CREATE_GEZONDHEID_TABLE);


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
        /* Create tables again*/
        onCreate(db);
    }

    /* Adding new User*/
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_UID, user.getuID()); // user id
        values.put(USER_SID, user.getsID()); // user sigaretten id
        values.put(USER_PERDAG, user.getPerDag()); // hoeveel die smoked per dag
        values.put(USER_LEVEL, user.getLevel()); // level van user. bij nieuwe user gewoon 1.

        // Inserting Row
        assert db != null;
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    public void addSigarette(Sigaretten sigaret) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SIGARETTEN_SID, sigaret.getsID());
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













    /* Getting single User*/
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        assert db != null;
        Cursor cursor = db.query(TABLE_USER, new String[] {
                        USER_UID,
                        USER_SID,
                        USER_PERDAG,
                        USER_LEVEL }, USER_UID + "=?",
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
        return sigaret;
    }



    /* Getting single challenge*/
    public Challenges getChallenge(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        assert db != null;
        Cursor cursor = db.query(TABLE_CHALLENGES, new String[] {
                        CHALLENGES_CID,
                        CHALLENGES_TITEL,
                        CHALLENGES_BESCHRIJVING }, CHALLENGES_CID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Challenges challenge = new Challenges(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));
        /* return challenge */
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
        return gezondheid;
    }

    /* Getting single Level*/
    public Levels getLevel(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        assert db != null;
        Cursor cursor = db.query(TABLE_LEVELS, new String[] {
                        LEVEL_LID,
                        LEVEL_TITEL,
                        LEVEL_BESCHRIJVING}, LEVEL_LID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Levels level = new Levels(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));

        /* return contact */
        return level;
    }




    /* Updating single Userr */
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



}