package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Phase1 extends Phase {
    private int waitFrame;
<<<<<<< Updated upstream
    private Random random;
    public Phase1(int requirement) {
        super.size = 3;
        waitFrame = 50;
        random = new Random();
        super.requirement = requirement + 20;
=======
    public Phase1(int requirement) {
        super.size = 3;
        waitFrame = 50;
        super.requirement = requirement + 5;
>>>>>>> Stashed changes
    }
    @Override
    public void enemyMovement(Canvas canvas, ArrayList<EnemySpaceShip> enemies, ArrayList<Bullet> eBullets, Context context, int screenWidth) {
        if (waitFrame != 0) {
            waitFrame--;
            return;
        }
        //Cooldown between stages

        if (enemies.size() < super.size) {
            EnemySpaceShip enemy = new EnemySpaceShip(context, false);
<<<<<<< Updated upstream
            enemy.ex = random.nextInt(screenWidth / 2);
=======
>>>>>>> Stashed changes
            enemy.eVelx = 15;
            enemies.add(enemy);
        } //Create enemy

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).ex += enemies.get(i).eVelx;
            if (enemies.get(i).ex + enemies.get(i).getEnemySpaceShipWidth() >= screenWidth || enemies.get(i).ex <=0) {
                enemies.get(i).eVelx *= -1;
            } //Enemy bounce back

            canvas.drawBitmap(enemies.get(i).getEnemySpaceShip(), enemies.get(i).ex, enemies.get(i).ey, null);
            //Draw enemy

            if (eBullets.size() < 3) {
                Bullet enemyBullet = new Bullet(context, enemies.get(i).ex + enemies.get(i).getEnemySpaceShipWidth()/2, enemies.get(i).ey + i * enemies.get(i).getEnemySpaceShipWidth(), 0, false);
                enemyBullet.bVely = 20;
                eBullets.add(enemyBullet);
            } //Enemy shoot bullet

            for (Bullet eBullet : eBullets) {
                eBullet.by += eBullet.bVely;
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
    }
}
