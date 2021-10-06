package it.gruppopam.app_common.network.api;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceAppManagerApi {

    @GET("settings")
    Call<HashMap<String, String>> getSettings(@Query("store_id") Long storeId);

    @GET("settings")
    HashMap<String, String> getSettingsSynchronous(@Query("store_id") Long storeId);

    @GET("apk/stores/{store_id}/{app_name}/{version_type}/validate")
    Call<ResponseBody> validateApkVersion(@Path("store_id") Long storeId, @Path("app_name") String appName,
                                          @Path("version_type") String validationTYpe, @Query("current_version") Long currentVersion);

    @GET("devices/{deviceId}/whitelist")
    Call<Boolean> isDeviceWhitelisted(@Path("deviceId") String deviceId);

}
