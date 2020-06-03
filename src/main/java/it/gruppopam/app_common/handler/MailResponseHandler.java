package it.gruppopam.app_common.handler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MailResponseHandler {

    protected Context context;

    public MailResponseHandler(Context context) {
        this.context = context;
    }

    public void handleMailResponse(boolean isSuccessful, int mail_sent_successfully,
                                   int mail_sending_failed) {
        if (isSuccessful) {
            Log.i(MailResponseHandler.class.getSimpleName(), "Mail Sent Successfully.");
            Toast.makeText(context, mail_sent_successfully, Toast.LENGTH_LONG).show();
        } else {
            Log.i(MailResponseHandler.class.getSimpleName(), "Mail Sending failed");
            Toast.makeText(context, mail_sending_failed, Toast.LENGTH_LONG).show();
        }
    }
}
