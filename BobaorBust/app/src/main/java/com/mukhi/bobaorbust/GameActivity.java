package com.mukhi.bobaorbust;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

/**
 *  This class represents the game activity
 */
public class GameActivity extends AppCompatActivity {
    private BoBGameView bobaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point screen = new Point();
        getWindowManager().getDefaultDisplay().getSize(screen);
        Constants.SCREEN_X = screen.x;
        Constants.SCREEN_Y = screen.y;

        Context context = this;

        bobaView = new BoBGameView(GameActivity.this, context);

        setContentView(bobaView);
    }

    @Override
    protected void onPause() {
        System.out.println("I am here");
        super.onPause();
        bobaView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bobaView.resume();
    }

}
