package ru.skroba.shop.base;

@FunctionalInterface
public interface FunctionWithException<I, O, E extends Exception> {
    O apply(I input) throws E;
}
