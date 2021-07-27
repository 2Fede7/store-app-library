package it.gruppopam.app_common.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import it.gruppopam.app_common.model.DistributionMode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = SupplierDistributionMode.SUPPLIER_DISTRIBUTION_MODE_TABLE,
        indices = {@Index(value = {SupplierDistributionMode.SUPPLIER_ID, "distribution_mode"}, unique = true)},
        foreignKeys = @ForeignKey(entity = Supplier.class,
                parentColumns = SupplierDistributionMode.SUPPLIER_ID,
                childColumns = SupplierDistributionMode.SUPPLIER_ID,
                onDelete = ForeignKey.CASCADE), inheritSuperIndices = true)
public class SupplierDistributionMode extends BaseEntity {

    public final static String SUPPLIER_DISTRIBUTION_MODE_TABLE = "supplier_distribution_modes";
    public final static String SUPPLIER_ID = "supplier_id";

    @NonNull
    @ColumnInfo(name = SUPPLIER_ID)
    private Long supplierId;

    @NonNull
    @ColumnInfo(name = "distribution_mode")
    private DistributionMode distributionMode;

    @Ignore
    public SupplierDistributionMode(@NonNull Long supplierId, @NonNull DistributionMode distributionMode) {
        this.distributionMode = distributionMode;
        this.supplierId = supplierId;
    }

}
