package it.gruppopam.app_common.model.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.gruppopam.app_common.utils.DateUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = ArticleOrderBlock.ARTICLE_ORDER_BLOCK,
        indices = {@Index(value = {"article_id", "block_reason"}, unique = true)},
        inheritSuperIndices = true)
public class ArticleOrderBlock extends BaseEntity {

    public static final String ARTICLE_ORDER_BLOCK = "article_order_block";

    @ColumnInfo(name = "article_id")
    @NonNull
    private Long articleId;

    @NonNull
    @JsonProperty("replenishment_block_reason")
    @ColumnInfo(name = "block_reason")
    private String blockReason;

    @ColumnInfo(name = "start_date_block")
    private Date startDateBlock;

    @ColumnInfo(name = "end_date_block")
    private Date endDateBlock;

    @SuppressLint("SimpleDateFormat")
    public String getStartDateBlockString() {
        return DateUtils.getSlashDateFormat().format(startDateBlock);
    }

    @SuppressLint("SimpleDateFormat")
    public String getEndDateBlockString() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(endDateBlock);
        cal.add(Calendar.DATE, 1);
        return DateUtils.getSlashDateFormat().format(cal.getTime());
    }

    @Ignore
    public ArticleOrderBlock(@NonNull Long articleId, @NonNull String blockReason, Date startDateBlock, Date endDateBlock) {
        this.articleId = articleId;
        this.blockReason = blockReason;
        this.startDateBlock = startDateBlock;
        this.endDateBlock = endDateBlock;
    }

}
