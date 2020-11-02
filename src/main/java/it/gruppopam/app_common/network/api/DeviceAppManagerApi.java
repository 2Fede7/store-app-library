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

    @GET("store_utilities_app/apk/validate")
    Call<ResponseBody> validateApkVersion(@Query("current_version") Long currentVersion);


    @GET("apk/stores/{store_id}/version_type/{version_type}/validate")
    Call<ResponseBody> validateApkVersion(@Path("store_id") Long storeId, @Path("version_type") String validationTYpe,
                                          @Query("current_version") Long currentVersion);

    @GET("devices/{deviceId}/whitelist")
    Call<Boolean> isDeviceWhitelisted(@Path("deviceId") String deviceId);

}
