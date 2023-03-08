package ru.skroba.shop.model;

import org.bson.Document;
import ru.skroba.shop.base.Currency;

public class Product implements MongoModel {
    private static final String PRODUCT_ID_FIELD_NAME = "productId";
    private static final String PRODUCT_NAME_FIELD_NAME = "productName";
    private static final String PRODUCT_PRICE_FIELD_NAME = "productPrice";
    private static final String CURRENCY_FIELD_NAME = "currency";
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

    public static Product of(final Document doc) {
        return new Product(doc.get(PRODUCT_ID_FIELD_NAME, Long.class), doc.get(PRODUCT_NAME_FIELD_NAME, String.class),
                doc.get(PRODUCT_PRICE_FIELD_NAME, Long.class),
                Currency.getCurrencyByName(doc.get(CURRENCY_FIELD_NAME, String.class)));
    }
    
    public String getProductName() {
        return productName;
    }
    
    public long getProductPrice() {
        return productPrice;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    @Override
    public String toString() {
        return this.toString(this.currency);
    }
    
    public String toString(Currency currency) {
        return "product: " + "productId: " + productId + ", productName: '" + productName + '\'' + ", productPrice: '" + productPrice + currency.getName() + "'}";
    }
    
    @Override
    public Document getModel() {
        return new Document(PRODUCT_ID_FIELD_NAME, productId).append(PRODUCT_NAME_FIELD_NAME, productName)
                .append(PRODUCT_PRICE_FIELD_NAME, productPrice)
                .append(CURRENCY_FIELD_NAME, currency.getName());
    }
    
    @Override
    public long getId() {
        return this.productId;
    }
}
