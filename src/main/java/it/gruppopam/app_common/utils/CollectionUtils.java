package it.gruppopam.app_common.utils;

import java.util.List;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isEmpty(List collections) {
        return collections == null || collections.size() == 0;
    }
}
