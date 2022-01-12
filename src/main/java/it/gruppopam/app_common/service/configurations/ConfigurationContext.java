package it.gruppopam.app_common.service.configurations;

import lombok.Data;

@Data
public class ConfigurationContext {
    private final Long storeId;
    private final String deviceId;
    private final String appName;
    private final String serialNumber;

}
