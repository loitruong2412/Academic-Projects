package com.mukhi.bobaorbust;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import static com.mukhi.bobaorbust.BoBGameView.screenRatioX;
import static com.mukhi.bobaorbust.BoBGameView.screenRatioY;

/**
 *  This class represents a germ object in the game.
 */
public class Germ {
    private int germX, germY, width, height, speed = 20;
    private Bitmap germ;

    Germ (Resources res) {
        germ = BitmapFactory.decodeResource(res, R.drawable.germ);

        width = germ.getWidth();
        height = germ.getHeight();

        width /= 10;
        height /= 10;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        germ = Bitmap.createScaledBitmap(germ, width, height, false);

        Random r = new Random();
        germX = r.nextInt(Constants.SCREEN_X - germ.getWidth());
        germY = -(r.nextInt(5 * Constants.SCREEN_Y)) - height;

    }

    public Bitmap getGerm() {
        return germ;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setX(int x) {
        this.germX = x;
    }

    public int getX() {
        return germX;
    }

    public int getY() {
        return germY;
    }

    public void setY(int y) {
        this.germY = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Rect getCollisionShape() {
        return new Rect(germX , germY, germX + width, germY + height);
    }

}
