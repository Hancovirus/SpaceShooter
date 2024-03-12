package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Player {
    Context context;
    Bitmap playerSpaceShip;
    int px, py;
    int pVel;
    Random random;
    boolean isAlive = true;

    public Player(Context context) {
        this.context = context;
        playerSpaceShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.placeholder);
        random = new Random();
        resetPlayerSpaceShip();
    }

    public Bitmap getPlayerSpaceShip() {
        return playerSpaceShip;
    }

    public int getPlayerSpaceShipWidth() {
        return playerSpaceShip.getWidth();
    }

    public int getPlayerSpaceShiHeight() {
        return playerSpaceShip.getHeight();
    }

    private void resetPlayerSpaceShip() {
        px = random.nextInt(SpaceShooter.screenWidth);
        py = SpaceShooter.screenHeight - playerSpaceShip.getHeight();
        pVel = 10 + random.nextInt(6);
    }
}
