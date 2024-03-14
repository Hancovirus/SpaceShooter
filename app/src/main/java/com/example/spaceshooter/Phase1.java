package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Phase1 extends Phase{
    public Phase1() {
        super.size = 3;
        super.requirement = 5;
    }
    @Override
    public void enemyMovement(Canvas canvas, ArrayList<EnemySpaceShip> enemies, ArrayList<Bullet> eBullets, Context context, int screenWidth) {
        if (enemies.size() < super.size) {
            EnemySpaceShip enemy = new EnemySpaceShip(context);
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
                Bullet enemyBullet = new Bullet(context, enemies.get(i).ex + enemies.get(i).getEnemySpaceShipWidth()/2, enemies.get(i).ey + i * enemies.get(i).getEnemySpaceShipWidth(), 0);
                enemyBullet.bVely = 20;
                eBullets.add(enemyBullet);
            } //Enemy shoot bullet

            for (Bullet eBullet : eBullets) {
                eBullet.by += eBullet.bVely;
            }
        }
    }

    @Override
    public void handleFinish(ArrayList<EnemySpaceShip> enemies) {
        for (Iterator<EnemySpaceShip> enemyIterator = enemies.iterator(); enemyIterator.hasNext(); ) {
            EnemySpaceShip enemy = enemyIterator.next();
            enemyIterator.remove(); // Remove enemy
        }
    }
}
