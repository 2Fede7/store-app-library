package it.gruppopam.app_common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

import it.gruppopam.app_common.model.DistributionMode;
import it.gruppopam.app_common.model.OrderOption;
import it.gruppopam.app_common.model.ReplenishmentType;
import it.gruppopam.app_common.utils.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ArticleDto {
    @Builder.Default
    Long departmentId = 0L;
    private Long articleId;
    private String articleName;
    private String rotationClass;
    private Integer piecesPerPackage;
    private Integer weight;
    private String measurementUnitCode;
    private Long sequenceNumber;
    private Integer minOrderableQuantity;
    private Integer maxOrderableQuantity;
    private Long merchandiseAreaId;
    @Builder.Default
    private String consumerDepartments = AppConstants.EMPTY_STRING;
    private List<PromotionDto> activeAndFuturePromotions;

    private String reorderClass;

    private Double standardPackageWeight;

    private Character saleType;

    private String assortmentType;

    private Long xdxvSupplierId;

    private DistributionMode distributionMode;

    private ReplenishmentType replenishmentType;

    @JsonProperty("supplier_lines")
    private List<ArticleSupplierLineDto> articleSupplierLines;

    @JsonProperty("article_order_block")
    private List<ArticleOrderBlockDto> articleOrderBlocks;

    private Long directSupplierId;

    private Boolean active;

    private Date assortmentStartDate;

    private Boolean replenishmentStatus;

    private OrderOption orderOption;

    private Long componentId;

    private Double wastagePercentage;

    private Character measurementUnitType;

    private Double price;

    private Long sectorId;

    private Long categoryId;

    private String changeType;

    private Integer blocks;

    private Integer tiers;

    private Boolean deliveryDateUnknown;

    private String unknownDeliveryReason;

    private Long substituteArticleId;

    @JsonProperty
    public void setAssortmentStartDate(String assortmentStartDate) {
        this.assortmentStartDate = LocalDate.parse(assortmentStartDate).toDate();
    }

    @JsonProperty
    public void setAssortmentStartDate(Date assortmentStartDate) {
        this.assortmentStartDate = assortmentStartDate;
    }
}
