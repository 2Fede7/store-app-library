package it.gruppopam.app_common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.util.Date;

import it.gruppopam.app_common.model.PlainISODateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromotionDto {

    private Long articleId;

    private String eventCode;

    private String promoType;

    private Integer promoYear;

    @JsonDeserialize(using = PlainISODateDeserializer.class)
    private Date startDate;

    @JsonDeserialize(using = PlainISODateDeserializer.class)
    private Date endDate;

    private Boolean isInFlyer;

    private String flyerCode;

    private BigDecimal price;

    private BigDecimal discountPercentage;

    private Long soldPieces;

    private Long paidPieces;

    private Boolean isFidelity;

    private BigDecimal fidelityDiscountPercentage;

    private BigDecimal fidelityPrice;

}
