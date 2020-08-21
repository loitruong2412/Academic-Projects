package com.mukhi.bobaorbust;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  This class represents the settings in the home screen. It lets user view how much time they
 *  have spent playing the game and how many games they have played. It also allows the user to
 *  reset the score database.
 */
public class Settings extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = DatabaseSingleton.getInstance(this).databaseHelper;

        int totalGames = db.getTotalGamesPlayed();

        Typeface t1 = Typeface.createFromAsset(getAssets(),"fonts/Oxygen-Bold.ttf");
        Typeface t2 = Typeface.createFromAsset(getAssets(),"fonts/Oxygen-Regular.ttf");


        TextView titleGamesPlayed = findViewById(R.id.titleGamesPlayed);
        titleGamesPlayed.setTypeface(t1);


        TextView gamesPlayed = findViewById(R.id.textView5);
        gamesPlayed.setText(Integer.toString(totalGames));
        gamesPlayed.setTypeface(t2);

        long secondsPlayed = db.getTotalTimePlayed();

        long minutesPlayed = secondsPlayed / 60;

        String timePlayed = Long.toString(minutesPlayed);

        String seconds = timePlayed + " minutes";

        TextView titleDuration = findViewById(R.id.textView3);
        titleDuration.setTypeface(t1);

        TextView duration = findViewById(R.id.textView4);
        duration.setTypeface(t2);
        duration.setText(seconds);



    }


    public void resetScores(View view) {
        db.deleteAllScores();

        TextView duration = findViewById(R.id.textView4);
        duration.setText("0 minutes");

        TextView gamesPlayed = findViewById(R.id.textView5);
        gamesPlayed.setText("0");

        Toast.makeText(getApplicationContext(),"Scores Have Successfully Reset",Toast.LENGTH_LONG).show();

    }







}
