package com.mukhi.bobaorbust;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import static com.mukhi.bobaorbust.BoBGameView.screenRatioX;
import static com.mukhi.bobaorbust.BoBGameView.screenRatioY;


import java.util.Random;

/**
 *  This class represents a boba object in the game
 */
public class Boba {
    private int bobaX, bobaY;
    private int width, height, speed = 20;
    private Bitmap boba;

    Boba (Resources res) {
        Random r = new Random();
        boba = BitmapFactory.decodeResource(res, R.drawable.boba);

        width = boba.getWidth();
        height = boba.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        boba = Bitmap.createScaledBitmap(boba, width, height, false);

        bobaX = r.nextInt((Constants.SCREEN_X - width));
        bobaY = -(r.nextInt(2 * Constants.SCREEN_Y)) - height;
    }

    public Bitmap getBoba() {
        return boba;

    }

    public int getSpeed() {
        return this.speed;
    }

    public int setSpeed(int speed) {
        return this.speed = speed;
    }

    public void setBobaX(int bobaX) {
        this.bobaX = bobaX;
    }

    public void setX(int x) {
        this.bobaX = x;
    }

    public int getX() {
        return bobaX;
    }

    public int getY() {
        return bobaY;
    }

    public void setY(int y) {
        this.bobaY = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Rect getCollisionShape() {
        return new Rect(bobaX , bobaY, bobaX + width, bobaY + height);
    }


}
