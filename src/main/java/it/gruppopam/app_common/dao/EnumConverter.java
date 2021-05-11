package it.gruppopam.app_common.dao;

import androidx.room.TypeConverter;

import com.annimon.stream.Stream;

import java.util.List;

import it.gruppopam.app_common.model.DistributionMode;
import it.gruppopam.app_common.model.OrderComponent;
import it.gruppopam.app_common.model.OrderOption;
import it.gruppopam.app_common.model.ReplenishmentType;

import static com.annimon.stream.Collectors.toList;
import static it.gruppopam.app_common.utils.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

@SuppressWarnings("PMD.ClassNamingConventions")
public final class EnumConverter {

    private EnumConverter() {
    }

    @TypeConverter
    public static String toString(Enum type) {
        return type == null ? null : type.name();
    }

    @TypeConverter
    public static OrderOption fromOrdinalOrderOption(String value) {
        return isBlank(value) ? null : OrderOption.valueOf(value);
    }

    @TypeConverter
    public static DistributionMode fromOrdinalDistributionMode(String value) {
        return isBlank(value) ? null : DistributionMode.valueOf(value);
    }

    @TypeConverter
    public static ReplenishmentType fromOrdinalReplenishmentType(String value) {
        return isBlank(value) ? null : ReplenishmentType.valueOf(value);
    }

    @TypeConverter
    public static OrderComponent fromOrderComponent(String value) {
        return isBlank(value) ? null : OrderComponent.valueOf(value);
    }

    @TypeConverter
    public static List<DistributionMode> fromDistributionModeList(List<String> value) {
        return isEmpty(value) ? null : Stream.of(value).map(DistributionMode::valueOf).collect(toList());
    }

    @TypeConverter
    public static List<String> fromDistributionModeOrdinalList(List<DistributionMode> value) {
        return isEmpty(value) ? null : Stream.of(value).map(DistributionMode::name).collect(toList());
    }

}
