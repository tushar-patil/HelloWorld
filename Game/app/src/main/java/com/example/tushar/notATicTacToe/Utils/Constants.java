package com.example.tushar.notATicTacToe.Utils;


import java.util.ArrayList;

public class Constants {

    public static final ArrayList<String> MenuOptions = new ArrayList<String>() {{
        add(NEW_GAME);
        add(HIGH_SCORE);
        add(SETTINGS);
        add(HELP);
    }};

    public static final String NEW_GAME = "Play Game";
    public static final String HIGH_SCORE = "High Score";
    public static final String SETTINGS = "Settings";
    public static final String HELP = "Help";

    public static final String INTENT_EXTRA_FOR_SERVER_OR_CLIENT = "INTENT_EXTRA_FOR_SERVER_OR_CLIENT";
    public static final String START_SERVER_THREAD = "START_SERVER_THREAD";
    public static final String START_CLIENT_THREAD = "START_CLIENT_THREAD";
    public static final String INTENT_SELECTED_DEVICE = "INTENT_SELECTED_DEVICE";

    public static final String WON = "WON";

    public static final int PORT_NO = 8888;

    public static final int SOCKET_CONNECTION_COMPLETE = 1001;
    public static final int SEND_MESSAGE = 1002;

    public static final int MESSAGE_RECEIVED = 1003;

}
