package ru.skroba.shop.response;

import java.util.Arrays;
import java.util.stream.Collectors;

public record ArrayResponse(Object[] result) {
    @Override
    public String toString() {
        return Arrays.stream(result)
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
