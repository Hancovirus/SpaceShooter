package com.example.spaceshooter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class GameOver extends AppCompatActivity {
    TextView tvPoints;
    private MediaPlayer BGMPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        int points = Objects.requireNonNull(getIntent().getExtras()).getInt("points");
        tvPoints = findViewById(R.id.tvPoints);
        tvPoints.setText("" + points);
        BGMPlayer = MediaPlayer.create(this, R.raw.gameover);
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

    public void restart(View v) {
        Intent intent = new Intent(this, StartUp.class);
        startActivity(intent);
        finish();
    }

    public void exit(View V) {
        finish();
    }
}
