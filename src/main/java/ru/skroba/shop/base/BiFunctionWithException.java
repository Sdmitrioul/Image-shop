package ru.skroba.shop.base;

@FunctionalInterface
public interface BiFunctionWithException<F, S, O, E extends Exception> {
    O apply(F first, S second) throws E;
}