package com.maradamark99.thesis.util;

import java.util.*;

public class CollectionUtil {

    private CollectionUtil() {

    }

    public static <T> Optional<Collection<T>> getNonExistingElements(Collection<T> searchIn,
            Collection<T> searchValues) {
        var searchValuesSet = new HashSet<>(searchValues);
        searchValuesSet.removeAll(searchIn);
        if (searchValuesSet.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(searchValuesSet);
    }
}
