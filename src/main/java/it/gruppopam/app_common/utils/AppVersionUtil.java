package it.gruppopam.app_common.utils;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.List;

import retrofit2.Response;

public class AppVersionUtil {

    private static final String TAG = AppVersionUtil.class.getCanonicalName();

    public static final String DESIRED_VERSION_ID = "X-Desired-Version-Id";
    public static final String DESIRED_VERSION_TYPE = "X-Desired-Version-Type";


    public AppVersionUtil() {
    }

    public static AlertDialog setupAlertDialog(int title, int message, DialogInterface.OnClickListener listener, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder = builder.setTitle(title);
        builder = builder.setMessage(message);
        builder = builder.setPositiveButton("OK", listener);
        builder = builder.setCancelable(false);
        builder = builder.setIcon(android.R.drawable.ic_dialog_alert);
        return builder.create();
    }

    public static boolean isResponseValid(Response response) {
        return response != null && response.raw() != null && isHeadersPresent(response);
    }

    public static boolean isHeadersPresent(Response response) {
        int versionId = Integer.parseInt(response.raw().headers().get(DESIRED_VERSION_ID));
        String versionType = response.raw().headers().get(DESIRED_VERSION_TYPE);
        return versionId > 0 && versionType != null;
    }

    public static boolean isAppRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                return true;
        }
        return false;
    }

}
