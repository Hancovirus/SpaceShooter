package com.example.spaceshooter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SpaceShooter extends View {
    Bitmap background, lifeImage;
    Context context;
    Handler handler;
    int UPDATE_MILLIS = 20;
    Rect screen;
    static int screenWidth, screenHeight;
    int points = 0;
    int life = 3;
    Paint scorePaint;
    int TEXT_SIZE = 80;
    boolean paused = false;
    Player player;
    Random random;
    Phase phase;
    ArrayList<Bullet> eBullets, pBullets;
    ArrayList<EnemySpaceShip> enemies;
    Explosion explosion;
    ArrayList<Explosion> explosions;
    int lastBulletFrame = 0;

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public SpaceShooter(Context context) {
        super(context);
        this.context = context;
        phase = new Phase1();
        random = new Random();
        eBullets = new ArrayList<>();
        pBullets = new ArrayList<>();
        explosions = new ArrayList<>();
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        screen = new Rect(0, 0, screenWidth, screenHeight);
        enemies = new ArrayList<>();
        player = new Player(context);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.placeholder);
        handler = new Handler(Looper.getMainLooper());
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    private void drawLife(Canvas canvas) {
        for (int i = life; i >= 1; i--) {
            canvas.drawBitmap(lifeImage, screenWidth - (30 + lifeImage.getWidth()) * i, 0, null);
        }
    }

    private void handleDeath() {
        if (life == 0) {
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }

    private void handleEnemy(Canvas canvas) {
        phase.enemyMovement(canvas, enemies, eBullets, context, screenWidth);
        if (phase.checkRequirement(points)) {
            phase.handleFinish(enemies);
            nextPhase();
        }
    }

    private void nextPhase() {
        if (phase instanceof Phase1)
            phase = new Phase2();
        else if (phase instanceof Phase2)
            phase = new PhaseBoss(context);

    }

    private void handlePlayer(Canvas canvas) {
        if (player.isAlive) {
            if (player.px > screenWidth - player.getPlayerSpaceShipWidth()) {
                player.px = screenWidth - player.getPlayerSpaceShipWidth();
            } else if (player.px < 0) {
                player.px = 0;
            }
            canvas.drawBitmap(player.getPlayerSpaceShip(), player.px, player.py, null);
        }//Draw player

        if (lastBulletFrame == 30) {
            Bullet pBullet = new Bullet(context, player.px + player.getPlayerSpaceShipWidth()/2, player.py, 0);
            pBullets.add(pBullet);
            lastBulletFrame = 0;
        } else {
            lastBulletFrame++;
        } //Player shoot bullet
    }

    private void handleBulletCollision(Canvas canvas) {
        for (Iterator<Bullet> iterator = eBullets.iterator(); iterator.hasNext();) {
            Bullet eBullet = iterator.next();
            canvas.drawBitmap(eBullet.getBullet(), eBullet.bx, eBullet.by, null);
            if ((eBullet.bx >= player.px
                    && eBullet.bx <= player.px + player.getPlayerSpaceShipWidth()
                    && eBullet.by >= player.py
                    && eBullet.by <= player.py + player.getPlayerSpaceShiHeight())
            || (eBullet.bx + eBullet.getBulletWidth() >= player.px
                    && eBullet.bx + eBullet.getBulletWidth() <= player.px + player.getPlayerSpaceShipWidth()
                    && eBullet.by >= player.py
                    && eBullet.by <= player.py + player.getPlayerSpaceShiHeight())
            || (eBullet.bx >= player.px
                    && eBullet.bx <= player.px + player.getPlayerSpaceShipWidth()
                    && eBullet.by + eBullet.getBulletHeight() >= player.py
                    && eBullet.by + eBullet.getBulletHeight() <= player.py + player.getPlayerSpaceShiHeight())
            || (eBullet.bx + eBullet.getBulletWidth() >= player.px
                    && eBullet.bx + eBullet.getBulletWidth() <= player.px + player.getPlayerSpaceShipWidth()
                    && eBullet.by + eBullet.getBulletHeight() >= player.py
                    && eBullet.by + eBullet.getBulletHeight() <= player.py + player.getPlayerSpaceShiHeight())) {
                //life--;
                iterator.remove();
                //explosion = new Explosion(context, player.px, player.py);
                //explosions.add(explosion);
            } else if (eBullet.by >= screenHeight) {
                iterator.remove();
            }
        } //Enemy bullet handle

        for (Iterator<Bullet> iterator = pBullets.iterator(); iterator.hasNext();) {
            Bullet pBullet = iterator.next();
            pBullet.by -= 150;
            canvas.drawBitmap(pBullet.getBullet(), pBullet.bx, pBullet.by, null);

            for (Iterator<EnemySpaceShip> enemyIterator = enemies.iterator(); enemyIterator.hasNext();) {
                EnemySpaceShip enemy = enemyIterator.next();
                if ((pBullet.bx >= enemy.ex
                        && pBullet.bx <= enemy.ex + enemy.getEnemySpaceShipWidth()
                        && pBullet.by <= enemy.ey + enemy.getEnemySpaceShipHeight()
                        && pBullet.by >= enemy.ey)
                || (pBullet.bx + pBullet.getBulletWidth() >= enemy.ex
                        && pBullet.bx + pBullet.getBulletWidth() <= enemy.ex + enemy.getEnemySpaceShipWidth()
                        && pBullet.by <= enemy.ey + enemy.getEnemySpaceShipHeight()
                        && pBullet.by >= enemy.ey)
                || (pBullet.bx >= enemy.ex
                        && pBullet.bx <= enemy.ex + enemy.getEnemySpaceShipWidth()
                        && pBullet.by + pBullet.getBulletHeight() <= enemy.ey + enemy.getEnemySpaceShipHeight()
                        && pBullet.by + pBullet.getBulletHeight() >= enemy.ey)
                || (pBullet.bx + pBullet.getBulletWidth() >= enemy.ex
                        && pBullet.bx + pBullet.getBulletWidth() <= enemy.ex + enemy.getEnemySpaceShipWidth()
                        && pBullet.by + pBullet.getBulletHeight() <= enemy.ey + enemy.getEnemySpaceShipHeight()
                        && pBullet.by + pBullet.getBulletHeight() >= enemy.ey)) {
                    points++;
                    iterator.remove();
                    enemyIterator.remove(); // Remove enemy
                    break;
                }
            }

            if (pBullet.by <= -100) {
                iterator.remove(); // Remove pBullet
            }
        }
        //Player bullet handle
    }

    private void handleExplosion(Canvas canvas) {
        for (int i = 0; i < explosions.size(); i++) {
            canvas.drawBitmap(explosions.get(i).getExplosion(), explosions.get(i).ex, explosions.get(i).ey, null);
            explosions.get(i).frame++;
            if (explosions.get(i).frame > 8) {
                explosions.remove(i);
            }
        } //Create explosion for 8 frame
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        canvas.drawBitmap(background, null, screen, null);
        canvas.drawText("Pt: " + points, 0, TEXT_SIZE, scorePaint);

        drawLife(canvas);

        handleDeath();

        handleEnemy(canvas);

        handlePlayer(canvas);

        handleBulletCollision(canvas);

        //handleExplosion(canvas);

        if (!paused) {
            handler.postDelayed(runnable, UPDATE_MILLIS);
        } //Execute runnable after 20 Milli-secs
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            player.px = touchX - player.getPlayerSpaceShipWidth()/2;
            player.py = touchY - player.getPlayerSpaceShiHeight()/2;
        }
        return true;
    }
}
