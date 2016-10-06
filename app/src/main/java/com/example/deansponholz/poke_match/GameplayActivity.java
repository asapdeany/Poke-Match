package com.example.deansponholz.poke_match;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by deansponholz on 10/1/16.
 */

public class GameplayActivity extends AppCompatActivity {

    //instance data
    private Handler handler = null;

    //gameplay
    private GameplayFragment gameplayFragment;

    TextView clickCount = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);


        //initialize instance data
        handler = new Handler();
        clickCount = (TextView) findViewById(R.id.clickTotalTextView);
        gameplayFragment = (GameplayFragment) getFragmentManager().findFragmentById(R.id.gameInstanceFragment);
    }

    public void playerWin() {
        View textview = findViewById(R.id.restart);
        textview.setVisibility(View.VISIBLE);
    }

    public void hideRestart() {
        View textview = findViewById(R.id.restart);
        textview.setVisibility(View.GONE);
    }

    public void updateClickScore(final int score) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                clickCount.setText(getText(R.string.clickTotal) + Integer.toString(score));
            }
        });
    }

    public void restartGame() {
        gameplayFragment.restartGame();
        clickCount.setText(getText(R.string.clickTotal) + Integer.toString(0));
    }

    public void progressBegin() {
        View view = findViewById(R.id.progressBar_Layout);
        view.setVisibility(View.VISIBLE);
    }


    public void progressEnd(){
        View view = findViewById(R.id.progressBar_Layout);
        view.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}


