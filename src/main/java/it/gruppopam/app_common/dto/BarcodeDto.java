package it.gruppopam.app_common.dto;


import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarcodeDto {

    @NonNull
    private Long articleId;

    @NonNull
    @JsonProperty("barcode")
    private String barcodeString;

    @JsonProperty("code_type")
    private String type;

    private Date startDate;

    private Date endDate;

    private Long sequenceNumber;

}
