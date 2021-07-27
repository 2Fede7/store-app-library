package it.gruppopam.app_common.model;

import android.content.res.Resources;

import androidx.collection.LongSparseArray;

import com.annimon.stream.Stream;

import java.util.List;

import it.gruppopam.app_common.R;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.annimon.stream.Collectors.toList;
import static it.gruppopam.app_common.model.OrderOption.CEDI;
import static it.gruppopam.app_common.model.OrderOption.CIDD_CARNE;
import static it.gruppopam.app_common.model.OrderOption.CIDD_GROCERY;
import static it.gruppopam.app_common.model.OrderOption.CIDD_LATTICINI;
import static it.gruppopam.app_common.model.OrderOption.CIDD_ORTOFRUTTA;
import static it.gruppopam.app_common.model.OrderOption.CIDD_PESCE;
import static it.gruppopam.app_common.model.OrderOption.DCS_BAKERY_DIRECT;
import static it.gruppopam.app_common.model.OrderOption.DCS_CARNE_DIRECT;
import static it.gruppopam.app_common.model.OrderOption.DCS_FRUIT_VEGETABLES_DIRECT;
import static it.gruppopam.app_common.model.OrderOption.DCS_PESCE_DIRECT;
import static it.gruppopam.app_common.model.OrderOption.DLS_SA_FO_GASTRONOMY_DIRECT;
import static it.gruppopam.app_common.model.OrderOption.DLS_SA_FO_GASTRONOMY_XD;
import static it.gruppopam.app_common.model.OrderOption.GROCERY_DIRECT;
import static it.gruppopam.app_common.model.OrderOption.GROCERY_XD;
import static it.gruppopam.app_common.model.OrderOption.HEAVY_BAZAAR_DIRECT;
import static it.gruppopam.app_common.model.OrderOption.HEAVY_BAZAAR_XD;
import static it.gruppopam.app_common.model.OrderOption.LIGHT_BAZAAR_DIRECT;
import static it.gruppopam.app_common.model.OrderOption.LIGHT_BAZAAR_XD;
import static it.gruppopam.app_common.model.OrderOption.NON_FOOD_CEDI;
import static it.gruppopam.app_common.model.OrderOption.PACKAGING_ARTICLE_DIRECT;
import static it.gruppopam.app_common.model.OrderOption.SURGELATI;
import static it.gruppopam.app_common.model.OrderOption.TEXTILES_DIRECT;
import static it.gruppopam.app_common.model.OrderOption.TEXTILES_XD;
import static java.util.Arrays.asList;

@Getter
@AllArgsConstructor
public enum OrderComponent {

    GROCERY(1L, R.string.grocery_merchandise_area_name, true, asList(CEDI, CIDD_GROCERY, GROCERY_XD, GROCERY_DIRECT)),
    DLS_SA_FO_GASTRONOMY(2L, R.string.dls_cheese_salami_merchandise_area_name, true, asList(CEDI, SURGELATI, CIDD_LATTICINI, DLS_SA_FO_GASTRONOMY_DIRECT, DLS_SA_FO_GASTRONOMY_XD)),
    DCS_BAKERY(3L, R.string.dcs_bakery_department_name, true, asList(CEDI, SURGELATI, CIDD_LATTICINI, DCS_BAKERY_DIRECT)), //G, D
    DCS_FRUIT_VEGETABLES(4L, R.string.dcs_fruits_veggies_department_name, true, asList(CEDI, CIDD_ORTOFRUTTA, DCS_FRUIT_VEGETABLES_DIRECT)),
    DCS_CARNE(6L, R.string.dcs_meat_department_name, true, asList(CEDI, CIDD_CARNE, DCS_CARNE_DIRECT)),
    DCS_PESCE(7L, R.string.dcs_fish_department_name, true, asList(CEDI, CIDD_PESCE, DCS_PESCE_DIRECT)),
    LIGHT_BAZAAR(10L, R.string.light_bazaar_sector_name, true, asList(NON_FOOD_CEDI, LIGHT_BAZAAR_XD, LIGHT_BAZAAR_DIRECT)),
    HEAVY_BAZAAR(11L, R.string.heavy_bazaar_sector_name, true, asList(NON_FOOD_CEDI, HEAVY_BAZAAR_XD, HEAVY_BAZAAR_DIRECT)),
    TEXTILES(12L, R.string.textiles_sector_name, true, asList(NON_FOOD_CEDI, TEXTILES_XD, TEXTILES_DIRECT)),
    SERVIZI_GENERALI(8L, R.string.servizi_generali_merchandise_area_name, true, asList(CEDI)),
    PACKAGING_ARTICLES(9L, R.string.packaging_articles_department_name, true, asList(CEDI, PACKAGING_ARTICLE_DIRECT));

    private static LongSparseArray<OrderComponent> idToComponentMap = new LongSparseArray<>();

    static {
        Stream.of(OrderComponent.values()).forEach(each -> idToComponentMap.put(each.getComponentId(), each));
    }

    private long componentId;
    private int titleResId;
    private boolean enabled;
    private List<OrderOption> orderOptions;

    public static List<OrderComponent> getRelatedComponents(OrderOption orderOption) {
        return Stream.of(OrderComponent.values())
                .filter(c -> c.getOrderOptions().contains(orderOption) && c.isEnabled())
                .collect(toList());
    }

    public static OrderComponent valueOf(Long componentId) {
        return idToComponentMap.get(componentId);
    }

    public static boolean isNonFood(Long componentId) {
        return Long.valueOf(LIGHT_BAZAAR.getComponentId()).equals(componentId)
                || Long.valueOf(HEAVY_BAZAAR.getComponentId()).equals(componentId)
                || Long.valueOf(TEXTILES.getComponentId()).equals(componentId);
    }

    public static boolean isFishComponent(Long componentId) {
        return Long.valueOf(DCS_PESCE.componentId).equals(componentId);
    }

    public String getTitle(Resources resources) {
        return resources.getString(titleResId);
    }

    public boolean isDCS() {
        return DCS_BAKERY.equals(this) || DCS_CARNE.equals(this) || DCS_FRUIT_VEGETABLES.equals(this) || DCS_PESCE.equals(this);
    }

    public boolean isGrocery() {
        return GROCERY.equals(this);
    }
}
