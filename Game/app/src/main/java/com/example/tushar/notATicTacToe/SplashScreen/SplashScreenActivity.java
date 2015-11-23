package com.example.tushar.notATicTacToe.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tushar.notATicTacToe.MenuScreen.GameMenuActivity;
import com.example.tushar.notATicTacToe.R;


public class SplashScreenActivity extends Activity implements View.OnClickListener {
    private static String TAG = SplashScreenActivity.class.getSimpleName();

    private Button mLetsPlayButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initView();
    }

    private void initView() {
        mLetsPlayButton = (Button) findViewById(R.id.splash_let_play_button);

        mLetsPlayButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_let_play_button:
                onLetPlayClick();
                break;

            default:
                break;
        }
    }

    private void onLetPlayClick() {
        Intent intent = new Intent(this, GameMenuActivity.class);
        startActivity(intent);
    }
}
