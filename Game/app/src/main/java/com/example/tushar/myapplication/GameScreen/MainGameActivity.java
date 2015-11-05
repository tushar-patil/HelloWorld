package com.example.tushar.myapplication.GameScreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.tushar.myapplication.R;

public class MainGameActivity extends Activity {

    private TextView mPlayerTurnTextView = null;

    private PLAYER_TURN mTurnOfPlayer = null;

    private TableLayout mFirstHouse = 
    private enum PLAYER_TURN {
        ONE(1), TWO(2);
        private int mPLAYER_TURN;

        PLAYER_TURN(int i) {
            this.mPLAYER_TURN = i;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        initView();
        mTurnOfPlayer = PLAYER_TURN.ONE;
        mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));
    }

    private void initView() {
        mPlayerTurnTextView = (TextView) findViewById(R.id.player_turn_text);
    }

    public void oneClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            if (mTurnOfPlayer == PLAYER_TURN.ONE) {
                imageView.setImageResource(R.drawable.empty_dot);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));
                mTurnOfPlayer = PLAYER_TURN.TWO;
            } else {
                imageView.setImageResource(R.drawable.cross);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));
                mTurnOfPlayer = PLAYER_TURN.ONE;
            }
        }
    }

    public void twoClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            if (mTurnOfPlayer == PLAYER_TURN.ONE) {
                imageView.setImageResource(R.drawable.empty_dot);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));
                mTurnOfPlayer = PLAYER_TURN.ONE;

            }
        }
    }

    public void threeClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            if (mTurnOfPlayer == PLAYER_TURN.ONE) {
                imageView.setImageResource(R.drawable.empty_dot);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.ONE;

            }
        }
    }

    public void fourClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            if (mTurnOfPlayer == PLAYER_TURN.ONE) {
                imageView.setImageResource(R.drawable.empty_dot);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.ONE;

            }
        }
    }


    public void fiveclick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            if (mTurnOfPlayer == PLAYER_TURN.ONE) {
                imageView.setImageResource(R.drawable.empty_dot);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.ONE;

            }
            imageView.setEnabled(false);

        }
    }

    public void sixClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            if (mTurnOfPlayer == PLAYER_TURN.ONE) {
                imageView.setImageResource(R.drawable.empty_dot);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.ONE;

            }
            imageView.setEnabled(false);

        }
    }

    public void sevenClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            if (mTurnOfPlayer == PLAYER_TURN.ONE) {
                imageView.setImageResource(R.drawable.empty_dot);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.ONE;

            }
            imageView.setEnabled(false);

        }
    }

    public void eightClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            if (mTurnOfPlayer == PLAYER_TURN.ONE) {
                imageView.setImageResource(R.drawable.empty_dot);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.ONE;

            }
            imageView.setEnabled(false);

        }
    }

    public void nineClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            if (mTurnOfPlayer == PLAYER_TURN.ONE) {
                imageView.setImageResource(R.drawable.empty_dot);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));
                mTurnOfPlayer = PLAYER_TURN.ONE;

            }
            imageView.setEnabled(false);

        }
    }
}
