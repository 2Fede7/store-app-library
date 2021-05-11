package it.gruppopam.app_common.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import it.gruppopam.app_common.model.entity.CostAndDiscount;
import it.gruppopam.app_common.model.entity.DiscountDetails;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

@Getter
@Setter
@EqualsAndHashCode
public class CostAndDiscountWithDetails {

    public static final String ARTICLE_ID = "article_id";

    @Embedded
    @Delegate
    private CostAndDiscount constAndDiscount = new CostAndDiscount();

    @JsonProperty("discount_details")
    @Relation(entity = DiscountDetails.class, parentColumn = ARTICLE_ID, entityColumn = ARTICLE_ID)
    private List<DiscountDetails> discountDetails;

}
