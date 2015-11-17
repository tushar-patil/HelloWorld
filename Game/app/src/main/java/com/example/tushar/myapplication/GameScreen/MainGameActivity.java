package com.example.tushar.myapplication.GameScreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.tushar.myapplication.R;
import com.example.tushar.myapplication.Utils.AlertDialog;
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

    private Button mUndoButton = null;

    private TableLayout mCurrentHouse = null;

    private enum PLAYER_TURN {
        ONE(1), TWO(2);
        private int mPLAYER_TURN;

        PLAYER_TURN(int i) {
            this.mPLAYER_TURN = i;
        }
    }

    private int mHouseWinnerArray[] = new int[9];
    private int mCurrentHouseInt = -1;

    private int PLAYER_ONE = 1;
    private int PLAYER_TWO = 2;

    private float opacityLevel = 0.4f;

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
        mCurrentHouseInt = 1;
        mCurrentHouse.setAlpha(1f);
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

        mUndoButton = (Button) findViewById(R.id.undo_btn);

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
                imageView.setTag(PLAYER_TURN.ONE);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));
                mTurnOfPlayer = PLAYER_TURN.TWO;
            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
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
                imageView.setTag(PLAYER_TURN.ONE);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
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
                imageView.setTag(PLAYER_TURN.ONE);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));
                mTurnOfPlayer = PLAYER_TURN.TWO;
            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
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
                imageView.setTag(PLAYER_TURN.ONE);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
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
                imageView.setTag(PLAYER_TURN.ONE);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));


                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
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
                imageView.setTag(PLAYER_TURN.ONE);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
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
                imageView.setTag(PLAYER_TURN.ONE);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
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
                imageView.setTag(PLAYER_TURN.ONE);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
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
                imageView.setTag(PLAYER_TURN.ONE);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_tuen_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_tuen_txt));
                mTurnOfPlayer = PLAYER_TURN.ONE;

            }
            imageView.setEnabled(false);
            initializationsAfterClick(9, mNinthHouse);
        }
    }

    private boolean isGameWon() {

        setResultCurrentHouse();
        int housesWonByPlayer1 = 0, housesWonByPlayer2 = 0;
        LogUtil.d(TAG, "HouseWinterArray -> ");
        for (int counter = 0; counter < 9; counter++) {
            LogUtil.d(TAG, "mHouseWinnerArray[" + counter + "] = " +
                    mHouseWinnerArray[counter]);
            if (mHouseWinnerArray[counter] == PLAYER_ONE) {
                housesWonByPlayer1++;
            } else if (mHouseWinnerArray[counter] == PLAYER_TWO) {
                housesWonByPlayer2++;
            }
        }

        if (housesWonByPlayer1 >= 5) {
            AlertDialog.showWinningDialog(this, "Congratulations!!", "Player 1 won the Game", "Ok",
                    new AlertDialog.WinDialogOkListener() {
                        @Override
                        public void onOkClick() {
                            finish();
                        }
                    });
            return true;
        } else if (housesWonByPlayer2 >= 5) {
            AlertDialog.showWinningDialog(this, "Congratulations!!", "Player 2 won the Game", "Ok",
                    new AlertDialog.WinDialogOkListener() {
                        @Override
                        public void onOkClick() {
                            finish();
                        }
                    });
            return true;
        }
        return false;
    }

    private void setResultCurrentHouse() {

        if (mHouseWinnerArray[mCurrentHouseInt - 1] != 0) {
            return;
        }
        TableRow firstRow = (TableRow) mCurrentHouse.getChildAt(0);
        TableRow secondRow = (TableRow) mCurrentHouse.getChildAt(1);
        TableRow thirdRow = (TableRow) mCurrentHouse.getChildAt(2);

        LogUtil.d(TAG, "firstRow Id " + firstRow.getId());
        LogUtil.d(TAG, "firstRow toString " + firstRow.toString());

        LogUtil.d(TAG, "secondRow Id " + secondRow.getId());
        LogUtil.d(TAG, "secondRow toString " + secondRow.toString());

        LogUtil.d(TAG, "thirdRow Id " + thirdRow.getId());
        LogUtil.d(TAG, "thirdRow toString " + thirdRow.toString());

        if (isRowFilledBySamePlayer(firstRow)) {
            setHouseWin((ImageView) firstRow.getChildAt(0));
            return;
        } else if (isRowFilledBySamePlayer(secondRow)) {
            setHouseWin((ImageView) secondRow.getChildAt(0));
            return;
        } else if (isRowFilledBySamePlayer(thirdRow)) {
            setHouseWin((ImageView) thirdRow.getChildAt(0));
            return;
        } else {
            for (int i = 0; i < 3; i++)
                if (isTheseImageviewsHasSameImage((ImageView) firstRow.getChildAt(i),
                        (ImageView) secondRow.getChildAt(i), (ImageView) thirdRow.getChildAt(i))) {
                    setHouseWin((ImageView) firstRow.getChildAt(i));
                    return;
                }
        }

        if (isTheseImageviewsHasSameImage((ImageView) firstRow.getChildAt(0),
                (ImageView) secondRow.getChildAt(1), (ImageView) thirdRow.getChildAt(2))) {
            setHouseWin((ImageView) firstRow.getChildAt(0));
            return;
        }

        if (isTheseImageviewsHasSameImage((ImageView) firstRow.getChildAt(2),
                (ImageView) secondRow.getChildAt(1), (ImageView) thirdRow.getChildAt(0))) {
            setHouseWin((ImageView) firstRow.getChildAt(2));
        }
    }

    void initializationsAfterClick(int nextHouseInt, TableLayout nextHouse) {
        if (!isGameWon()) {
            mCurrentHouse.setAlpha(opacityLevel);
            mCurrentHouse.setEnabled(false);

            nextHouse.setEnabled(true);
            nextHouse.setAlpha(1);

            mCurrentHouse = nextHouse;
            mCurrentHouseInt = nextHouseInt;
            mHousePlayInfoTextView.setText(getResources().getString(R.string.house_play_info_tv) + " " + nextHouseInt + " ");
        }
    }

    boolean isRowFilledBySamePlayer(TableRow receivedRow) {
        ImageView im1 = (ImageView) receivedRow.getChildAt(0);
        ImageView im2 = (ImageView) receivedRow.getChildAt(1);
        ImageView im3 = (ImageView) receivedRow.getChildAt(2);

        if (im1.getDrawable() == null || im2.getDrawable() == null ||
                im3.getDrawable() == null) {
            return false;
        }

        LogUtil.d(TAG, "im1 ConstantState" +
                ((ImageView) receivedRow.getChildAt(0)).getDrawable().getConstantState());

        LogUtil.d(TAG, "im2 ConstantState()" +
                ((ImageView) receivedRow.getChildAt(1)).getDrawable().getConstantState());

        LogUtil.d(TAG, "im3 ConstantState()" +
                ((ImageView) receivedRow.getChildAt(2)).getDrawable().getConstantState());

        return ((im1.getDrawable().getConstantState() ==
                im2.getDrawable().getConstantState()) &&
                (im2.getDrawable().getConstantState() ==
                        im3.getDrawable().getConstantState()));
    }

    boolean isTheseImageviewsHasSameImage(ImageView im1, ImageView im2, ImageView im3) {
        if (im1.getDrawable() == null || im2.getDrawable() == null ||
                im3.getDrawable() == null) {
            return false;
        }

        return ((im1.getDrawable().getConstantState() ==
                im2.getDrawable().getConstantState()) &&
                (im2.getDrawable().getConstantState() ==
                        im3.getDrawable().getConstantState()));
    }

    void setHouseWin(ImageView gotImage) {
        LogUtil.d(TAG, "ImageView with Tag " + gotImage.getTag());
        if (gotImage.getTag() == PLAYER_TURN.ONE) {
            mHouseWinnerArray[mCurrentHouseInt - 1] = PLAYER_ONE;
        } else if (gotImage.getTag() == PLAYER_TURN.TWO) {
            mHouseWinnerArray[mCurrentHouseInt - 1] = PLAYER_TWO;
        }
    }
}
