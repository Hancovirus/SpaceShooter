package com.example.spaceshooter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Settings {
    private static String filename = "setting";
    private static String filepath = "MyFileDir";
    private static Context context;
    public static boolean sound = true;
    public static boolean music = true;

    public Settings(Context context) {
        Settings.context = context;
        readTextFile();
    }

    public static void writeTextFile() {
        File myExternalFile = new File(context.getExternalFilesDir(filepath), filename);
        FileOutputStream fos = null;
        String setting = "";
        if (sound)
            setting += "true-";
        else
            setting += "false-";

        if (music)
            setting += "true";
        else
            setting += "false";

        Log.d(TAG, "onWrite: " + sound);
        Log.d(TAG, "onWrite: " + music);

        try {
            fos = new FileOutputStream(myExternalFile);
            fos.write(setting.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void readTextFile() {
        File myExternalFile = new File(context.getExternalFilesDir(filepath), filename);

        try {
            FileReader fr = new FileReader(myExternalFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            Pair<Double, Double> pair = null;
            while ((line = br.readLine()) != null) {
                String[] settings = line.split("-");
                if (settings.length == 2) {
                    Log.d(TAG, "onRead: " + line);
                    if (Objects.equals(settings[0], "true")) {
                        sound = true;
                    } else {
                        sound = false;
                    }
                    if (Objects.equals(settings[1], "true")) {
                        music = true;
                    } else {
                        music = false;
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
