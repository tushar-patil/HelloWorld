package com.example.tushar.myapplication.MenuScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tushar.myapplication.GameScreen.MainGameActivity;
import com.example.tushar.myapplication.R;
import com.example.tushar.myapplication.SplashScreen.SplashScreenActivity;
import com.example.tushar.myapplication.Utils.Constants;


public class GameMenuActivity extends Activity {
    private static String TAG = SplashScreenActivity.class.getSimpleName();

    private ListView mGameMenuListView = null;
    private GameMenuAdapter mGameMenuAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        initView();

        showGameMenu();
    }

    private void initView() {
        mGameMenuListView = (ListView) findViewById(R.id.game_menu_lv);

    }

    private void showGameMenu() {
        mGameMenuAdapter = new GameMenuAdapter(this, Constants.MenuOptions);
        mGameMenuListView.setAdapter(mGameMenuAdapter);
        mGameMenuListView.setOnItemClickListener(mMenuItemClickListener);
    }

    AdapterView.OnItemClickListener mMenuItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               omMenuItemClick(Constants.MenuOptions.get(position));
        }
    };

    private void omMenuItemClick(String selectedMenuOption) {
        switch (selectedMenuOption) {
            case Constants.NEW_GAME:
                Intent intent = new Intent(this, MainGameActivity.class);
                startActivity(intent);
                break;
            case Constants.HIGH_SCORE:
                break;
            case Constants.SETTINGS:
                break;
            case Constants.HELP:
                break;
            default:
                break;
        }
    }
}
