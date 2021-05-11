package it.gruppopam.app_common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import it.gruppopam.app_common.model.DistributionMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDto {

    @JsonProperty("id")
    private Long supplierId;
    @JsonProperty("name")
    private String supplierName;

    private Boolean active;

    private Long sequenceNumber;

    private List<DistributionMode> distributionModes;
}
