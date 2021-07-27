package it.gruppopam.app_common.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = DiscountDetails.DISCOUNT_DETAILS_TABLE,
        indices = {@Index(value = {"article_id", "supplier_id"}, unique = true)}, inheritSuperIndices = true)
public class DiscountDetails extends BaseEntity {

    public static final String DISCOUNT_DETAILS_TABLE = "discount_details";

    @NonNull
    @ColumnInfo(name = "article_id")
    private Long articleId;

    @NonNull
    @ColumnInfo(name = "supplier_id")
    private Long supplierId;

    @ColumnInfo(name = "start_date")
    private Date startDate;

    @ColumnInfo(name = "discount_code")
    private String discountCode;

    @ColumnInfo(name = "value")
    private Double value;

    @ColumnInfo(name = "percentage")
    private Double percentage;

    @ColumnInfo(name = "paid_quantity")
    private Double paidQuantity;

    @ColumnInfo(name = "free_quantity")
    private Double freeQuantity;

    @Ignore
    public DiscountDetails(@NonNull Long articleId, @NonNull Long supplierId, Date startDate,
                           String discountCode, Double value, Double percentage, Double paidQuantity, Double freeQuantity) {
        this.articleId = articleId;
        this.supplierId = supplierId;
        this.startDate = startDate;
        this.discountCode = discountCode;
        this.value = value;
        this.percentage = percentage;
        this.paidQuantity = paidQuantity;
        this.freeQuantity = freeQuantity;
    }
}
