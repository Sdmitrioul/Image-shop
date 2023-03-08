package ru.skroba.shop.model;

import ru.skroba.shop.base.Currency;

public class Product {
    private final long productId;
    private final String productName;
    
    private final long productPrice;
    
    private final Currency currency;
    
    public Product(final long productId, final String productName, final long productPrice, final Currency currency) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.currency = currency;
    }
    
    @Override
    public String toString() {
        return this.toString(this.currency);
    }
    
    public String toString(Currency currency) {
        return "product: " + "productId: " + productId + ", productName: '" + productName + '\'' + ", productPrice: '" + productPrice + currency.getName() + "'}";
    }
}
