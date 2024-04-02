package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Explosion {
    Bitmap explosion[] = new Bitmap[8];
    int frame;
    int ex, ey, width, height;
    Rect rect;

    public Explosion(Context context, int ex, int ey, int width, int height) {
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.exp1);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.exp2);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.exp3);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.exp4);
        explosion[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.exp5);
        explosion[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.exp6);
        explosion[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.exp7);
        explosion[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.exp8);
        frame = 0;
        this.ex = ex;
        this.ey = ey;
        this.width = width;
        this.height = height;
    }

    public Bitmap getExplosion(int frame) {
        return explosion[frame];
    }

}
