package it.gruppopam.app_common.utils;

import android.util.Log;

import java.io.IOException;

import it.gruppopam.app_common.network.api.DeviceAppManagerApi;
import lombok.Getter;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static it.gruppopam.app_common.utils.AppVersionUtil.DESIRED_VERSION_ID;
import static it.gruppopam.app_common.utils.AppVersionUtil.DESIRED_VERSION_TYPE;
import static it.gruppopam.app_common.utils.AppVersionUtil.STORE_UTILITIES_APP;

public class CheckForUpdateUtil {

    private static final String TAG = CheckForUpdateUtil.class.getCanonicalName();

    public static final String UPDATE_INTENT_KEY = "UPDATE_INTENT_KEY";
    public static final String CHECK_FOR_UPDATE = "CHECK_FOR_UPDATE";
    public static final String UPDATE_FOUND = "UPDATE_FOUND";
    public static final String NO_UPDATE = "NO_UPDATE";

    @Getter
    private AppVersionDetail appVersionDetail;

    private String app;
    private Long storeId;
    private Long currentVersionId;
    private String currentVersionType;

    private DeviceAppManagerApi deviceAppManagerApi;

    public CheckForUpdateUtil(String app, Long storeId, Long currentVersionId, String currentVersionType,
                              DeviceAppManagerApi deviceAppManagerApi) {
        this.app = app;
        this.storeId = storeId;
        this.currentVersionId = currentVersionId;
        this.currentVersionType = currentVersionType;
        this.deviceAppManagerApi = deviceAppManagerApi;
    }

    public boolean checkVersion() {

        try {
            Response<ResponseBody> response;

            if (STORE_UTILITIES_APP.equals(app)) {
                response = deviceAppManagerApi.validateApkVersion(currentVersionId).execute();
            } else {
                response = deviceAppManagerApi.validateApkVersion(storeId, currentVersionType, currentVersionId).execute();
            }

            if (AppVersionUtil.isResponseValid(app, response)) {

                int versionId = Integer.parseInt(response.raw().headers().get(DESIRED_VERSION_ID));
                String versionType = response.raw().headers().get(DESIRED_VERSION_TYPE);
                appVersionDetail = new AppVersionDetail(app, storeId, versionType, versionId);

                if (!response.isSuccessful()) {
                    Log.e(TAG, "WRONG APP VERSION");
                    return false;
                }

            } else {
                return false;
            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }

        return true;
    }

}
