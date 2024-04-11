package com.example.spaceshooter;

import static com.example.spaceshooter.SpaceShooter.screenHeight;
import static com.example.spaceshooter.SpaceShooter.screenWidth;
import static java.lang.Math.cos;
import static java.lang.Math.random;
import static java.lang.Math.sin;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PhaseBoss extends Phase{
    private long currentAngle;
    private final int bulletVelocity = 20;
    private boolean isSpawned = false;
    private int waitFrame;
    private int p0Rotaion;
    private int p1Rotaion;
    private int p2Rotaion;
    private Rect bossImg;
    private static int pattern = 0;
    EnemySpaceShip boss;
    public PhaseBoss(Context context, int requirement) {
<<<<<<< Updated upstream
        super.requirement = requirement + 30;
=======
        super.requirement = requirement + 5;
>>>>>>> Stashed changes
        boss = new EnemySpaceShip(context, true);
        boss.ex = 0;
        boss.ey = 0;
        waitFrame = 50;
        p0Rotaion = 56;
        p2Rotaion = 87;
        currentAngle = 0;
        bossImg = new Rect(0, 0, boss.getEnemySpaceShipWidth(), boss.getEnemySpaceShipHeight() / 2);
        //Create Boss
    }
    @Override
    public void enemyMovement(Canvas canvas, ArrayList<EnemySpaceShip> enemies, ArrayList<Bullet> eBullets, Context context, int screenWidth) {
        if (waitFrame != 0) {
            waitFrame--;
            return;
        }
        //Cooldown between stages

        if (!isSpawned) {
            enemies.add(boss);
            isSpawned = true;
        }

        canvas.drawBitmap(boss.getEnemySpaceShip(), null, bossImg, null);
        //Draw Boss

        if (pattern == 0)
            handlePattern0(context, eBullets);
        else if (pattern == 1) {
            handlePattern1(context, eBullets);
        } else {
            handlePattern2(context, eBullets);
        }

    }

    private void handlePattern0(Context context, ArrayList<Bullet> eBullets) {
            currentAngle += p0Rotaion;

            double angle = Math.toRadians(currentAngle % 360);

            Bullet enemyBullet1 = new Bullet(context, boss.ex + boss.getEnemySpaceShipWidth()/2, boss.ey + boss.getEnemySpaceShipWidth()/2, 1, false);
            enemyBullet1.angle = angle;
            enemyBullet1.bVelx = bulletVelocity;
            enemyBullet1.bVely = bulletVelocity;

            Bullet enemyBullet2 = new Bullet(context, boss.ex + boss.getEnemySpaceShipWidth()/2, boss.ey + boss.getEnemySpaceShipWidth()/2, 1, false);
            enemyBullet2.angle = angle + Math.toRadians(180);
            enemyBullet2.bVelx = bulletVelocity;
            enemyBullet2.bVely = bulletVelocity;

            eBullets.add(enemyBullet1);
            eBullets.add(enemyBullet2);

            //Enemy shoot bullet

        for (Bullet eBullet : eBullets) {
            eBullet.bVelx += bulletVelocity;
            eBullet.bVely += bulletVelocity;
            eBullet.bx = (int) (eBullet.bVelx * sin(eBullet.angle)) + boss.ex + boss.getEnemySpaceShipWidth()/2;
            eBullet.by = (int) (eBullet.bVely * cos(eBullet.angle)) + boss.ey + boss.getEnemySpaceShipWidth()/2;
        }
    }

    Random generator = new Random();
    private void handlePattern1(Context context, ArrayList<Bullet> eBullets) {
        currentAngle += generator.nextInt(90);

        double angle = Math.toRadians(currentAngle % 360);

        Bullet enemyBullet1 = new Bullet(context, boss.ex + boss.getEnemySpaceShipWidth()/2, boss.ey + boss.getEnemySpaceShipWidth()/2, 1, false);
        enemyBullet1.angle = angle;
        enemyBullet1.bVelx = bulletVelocity;
        enemyBullet1.bVely = bulletVelocity;

        eBullets.add(enemyBullet1);

        currentAngle += generator.nextInt(90);

        angle = Math.toRadians(currentAngle % 360);

        Bullet enemyBullet2 = new Bullet(context, boss.ex + boss.getEnemySpaceShipWidth()/2, boss.ey + boss.getEnemySpaceShipWidth()/2, 1, false);
        enemyBullet2.angle = angle;
        enemyBullet2.bVelx = bulletVelocity;
        enemyBullet2.bVely = bulletVelocity;

        eBullets.add(enemyBullet2);
        //Enemy shoot bullet

        for (Bullet eBullet : eBullets) {
            eBullet.bVelx += bulletVelocity;
            eBullet.bVely += bulletVelocity;
            eBullet.bx = (int) (eBullet.bVelx * sin(eBullet.angle)) + boss.ex + boss.getEnemySpaceShipWidth()/2;
            eBullet.by = (int) (eBullet.bVely * cos(eBullet.angle)) + boss.ey + boss.getEnemySpaceShipWidth()/2;
        }
    }

    private void handlePattern2(Context context, ArrayList<Bullet> eBullets) {
        currentAngle += p2Rotaion;

        double angle = Math.toRadians(currentAngle % 360);

        Bullet enemyBullet1 = new Bullet(context, boss.getEnemySpaceShipWidth()/4, boss.ey + boss.getEnemySpaceShipWidth()/2, 1, false);
        enemyBullet1.angle = angle;
        enemyBullet1.bVelx = bulletVelocity;
        enemyBullet1.bVely = bulletVelocity;

        Bullet enemyBullet2 = new Bullet(context, 3 * boss.getEnemySpaceShipWidth()/4, boss.ey + boss.getEnemySpaceShipWidth()/2, 1, false);
        enemyBullet2.angle = angle;
        enemyBullet2.bVelx = bulletVelocity;
        enemyBullet2.bVely = bulletVelocity;

        eBullets.add(enemyBullet1);
        eBullets.add(enemyBullet2);
        //Enemy shoot bullet
        int count = 0;
        for (Bullet eBullet : eBullets) {
            eBullet.bVelx += bulletVelocity;
            eBullet.bVely += bulletVelocity;
            if (count == 0){
                eBullet.bx = (int) (eBullet.bVelx * sin(eBullet.angle)) + boss.ex + boss.getEnemySpaceShipWidth() / 4;
                eBullet.by = (int) (eBullet.bVely * cos(eBullet.angle)) + boss.ey + boss.getEnemySpaceShipWidth() / 2;
                count = 1;
            } else {
                eBullet.bx = (int) (eBullet.bVelx * sin(eBullet.angle)) + boss.ex + 3 * boss.getEnemySpaceShipWidth() / 4;
                eBullet.by = (int) (eBullet.bVely * cos(eBullet.angle)) + boss.ey + boss.getEnemySpaceShipWidth() / 2;
                count = 0;
            }
        }
    }

    @Override
    public void handleFinish(ArrayList<EnemySpaceShip> enemies, ArrayList<Bullet> eBullets) {
        for (Iterator<EnemySpaceShip> enemyIterator = enemies.iterator(); enemyIterator.hasNext();) {
            EnemySpaceShip enemy = enemyIterator.next();
            enemyIterator.remove(); // Remove enemy
        }

        for (Iterator<Bullet> bulletIterator = eBullets.iterator(); bulletIterator.hasNext();) {
            Bullet bullet = bulletIterator.next();
            bulletIterator.remove(); // Remove enemy
        }

        pattern++;
        if (pattern == 3)
            pattern = 0;
    }
}
