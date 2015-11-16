package com.example.tushar.myapplication.GameScreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.tushar.myapplication.R;
import com.example.tushar.myapplication.Utils.LogUtil;

public class MainGameActivity extends Activity {
    private static final String TAG = MainGameActivity.class.getSimpleName();

    private TextView mPlayerTurnTextView = null;
    private PLAYER_TURN mTurnOfPlayer = null;

    private TextView mHousePlayInfoTextView = null;

    private TableLayout mFirstHouse = null;
    private TableLayout mSecondHouse = null;
    private TableLayout mThirdHouse = null;

    private TableLayout mFourthHouse = null;
    private TableLayout mFifthHouse = null;
    private TableLayout mSixthHouse = null;

    private TableLayout mSeventhHouse = null;
    private TableLayout mEigthHouse = null;
    private TableLayout mNinthHouse = null;

    private TableLayout mCurrentHouse = null;

    private enum PLAYER_TURN {
        ONE(1), TWO(2);
        private int mPLAYER_TURN;

        PLAYER_TURN(int i) {
            this.mPLAYER_TURN = i;
        }
    }

    private int mHouseWinnerArray[]= new int[9];
    private int mCurrentHouseInt = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        initView();
        mTurnOfPlayer = PLAYER_TURN.ONE;
        mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));

        mHousePlayInfoTextView.setText(getResources().getString(R.string.house_play_info_tv) + " 1");

        mFirstHouse.setClickable(true);
        mCurrentHouse = mFirstHouse;
    }

    private void initView() {
        mPlayerTurnTextView = (TextView) findViewById(R.id.player_turn_text);

        mHousePlayInfoTextView = (TextView) findViewById(R.id.house_play_info_tv);

        mFirstHouse = (TableLayout) findViewById(R.id.house_1);
        mSecondHouse = (TableLayout) findViewById(R.id.house_2);
        mThirdHouse = (TableLayout) findViewById(R.id.house_3);

        mFourthHouse = (TableLayout) findViewById(R.id.house_4);
        mFifthHouse = (TableLayout) findViewById(R.id.house_5);
        mSixthHouse = (TableLayout) findViewById(R.id.house_6);

        mSeventhHouse = (TableLayout) findViewById(R.id.house_7);
        mEigthHouse = (TableLayout) findViewById(R.id.house_8);
        mNinthHouse = (TableLayout) findViewById(R.id.house_9);

        mSecondHouse.setEnabled(false);
        mThirdHouse.setEnabled(false);

        mFourthHouse.setEnabled(false);
        mFifthHouse.setEnabled(false);
        mSixthHouse.setEnabled(false);

        mSeventhHouse.setEnabled(false);
        mEigthHouse.setEnabled(false);
        mNinthHouse.setEnabled(false);

    }

    public void oneClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            TableLayout gotHouse = (TableLayout) ((ViewGroup) v.getParent()).getParent();
            LogUtil.d(TAG, "Got House = " + gotHouse.getId() + "Current House" + mCurrentHouse.getId());
            LogUtil.d(TAG, "Got House = " + gotHouse.toString());
            LogUtil.d(TAG, "Current House = " + mCurrentHouse.toString());
            if (!gotHouse.equals(mCurrentHouse)) {
                return;
            }
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
            initializationsAfterClick(1, mFirstHouse);
        }
    }

    public void twoClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            TableLayout gotHouse = (TableLayout) ((ViewGroup) v.getParent()).getParent();
            LogUtil.d(TAG, "Got House = " + gotHouse.getId() + "Current House" + mCurrentHouse.getId());
            LogUtil.d(TAG, "Got House = " + gotHouse.toString());
            LogUtil.d(TAG, "Current House = " + mCurrentHouse.toString());
            if (!gotHouse.equals(mCurrentHouse)) {
                return;
            }
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
            initializationsAfterClick(2, mSecondHouse);
        }
    }

    public void threeClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            TableLayout gotHouse = (TableLayout) ((ViewGroup) v.getParent()).getParent();
            LogUtil.d(TAG, "Got House = " + gotHouse.getId() + "Current House" + mCurrentHouse.getId());
            LogUtil.d(TAG, "Got House = " + gotHouse.toString());
            LogUtil.d(TAG, "Current House = " + mCurrentHouse.toString());
            if (!gotHouse.equals(mCurrentHouse)) {
                return;
            }
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
            initializationsAfterClick(3, mThirdHouse);
        }
    }

    public void fourClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            TableLayout gotHouse = (TableLayout) ((ViewGroup) v.getParent()).getParent();
            LogUtil.d(TAG, "Got House = " + gotHouse.getId() + "Current House" + mCurrentHouse.getId());
            LogUtil.d(TAG, "Got House = " + gotHouse.toString());
            LogUtil.d(TAG, "Current House = " + mCurrentHouse.toString());
            if (!gotHouse.equals(mCurrentHouse)) {
                return;
            }
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
            initializationsAfterClick(4, mFourthHouse);
        }
    }


    public void fiveclick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            TableLayout gotHouse = (TableLayout) ((ViewGroup) v.getParent()).getParent();
            LogUtil.d(TAG, "Got House = " + gotHouse.getId() + "Current House" + mCurrentHouse.getId());
            LogUtil.d(TAG, "Got House = " + gotHouse.toString());
            LogUtil.d(TAG, "Current House = " + mCurrentHouse.toString());
            if (!gotHouse.equals(mCurrentHouse)) {
                return;
            }
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
            initializationsAfterClick(5, mFifthHouse);
        }
    }

    public void sixClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            TableLayout gotHouse = (TableLayout) ((ViewGroup) v.getParent()).getParent();
            LogUtil.d(TAG, "Got House = " + gotHouse.getId() + "Current House" + mCurrentHouse.getId());
            LogUtil.d(TAG, "Got House = " + gotHouse.toString());
            LogUtil.d(TAG, "Current House = " + mCurrentHouse.toString());
            if (!gotHouse.equals(mCurrentHouse)) {
                return;
            }
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
            initializationsAfterClick(6, mSixthHouse);
        }
    }

    public void sevenClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            TableLayout gotHouse = (TableLayout) ((ViewGroup) v.getParent()).getParent();
            LogUtil.d(TAG, "Got House = " + gotHouse.getId() + "Current House" + mCurrentHouse.getId());
            LogUtil.d(TAG, "Got House = " + gotHouse.toString());
            LogUtil.d(TAG, "Current House = " + mCurrentHouse.toString());
            if (!gotHouse.equals(mCurrentHouse)) {
                return;
            }
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
            initializationsAfterClick(7, mSeventhHouse);

        }
    }

    public void eightClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            TableLayout gotHouse = (TableLayout) ((ViewGroup) v.getParent()).getParent();
            LogUtil.d(TAG, "Got House = " + gotHouse.getId() + "Current House" + mCurrentHouse.getId());
            LogUtil.d(TAG, "Got House = " + gotHouse.toString());
            LogUtil.d(TAG, "Current House = " + mCurrentHouse.toString());
            if (!gotHouse.equals(mCurrentHouse)) {
                return;
            }
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
            initializationsAfterClick(8, mEigthHouse);
        }
    }

    public void nineClick(View v) {
        ImageView imageView = (ImageView) v;
        if (imageView.isEnabled()) {
            TableLayout gotHouse = (TableLayout) ((ViewGroup) v.getParent()).getParent();
            LogUtil.d(TAG, "Got House = " + gotHouse.getId() + "Current House" + mCurrentHouse.getId());
            LogUtil.d(TAG, "Got House = " + gotHouse.toString());
            LogUtil.d(TAG, "Current House = " + mCurrentHouse.toString());

            if (!gotHouse.equals(mCurrentHouse)) {
                return;
            }
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
            initializationsAfterClick(9, mNinthHouse);
        }
    }

    boolean isGameWon() {

        return false;
    }

    boolean isCurrentHouseWon() {

        if (((ImageView) mCurrentHouse.getChildAt(0)).getDrawable().getConstantState() ==
                ((ImageView) mCurrentHouse.getChildAt(1)).getDrawable().getConstantState() &&
                ((ImageView) mCurrentHouse.getChildAt(1)).getDrawable().getConstantState() ==
                        ((ImageView) mCurrentHouse.getChildAt(2)).getDrawable().getConstantState()) {

            return true;
        } else if (((ImageView) mCurrentHouse.getChildAt(4)).getDrawable().getConstantState() ==
                ((ImageView) mCurrentHouse.getChildAt(5)).getDrawable().getConstantState() &&
                ((ImageView) mCurrentHouse.getChildAt(5)).getDrawable().getConstantState() ==
                        ((ImageView) mCurrentHouse.getChildAt(6)).getDrawable().getConstantState()) {
            return true;
        }
        return false;
    }

    void initializationsAfterClick(int nextHouseInt, TableLayout nextHouse) {
        mCurrentHouse.setEnabled(false);
        nextHouse.setEnabled(true);
        mCurrentHouse = nextHouse;
        mCurrentHouseInt = nextHouseInt;
        mHousePlayInfoTextView.setText(getResources().getString(R.string.house_play_info_tv) + " " + nextHouseInt + " ");

    }
}
