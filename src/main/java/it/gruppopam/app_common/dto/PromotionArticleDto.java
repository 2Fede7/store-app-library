package it.gruppopam.app_common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionArticleDto {

    private Long articleId;

    private String eventCode;

    private Long totalBookedQuantity;

    private Long orderedQuantity;

    private Long componentId;

    private String promotionStartDate;

    private String promotionEndDate;

}
