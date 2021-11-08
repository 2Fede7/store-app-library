package it.gruppopam.app_common.dto;

import lombok.Data;

@Data
public class OnBoardDeviceDto {
    private final String deviceId;
    private final Long storeId;
    private final String appName;
    private final String serialNumber;
}
