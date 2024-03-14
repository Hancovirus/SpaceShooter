package com.example.spaceshooter;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

public class PhaseBoss extends Phase{
    private int lastBulletFrame = 30;
    private int currentAngle = 0;
    private int bulletVelocity = 20;
    EnemySpaceShip boss;
    public PhaseBoss(Context context) {
        super.size = 1;
        super.requirement = 50;
        boss = new EnemySpaceShip(context);
        boss.ex = 500;
        boss.ey = 500;
        //Create Boss
    }
    @Override
    public void enemyMovement(Canvas canvas, ArrayList<EnemySpaceShip> enemies, ArrayList<Bullet> eBullets, Context context, int screenWidth) {
        canvas.drawBitmap(boss.getEnemySpaceShip(), boss.ex, boss.ey, null);
        //Draw Boss

        if (lastBulletFrame == 30) {
            currentAngle += 5;

            double angle = Math.toRadians(currentAngle % 360);
            Bullet enemyBullet1 = new Bullet(context, boss.ex + boss.getEnemySpaceShipWidth()/2, boss.ey + boss.getEnemySpaceShipWidth()/2, 1);
            enemyBullet1.angle = angle;
            enemyBullet1.bVelx = bulletVelocity;
            enemyBullet1.bVely = bulletVelocity;
            Bullet enemyBullet2 = new Bullet(context, boss.ex + boss.getEnemySpaceShipWidth()/2, boss.ey + boss.getEnemySpaceShipWidth()/2, 1);
            enemyBullet2.angle = angle + Math.toRadians(180);
            enemyBullet2.bVelx = bulletVelocity;
            enemyBullet2.bVely = bulletVelocity;
            eBullets.add(enemyBullet1);
            eBullets.add(enemyBullet2);
        } else {
            lastBulletFrame++;
        } //Enemy shoot bullet

        for (Bullet eBullet : eBullets) {
            eBullet.bVelx += bulletVelocity;
            eBullet.bVely += bulletVelocity;
            eBullet.bx = (int) (eBullet.bVelx * sin(eBullet.angle)) + boss.ex + boss.getEnemySpaceShipWidth()/2;
            eBullet.by = (int) (eBullet.bVely * cos(eBullet.angle)) + boss.ey + boss.getEnemySpaceShipWidth()/2;
        }
    }

    @Override
    public void handleFinish(ArrayList<EnemySpaceShip> enemies) {

    }
}
