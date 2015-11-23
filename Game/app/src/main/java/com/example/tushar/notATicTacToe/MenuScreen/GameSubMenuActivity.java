package com.example.tushar.notATicTacToe.MenuScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tushar.notATicTacToe.GameScreen.MainGameActivity;
import com.example.tushar.notATicTacToe.R;
import com.example.tushar.notATicTacToe.Utils.LogUtil;
import com.example.tushar.notATicTacToe.WiFiService.WiFiDeviceListActivity;

import java.util.ArrayList;
import java.util.List;

public class GameSubMenuActivity extends Activity {
    private static String TAG = GameSubMenuActivity.class.getSimpleName();

    private ListView mGameSubMenuListView = null;
    private com.example.tushar.notATicTacToe.MenuScreen.GameSubMenuAdapter mGameMenuAdapter = null;

    List<String> mGameSubManuListString = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_sub_menu);

        initView();

        showGameMenu();
    }
    private void initView() {
        mGameSubMenuListView = (ListView) findViewById(R.id.game_menu_lv);

    }

    private void showGameMenu() {
        LogUtil.d(TAG, "showing Game SubMenu");
        mGameSubManuListString.add(getResources().getString(R.string.one_player));
        mGameSubManuListString.add(getResources().getString(R.string.two_player));
        mGameSubManuListString.add(getResources().getString(R.string.two_player_over_wifi));

        mGameMenuAdapter = new com.example.tushar.notATicTacToe.MenuScreen.GameSubMenuAdapter(this, mGameSubManuListString);
        mGameSubMenuListView.setAdapter(mGameMenuAdapter);
        mGameSubMenuListView.setOnItemClickListener(mMenuItemClickListener);
    }

    AdapterView.OnItemClickListener mMenuItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            omMenuItemClick(position);
        }
    };

    private void omMenuItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                break;
            case 1:
                intent = new Intent(this, MainGameActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, WiFiDeviceListActivity.class);
                startActivity(intent);
                break;
            case 3:
                break;
            default:
                break;
        }
    }
}
