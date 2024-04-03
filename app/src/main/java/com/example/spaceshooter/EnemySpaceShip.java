package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class EnemySpaceShip {
    Context context;
    Bitmap enemySpaceShip;
    int ex, ey;
    int eVelx, eVely;
    Random random;

    public EnemySpaceShip(Context context) {
        this.context = context;
        enemySpaceShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.threat);
        random = new Random();
        resetEnemySpaceShip();
    }

    public Bitmap getEnemySpaceShip(){
        return enemySpaceShip;
    }

    public int getEnemySpaceShipHeight() {
        return enemySpaceShip.getHeight();
    }

    public int getEnemySpaceShipWidth() {
        return enemySpaceShip.getWidth();
    }

    private void resetEnemySpaceShip() {
        ex = 0;
        ey = random.nextInt(1000);
        eVelx = 0;
        eVely = 0;
    }

}
