package ru.company.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private boolean isTimerOn = false;
    private TextView textView;
    private SeekBar seekBar;
    private CountDownTimer countDownTimer;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.start_btn);
        seekBar.setMax(600);
        seekBar.setProgress(60);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                long progressOmMillis = i*1000;
                updateTimer(progressOmMillis);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void start(View view) {
        if (!isTimerOn){
            seekBar.setEnabled(false);
            button.setText("STOP");
            isTimerOn = true;

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer(l);
                }


                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mediaPlayer.start();
                    timerReset();
                }
            };
            countDownTimer.start();
        } else {
            timerReset();
        }

    }
    private void updateTimer(long l){
        int minutes = (int)l/1000/60;
        int seconds = (int)l/1000 - (minutes * 60);

        String stringMinutes = "";
        String stringSeconds = "";

        if (minutes < 10){
            stringMinutes = "0" + minutes;
        } else {stringMinutes = String.valueOf(minutes);}

        if (seconds < 10){
            stringSeconds = "0" + seconds;
        } else {stringSeconds = String.valueOf(seconds);}

        textView.setText(stringMinutes + ":" + stringSeconds);
    }

    private void timerReset(){
        countDownTimer.cancel();
        seekBar.setEnabled(true);
        button.setText("START");
        seekBar.setProgress(60);
        isTimerOn = false;
    }
}
