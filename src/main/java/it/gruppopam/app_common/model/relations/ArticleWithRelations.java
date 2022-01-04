package it.gruppopam.app_common.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.annimon.stream.Stream;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import it.gruppopam.app_common.model.OrderComponent;
import it.gruppopam.app_common.model.entity.Article;
import it.gruppopam.app_common.model.entity.ArticleOrderBlock;
import it.gruppopam.app_common.model.entity.ArticleSupplierLine;
import it.gruppopam.app_common.model.entity.CostAndDiscount;
import it.gruppopam.app_common.model.entity.Promotion;
import it.gruppopam.app_common.model.entity.SubstituteArticle;
import it.gruppopam.app_common.utils.AppConstants;
import it.gruppopam.app_common.utils.DateUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

import static com.annimon.stream.Collectors.toList;
import static it.gruppopam.app_common.utils.AppConstants.DEFAULT_QUANTITY;
import static it.gruppopam.app_common.utils.AppConstants.DEFAULT_WEIGHT_UNIT;
import static it.gruppopam.app_common.utils.CollectionUtils.isEmpty;
import static it.gruppopam.app_common.utils.DateUtils.isAfterOrEquals;
import static it.gruppopam.app_common.utils.DateUtils.isBeforeOrEquals;
import static it.gruppopam.app_common.utils.DateUtils.todayStartOfDay;

@Getter
@Setter
@EqualsAndHashCode
public class ArticleWithRelations {

    public static final String ARTICLE_ID = "article_id";

    @Embedded
    @Delegate
    private Article article = new Article();

    @JsonProperty("supplier_lines")
    @Relation(entity = ArticleSupplierLine.class, parentColumn = ARTICLE_ID, entityColumn = ARTICLE_ID)
    private List<ArticleSupplierLine> articleSupplierLines;

    @JsonProperty("active_and_future_promotions")
    @Relation(entity = Promotion.class, parentColumn = ARTICLE_ID, entityColumn = ARTICLE_ID)
    private List<Promotion> activeAndFuturePromotions;

    @JsonProperty("article_order_block")
    @Relation(entity = ArticleOrderBlock.class, parentColumn = ARTICLE_ID, entityColumn = ARTICLE_ID)
    private List<ArticleOrderBlock> articleOrderBlocks;

    @Relation(entity = SubstituteArticle.class, parentColumn = ARTICLE_ID, entityColumn = ARTICLE_ID)
    private SubstituteArticle substituteArticle;

    @Relation(entity = CostAndDiscount.class, parentColumn = ARTICLE_ID, entityColumn = ARTICLE_ID)
    private List<CostAndDiscount> costAndDiscount;

    public Promotion getActivePromotion() {
        boolean hasPromotions = !isEmpty(activeAndFuturePromotions);
        return hasPromotions ? getFirstActivePromotion(activeAndFuturePromotions) : null;
    }

    public Promotion getFuturePromotion() {
        Collections.sort(activeAndFuturePromotions, (lhs, rhs) -> lhs.getStartDate().compareTo(rhs.getStartDate()));
        List<Promotion> futurePromotions = filterFuturePromotions(activeAndFuturePromotions);
        return futurePromotions.size() > 0 ? futurePromotions.get(0) : null;
    }

    public Promotion getPromotion(String eventCode) {
        if (isEmpty(activeAndFuturePromotions)) {
            return null;
        }
        return Stream.of(activeAndFuturePromotions).filter(p -> eventCode.equals(p.getEventCode())).findSingle().orElse(null);
    }

    public List<Promotion> getActiveAndFuturePromotion() {
        Promotion activePromotion = getActivePromotion();
        Promotion futurePromotion = getFuturePromotion();
        List<Promotion> promotions = new ArrayList<>();
        if (activePromotion != null) {
            promotions.add(activePromotion);
        }
        if (futurePromotion != null) {
            promotions.add(futurePromotion);
        }
        return promotions;
    }

    private Promotion getFirstActivePromotion(List<Promotion> promotions) {
        Date todaySod = todayStartOfDay();
        for (Promotion p : promotions) {
            if (p.isActiveOn(todaySod)) {
                return p;
            }
        }
        return null;
    }

    private List<Promotion> filterFuturePromotions(List<Promotion> sortedPromotions) {
        List<Promotion> futurePromotions = new ArrayList<>();
        Date today = DateTime.now().withTimeAtStartOfDay().toDate();
        Date tenDaysFromNow = DateTime.now().withTimeAtStartOfDay().plusDays(AppConstants.PROMOTIONS_LOOK_AHEAD_DAYS).toDate();
        for (Promotion promotion : sortedPromotions) {
            Date startDate = DateUtils.truncateTime(promotion.getStartDate());
            if (startDate.after(today) && !startDate.after(tenDaysFromNow)) {
                futurePromotions.add(promotion);
            }
        }
        return futurePromotions;
    }

    public List<ArticleOrderBlock> getActiveArticleOrderBlocks() {
        return Stream.of(articleOrderBlocks)
                .filter(this::isActiveArticleOrderBlock)
                .collect(toList());
    }

    private boolean isActiveArticleOrderBlock(ArticleOrderBlock articleOrderBlock) {
        return isBeforeOrEquals(articleOrderBlock.getStartDateBlock(), todayStartOfDay()) &&
                (articleOrderBlock.getEndDateBlock() == null || isAfterOrEquals(articleOrderBlock.getEndDateBlock(), todayStartOfDay()));
    }

    public String getArticleDescription() {
        return String.format("%s %s %s", article.getArticleName(), article.getWeight(), article.getMeasurementUnitCode());
    }

    public Long getArticleCode() {
        return article.getArticleId();
    }

    public String getArticleAssortmentType() {
        return article.getAssortmentType();
    }

    public boolean isArticlePackagingMaterial() {
        return isPackagingMaterial();
    }

    public Integer getArticlePiecesPerPackage() {
        return article.getPiecesPerPackage();
    }

    public String getMinOrderableWithUnit() {
        if (isFishOrderableByWeight()) {
            long standardPackageWeight = getStandardPackageWeightOrDefault();
            return standardPackageWeight + " " + DEFAULT_WEIGHT_UNIT;
        }
        //return getMinOrderableQuantityOrDefault().toString() + " " + StoreReplenishmentApplication.getApplicationResources().getString(R.string.packages_unit_label);
        return getMinOrderableQuantityOrDefault().toString() + " " + "Pkg/Colli";
    }

    private boolean isFishOrderableByWeight() {
        return isMeasurementTypeP() && OrderComponent.isFishComponent(article.getComponentId());
    }

    private long getStandardPackageWeightOrDefault() {
        return article.getStandardPackageWeight() == null ? DEFAULT_QUANTITY : ((long) Math.ceil(article.getStandardPackageWeight()));
    }

    private Integer getMinOrderableQuantityOrDefault() {
        return article.getMinOrderableQuantity() == null ? DEFAULT_QUANTITY.intValue() : article.getMinOrderableQuantity();
    }

    public boolean isPackagingMaterial() {
        return article.getConsumerDepartments() != null && !article.getConsumerDepartments().isEmpty();
    }

//    public boolean isReplacedOrHaveSubstituteArticle() {
//        return substituteArticles != null;
//    }

}
