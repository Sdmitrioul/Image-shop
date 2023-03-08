package ru.skroba.shop.model;

import ru.skroba.shop.base.Currency;

public class User {
    private final long userId;
    
    private final String userName;
    
    private final Currency userCurrency;
    
    public User(final long userId, final String userName, final Currency userCurrency) {
        this.userId = userId;
        this.userName = userName;
        this.userCurrency = userCurrency;
    }
    
    @Override
    public String toString() {
        return "user: {" + "userId: " + userId + ", userName: '" + userName + '\'' + ", userCurrency: " + userCurrency.getName() + '}';
    }
}
