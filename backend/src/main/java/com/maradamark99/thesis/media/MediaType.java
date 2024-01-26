package com.maradamark99.thesis.media;

import java.util.Arrays;

public enum MediaType {
    IMAGE,
    VIDEO,
    DOCUMENT;

    public static MediaType of(String value) {
        return Arrays.stream(MediaType.values())
                .filter(m -> value.toUpperCase().contains(m.toString().toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Given MediaType does not exist"));
    }

}
