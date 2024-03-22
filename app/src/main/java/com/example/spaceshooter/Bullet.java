package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

public class Bullet {
    Bitmap bullet;
    Context context;
    int bx, by;
    double angle;
    int bVelx, bVely;
    private MediaPlayer bulletSoundPlayer;

    public Bullet(Context context, int bx, int by, int bulletType, boolean sound) {
        if (sound) {
            bulletSoundPlayer = MediaPlayer.create(context, R.raw.bulletsound);
            bulletSoundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (bulletSoundPlayer != null) {
                        bulletSoundPlayer.release();
                        bulletSoundPlayer = null;
                    }
                }
            });
            bulletSoundPlayer.start();
        }
        this.context = context;
        if (bulletType == 0)
            bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        else if (bulletType == 1)
            bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet1);
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
