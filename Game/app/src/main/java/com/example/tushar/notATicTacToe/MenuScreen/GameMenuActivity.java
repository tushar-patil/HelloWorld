package com.example.tushar.notATicTacToe.MenuScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tushar.notATicTacToe.R;
import com.example.tushar.notATicTacToe.Utils.AlertDialog;
import com.example.tushar.notATicTacToe.Utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class GameMenuActivity extends Activity {
    private static String TAG = GameMenuActivity.class.getSimpleName();

    private ListView mGameMenuListView = null;
    private com.example.tushar.notATicTacToe.MenuScreen.GameMenuAdapter mGameMenuAdapter = null;

    List<String> mGameMenuStringList = new ArrayList<>();

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
        LogUtil.d(TAG, "Showing Game Menu");

        mGameMenuStringList.add(getResources().getString(R.string.play_game));
        mGameMenuStringList.add(getResources().getString(R.string.high_score));
        mGameMenuStringList.add(getResources().getString(R.string.settings));
        mGameMenuStringList.add(getResources().getString(R.string.help));

        mGameMenuAdapter = new com.example.tushar.notATicTacToe.MenuScreen.GameMenuAdapter(this, mGameMenuStringList);
        mGameMenuListView.setAdapter(mGameMenuAdapter);
        mGameMenuListView.setOnItemClickListener(mMenuItemClickListener);
    }

    AdapterView.OnItemClickListener mMenuItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            omMenuItemClick(position);
        }
    };

    private void omMenuItemClick(int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent(this, GameSubMenuActivity.class);
                startActivity(intent);
                break;
            case 1:
                AlertDialog.showWinningDialog(GameMenuActivity.this,
                        getResources().getString(R.string.stay_tuned),
                        getResources().getString(R.string.coming_soon),
                        getResources().getString(R.string.ok),
                        null);
                break;
            case 2:
                AlertDialog.showWinningDialog(GameMenuActivity.this,
                        getResources().getString(R.string.stay_tuned),
                        getResources().getString(R.string.coming_soon),
                        getResources().getString(R.string.ok),
                        null);
                break;
            case 3:
                AlertDialog.showWinningDialog(GameMenuActivity.this,
                        getResources().getString(R.string.stay_tuned),
                        getResources().getString(R.string.coming_soon),
                        getResources().getString(R.string.ok),
                        null);
                break;
            default:
                break;
        }
    }
}
