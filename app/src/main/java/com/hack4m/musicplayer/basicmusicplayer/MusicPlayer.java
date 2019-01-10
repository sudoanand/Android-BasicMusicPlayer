package com.hack4m.musicplayer.basicmusicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioModel audio;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Intent intent = getIntent();
        audio = (AudioModel) intent.getSerializableExtra("audio");

        TextView audioName = findViewById(R.id.audioName);
        Button playBtn = findViewById(R.id.playBtn);

        audioName.setText(audio.getaName());

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlaying();
            }
        });

    }

    public void startPlaying(){
        mediaPlayer = MediaPlayer.create(MusicPlayer.this, Uri.parse(audio.getaPath()));
        mediaPlayer.start();

        enableSeekBar();
    }

    public void stopPlaying(){
        mediaPlayer.stop();
    }

    public void enableSeekBar(){
        seekBar =  findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 10);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Update the progress depending on seek bar
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        stopPlaying();
        super.onBackPressed();
    }

}
