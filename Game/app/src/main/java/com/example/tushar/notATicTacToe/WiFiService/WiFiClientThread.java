package com.example.tushar.notATicTacToe.WiFiService;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pInfo;
import android.os.AsyncTask;

import com.example.tushar.notATicTacToe.Utils.Constants;
import com.example.tushar.notATicTacToe.Utils.LogUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tushar on 24/11/15.
 */
public class WiFiClientThread extends AsyncTask {
    private static final String TAG = WiFiClientThread.class.getSimpleName();

    private Context context;
    private WiFiPlayService.ServerThreadListener mListener;

    private ServerSocket serverSocket = null;
    private Socket socket = null;

    private InputStream inStream = null;
    private OutputStream outStream = null;

    private boolean mIsDataUpdated = false;

    private volatile byte[] readBuffer = null;
    private volatile int num = -1;

    public WiFiClientThread(Context ctx, WiFiPlayService.ServerThreadListener listener) {
        context = ctx;
        mListener = listener;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {

            WifiP2pInfo info = (WifiP2pInfo) params[0];

            /**
             * Create a client socket with the host,
             * port, and timeout information.
             */
            InetAddress address = info.groupOwnerAddress;

            LogUtil.d(TAG, "Clieent Thread started");
            socket = new Socket();
            socket.bind(null);
            LogUtil.d(TAG, "socket.bind() done");

            socket.connect((new InetSocketAddress(address.getHostAddress(), Constants.PORT_NO)),
                    10000);

            LogUtil.d(TAG, "client connected");
            this.publishProgress(Constants.SOCKET_CONNECTION_COMPLETE);

            outStream = socket.getOutputStream();
            inStream = socket.getInputStream();


            while (true) {
                readBuffer = new byte[200];
                num = inStream.read(readBuffer);
                if (num > 0) {
                    byte[] arrayBytes = new byte[num];
                    System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
                    String receivedMsg = new String(arrayBytes, "UTF-8");
                    LogUtil.d(TAG, "Received message :" + receivedMsg);
                    this.publishProgress(Constants.MESSAGE_RECEIVED);
                    readBuffer = null;
                    num = -1;
                    if (receivedMsg.contains(Constants.WON)) {
                        break;
                    }
                }

                if (mListener.ismSendMessageNow()) {
                    String messageToBeSent = mListener.getmMessageToBeSent();
                    if (messageToBeSent != null && messageToBeSent.length() > 0) {
                        synchronized (socket) {
                            outStream.write(messageToBeSent.getBytes("UTF-8"));
                        }
                        if (messageToBeSent.contains(Constants.WON)) {
                            break;
                        }
                    }
                }
            }

            LogUtil.d(TAG, "closing Socket");
            socket.close();
            return "Game Successfully Completed";
        } catch (Exception e) {
            LogUtil.d(TAG, "Crash in Client");
            LogUtil.e(TAG, e.getMessage());
            LogUtil.printStackTrace(e);
            return null;
        }

    }


    @Override
    protected void onProgressUpdate(Object[] values) {
        LogUtil.d(TAG, "In onProgressUpdate" + values[0].toString());
        super.onProgressUpdate(values);
        switch ((Integer) values[0]) {
            case Constants.SOCKET_CONNECTION_COMPLETE:
                mListener.onSocketConnectionCompleted();
                break;
            case Constants.MESSAGE_RECEIVED:
                mListener.onMessageReceived((String) values[1]);
            default:
                break;

        }
    }

   /* @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }*/
}
