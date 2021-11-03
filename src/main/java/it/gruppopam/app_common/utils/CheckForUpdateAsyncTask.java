package it.gruppopam.app_common.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import it.gruppopam.app_common.network.api.DeviceAppManagerApi;
import lombok.Getter;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static it.gruppopam.app_common.utils.AppVersionUtil.DESIRED_VERSION_ID;
import static it.gruppopam.app_common.utils.AppVersionUtil.DESIRED_VERSION_TYPE;
import static it.gruppopam.app_common.utils.AppVersionUtil.STORE_UTILITIES_APP;

public abstract class CheckForUpdateAsyncTask extends AsyncTask<Void, String, CheckForUpdateAsyncTask.VersionCheck> {

    private static final String TAG = CheckForUpdateAsyncTask.class.getCanonicalName();

    public static final String UPDATE_INTENT_KEY = "UPDATE_INTENT_KEY";
    public static final String CHECK_FOR_UPDATE = "CHECK_FOR_UPDATE";
    public static final String UPDATE_FOUND = "UPDATE_FOUND";
    public static final String NO_UPDATE = "NO_UPDATE";

    @Getter
    private Context context;
    @Getter
    private AppVersionDetail appVersionDetail;

    private String app;
    private Long storeId;
    private Long currentVersionId;
    private String currentVersionType;

    private DeviceAppManagerApi deviceAppManagerApi;

    public CheckForUpdateAsyncTask(String app, Long storeId, Long currentVersionId, String currentVersionType,
                                   DeviceAppManagerApi deviceAppManagerApi, Context context) {
        this.app = app;
        this.storeId = storeId;
        this.currentVersionId = currentVersionId;
        this.currentVersionType = currentVersionType;
        this.deviceAppManagerApi = deviceAppManagerApi;
        this.context = context;
    }

    @Override
    protected VersionCheck doInBackground(Void... voids) {
        return checkVersion();
    }

    @Override
    protected void onProgressUpdate(String... status) {
    }

    @Override
    protected abstract void onPostExecute(VersionCheck result);

    public VersionCheck checkVersion() {

        try {
            Response<ResponseBody> response;
            // Todo: Remove this section and restore line 72 after Store Utilities App EDGE will be released
            if (STORE_UTILITIES_APP.equals(app)) {
                response = deviceAppManagerApi.validateApkVersion(currentVersionId).execute();
            } else {
                response = deviceAppManagerApi.validateApkVersion(storeId, app, currentVersionType, currentVersionId).execute();
            }

//            response = deviceAppManagerApi.validateApkVersion(storeId, app, currentVersionType, currentVersionId).execute();

            if (AppVersionUtil.isResponseValid(app, response)) {

                return getVersionIsCorrect(response);
            }

            // unable to get valid response
            return VersionCheck.NA;

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return VersionCheck.NA;
        }
    }

    @NotNull
    private VersionCheck getVersionIsCorrect(Response<ResponseBody> response) {
        int versionId = Integer.parseInt(response.raw().headers().get(DESIRED_VERSION_ID));
        String versionType = response.raw().headers().get(DESIRED_VERSION_TYPE);
        appVersionDetail = new AppVersionDetail(app, storeId, versionType, versionId);

        if (!response.isSuccessful()) {
            Log.e(TAG, "WRONG APP VERSION");
            return VersionCheck.KO;
        }
        return VersionCheck.OK;
    }

    public enum VersionCheck {
        OK,
        KO,
        NA
    }

}
