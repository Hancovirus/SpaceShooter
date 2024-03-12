package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {
    Bitmap bullet;
    Context context;
    int bx, by;
    int bVelx, bVely;

    public Bullet(Context context, int bx, int by) {
        this.context = context;
        bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        this.bx = bx;
        this.by = by;
    }

    public int getBulletHeight() {
        return bullet.getHeight();
    }

    public int getBulletWidth() {
        return bullet.getWidth();
    }

    public Bitmap getBullet() {
        return bullet;
    }

}
