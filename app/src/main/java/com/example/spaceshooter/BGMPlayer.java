package com.example.spaceshooter;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BGMPlayer extends Service {
    private static final String TAG = null;
    MediaPlayer player;
    private String music;
    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.menubgm);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
        player.start();
    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void setMusic(String m) {
        music = m;
    }

    protected void onNewIntent() {
        player.pause();
    }
}