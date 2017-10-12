package com.noela.clocktimer;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;
    ImageView imageView;

    public void restartTimer () {

        counterIsActive = false;
        timerSeekBar.setEnabled(true);
        controllerButton.setText("Go");
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
    }


    public void updateTimer (int secondsLeft) {


        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }



        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);



    }


    public void controlTime (View view) {

        if (counterIsActive == false) {

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");



            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);


                }

                @Override
                public void onFinish() {

                    restartTimer();

                    ObjectAnimator
                            .ofFloat(imageView, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0)
                            .setDuration(13000)
                            .start();

                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                    mplayer.start();



                }
            }.start();
        } else {
            restartTimer();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar =(SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button)  findViewById(R.id.controllerButton);
        imageView = (ImageView)findViewById(R.id.imageViewId);

        timerSeekBar.setMax(3600);
        timerSeekBar.setProgress(30);


        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
