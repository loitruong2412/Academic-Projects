package com.mukhi.bobaorbust;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

/**
 *  This class represents the result activity, which pops up after a game is finished to let the
 *  user decides what to do next.
 */
public class GameResult extends AppCompatActivity {

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);


        Typeface t1 = Typeface.createFromAsset(getAssets(),"fonts/Oxygen-Bold.ttf");

        score = DatabaseSingleton.getInstance(this).databaseHelper.getMostRecentScore();

        TextView view = findViewById(R.id.score_view);
        view.setText(Integer.toString(score));
        view.setTypeface(t1);


    }

    public void openMainMenu(View view) {
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }

    public void openHighScores(View view) {
        Intent intentScores = new Intent(this, ViewScores.class);
        startActivity(intentScores);
    }

    @Override
    public void onBackPressed() {
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }


}
