package com.example.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoard extends AppCompatActivity {
    ListView leaderlv;
    List<String> contactList;
    Button back;
    private List<Pair<String, Integer>> pairsList = new ArrayList<>();
    private int points;
    private String account;
    private String filename = "highscore";
    private String filepath = "MyFileDir";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_board);
        contactList = new ArrayList<>();

        leaderlv = (ListView) findViewById(R.id.leaderlv);
        readTextFile();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactList);
        leaderlv.setAdapter(adapter);

        back = (Button) findViewById(R.id.back);
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

    public void readTextFile() {
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);

        try {
            FileReader fr = new FileReader(myExternalFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                contactList.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
