package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {
    Button buttonPlayPause,buttonPrevious,buttonNext;
    SeekBar volumeSeekbar,musicSeekbar;
    TextView textviewFileNameMusic,textViewProgress,textViewTotalTime;

    //variables for passing the information from adapter class
    String title,filePath;
    int position;
    ArrayList<String>arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        buttonPlayPause=findViewById(R.id.play_pause);
        buttonPrevious=findViewById(R.id.skip_previous);
        buttonNext=findViewById(R.id.skip_next);
        volumeSeekbar=findViewById(R.id.volumeSeekbar);
        musicSeekbar=findViewById(R.id.musicSeekbar);
        textviewFileNameMusic=findViewById(R.id.textviewFileNameMusic);
        textViewProgress=findViewById(R.id.textViewProgress);
        textViewTotalTime=findViewById(R.id.textViewTotalTime);

        title = getIntent().getStringExtra ( "title");
        filePath = getIntent().getStringExtra ("filepath");
        position = getIntent().getIntExtra ("position", 0);
        arrayList = getIntent().getStringArrayListExtra ( "list");

    }


//    private void isMute(){
//
//        unmute.setImageResource(R.drawable.mute);
//        musicSeekbar.setProgress(0);
//    }

}