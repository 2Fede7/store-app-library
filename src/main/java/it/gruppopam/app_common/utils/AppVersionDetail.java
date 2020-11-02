package it.gruppopam.app_common.utils;

import lombok.Data;

@Data
public class AppVersionDetail {

    private String app;

    private Long storeId;

    private String versionType;

    private int versionId;

    public AppVersionDetail(String app, Long storeId, String versionType, int versionId) {
        this.app = app;
        this.storeId = storeId;
        this.versionType = versionType;
        this.versionId = versionId;
    }
}
