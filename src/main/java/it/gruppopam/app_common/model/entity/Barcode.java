package it.gruppopam.app_common.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = Barcode.BARCODES_TABLE, indices = {@Index(value = {"article_id", Barcode.BARCODE}, unique = true)}, inheritSuperIndices = true)
@SuppressWarnings("PMD.AvoidFieldNameMatchingTypeName")
public class Barcode extends BaseEntity {

    public static final String BARCODES_TABLE = "barcode";
    public static final String BARCODE = "barcode";

    @NonNull
    @ColumnInfo(name = "article_id")
    private Long articleId;

    @NonNull
    @JsonProperty(BARCODE)
    @ColumnInfo(name = BARCODE)
    private String barcodeString;

    @JsonProperty("code_type")
    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "start_date")
    private Date startDate;

    @ColumnInfo(name = "end_date")
    private Date endDate;

    @ColumnInfo(name = "sequence_number")
    private Long sequenceNumber;

    @Ignore
    public Barcode(@NonNull Long articleId, @NonNull String barcodeString, String type, Date startDate, Date endDate, Long sequenceNumber) {
        this.articleId = articleId;
        this.barcodeString = barcodeString;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sequenceNumber = sequenceNumber;
    }

    @Ignore
    public Barcode(@NonNull Long articleId, @NonNull String barcodeString, Date startDate, Date endDate) {
        this.articleId = articleId;
        this.barcodeString = barcodeString;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isActive() {
        Date now = new Date();
        return !startDate.after(now) && (!endDate.before(now));
    }
}
