package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

public abstract class Phase {
    protected int size;
    protected int requirement;
    public abstract void enemyMovement(Canvas canvas, ArrayList<EnemySpaceShip> enemies, ArrayList<Bullet> eBullets, Context context, int screenWidth);
    public boolean checkRequirement(int points) {
        return points >= requirement;
    }
    public abstract void handleFinish(ArrayList<EnemySpaceShip> enemies);
}
