package com.example.spaceshooter;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.media.MediaPlayer;

public class PlaySongService extends Service {
    private MediaPlayer mediaPlayer;
    public PlaySongService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Create MediaPlayer object, to play your song.
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.menubgm);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                restartAudio();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null && action.equals("SET_VOLUME")) {
                int volumeLevel = intent.getIntExtra("VOLUME_LEVEL", -1);
                if (volumeLevel != -1) {
                    adjustVolume(volumeLevel);
                }
            } else if (action != null && action.equals("PAUSE_MUSIC")) {
                pauseMusic();
            } else if (action != null && action.equals("PLAY_SONG")) {
                int songId = intent.getIntExtra("SONG_ID", -1);
                if (songId != -1) {
                    playSong(songId);
                }
            } else {
                resumeMusic();
            }
        }
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Destroy
    @Override
    public void onDestroy() {
        // Release the resources
        mediaPlayer.release();
        super.onDestroy();
    }

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void resumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }


    private void restartAudio() {
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }

    private void playSong(int songId) {
        // Dựa vào songId để chọn bài hát cần phát
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        // Phát bài hát tương ứng với songId
        if (songId == 1) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.menubgm);
        } else if (songId == 2) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.gamebgm);
        }
        else if (songId == 3) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.gameover);
        }
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
    private void adjustVolume(int volumeLevel) {
        if (mediaPlayer != null) {
            float volume = (float) volumeLevel / 100;
            mediaPlayer.setVolume(volume, volume);
        }
    }


}