package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    private SeekBar seekBarMusic,seekBarVolume;
    ImageView skip_prev,skip_next,play_pause;
    private TextView textViewTime,textViewRemainingTime,txtMusicName;
    private MediaPlayer mp;
    int totalTime;
    String musicName,title;
    int position;
    ArrayList<String> musicsList = new ArrayList<>();
    private Animation animation;

    Runnable runnable;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        seekBarMusic = findViewById(R.id.musicSeekbar);
        seekBarVolume = findViewById(R.id.volumeSeekbar);
        play_pause = findViewById(R.id.play_pause);
        skip_prev = findViewById(R.id.skip_previous);
        skip_next = findViewById(R.id.skip_next);
        textViewTime = findViewById(R.id.textViewProgress);
        txtMusicName = findViewById(R.id.textviewFileNameMusic);
        textViewRemainingTime = findViewById(R.id.textViewTotalTime);

        animation = AnimationUtils.loadAnimation(this,R.anim.slide_animation);
        txtMusicName.setAnimation(animation);

        musicName = getIntent().getStringExtra("path");
        title = getIntent().getStringExtra("title");
        position = getIntent().getIntExtra("position",5);
        musicsList = getIntent().getStringArrayListExtra("musics");

        txtMusicName.setText(title);

        mp = new MediaPlayer();
        try {
            mp.setDataSource(musicName);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        totalTime = mp.getDuration();
        seekBarMusic.setMax(totalTime);

        skip_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.reset();

                if (position == 0)
                {
                    position = musicsList.size()-1;
                }
                else
                {
                    position--;
                }

                String newPath = musicsList.get(position);

                try {

                    mp.setDataSource(newPath);
                    mp.prepare();
                    mp.start();

                    play_pause.setImageResource(R.drawable.pause);

                    txtMusicName.clearAnimation();
                    txtMusicName.startAnimation(animation);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                txtMusicName.setText(newPath.substring(newPath.lastIndexOf("/")+1));
            }
        });


        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mp.isPlaying())
                {
                    mp.start();
                    play_pause.setImageDrawable(null);
                    play_pause.setImageResource(R.drawable.pause);
                }
                else
                {
                    mp.pause();
                    play_pause.setImageDrawable(null);
                    play_pause.setImageResource(R.drawable.play);
//                    play_pause.setBackgroundResource(R.drawable.play);
                }

            }
        });

        skip_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.reset();

                if (position == musicsList.size()-1)
                {
                    position = 0;
                }
                else
                {
                    position++;
                }

                String newPath = musicsList.get(position);

                try {
                    mp.setDataSource(newPath);
                    mp.prepare();
                    mp.start();

                    play_pause.setImageResource(R.drawable.pause);

                    txtMusicName.clearAnimation();
                    txtMusicName.startAnimation(animation);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                txtMusicName.setText(newPath.substring(newPath.lastIndexOf("/")+1));
            }
        });

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser)
                {
                    seekBarVolume.setProgress(progress);
                    float volumeLevel = progress / 100f;
                    Log.e("volume level : ",String.valueOf(volumeLevel));
                    mp.setVolume(volumeLevel,volumeLevel);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                {
                    mp.seekTo(progress);
                    seekBarMusic.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (mp != null)
                {
                    int cP = mp.getCurrentPosition();
                    Log.e("cp : ",String.valueOf(cP));
                    seekBarMusic.setProgress(cP);

                    String elapsedTime = createTimeLabel(cP);
                    textViewTime.setText(elapsedTime);

                    String lastTime = createTimeLabel(totalTime);
                    textViewRemainingTime.setText(lastTime);

                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                            mp.reset();

                            if (position == musicsList.size()-1)
                            {
                                position = 0;
                            }
                            else
                            {
                                position++;
                            }

                            String newPath = musicsList.get(position);

                            try {
                                mp.setDataSource(newPath);
                                mp.prepare();
                                mp.start();
                                play_pause.setImageResource(R.drawable.pause);
                                txtMusicName.clearAnimation();
                                txtMusicName.startAnimation(animation);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            txtMusicName.setText(newPath.substring(newPath.lastIndexOf("/")+1));

                        }
                    });

                    handler.postDelayed(runnable,1000);
                }
            }
        };

        handler.post(runnable);
    }

    public String createTimeLabel(int currentPosition)
    {
        String timeLabel;

        int minute = currentPosition / 1000 / 60;
        int second = currentPosition / 1000 % 60;

        if (second < 10)
        {
            timeLabel = minute+":0"+second;
        }
        else
        {
            timeLabel = minute + ":" + second;
        }

        return timeLabel;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mp.isPlaying())
        {
            mp.stop();
            finish();
        }
    }

    public void isMute(View view) {

        if(mp.isPlaying() || !mp.isPlaying()){
            AudioManager aManager=(AudioManager)getSystemService(AUDIO_SERVICE);
            aManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }
    }

    public void isUnmute(View view) {
        if(mp.isPlaying() || !mp.isPlaying()){
            AudioManager aManager=(AudioManager)getSystemService(AUDIO_SERVICE);
            aManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        }
    }
}