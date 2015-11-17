package com.example.tushar.myapplication.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.tushar.myapplication.R;

public class AlertDialog {

    public interface WinDialogOkListener {
         void onOkClick();
    };

    public static void showWinningDialog(Context context, String title, String message, String buttonText,
                                         final WinDialogOkListener okButtonListener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info);
        dialog.setCancelable(false);

        TextView titleView = (TextView) dialog.findViewById(R.id.title);
        if (title != null && title.length() > 0) {
            titleView.setText(title);
        }
//        Utils.setCustomTextViewFont(context, titleView, EnumForTextViewFont.BOLD);

        TextView messageView = (TextView) dialog.findViewById(R.id.message);
        messageView.setText(message);
//        Utils.setCustomTextViewFont(context, messageView, EnumForTextViewFont.NORMAL);

        TextView buttonView = (TextView) dialog.findViewById(R.id.button);
        buttonView.setText(buttonText);
//        Utils.setCustomTextViewFont(context, buttonView, EnumForTextViewFont.NORMAL);
        buttonView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                if (okButtonListener != null) {
                    okButtonListener.onOkClick();
                }
            }
        });
        dialog.show();
    }


}
