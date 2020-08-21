package com.mukhi.bobaorbust;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

import static com.mukhi.bobaorbust.BoBGameView.screenRatioX;
import static com.mukhi.bobaorbust.BoBGameView.screenRatioY;

/**
 *  This class represents a cup object in the game
 */
public class Cup {

    private int cupX, cupY;
    private int width, height;
    private Bitmap cup, cup2, cup3, cup4;
    private boolean isMoving = false;

    Cup (Resources res) {
        cup = BitmapFactory.decodeResource(res, R.drawable.cup);
        cup2 = BitmapFactory.decodeResource(res, R.drawable.cup2);
        cup3 = BitmapFactory.decodeResource(res, R.drawable.cup3);
        cup4 = BitmapFactory.decodeResource(res, R.drawable.cup4);

        width = cup.getWidth();
        height = cup.getHeight();

        width /= 10;
        height /= 10;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);


        cupX = Constants.SCREEN_X / 2;
        cupY = Constants.SCREEN_Y - height - (int) (65 * screenRatioY);

        cup = Bitmap.createScaledBitmap(cup, width, height, false);
        cup2 = Bitmap.createScaledBitmap(cup2, width, height, false);
        cup3 = Bitmap.createScaledBitmap(cup3, width, height, false);
        cup4 = Bitmap.createScaledBitmap(cup4, width, height, false);
    }

    public void setCupX(int newXCoord) {
        this.cupX = newXCoord;
    }

    public void setIsMoving(boolean b) {
        this.isMoving = b;
    }

    public Bitmap getCup(Boolean goingLeft, int level) {
        if (level < 4) {
            return flipImage(goingLeft, cup);
        }
        else if (level < 11) {
            return flipImage(goingLeft, cup2);
        }
        else if (level < 19) {
            return flipImage(goingLeft, cup3);
        }
        else {
            return flipImage(goingLeft, cup4);
        }
    }

    private Bitmap flipImage(boolean goingLeft, Bitmap cupImage) {
        if (!goingLeft) {
            Matrix m = new Matrix();
            m.preScale(-1, 1);
            Bitmap newD = Bitmap.createBitmap(cupImage, 0, 0, width, height, m,
                    false);
            return newD;
        }
        return cupImage;
    }

    public int getX() {
        return this.cupX;
    }

    public int getY() {
        return this.cupY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean getIsMoving() {
        return this.isMoving;
    }

    public Rect getCollisionShape() {
        return new Rect(cupX , cupY, cupX + width, cupY + height);
    }
}
