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
    TextView pointsTV, pointsTVLabel;
    private MediaPlayer BGMPlayer;
    Language language;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        language = Language.getInstance();
        int points = Objects.requireNonNull(getIntent().getExtras()).getInt("points");
        pointsTVLabel = findViewById(R.id.pointsTVLabel);
        pointsTVLabel.setText(language.getPoints() + ":");
        pointsTV = findViewById(R.id.pointsTV);
        pointsTV.setText("" + points);
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
