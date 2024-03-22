package com.example.spaceshooter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartUp extends AppCompatActivity {
    private MediaPlayer BGMPlayer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        BGMPlayer = MediaPlayer.create(this, R.raw.menubgm);
        BGMPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                restartAudio();
            }
        });
        BGMPlayer.start();
    }

    private void restartAudio() {
        BGMPlayer.seekTo(0);
        BGMPlayer.start();
    }

    private void stopAudio() {
        BGMPlayer.release();
        BGMPlayer = null;
    }

    public void startGame(View v) {
        stopAudio();
        setContentView(new SpaceShooter(this));
    }
}
