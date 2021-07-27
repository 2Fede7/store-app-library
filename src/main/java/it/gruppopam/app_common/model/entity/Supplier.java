package it.gruppopam.app_common.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = Supplier.SUPPLIER_TABLE,
        indices = {@Index(value = {"supplier_id"}, unique = true)}, inheritSuperIndices = true)
public class Supplier extends BaseEntity {
    public final static String SUPPLIER_TABLE = "supplier_anagraphic_details";

    @JsonProperty("id")
    @ColumnInfo(name = "supplier_id")
    private Long supplierId;

    @ColumnInfo(name = "name")
    private String supplierName;

    @ColumnInfo(name = "active")
    private Boolean active;

    @ColumnInfo(name = "sequence_number")
    private Long sequenceNumber;

    @Ignore
    public Supplier(Long supplierId, String supplierName, Boolean active, Long sequenceNumber) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.active = active;
        this.sequenceNumber = sequenceNumber;
    }
}
