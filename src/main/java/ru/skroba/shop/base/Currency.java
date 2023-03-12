package ru.skroba.shop.base;

import ru.skroba.shop.exception.EntityException;

import java.util.Arrays;

public enum Currency {
    RUB("rub"), EUR("eu"), USD("usd"), GBP("gbp");
    
    private final String name;
    
    Currency(final String name) {
        this.name = name;
    }
    
    public static Currency getCurrencyByName(String name) throws EntityException {
        return Arrays.stream(Currency.values())
                .filter(it -> it.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new EntityException("Unknown currency"));
    }
    
    public static Currency getCurrencyByName(String name, Currency defaultValue) {
        return Arrays.stream(Currency.values())
                .filter(it -> it.name.equals(name))
                .findFirst()
                .orElse(defaultValue);
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "\"" + getName() + "\"";
    }
}
