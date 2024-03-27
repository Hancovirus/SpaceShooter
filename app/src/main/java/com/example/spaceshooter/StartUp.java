package com.example.spaceshooter;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StartUp extends AppCompatActivity {
    TextView startTV;
    ImageView start;
    Button lastPlayed;
    private MediaPlayer BGMPlayer;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;
    private List<Pair<Double, Double>> pairsList = new ArrayList<>();
    private String filename = "lastplayed";
    private String filepath = "MyFileDir";
    private Language language;
    private com.google.android.gms.location.LocationRequest locationRequest = com.google.android.gms.location.LocationRequest.create();
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private Pair<Double, Double> locationPair;
    private Double latitude, longitude;
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                try {
                    locationPair = new Pair<>(location.getLatitude(), location.getLongitude());
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (!addresses.isEmpty()) {
                        Language.setCountryName(addresses.get(0).getCountryName());
                        language = Language.getInstance();
                        startTV = findViewById(R.id.startTV);
                        startTV.setText(language.getStart());
                    } else {
                        Log.e(TAG, "No address found.");
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Geocoder error: " + e.getMessage());
                }
            }
            stopLocationUpdates();
            lastPlayed.setEnabled(true);
            start.setEnabled(true);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // Initialize permission request launcher
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                //getLastLocation();
                checkSettingsAndStartLocationUpdates();
            } else {
                Log.e(TAG, "Permissions not granted.");
            }
        });

        // Request location permissions if not granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            checkSettingsAndStartLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });

        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult(StartUp.this, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        locationRequest.setInterval(200);
        locationRequest.setFastestInterval(100);
        locationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY);
        geocoder = new Geocoder(this, Locale.getDefault());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        BGMPlayer = MediaPlayer.create(this, R.raw.menubgm);
        BGMPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                restartAudio();
            }
        });
        BGMPlayer.start();
        lastPlayed = (Button) findViewById(R.id.lastPlayed);
        start = (ImageView) findViewById(R.id.start);
        lastPlayed.setEnabled(false);
        start.setEnabled(false);
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
        readTextFile();
        writeTextFile();
        setContentView(new SpaceShooter(this));
    }

    public void seePast(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
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
            pairsList.add(new Pair<>(locationPair.first, locationPair.second));
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
