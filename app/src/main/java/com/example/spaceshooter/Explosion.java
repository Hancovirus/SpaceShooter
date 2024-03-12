package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {
    Bitmap explosion;
    int frame;
    int ex, ey;

    public Explosion(Context context, int ex, int ey) {
        explosion = BitmapFactory.decodeResource(context.getResources(), R.drawable.placeholder);
        frame = 0;
        this.ex = ex;
        this.ey = ey;
    }

    public Bitmap getExplosion() {
        return explosion;
    }

}
