package com.example.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartUp extends AppCompatActivity {
    Button webButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        webButton = this.findViewById(R.id.btn_web);
        this.webButton.setOnClickListener(v -> goToWebActivity());
    }

    public void startGame(View v) {
        setContentView(new SpaceShooter(this));
    }

    public void goToWebActivity(){
        Intent intent = new Intent(this, WebActivity.class);
        this.startActivity(intent);
    }
}
