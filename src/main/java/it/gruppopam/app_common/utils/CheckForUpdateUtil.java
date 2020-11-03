package it.gruppopam.app_common.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import it.gruppopam.app_common.network.api.DeviceAppManagerApi;
import lombok.Getter;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static it.gruppopam.app_common.utils.AppVersionUtil.DESIRED_VERSION_ID;
import static it.gruppopam.app_common.utils.AppVersionUtil.DESIRED_VERSION_TYPE;

public abstract class CheckForUpdateUtil extends AsyncTask<Void, String, Boolean> {

    private static final String TAG = CheckForUpdateUtil.class.getCanonicalName();

    public final static String UPDATE_INTENT_KEY = "UPDATE_INTENT_KEY";
    public static final String CHECK_FOR_UPDATE = "CHECK_FOR_UPDATE";
    public static final String UPDATE_FOUND = "UPDATE_FOUND";
    public static final String NO_UPDATE = "NO_UPDATE";

    public static final String STORE_UTILITIES_APP = "STORE_UTILITIES_APP";
    public static final String STORE_REPLENISHMENT_APP = "STORE_REPLENISHMENT_APP";


    @Getter
    private Context context;
    @Getter
    private AppVersionDetail appVersionDetail;

    private String app;
    private Long storeId;
    private Long currentVersionId;
    private String currentVersionType;

    private DeviceAppManagerApi deviceAppManagerApi;

    public CheckForUpdateUtil(String app, Long storeId, Long currentVersionId, String currentVersionType,
                              DeviceAppManagerApi deviceAppManagerApi, Context context) {
        this.app = app;
        this.storeId = storeId;
        this.currentVersionId = currentVersionId;
        this.currentVersionType = currentVersionType;
        this.deviceAppManagerApi = deviceAppManagerApi;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return checkVersion();
    }

    @Override
    protected void onProgressUpdate(String... status) {
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Intent progressIntent = createIntentWithProgress(UPDATE_FOUND);
            context.startActivity(progressIntent);
        }
    }

    public abstract Intent createIntentWithProgress(String status);

    public boolean checkVersion() {

        try {
            Response<ResponseBody> response;

            if (STORE_UTILITIES_APP.equals(app)) {
                response = deviceAppManagerApi.validateApkVersion(currentVersionId).execute();
            } else {
                response = deviceAppManagerApi.validateApkVersion(storeId, currentVersionType, currentVersionId).execute();
            }

            if (AppVersionUtil.isResponseValid(response)) {

                int versionId = Integer.parseInt(response.raw().headers().get(DESIRED_VERSION_ID));
                String versionType = response.raw().headers().get(DESIRED_VERSION_TYPE);
                appVersionDetail = new AppVersionDetail(app, storeId, versionType, versionId);


                if (!response.isSuccessful()) {
                    Log.e(TAG, "WRONG APP VERSION");
                    publishProgress(UPDATE_FOUND);
                    return true;
                }

            }


        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return false;
    }

}
