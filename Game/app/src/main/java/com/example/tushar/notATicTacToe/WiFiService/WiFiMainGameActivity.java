package com.example.tushar.notATicTacToe.WiFiService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.tushar.notATicTacToe.R;
import com.example.tushar.notATicTacToe.Utils.AlertDialog;
import com.example.tushar.notATicTacToe.Utils.Constants;
import com.example.tushar.notATicTacToe.Utils.IprogressCancelListener;
import com.example.tushar.notATicTacToe.Utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class WiFiMainGameActivity extends Activity implements WifiP2pManager.ConnectionInfoListener {

    private static final String TAG = WiFiMainGameActivity.class.getSimpleName();

    private RelativeLayout mGameLayout = null;
    private RelativeLayout mWiFiDevicesLayout = null;

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

    private WifiP2pInfo mDeviceInfo = null;
    private enum PLAYER_TURN {
        ONE(1), TWO(2);
        private int mPLAYER_TURN;

        PLAYER_TURN(int i) {
            this.mPLAYER_TURN = i;
        }
    }

    private boolean mIsPlayingThroughWiFI = false;
    private boolean mIsWiFiServer = false;
    private String mWiFiPlayerName = null;
    private int mHouseWinnerArray[] = new int[9];
    private int mCurrentHouseInt = -1;

    private int PLAYER_ONE = 1;
    private int PLAYER_TWO = 2;

    private float opacityLevel = 0.4f;

    private ListView mWiFiDevicesListView = null;
    private WiFiDevicesListAdapter mWiFiDevicesListAdapter = null;

    private Button mSearchWiFiDevicesButton = null;

    private TextView mNoDeviceTextView = null;
    private ProgressDialog mProgressDialog = null;

    WiFiPlayService mWiFiPlayServiceObj = null;

    private boolean mIsServiceCalled = false;

    private int mSelectedDevicePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi_main_game);

        initView();
        mTurnOfPlayer = PLAYER_TURN.ONE;
       /* Intent intent = getIntent();
        String wifiServerOrClient = intent.getStringExtra(Constants.INTENT_EXTRA_FOR_SERVER_OR_CLIENT);
        if (wifiServerOrClient != null) {
            mIsPlayingThroughWiFI = true;
            if (intent.getStringExtra(Constants.INTENT_SELECTED_DEVICE) != null) {
                mWiFiPlayerName = intent.getStringExtra(Constants.INTENT_SELECTED_DEVICE);
            }

            switch (wifiServerOrClient) {
                case Constants.START_SERVER_THREAD:
                    mIsWiFiServer = true;
                  *//*  WiFiGameServerThread serverThread = new WiFiGameServerThread(this);
                    serverThread.execute();*//*
                    break;
                case Constants.START_CLIENT_THREAD:
                    mIsWiFiServer = false;
                    break;
                default:
                    LogUtil.d(TAG, "UnExpected Data");
                    break;
            }
        } else {
            LogUtil.d(TAG, "Data Obtained through Intent null");
        }*/

        mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));

        mHousePlayInfoTextView.setText(getResources().getString(R.string.house_play_info_tv) + " 1");

        mFirstHouse.setClickable(true);
        mCurrentHouse = mFirstHouse;
        mCurrentHouseInt = 1;
        mCurrentHouse.setAlpha(1f);
    }

    private void initView() {
        mGameLayout = (RelativeLayout) findViewById(R.id.game_view);
        mWiFiDevicesLayout = (RelativeLayout) findViewById(R.id.device_list_view);

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

        mWiFiDevicesListView = (ListView) findViewById(R.id.devies_lv);

        mNoDeviceTextView = (TextView) findViewById(R.id.no_devices_tv);
        mSearchWiFiDevicesButton = (Button) findViewById(R.id.search_wiFi_device_btn);
        mSearchWiFiDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startServiceSearchDevices();
            }
        });

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
                setTextForPlayerTurn(PLAYER_TURN.ONE);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_turn_txt));
                mTurnOfPlayer = PLAYER_TURN.TWO;
            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));
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
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_turn_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));
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
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_turn_txt));
                mTurnOfPlayer = PLAYER_TURN.TWO;
            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));

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
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_turn_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));

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
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_turn_txt));


                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));

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
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_turn_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));

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
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_turn_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));
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
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_turn_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));
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
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_turn_txt));

                mTurnOfPlayer = PLAYER_TURN.TWO;

            } else {
                imageView.setImageResource(R.drawable.cross);
                imageView.setTag(PLAYER_TURN.TWO);
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));
                mTurnOfPlayer = PLAYER_TURN.ONE;

            }
            imageView.setEnabled(false);
            initializationsAfterClick(9, mNinthHouse);
        }
    }

    private void startServiceSearchDevices() {
        mProgressDialog = AlertDialog.showProgressDialog(this,
                getResources().getString(R.string.searching_devices), mSearchWiFionOnCancelListener);

        Intent serviceIntent = new Intent(this, WiFiPlayService.class);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);

    }

    IprogressCancelListener mSearchWiFionOnCancelListener = new IprogressCancelListener() {
        @Override
        public void onCancel() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }

        }
    };

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d("ServiceConnection", "bind service success");
            WiFiPlayService.WiFiServiceBinder binder = (WiFiPlayService.WiFiServiceBinder) service;
            mWiFiPlayServiceObj = binder.getService();

            mWiFiPlayServiceObj.bindAcitivity(WiFiMainGameActivity.this);
            mWiFiPlayServiceObj.bindListener(new WiFiServiceListener());

            mIsServiceCalled = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsServiceCalled = true;
        }
    };

    private void setTextForPlayerTurn(PLAYER_TURN turn) {

        if (mIsPlayingThroughWiFI) {
            if (mIsWiFiServer) {
                if (turn == PLAYER_TURN.ONE) {
                    mPlayerTurnTextView.setText(mWiFiPlayerName);
                } else {
                    mPlayerTurnTextView.setText(getResources().getString(R.string.your_turn));
                }
            } else {
                if (turn == PLAYER_TURN.ONE) {
                    mPlayerTurnTextView.setText(getResources().getString(R.string.your_turn));
                } else {
                    mPlayerTurnTextView.setText(mWiFiPlayerName);
                }
            }
        } else {
            if (turn == PLAYER_TURN.ONE) {
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_two_turn_txt));
            } else {
                mPlayerTurnTextView.setText(getResources().getString(R.string.player_one_turn_txt));
            }
        }
    }

    private boolean isGameWon(int nextHouseInt) {

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
            AlertDialog.showWinningDialog(this, getResources().getString(R.string.congratulations),
                    getResources().getString(R.string.player1_win), getResources().getString(R.string.ok),
                    new AlertDialog.WinDialogOkListener() {
                        @Override
                        public void onOkClick() {
                            finish();
                        }
                    });
            sendMessage(mCurrentHouseInt, nextHouseInt, true);
            return true;
        } else if (housesWonByPlayer2 >= 5) {
            AlertDialog.showWinningDialog(this, getResources().getString(R.string.congratulations),
                    getResources().getString(R.string.player2_win), getResources().getString(R.string.ok),
                    new AlertDialog.WinDialogOkListener() {
                        @Override
                        public void onOkClick() {
                            finish();
                        }
                    });
            sendMessage(mCurrentHouseInt, nextHouseInt, true);
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
        if (!isGameWon(nextHouseInt)) {
            mCurrentHouse.setAlpha(opacityLevel);
            mCurrentHouse.setEnabled(false);

            sendMessage(mCurrentHouseInt, nextHouseInt, false);
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

    public class WiFiServiceListener implements WifiP2pManager.PeerListListener {

        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {
            LogUtil.d(TAG, "onPeersAvailable ");
            mProgressDialog.dismiss();

            if (peers == null || peers.getDeviceList().size() == 0) {
                onNoDeviceFound();
                return;
            }

            List<WifiP2pDevice> deviceList = new ArrayList<>();
            deviceList.addAll(peers.getDeviceList());

/*
            WifiP2pDevice device = deviceList.get(0);
*/
            mSearchWiFiDevicesButton.setVisibility(View.GONE);
            mNoDeviceTextView.setVisibility(View.GONE);
            mWiFiDevicesListView.setVisibility(View.VISIBLE);

          /*  if (mWiFiDevicesListAdapter != null && mWiFiDevicesListAdapter.getCount() > 0) {
                if (mDeviceInfo != null) {
                    WiFiMainGameActivity.this.onConnectionInfoAvailable(mDeviceInfo);
                } else {
                    mWiFiPlayServiceObj.startServerThread(deviceList.get(0));
                    LogUtil.d(TAG, "mDeviceInfo = null");
                }
                return;
            }
*/
            mWiFiDevicesListAdapter = new WiFiDevicesListAdapter(WiFiMainGameActivity.this,
                    deviceList);
            mWiFiDevicesListView.setAdapter(mWiFiDevicesListAdapter);
            mWiFiDevicesListView.setOnItemClickListener(mDeviceClickListener);
        }
    }

    private void onNoDeviceFound() {

        mWiFiDevicesListView.setVisibility(View.GONE);
        mNoDeviceTextView.setVisibility(View.VISIBLE);
        mSearchWiFiDevicesButton.setText(this.getResources().getString(R.string.search_again));
    }

    AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mProgressDialog = AlertDialog.showProgressDialog(WiFiMainGameActivity.this,
                    getResources().getString(R.string.connecting_to_device),
                    mDeviceConnectIprogressCancelListener);

            mSelectedDevicePosition = position;

            //obtain a peer from the WifiP2pDeviceList
            WifiP2pDevice device = mWiFiDevicesListAdapter.getItem(position);
            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = device.deviceAddress;

            mWiFiPlayServiceObj.connect(config);
        }
    };

    IprogressCancelListener mDeviceConnectIprogressCancelListener = new IprogressCancelListener() {
        @Override
        public void onCancel() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            LogUtil.e(TAG, "cancelDisconnect.");
            if (mWiFiPlayServiceObj.isWifiP2pEnabled()) {
                if (mSelectedDevicePosition != -1) {
                    mWiFiPlayServiceObj.disConnect(mWiFiDevicesListAdapter.getItem(mSelectedDevicePosition).status);
                }
            }
        }
    };

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        mProgressDialog.dismiss();
        LogUtil.d(TAG, "onConnectionInfoAvailable, info =" + info);

        WifiP2pDevice device = null;
        /*mDeviceInfo = info;*/
        if (mSelectedDevicePosition != -1)
            device = mWiFiDevicesListAdapter.getItem(mSelectedDevicePosition);
        if (info.groupFormed && info.isGroupOwner) {
            mWiFiPlayServiceObj.startServerThread(device);
        } else if (info.groupFormed) {
            mWiFiPlayServiceObj.startClientThread(info);
        }
    }

    @Override
    protected void onDestroy() {
        LogUtil.d(TAG, "onDestroy   unbindService service.");
        if (mIsServiceCalled && mConnection != null) {
            mIsServiceCalled = false;
            unbindService(mConnection);
            mConnection = null;
        }

        super.onDestroy();
    }

    public void onSocketConnectionCompleted() {
        LogUtil.d(TAG, "onSocketConnectionCompleted");

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mGameLayout.setVisibility(View.VISIBLE);
        mWiFiDevicesLayout.setVisibility(View.GONE);
        /*if (mNoDeviceTextView.getVisibility() == View.VISIBLE) {
            mNoDeviceTextView.setVisibility(View.GONE);
        }

        if (mSearchWiFiDevicesButton.getVisibility() == View.VISIBLE) {
            mSearchWiFiDevicesButton.setVisibility(View.GONE);
        }*/
    }

    public void onMessageReceived(String message) {
        LogUtil.d(TAG, "onMessageReceived, mesaage = " + message);

        decideClick(message);
    }

    public void sendMessage(int HouseNo, int positionInHouse, boolean isWon) {
        String messageToBeSent = String.valueOf(HouseNo * 10 + positionInHouse);
        if (isWon) {
            if (mTurnOfPlayer == PLAYER_TURN.ONE) {
                    messageToBeSent = messageToBeSent + Constants.WON + PLAYER_TWO;
            } else if (mTurnOfPlayer == PLAYER_TURN.TWO) {
                    messageToBeSent = messageToBeSent + Constants.WON + PLAYER_ONE;
            }
        }
        LogUtil.d(TAG, "Sending Message  = " + messageToBeSent);
        mWiFiPlayServiceObj.sendMessage(messageToBeSent);
    }

    private void decideClick(String message) {
        int messageInt = Integer.parseInt(message);

        switch (messageInt % 10) {
            case 1:
                oneClick(((TableRow) mCurrentHouse.getChildAt(1)).getChildAt(1));
                break;
            case 2:
                twoClick(((TableRow) mCurrentHouse.getChildAt(1)).getChildAt(2));
                break;
            case 3:
                threeClick(((TableRow) mCurrentHouse.getChildAt(1)).getChildAt(3));
                break;
            case 4:
                fourClick(((TableRow) mCurrentHouse.getChildAt(2)).getChildAt(1));
                break;
            case 5:
                fiveclick(((TableRow) mCurrentHouse.getChildAt(2)).getChildAt(2));
                break;
            case 6:
                sixClick(((TableRow) mCurrentHouse.getChildAt(2)).getChildAt(3));
                break;
            case 7:
                sevenClick(((TableRow) mCurrentHouse.getChildAt(3)).getChildAt(1));
                break;
            case 8:
                eightClick(((TableRow) mCurrentHouse.getChildAt(3)).getChildAt(2));
                break;
            case 9:
                nineClick(((TableRow)mCurrentHouse.getChildAt(3)).getChildAt(3));
                break;
            default:
                LogUtil.d(TAG, "Unecpected Int from other peer");
                break;
        }
    }
}
