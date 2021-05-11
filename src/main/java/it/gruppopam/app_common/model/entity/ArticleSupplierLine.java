package it.gruppopam.app_common.model.entity;

import androidx.annotation.NonNull;
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
@Entity(tableName = ArticleSupplierLine.ARTICLE_SUPPLIER_LINES_TABLE,
        indices = {@Index(value = {"article_id", "supplier_id", "line_id"}, unique = true)})
public class ArticleSupplierLine extends BaseEntity {

    public static final String ARTICLE_SUPPLIER_LINES_TABLE = "article_supplier_lines";

    @NonNull
    @ColumnInfo(name = "article_id")
    private Long articleId;
    @NonNull
    @ColumnInfo(name = "supplier_id")
    private Long supplierId;

    @NonNull
    @ColumnInfo(name = "line_id")
    private Long lineId;

    @Ignore
    public ArticleSupplierLine(@NonNull Long supplierId, @NonNull Long lineId, @NonNull Long articleId) {
        this.articleId = articleId;
        this.supplierId = supplierId;
        this.lineId = lineId;
    }

}
