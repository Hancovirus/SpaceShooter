package com.example.spaceshooter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameOver extends AppCompatActivity {
    TextView pointsTV, pointsTVLabel, accountTV;

    Language language;
    private boolean audioPlayed = false;
    private List<Pair<String, Integer>> pairsList = new ArrayList<>();
    private int points;
    private String account;
    private String filename = "highscore";
    private String filepath = "MyFileDir";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        language = Language.getInstance();
        points = Objects.requireNonNull(getIntent().getExtras()).getInt("points");
        account = Objects.requireNonNull(getIntent().getExtras()).getString("account");
        accountTV = findViewById(R.id.accountTV);
        accountTV.setText(account);
        pointsTVLabel = findViewById(R.id.pointsTVLabel);
        pointsTVLabel.setText(language.getPoints() + ":");
        pointsTV = findViewById(R.id.pointsTV);
        pointsTV.setText("" + points);

        readTextFile();
        writeTextFile();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseAudio();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (audioPlayed == false)
        {
            playAudio();
            audioPlayed = true;
        }
        else
        {
            resumeAudio();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAudio();
    }

    private void stopAudio() {
        Intent myIntent = new Intent(GameOver.this, PlaySongService.class);
        stopService(myIntent);
    }
    private void pauseAudio() {
        Intent myIntent = new Intent(GameOver.this, PlaySongService.class);
        myIntent.setAction("PAUSE_MUSIC");
        startService(myIntent);
    }
    public void playAudio() {
        Intent myIntent = new Intent(GameOver.this, PlaySongService.class);
        myIntent.setAction("PLAY_SONG");
        myIntent.putExtra("SONG_ID", 3);
        startService(myIntent);
    }
    public void resumeAudio() {
        Intent myIntent = new Intent(GameOver.this, PlaySongService.class);
        startService(myIntent);
    }



    public void restart(View v) {
        stopAudio();
        Intent intent = new Intent(this, StartUp.class);
        startActivity(intent);
        finish();
    }

    public void exit(View V) {
        stopAudio();
        finish();
    }

    public void writeTextFile() {
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
        FileOutputStream fos = null;
        StringBuilder fileContent = new StringBuilder();
        boolean added = false;

        for (Pair<String, Integer> pair : pairsList) {
            if (!added && pair.second <= points) {
                fileContent.append(account).append("-").append(points).append("\n");
                added = true;
            }
            fileContent.append(pair.first).append("-").append(pair.second).append("\n");
        }

        if (!added)
            fileContent.append(account).append("-").append(points).append("\n");

        try {
            fos = new FileOutputStream(myExternalFile);
            fos.write(fileContent.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readTextFile() {
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);

        try {
            FileReader fr = new FileReader(myExternalFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            Pair<String, Integer> pair = null;
            while ((line = br.readLine()) != null) {
                String[] points = line.split("-");
                if (points.length == 2) {
                    String name = points[0];
                    int point = Integer.parseInt(points[1].replaceAll("[^\\d.]", ""));
                    pair = new Pair<>(name, point);
                    pairsList.add(pair);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
