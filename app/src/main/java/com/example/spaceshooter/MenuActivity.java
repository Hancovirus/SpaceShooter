package com.example.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    ImageView sound, music, back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sound = findViewById(R.id.sound_button);
        music = findViewById(R.id.music_button);
        back = findViewById(R.id.yes_button);



        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.sound = !Settings.sound;
                if (!Settings.sound) sound.setImageResource(R.drawable.soundbuttonoff);
                else sound.setImageResource(R.drawable.soundbutton);
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.music = !Settings.music;
                if (!Settings.music) music.setImageResource(R.drawable.musicbuttonoff);
                else music.setImageResource(R.drawable.musicbutton);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToStart(v);
            }
        });
    }

    public void backToStart(View v) {
        Intent intent = new Intent(this, StartUp.class);
        startActivity(intent);
        finish();
    }

}
