package ru.skroba.shop.base;

import java.util.Arrays;

public enum Currency {
    RUB("rub"), EUR("eu"), USD("usd"), GBP("gbp");
    
    private final String name;
    
    Currency(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public Currency getCurrencyByName(String name) {
        return Arrays.stream(Currency.values())
                .filter(it -> it.name.equals(name))
                .findFirst()
                .orElse(USD);
    }
}
