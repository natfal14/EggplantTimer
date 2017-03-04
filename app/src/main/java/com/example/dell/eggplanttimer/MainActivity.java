package com.example.dell.eggplanttimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MediaPlayer chicken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView slime = (ImageView) findViewById(R.id.slime);
        slime.setVisibility(View.GONE);

        TextView reset = (TextView) findViewById(R.id.reset);
        reset.setVisibility(View.GONE);

        chicken = MediaPlayer.create(this, R.raw.chicken);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(600000);

        TextView timeLeft = (TextView) findViewById(R.id.timeLeft);

        seekBar.setProgress(30000);
        timeLeft.setText("00:30");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int mins = progress / 60000;
                float remainder = ((float) progress / 60000) - mins;
                int secs = (int) (remainder * 60);

                String curMins = String.format("%02d", mins);
                String curSecs = String.format("%02d", secs);

                TextView timeLeft = (TextView) findViewById(R.id.timeLeft);
                timeLeft.setText(curMins + ":" + curSecs);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    CountDownTimer waitTimer;

    public void timer() {

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        int timer = seekBar.getProgress();

        waitTimer = new CountDownTimer(timer, 1000) {

            public void onTick(long millisecondsUntilDone) {

                int mins = (int) millisecondsUntilDone / 60000;
                float remainder = ((float) millisecondsUntilDone / 60000) - mins;
                int secs = (int) (remainder * 60);

                String curMins = String.format("%02d", mins);
                String curSecs = String.format("%02d", secs);

                TextView timeLeft = (TextView) findViewById(R.id.timeLeft);
                timeLeft.setText(curMins + ":" + curSecs);

            }

            public void onFinish() {

                TextView timeLeft = (TextView) findViewById(R.id.timeLeft);
                timeLeft.setVisibility(View.GONE);

                ImageView eggplant = (ImageView) findViewById(R.id.eggplant);
                eggplant.setImageResource(R.drawable.eggplantdone);

                chicken.start();
                chicken.setLooping(true);

                Button start = (Button) findViewById(R.id.start);
                Button stop = (Button) findViewById(R.id.stop);
                start.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
                stop.setText("Reset");

                ImageView slime = (ImageView) findViewById(R.id.slime);
                slime.setVisibility(View.VISIBLE);

                TextView reset = (TextView) findViewById(R.id.reset);
                reset.setVisibility(View.VISIBLE);

                stop.setX(110);
                stop.setY(150);

                stop.setAlpha(0f);

                //stop.setVisibility(View.INVISIBLE);

            }

        }.start();

    }

    public void start(View view) {

        Button start = (Button) findViewById(R.id.start);
        start.setVisibility(View.GONE);

        Button stop = (Button) findViewById(R.id.stop);
        stop.setVisibility(View.VISIBLE);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setVisibility(View.GONE);

        timer();

        }

    public void stop(View view) {

        Button start = (Button) findViewById(R.id.start);
        start.setVisibility(View.VISIBLE);

        Button stop = (Button) findViewById(R.id.stop);
        stop.setVisibility(View.GONE);

        TextView timeLeft = (TextView) findViewById(R.id.timeLeft);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(30000);
        seekBar.setVisibility(View.VISIBLE);

        timeLeft.setVisibility(View.VISIBLE);
        timeLeft.setText("00:30");

        ImageView eggplant = (ImageView) findViewById(R.id.eggplant);
        eggplant.setImageResource(R.drawable.eggplant);

        waitTimer.cancel();

        ImageView slime = (ImageView) findViewById(R.id.slime);
        slime.setVisibility(View.GONE);

        TextView reset = (TextView) findViewById(R.id.reset);
        reset.setVisibility(View.GONE);

        stop.setX(start.getX());
        stop.setY(start.getY());

        stop.setText("Stop");


        stop.setAlpha(1f);

        chicken.stop();
        chicken.reset();
        chicken = MediaPlayer.create(this, R.raw.chicken);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
