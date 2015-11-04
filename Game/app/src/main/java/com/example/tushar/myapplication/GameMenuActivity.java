package com.example.tushar.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class GameMenuActivity extends Activity {
    private static String TAG = SplashScreenActivity.class.getSimpleName();

    private ListView mGameMenuListView = null;
    private GameMenuAdapter mGameMenuAdapter = null;

    private static List<String>MenuOptions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        initView();

        fillMenuOptions();
        showGameMenu();
    }

    private void initView() {
        mGameMenuListView = (ListView) findViewById(R.id.game_menu_lv);

    }

    private void fillMenuOptions() {
        MenuOptions.clear();
        MenuOptions.add("Play Game");
        MenuOptions.add("High Score");
        MenuOptions.add("Settings");
        MenuOptions.add("Help");
    }

    private void showGameMenu() {
        mGameMenuAdapter = new GameMenuAdapter(this, MenuOptions);
        mGameMenuListView.setAdapter(mGameMenuAdapter);
    }
}
