package it.gruppopam.app_common.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = LineDetail.LINE_DETAILS_TABLE,
        indices = {@Index(value = {"line_id", "supplier_id"}, unique = true)}, inheritSuperIndices = true)
public class LineDetail extends BaseEntity {
    public static final String LINE_DETAILS_TABLE = "line_details";

    @ColumnInfo(name = "line_id")
    private long lineId;

    @ColumnInfo(name = "supplier_id")
    private long supplierId;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "active")
    private Boolean active;

    @ColumnInfo(name = "sequence_number")
    private Long sequenceNumber;

    @Ignore
    public LineDetail(long lineId, long supplierId) {
        this.lineId = lineId;
        this.supplierId = supplierId;
    }

    @Ignore
    public LineDetail(long lineId, long supplierId, String description, Boolean active) {
        this.lineId = lineId;
        this.supplierId = supplierId;
        this.description = description;
        this.active = active;
    }
}
