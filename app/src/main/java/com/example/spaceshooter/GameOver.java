package com.example.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class GameOver extends AppCompatActivity {
    TextView tvPoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        int points = Objects.requireNonNull(getIntent().getExtras()).getInt("points");
        tvPoints = findViewById(R.id.tvPoints);
        tvPoints.setText("" + points);
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
