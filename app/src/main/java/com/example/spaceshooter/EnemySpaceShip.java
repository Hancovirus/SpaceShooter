package com.example.spaceshooter;

import static com.example.spaceshooter.SpaceShooter.screenWidth;

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

    public EnemySpaceShip(Context context, boolean boss) {
        this.context = context;
        if (!boss)
            enemySpaceShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.threat);
        else
            enemySpaceShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss);
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
        if (enemySpaceShip.getWidth() < screenWidth)
            return enemySpaceShip.getWidth();
        else
            return screenWidth;
    }

    private void resetEnemySpaceShip() {
        ex = 0;
        ey = random.nextInt(1000);
        eVelx = 0;
        eVely = 0;
    }

}
