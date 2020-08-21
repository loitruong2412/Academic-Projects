package com.mukhi.bobaorbust;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 *  This class represents a database where the scores are stored. All queries regarding the
 *  database are here as well.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int databaseVersion = 1;
    private static final String databaseName = "scoreboard";
    private static final String tableName = "scores";
    private static final String columnID = "id";
    private static final String columnDate = "date";
    private static final String columnScore= "score";
    private static final String duration = "duration";


    public DatabaseHelper(Context context){
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_LINK_TABLE = "CREATE TABLE " + tableName + " (" + columnID +
                " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + columnDate + " VARCHAR(30), "
                + columnScore + " INTEGER, "+ duration + " BIGINT)";

        db.execSQL(CREATE_LINK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion ){

        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);

    }



    public long create(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(columnDate, result.getGameDate());
        values.put(columnScore, result.getScore());
        values.put(duration, result.getDuration());
        long id = db.insert(tableName, null, values);
        db.close();
        return id;
    }


    public ArrayList<Result> getTopScores() {
        ArrayList<Result> scoreList = new ArrayList<>();


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + tableName + " ORDER BY " + columnScore + " DESC LIMIT 10"
                , null);

        //iterate through the table
        if(cursor.moveToFirst()){
            do {
                Result result = new Result();
                result.setId(Integer.parseInt(cursor.getString(0)));

                result.setGameDate(cursor.getString(1));
                result.setScore(Integer.parseInt(cursor.getString(2)));
                scoreList.add(result);
            } while (cursor.moveToNext());
        }
        return scoreList;
    }

    public int getTotalGamesPlayed() {
        SQLiteDatabase db = this.getWritableDatabase();

        int gamesPlayed = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);

        if(cursor.moveToFirst()) {

            do {
                gamesPlayed = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return gamesPlayed;
    }



    public long getTotalTimePlayed() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + duration +" FROM " + tableName, null);

        long timePlayed = 0;

        if(cursor.moveToFirst()) {

            do {
                timePlayed = timePlayed + Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return -timePlayed;
    }


    public int getMostRecentScore() {
        SQLiteDatabase db = this.getWritableDatabase();
        int score = 0;
        Cursor cursor = db.rawQuery("SELECT  * FROM " + tableName + " ORDER BY " + columnID + " DESC LIMIT 1", null);
        if(cursor.moveToFirst()){
            do {
                score = Integer.parseInt(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        return score;
    }


    public void deleteAllScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }
}
