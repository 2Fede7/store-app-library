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

import static it.gruppopam.app_common.utils.DateUtils.truncateTime;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = Promotion.PROMOTIONS_TABLE,
        indices = {@Index(value = {Promotion.ARTICLE_ID, "event_code", "promo_type", "promo_year"}, unique = true)},
        inheritSuperIndices = true)
public class Promotion extends BaseEntity {

    public static final String PROMOTIONS_TABLE = "promotions";
    public static final String ARTICLE_ID = "article_id";

    @NonNull
    @ColumnInfo(name = ARTICLE_ID)
    private Long articleId;

    @NonNull
    @ColumnInfo(name = "event_code")
    private String eventCode;

    @NonNull
    @ColumnInfo(name = "promo_type")
    private String promoType;

    @NonNull
    @ColumnInfo(name = "promo_year")
    private Integer promoYear;

    @ColumnInfo(name = "start_date")
    private Date startDate;

    @ColumnInfo(name = "end_date")
    private Date endDate;

    @ColumnInfo(name = "is_in_flyer")
    private Boolean isInFlyer;

    @ColumnInfo(name = "flyer_code")
    private String flyerCode;

    @ColumnInfo(name = "price")
    private Double price;

    @ColumnInfo(name = "discount_percentage")
    private Double discountPercentage;

    @ColumnInfo(name = "sold_pieces")
    private Long soldPieces;

    @ColumnInfo(name = "paid_pieces")
    private Long paidPieces;

    @ColumnInfo(name = "is_fidelity")
    private Boolean isFidelity;

    @ColumnInfo(name = "fidelity_discount_percentage")
    private Double fidelityDiscountPercentage;

    @ColumnInfo(name = "fidelity_price")
    private Double fidelityPrice;

    @Ignore
    public Promotion(@NonNull Long articleId, @NonNull String eventCode, @NonNull String promoType, @NonNull Integer promoYear, Date startDate, Date endDate, Boolean isInFlyer, String flyerCode) {
        this.articleId = articleId;
        this.eventCode = eventCode;
        this.promoType = promoType;
        this.promoYear = promoYear;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isInFlyer = isInFlyer;
        this.flyerCode = flyerCode;
    }

    @Ignore
    public Promotion(@NonNull Long articleId, @NonNull String eventCode, @NonNull String promoType, @NonNull Integer promoYear, Date startDate, Date endDate, Boolean isInFlyer, String flyerCode,
                     Double price, Double discountPercentage, Long soldPieces, Long paidPieces, Boolean isFidelity, Double fidelityDiscountPercentage, Double fidelityPrice) {
        this.articleId = articleId;
        this.eventCode = eventCode;
        this.promoType = promoType;
        this.promoYear = promoYear;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isInFlyer = isInFlyer;
        this.flyerCode = flyerCode;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.soldPieces = soldPieces;
        this.paidPieces = paidPieces;
        this.isFidelity = isFidelity;
        this.fidelityDiscountPercentage = fidelityDiscountPercentage;
        this.fidelityPrice = fidelityPrice;
    }

    public Boolean isActiveOn(Date date) {
        boolean startDateNotAfterTodayStart = !truncateTime(startDate).after(date);
        boolean endDateNotBeforeTodayStart = endDate == null || !truncateTime(endDate).before(date);
        return startDateNotAfterTodayStart && endDateNotBeforeTodayStart;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Promotion;
    }

}
