package it.gruppopam.app_common.model;

import android.content.res.Resources;

import it.gruppopam.app_common.R;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static it.gruppopam.app_common.utils.AppConstants.EMPTY_STRING;
import static it.gruppopam.app_common.utils.AppConstants.IconTexts.DIRECT;
import static it.gruppopam.app_common.utils.AppConstants.IconTexts.STOCKED;
import static it.gruppopam.app_common.utils.AppConstants.IconTexts.XD;
import static java.util.Arrays.asList;

@AllArgsConstructor
public enum OrderOption {
    CEDI(R.string.cedi_stocked_logistic_option_title, R.string.cedi_stocked_logistic_option_subtitle, STOCKED, R.color.order_option_stocked_color, true, true),
    CIDD_GROCERY(R.string.grocery_cidd_stocked_logistic_option_title, R.string.grocery_cidd_logistic_option_subtitle, STOCKED, R.color.order_option_stocked_color, true, true),
    GROCERY_XD(R.string.grocery_xd_logistic_option_title, R.string.grocery_xd_logistic_option_subtitle, XD, R.color.order_option_xd_color, false, true),
    GROCERY_DIRECT(R.string.grocery_direct_logistic_option_title, R.string.direct_logistic_option_subtitle, DIRECT, R.color.order_option_direct_color, false, true),
    DLS_SA_FO_GASTRONOMY_DIRECT(R.string.dls_cheese_salami_direct_logistic_option_title, R.string.direct_logistic_option_subtitle, DIRECT, R.color.order_option_direct_color, false, true),
    SURGELATI(R.string.surgelati_stocked_logistic_option_title, R.string.surgelati_stocked_logistic_option_subtitle, STOCKED, R.color.order_option_stocked_color, true, true),
    CIDD_LATTICINI(R.string.dls_cidd_stocked_logistic_option_title, R.string.cidd_latticini_logistic_option_subtitle, STOCKED, R.color.order_option_stocked_color, true, true), // Dept 4, 11
    //CIDD_GASTRONOMY_CHEESE_SALAMI_BAKERY(R.string.dcs_cidd_stocked_logistic_option_title, R.string.cidd_gcs_logistic_option_subtitle, STOCKED, R.color.order_option_stocked_color, true, false),
    // Dept 5, 18, 9
    CIDD_PESCE(R.string.dcs_cidd_stocked_logistic_option_title, R.string.cidd_pesce_logistic_option_subtitle, STOCKED, R.color.order_option_stocked_color, true, false),
    CIDD_CARNE(R.string.dcs_cidd_stocked_logistic_option_title, R.string.cidd_carne_logistic_option_subtitle, STOCKED, R.color.order_option_stocked_color, true, false),
    CIDD_ORTOFRUTTA(R.string.dcs_cidd_stocked_logistic_option_title, R.string.cidd_ortofrutta_logistic_option_subtitle, STOCKED, R.color.order_option_stocked_color, true, false), // Dept 3
    DLS_SA_FO_GASTRONOMY_XD(R.string.dcs_xd_logistic_option_title, R.string.dcs_xd_logistic_option_subtitle, XD, R.color.order_option_xd_color, false, true),
    DCS_BAKERY_DIRECT(R.string.dcs_bakery_direct_logistic_option_title, R.string.direct_logistic_option_subtitle, DIRECT, R.color.order_option_direct_color, false, true),
    DCS_FRUIT_VEGETABLES_DIRECT(R.string.dcs_fruits_and_vegetables_direct_logistic_option_title, R.string.direct_logistic_option_subtitle, DIRECT, R.color.order_option_direct_color, false, true),

    DCS_CARNE_DIRECT(R.string.dcs_carne_direct_logistic_option_title, R.string.direct_logistic_option_subtitle, DIRECT, R.color.order_option_direct_color, false, true),
    DCS_PESCE_DIRECT(R.string.dcs_pesce_direct_logistic_option_title, R.string.direct_logistic_option_subtitle, DIRECT, R.color.order_option_direct_color, false, true),

    NON_FOOD_CEDI(R.string.non_food_cedi_logistic_option_title, R.string.cedi_stocked_logistic_option_subtitle, STOCKED, R.color.order_option_stocked_color, true, true),
    LIGHT_BAZAAR_XD(R.string.light_bazaar_logistic_option_title, R.string.non_food_xd_logistic_option_subtitle, XD, R.color.order_option_xd_color, false, true),
    HEAVY_BAZAAR_XD(R.string.heavy_bazaar_logistic_option_title, R.string.non_food_xd_logistic_option_subtitle, XD, R.color.order_option_xd_color, false, true),
    TEXTILES_XD(R.string.textiles_logistic_option_title, R.string.non_food_xd_logistic_option_subtitle, XD, R.color.order_option_xd_color, false, true),
    LIGHT_BAZAAR_DIRECT(R.string.light_bazaar_logistic_option_title, R.string.non_food_direct_logistic_option_subtitle, DIRECT, R.color.order_option_direct_color, false, true),
    HEAVY_BAZAAR_DIRECT(R.string.heavy_bazaar_logistic_option_title, R.string.non_food_direct_logistic_option_subtitle, DIRECT, R.color.order_option_direct_color, false, true),
    TEXTILES_DIRECT(R.string.textiles_logistic_option_title, R.string.non_food_direct_logistic_option_subtitle, DIRECT, R.color.order_option_direct_color, false, true),
    PACKAGING_ARTICLE_DIRECT(R.string.packaging_articles_direct_logistic_option_title, R.string.non_food_direct_logistic_option_subtitle, DIRECT, R.color.order_option_direct_color, false, true),
    NONE(0, 0, EMPTY_STRING, 0, false, false);

    private final int titleResId;
    private final int subTitleResId;
    @Getter
    private final String iconText;
    @Getter
    private final int iconColorResourceId;
    @Getter
    private final boolean stocked;
    @Getter
    private final boolean supported;

    public String getTitle(Resources resources) {
        return resources.getString(titleResId);
    }

    public String getSubTitle(Resources resources) {
        return resources.getString(subTitleResId);
    }

    public boolean isDirect() {
        return asList(GROCERY_DIRECT, DLS_SA_FO_GASTRONOMY_DIRECT, DCS_BAKERY_DIRECT, DCS_FRUIT_VEGETABLES_DIRECT,
                DCS_CARNE_DIRECT, DCS_PESCE_DIRECT,
                LIGHT_BAZAAR_DIRECT, HEAVY_BAZAAR_DIRECT, TEXTILES_DIRECT, PACKAGING_ARTICLE_DIRECT).contains(this);
    }

    public boolean isNonFoodCrossDocked() {
        return asList(LIGHT_BAZAAR_XD, HEAVY_BAZAAR_XD, TEXTILES_XD).contains(this);
    }

    public boolean isFoodCrossDocked() {
        return asList(GROCERY_XD, DLS_SA_FO_GASTRONOMY_XD).contains(this);
    }

    public boolean isCrossDocked() {
        return isFoodCrossDocked() || isNonFoodCrossDocked();
    }

    public boolean isLineIdDependent() {
        return isCrossDocked();
    }

}
