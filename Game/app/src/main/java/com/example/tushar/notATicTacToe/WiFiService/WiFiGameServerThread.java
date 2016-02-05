package com.example.tushar.notATicTacToe.WiFiService;

import android.content.Context;
import android.os.AsyncTask;

import com.example.tushar.notATicTacToe.Utils.Constants;
import com.example.tushar.notATicTacToe.Utils.LogUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WiFiGameServerThread extends AsyncTask {
    private static final String TAG = WiFiGameServerThread.class.getSimpleName();

    private Context context;
    private WiFiPlayService.ServerThreadListener mListener;

    private ServerSocket serverSocket = null;
    private Socket socket = null;

    private InputStream inStream = null;
    private OutputStream outStream = null;

    private boolean mIsDataUpdated = false;

    public WiFiGameServerThread(Context context, WiFiPlayService.ServerThreadListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected String doInBackground(Object[] params) {
        try {

           /* *
         * Create a server socket and wait for client connections. This
         * call blocks until a connection is accepted from a client
         */

            LogUtil.d(TAG, "Executing Server Thread");

            serverSocket = new ServerSocket(Constants.PORT_NO);

            LogUtil.d(TAG, "ServerSocket made");
            socket = serverSocket.accept();

            LogUtil.d(TAG, "serverSocket.accept() executed");

            this.publishProgress(Constants.SOCKET_CONNECTION_COMPLETE);
            /**
             * If this code is reached, a client has connected and transferred data
             * proceed for next section
             */
            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();

            LogUtil.d(TAG, "Socket InputStream & outStream is attached");
            while (true) {
                byte[] readBuffer = new byte[200];
                int num = inStream.read(readBuffer);
                if (num > 0) {
                    byte[] arrayBytes = new byte[num];
                    System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
                    String receivedMsg = new String(arrayBytes, "UTF-8");
                    LogUtil.d(TAG, "Received message :" + receivedMsg);
                    this.publishProgress(Constants.MESSAGE_RECEIVED, receivedMsg);
                    if (receivedMsg.contains(Constants.WON)) {
                        break;
                    }
                }

                if (mListener.ismSendMessageNow()) {
                    LogUtil.d(TAG, "mListener.ismSendMessageNow() = " + mListener.ismSendMessageNow());
                    String messageToBeSent = mListener.getmMessageToBeSent();
                    LogUtil.d(TAG, "messageToBeSent = " + messageToBeSent);

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

    /*
    *//**
     * Start activity that can handle the JPEG image
     *//*
    protected void onPostExecute(String result) {
        if (result != null) {
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + result), "image*//*");
            context.startActivity(intent);
        }
    }*/

   /* public void createReadThread() {
        Thread readThread = new Thread() {
            public void run() {
                while (socket.isConnected()) {
                    try {
                        byte[] readBuffer = new byte[200];
                        int num = inStream.read(readBuffer);
                        if (num > 0) {
                            byte[] arrayBytes = new byte[num];
                            System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
                            String recvedMessage = new String(arrayBytes, "UTF-8");
                            System.out.println("Received message :" + recvedMessage);
                        } else {
                            notify();
                        }
                        ;
                        //System.arraycopy();

                    } catch (SocketException se) {
                        System.exit(0);
                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                }
            }
        };
        readThread.setPriority(Thread.MAX_PRIORITY);
        readThread.start();
    }*/

   /* public void createWriteThread() {
        Thread writeThread = new Thread() {
            public void run() {
                while (socket.isConnected()) {
                    try {
                        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                        sleep(100);
                        String typedMessage = inputReader.readLine();
                        if (typedMessage != null && typedMessage.length() > 0) {
                            synchronized (socket) {
                                outStream.write(typedMessage.getBytes("UTF-8"));
                                sleep(100);
                            }
                        } else {
                            notify();
                        }
                        ;
                        //System.arraycopy();

                    } catch (IOException i) {
                        i.printStackTrace();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        };
        writeThread.setPriority(Thread.MAX_PRIORITY);
        writeThread.start();
    }*/
}
