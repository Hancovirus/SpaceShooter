package com.example.spaceshooter;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;

public class PhaseBoss extends Phase{
    private int lastBulletFrame = 30;
    private int currentTime = 0;
    EnemySpaceShip boss;
    public PhaseBoss(Context context) {
        super.size = 1;
        super.requirement = 50;
        boss = new EnemySpaceShip(context);
        boss.ex = 500;
        boss.ey = 500;
        boss.eVelx = 30;
        //Create Boss
    }
    @Override
    public void enemyMovement(Canvas canvas, ArrayList<EnemySpaceShip> enemies, ArrayList<Bullet> eBullets, Context context, int screenWidth) {

        boss.ex += boss.eVelx;
        if (boss.ex + boss.getEnemySpaceShipWidth() >= screenWidth || boss.ex <=0) {
            boss.eVelx *= -1;
        } //Enemy bounce back

        canvas.drawBitmap(boss.getEnemySpaceShip(), boss.ex, boss.ey, null);
        //Draw Boss

        if (lastBulletFrame == 30) {
            currentTime += 30;
            int bulletVelovity = 20;
            double angle = Math.toRadians(currentTime % 360);
            Bullet enemyBullet1 = new Bullet(context, boss.ex + boss.getEnemySpaceShipWidth()/2, boss.ey + boss.getEnemySpaceShipWidth());
            enemyBullet1.bVelx = (int) (bulletVelovity * cos(angle));
            enemyBullet1.bVely = (int) (bulletVelovity * sin(angle));
            Bullet enemyBullet2 = new Bullet(context, boss.ex + boss.getEnemySpaceShipWidth()/2, boss.ey + boss.getEnemySpaceShipWidth());
            enemyBullet2.bVelx = (int) (bulletVelovity * cos(angle + Math.toRadians(180)));
            enemyBullet2.bVely = (int) (bulletVelovity * sin(angle + Math.toRadians(180)));
            eBullets.add(enemyBullet1);
            eBullets.add(enemyBullet2);
        } else {
            lastBulletFrame++;//Enemy shoot bullet
        }

        for (Bullet eBullet : eBullets) {
            eBullet.bx += eBullet.bVelx;
            eBullet.by += eBullet.bVely;
        }
    }

    @Override
    public void handleFinish(ArrayList<EnemySpaceShip> enemies) {

    }
}
