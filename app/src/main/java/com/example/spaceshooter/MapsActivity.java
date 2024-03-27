package com.example.spaceshooter;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.spaceshooter.databinding.ActivityMapsBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private List<Pair<Double, Double>> pairsList = new ArrayList<>();
    private int timePlay;
    Pair<Double, Double> lastPlayed;
    LatLng latLastPlayed;

    private String filename = "lastplayed";
    private String filepath = "MyFileDir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timePlay = 0;

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (isExternalStorageWritable()) {
            readTextFile();
            writeTextFile();
        }

        for (Pair<Double, Double> pair : pairsList) {
            timePlay++;
            LatLng played = new LatLng(pair.first, pair.second);
            mMap.addMarker(new MarkerOptions().position(played).title("Marker " + timePlay));
        }

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLastPlayed, 15);
        mMap.animateCamera(cameraUpdate);
    }

    public void back(View v) {
        Intent intent = new Intent(this, StartUp.class);
        startActivity(intent);
        finish();
    }

    public void writeTextFile() {
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
        FileOutputStream fos = null;
        StringBuilder fileContent = new StringBuilder();

        for (Pair<Double, Double> pair : pairsList) {
            fileContent.append(pair.first).append("-").append(pair.second).append("\n");
        }

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
            Pair<Double, Double> pair = null;
            while ((line = br.readLine()) != null) {
                String[] coordinates = line.split("-");
                if (coordinates.length == 2) {
                    double latitude = Double.parseDouble(coordinates[0]);
                    double longitude = Double.parseDouble(coordinates[1].replaceAll("[^\\d.]", ""));
                    pair = new Pair<>(latitude, longitude);
                    pairsList.add(pair);
                }
            }
            lastPlayed = pair;
            latLastPlayed = new LatLng(lastPlayed.first, lastPlayed.second);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}