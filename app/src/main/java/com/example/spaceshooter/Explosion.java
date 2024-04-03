package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Explosion {
    Bitmap explosion;
    int frame;
    int ex, ey, width, height;
    Rect rect;

    public Explosion(Context context, int ex, int ey, int width, int height) {
        explosion = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);
        frame = 0;
        this.ex = ex;
        this.ey = ey;
        this.width = width;
        this.height = height;
    }

    public Bitmap getExplosion() {
        return explosion;
    }

}
