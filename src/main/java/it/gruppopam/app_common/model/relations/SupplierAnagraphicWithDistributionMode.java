package it.gruppopam.app_common.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import it.gruppopam.app_common.model.entity.Supplier;
import it.gruppopam.app_common.model.entity.SupplierDistributionMode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

@Setter
@Getter
@EqualsAndHashCode
public class SupplierAnagraphicWithDistributionMode {

    @Embedded
    @Delegate
    private Supplier supplier;

    @JsonProperty("distribution_modes")
    @Relation(entity = SupplierDistributionMode.class, parentColumn = "supplier_id", entityColumn = "supplier_id")
    private List<SupplierDistributionMode> distributionModes;

    public static SupplierAnagraphicWithDistributionMode create(Long supplierId, String supplierName, boolean active) {
        Supplier supplier = new Supplier(supplierId, supplierName, active, null);
        SupplierAnagraphicWithDistributionMode supplierAnagraphic = new SupplierAnagraphicWithDistributionMode();
        supplierAnagraphic.setSupplier(supplier);
        return supplierAnagraphic;
    }

    public static SupplierAnagraphicWithDistributionMode create(Long supplierId, String supplierName, boolean active, List<SupplierDistributionMode> distributionModes) {
        Supplier supplier = new Supplier(supplierId, supplierName, active, null);
        SupplierAnagraphicWithDistributionMode supplierAnagraphic = new SupplierAnagraphicWithDistributionMode();
        supplierAnagraphic.setSupplier(supplier);
        supplierAnagraphic.setDistributionModes(distributionModes);
        return supplierAnagraphic;
    }
}
