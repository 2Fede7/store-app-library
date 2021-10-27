package it.gruppopam.app_common.model.entity;


import android.text.TextUtils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.annimon.stream.Stream;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import it.gruppopam.app_common.model.DistributionMode;
import it.gruppopam.app_common.model.OrderOption;
import it.gruppopam.app_common.model.PlainISODateDeserializer;
import it.gruppopam.app_common.model.ReplenishmentType;
import it.gruppopam.app_common.utils.AppConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.annimon.stream.Collectors.toList;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Entity(tableName = Article.ARTICLES_TABLE, indices = {@Index(value = {"article_id"}, unique = true)}, inheritSuperIndices = true)
public class Article implements Serializable {
    public static final String ARTICLES_TABLE = "articles";

    @ColumnInfo(name = "department_id")
    private Long departmentId = 0L;

    @PrimaryKey
    @ColumnInfo(name = "article_id")
    private Long articleId;

    @ColumnInfo(name = "name")
    private String articleName;

    @ColumnInfo(name = "rotation_class")
    private String rotationClass;

    @ColumnInfo(name = "pieces_per_package")
    private Integer piecesPerPackage;

    @ColumnInfo(name = "weight")
    private Integer weight;

    @ColumnInfo(name = "measurement_unit_code")
    private String measurementUnitCode;

    @ColumnInfo(name = "sequence_number")
    private Long sequenceNumber;

    @ColumnInfo(name = "min_orderable_quantity")
    private Integer minOrderableQuantity;

    @ColumnInfo(name = "max_orderable_quantity")
    private Integer maxOrderableQuantity;

    @ColumnInfo(name = "merchandise_area_id")
    private Long merchandiseAreaId;

    @ColumnInfo(name = "consumer_departments")
    private String consumerDepartments = AppConstants.EMPTY_STRING;

    @ColumnInfo(name = "reorder_class")
    private String reorderClass;

    @ColumnInfo(name = "standard_package_weight")
    private Double standardPackageWeight;

    @ColumnInfo(name = "sale_type")
    private Character saleType;

    @ColumnInfo(name = "assortment_type")
    private String assortmentType;

    @ColumnInfo(name = "xdxv_supplier_id")
    private Long xdxvSupplierId;

    @ColumnInfo(name = "distribution_mode")
    @JsonProperty("distribution_mode")
    private DistributionMode distributionMode;

    @ColumnInfo(name = "replenishment_type")
    private ReplenishmentType replenishmentType;

    @ColumnInfo(name = "direct_supplier_id")
    private Long directSupplierId;

    @ColumnInfo(name = "active")
    private Boolean active;

    @JsonDeserialize(using = PlainISODateDeserializer.class)
    @ColumnInfo(name = "assortment_start_date")
    private Date assortmentStartDate;

    @ColumnInfo(name = "replenishment_status")
    private Boolean replenishmentStatus;

    @ColumnInfo(name = "order_option")
    private OrderOption orderOption;

    @ColumnInfo(name = "component_id")
    @JsonProperty("component_id")
    private Long componentId;

    @ColumnInfo(name = "wastage_percentage")
    private Double wastagePercentage;

    @ColumnInfo(name = "measurement_unit_type")
    private Character measurementUnitType;

    @ColumnInfo(name = "price")
    private Double price;

    @ColumnInfo(name = "sector_id")
    private Long sectorId;

    @ColumnInfo(name = "category_id")
    private Long categoryId;

    @ColumnInfo(name = "change_type")
    private String changeType;

    @ColumnInfo(name = "blocks")
    private Integer blocks;

    @ColumnInfo(name = "tiers")
    private Integer tiers;

    @ColumnInfo(name = "delivery_date_unknown")
    private Boolean deliveryDateUnknown;

    @ColumnInfo(name = "unknown_delivery_reason")
    private String unknownDeliveryReason;

    @ColumnInfo(name = "dc_id")
    private Long dcId;

    @ColumnInfo(name = "decoted")
    private Boolean decoted;

    @ColumnInfo(name = "decote_percentage")
    private Double decotePercentage;

    public String getDescription() {
        return String.format("%s %s %s", articleName, weight, measurementUnitCode);
    }

    public String getRotationClass() {
        return rotationClass != null ? rotationClass : "";
    }

    public String getAssortmentType() {
        return assortmentType != null ? assortmentType : "-";
    }

    public List<Long> getConsumerDepartmentAsList() {
        if (TextUtils.isEmpty(consumerDepartments)) {
            return Collections.emptyList();
        }
        return Stream.of(consumerDepartments.split(","))
                .map(Long::valueOf)
                .collect(toList());
    }

    public boolean isPackagingArticleWithOneChoice() {
        return getConsumerDepartmentAsList().size() == 1;
    }

    public boolean isPackagingArticleWithManyChoices() {
        return getConsumerDepartmentAsList().size() > 1;
    }

    public boolean isDirect() {
        return getOrderOption() != null && getOrderOption().isDirect();
    }

    public boolean isMeasurementTypeN() {
        return isMeasurementType('N');
    }

    public boolean isMeasurementTypeP() {
        return isMeasurementType('P');
    }

    private boolean isMeasurementType(Character measurementType) {
        return this.getMeasurementUnitType() != null && this.getMeasurementUnitType().equals(measurementType);
    }

    public boolean isSoldOnPieces(){
        return isSoldOn('1');
    }

    public boolean isSoldOnPackagedWeight(){
        return isSoldOn('2');
    }

    private boolean isSoldOn(Character saleType){
        return this.getSaleType() != null && this.getSaleType().equals(saleType);
    }

}
