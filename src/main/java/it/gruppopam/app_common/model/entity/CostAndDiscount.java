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
@Entity(tableName = CostAndDiscount.COST_AND_DISCOUNT_TABLE,
        indices = {@Index(value = {"article_id", "supplier_id"}, unique = true)},
        inheritSuperIndices = true)
public class CostAndDiscount extends BaseEntity {

    public static final String COST_AND_DISCOUNT_TABLE = "cost_and_discount";

    @NonNull
    @ColumnInfo(name = "article_id")
    private Long articleId;

    @NonNull
    @ColumnInfo(name = "supplier_id")
    private Long supplierId;

    @ColumnInfo(name = "start_date")
    private Date startDate;

    @ColumnInfo(name = "gross_cost")
    private Double grossCost;

    @ColumnInfo(name = "net_cost")
    private Double netCost;

    @Ignore
    public CostAndDiscount(@NonNull Long articleId, @NonNull Long supplierId, Date startDate, Double grossCost, Double netCost) {
        this.articleId = articleId;
        this.supplierId = supplierId;
        this.startDate = startDate;
        this.grossCost = grossCost;
        this.netCost = netCost;
    }
}
