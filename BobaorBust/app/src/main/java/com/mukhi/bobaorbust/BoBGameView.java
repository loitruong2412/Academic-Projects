package com.mukhi.bobaorbust;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;
import android.content.SharedPreferences;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

/**
 *  This class is where the game logic happens. It also draws the game on the screen
 */
public class BoBGameView extends SurfaceView implements Runnable, SensorEventListener {
    private Thread thread;
    private boolean isPlaying, isGameOver, goingLeft = false;
    private int screenX, screenY, lives, score = 0, oldLevel = 0, level = 0, bobaSpeed, germSpeed;
    public  static float screenRatioX, screenRatioY;
    private Paint paint;
    private Cup cup;
    private ArrayList<Germ> germs;
    private Random random;
    private GameActivity activity;
    private Bitmap gameBackground;
    private ArrayList<Boba> bobas;
    private SoundPlayer soundPlayer;
    private Context context;
    private SharedPreferences prefs;


    // tilt stuff
    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;

    private float[] accelOutput;
    private float[] magOutput;

    private float[] orientation = new float[3];
    private float[] startOrientation = null;
    private long frameTime;
    private LocalTime start = LocalTime.now();



    public BoBGameView(GameActivity activity, Context context) {
        super(activity);
        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        soundPlayer = new SoundPlayer(activity);

        this.screenX = Constants.SCREEN_X;
        this.screenY = Constants.SCREEN_Y;

        screenRatioX = screenX / Constants.DEFAULT_SCREEN_WIDTH;
        screenRatioY = screenY / Constants.DEFAULT_SCREEN_HEIGHT;

        paint = new Paint();
        paint.setTextSize(40 * screenRatioY);
        paint.setColor(Color.RED);
        cup = new Cup(getResources());

        // Germ
        germs = new ArrayList<>();
        loopCreateGerm(2);
        germSpeed = germs.get(0).getSpeed();

        // Boba
        bobas = new ArrayList<>();
        loopCreateBoba(6);
        bobaSpeed = bobas.get(0).getSpeed();

        this.context = context;

        // Random
        random = new Random();

        // Lives
        lives = 3;

        // Background
        gameBackground = BitmapFactory.decodeResource(getResources(),R.drawable.game_background);
        gameBackground = Bitmap.createScaledBitmap(gameBackground, screenX, screenY, false);

        //Orientation data (phone tilt)
        manager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        register();
        frameTime = System.currentTimeMillis();



    }

    @Override
    public void run() {

        while(isPlaying) {

            changePos();
            draw();
            sleep();
        }

    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(BoBGameView.this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void changePos() {
        level = score / 50;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (level > oldLevel) {
                    Toast.makeText(activity.getApplicationContext(), "LEVEL UP: " + level,
                            Toast.LENGTH_LONG).show();
                    oldLevel = level;
                }
            }
        });

        if (level > oldLevel) {
            levelCheck();
        }
        int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();

        if (getOrientation() != null
                && getStartOrientation() != null){

            float roll = getOrientation()[2]
                    - getStartOrientation()[2];

            float xSpeed = (2 * roll * Constants.SCREEN_X/1000f) * screenRatioX;

            int distance = cup.getX();
            distance += Math.abs(xSpeed*elapsedTime) > 5 ? xSpeed*elapsedTime: 0;

            if (distance - cup.getX() > 5) {
                goingLeft = false;
            }
            else if (distance - cup.getX() < -5 ){
                goingLeft = true;
            }
            cup.setCupX(distance);

        }


        // Checks if the cup tries to go outta the bounds of the screen
        if (cup.getX() < 0) {
            cup.setCupX(0);
        }
        else if (cup.getX() >= screenX - cup.getWidth()) {
            cup.setCupX(screenX - cup.getWidth());
        }

        // Move the germ
        for (Germ germ : germs) {
            int germY = germ.getY();
            int germNewSpeed = (int)((germSpeed + level) * screenRatioY);
            Log.d("Germ speed: ", germNewSpeed + "");
            germ.setSpeed(germNewSpeed);
            germY += germ.getSpeed();
            germ.setY(germY);

            int germHeight = germ.getHeight();
            // If the germ moves past the screen, place it back on top of the screen
            if (germ.getY() + germHeight > screenY) {
                germ.setY(-(random.nextInt(4 * screenY)) - germHeight);
                germ.setX(random.nextInt(screenX - germ.getWidth()));
            }

            // Check if the germ hits the cup
            if (Rect.intersects(germ.getCollisionShape(), cup.getCollisionShape())) {
                if (!prefs.getBoolean("isMute", false)) {
                    soundPlayer.playHitGermSound();
                }
                Log.d("GAME VIEW: ", "Germ hits cup");
                lives --;
                Log.d("GAME VIEW: ", "Lives: " + lives);
                germ.setY(-(random.nextInt(4 * screenY)) - germHeight);
                if (lives == 0) {
                    if (!prefs.getBoolean("isMute", false)) {
                        soundPlayer.playGameOverSound();
                    }
                    isGameOver = true;
                    return;
                }
            }


        }

        for (Boba boba : bobas) {
            int bobaY = boba.getY();
            int bobaNewSpeed = (int)((bobaSpeed + level) * screenRatioY);
            Log.d("Boba speed: ", bobaNewSpeed + "");
            boba.setSpeed(bobaNewSpeed);
            bobaY += boba.getSpeed();
            boba.setY(bobaY);

            int bobaHeight = boba.getHeight();
            // The 3 * is how far out of the screen we want to set the next boba. It gives it a fake
            // sense of not as many boba as 6 on the screen at a time.
            if (boba.getY() + bobaHeight > screenY) {
                boba.setY(-(random.nextInt(3 * screenY)) - bobaHeight);
                boba.setX(random.nextInt(screenX - boba.getWidth()));
            }
            if (Rect.intersects(boba.getCollisionShape(), cup.getCollisionShape())) {
                if (!prefs.getBoolean("isMute", false)){
                    soundPlayer.playHitBobaSound();
                }
                boba.setY(-(random.nextInt(3 * screenY)) - bobaHeight);
                score += 10;
                Log.d("Boba GAME VIEW", String.valueOf(score));
                return;
            }
        }

    }


    private void draw() {

        if(getHolder().getSurface().isValid()) {

            // Returns the current canvas to draw on
            Canvas canvas = getHolder().lockCanvas();


            // Sets the background color (NOTE: Get the hex color code int)
            canvas.drawBitmap(gameBackground,0,0,paint);
            //canvas.drawColor(Color.CYAN);

            for (Germ germ : germs) {
                canvas.drawBitmap(germ.getGerm(), germ.getX(), germ.getY(), paint);
            }

            canvas.drawText("Score: " + score, 30 * screenRatioX, 50 * screenRatioY, paint);
            canvas.drawText("Lives: "+ lives , 30 * screenRatioX, 120 * screenRatioY, paint);
            canvas.drawText("Level: "+ level , 30 * screenRatioX, 190 * screenRatioY, paint);


            for (Boba boba : bobas) {
                canvas.drawBitmap(boba.getBoba(), boba.getX(), boba.getY(), paint);
//                boba.increaseCounter();
            }

            if(isGameOver) {

                TimeCalculator gameTime = new TimeCalculator(start, LocalTime.now());
                Result gameResult = new Result(score, gameTime);

                DatabaseSingleton.getInstance(activity).databaseHelper.create(gameResult);

                isPlaying = false;
                canvas.drawBitmap(gameBackground,0,0,paint);
                getHolder().unlockCanvasAndPost(canvas);

                waitBeforeExiting();
                return;

            }

            canvas.drawBitmap(cup.getCup(goingLeft, level), cup.getX(), cup.getY(), paint);

            // Display drawn canvas on the screen
            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    private void waitBeforeExiting() {
        try {

            Thread.sleep(1000);
            Intent endIntent = new Intent(context, GameResult.class);
            context.startActivity(endIntent);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            cup.setIsMoving(true);
        }
        else {
            cup.setIsMoving(false);
        }
        return true;
    }

    private void levelCheck() {
        if (level == 4) {
           loopCreateGerm(2);
           loopCreateBoba(2);
           return;
        }
        else if (level == 11) {
            loopCreateGerm(4);
            loopCreateBoba(4);
            return;
        }
        else if (level == 19) {
            loopCreateGerm(4);
            loopCreateBoba(4);
            return;
        }
        return;
    }

    private void loopCreateGerm(int num) {
        for (int i = 0; i < num; i++) {
            germs.add(new Germ(getResources()));
        }
    }

    private void loopCreateBoba(int num) {
        for (int i = 0; i < num; i++) {
            bobas.add(new Boba(getResources()));
        }
    }

    public float[] getOrientation(){
        return orientation;
    }

    public float[] getStartOrientation() {
        return startOrientation;
    }

    public void register() {
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelOutput = event.values;
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magOutput = event.values;
        }
        if(accelOutput != null && magOutput != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R,I, accelOutput, magOutput);
            if (success){
                SensorManager.getOrientation(R, orientation);
                if (startOrientation == null) {
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0,
                            orientation.length);
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
