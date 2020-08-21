package com.mukhi.bobaorbust;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *  This class represents the view high score activity in the home screen. It lets user view the
 *  top 10 highest scores so far.
 */
public class ViewScores extends AppCompatActivity {
    ArrayList<TextView> scores = new ArrayList<>();
    ArrayList<TextView> dates = new ArrayList<>();
    Typeface t1,t2;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);


        t1 = Typeface.createFromAsset(getAssets(),"fonts/Oxygen-Bold.ttf");
        t2 = Typeface.createFromAsset(getAssets(),"fonts/Oxygen-Regular.ttf");

        TextView title = findViewById(R.id.highscore);
        String high="Top Ten Scores";
        title.setText(high);
        title.setTypeface(t1);


        TextView dateHeader = findViewById(R.id.dateHeading);
        String date="Date";
        dateHeader.setText(date);
        dateHeader.setTypeface(t1);


        TextView scoreHeader = findViewById(R.id.scoreheading);
        String score = "Score";
        scoreHeader.setText(score);
        scoreHeader.setTypeface(t1);

        TextView date1 = findViewById(R.id.date1);
        date1.setText("");
        dates.add(date1);

        TextView date2 = findViewById(R.id.date2);
        date2.setText("");
        dates.add(date2);

        TextView date3 = findViewById(R.id.date3);
        date3.setText("");
        dates.add(date3);

        TextView date4 = findViewById(R.id.date4);
        date4.setText("");
        dates.add(date4);

        TextView date5 = findViewById(R.id.date5);
        date5.setText("");
        dates.add(date5);

        TextView date6 = findViewById(R.id.date6);
        date6.setText("");
        dates.add(date6);

        TextView date7 = findViewById(R.id.date7);
        date7.setText("");
        dates.add(date7);

        TextView date8 = findViewById(R.id.date8);
        date8.setText("");
        dates.add(date8);

        TextView date9 = findViewById(R.id.date9);
        date9.setText("");
        dates.add(date9);

        TextView date10 = findViewById(R.id.date10);
        date10.setText("");
        dates.add(date10);

        TextView score1 = findViewById(R.id.score1);
        score1.setText("");
        scores.add(score1);

        TextView score2 = findViewById(R.id.score2);
        score2.setText("");
        scores.add(score2);

        TextView score3 = findViewById(R.id.score3);
        score3.setText("");
        scores.add(score3);

        TextView score4 = findViewById(R.id.score4);
        score4.setText("");
        scores.add(score4);

        TextView score5 = findViewById(R.id.score5);
        score5.setText("");
        scores.add(score5);

        TextView score6 = findViewById(R.id.score6);
        score6.setText("");
        scores.add(score6);

        TextView score7 = findViewById(R.id.score7);
        score7.setText("");
        scores.add(score7);

        TextView score8 = findViewById(R.id.score8);
        score8.setText("");
        scores.add(score8);

        TextView score9 = findViewById(R.id.score9);
        score9.setText("");
        scores.add(score9);

        TextView score10 = findViewById(R.id.score10);
        score10.setText("");
        scores.add(score10);

        db = DatabaseSingleton.getInstance(this).databaseHelper;


        ArrayList<Result> topResults = db.getTopScores();

        for (int i = 0; i < topResults.size(); i++) {
            System.out.println("hi");
            System.out.println(topResults.get(i).getScore());
            System.out.println(topResults.get(i).getGameDate());

        }



         for (int i = 0; i < topResults.size(); i++) {

             Result result = topResults.get(i);
             System.out.println("1");

             TextView currentScore = scores.get(i);
             TextView currentDate = dates.get(i);

             System.out.println("2");

             currentScore.setText(Integer.toString(result.getScore()));
             currentScore.setTypeface(t2);

             System.out.println("3");

             currentDate.setText(topResults.get(i).getGameDate());
             currentDate.setTypeface(t2);

             System.out.println("4");

            }


    }


    public void resetScores(View view) {
        db.deleteAllScores();

        for (int i =0; i < scores.size(); i ++) {
            scores.get(i).setText("");
        }

        for (int i =0; i < dates.size(); i ++) {
            dates.get(i).setText("");
        }

    }

}
