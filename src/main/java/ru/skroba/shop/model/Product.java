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
    private final double productPrice;
    private final Currency currency;
    
    private CurrencyExchangeRate baseRate;
    
    public void setBaseRate(final CurrencyExchangeRate baseRate) {
        this.baseRate = baseRate;
    }
    
    public Product(final long productId, final String productName, final double productPrice, final Currency currency) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.currency = currency;
        
        this.baseRate = new CurrencyExchangeRate(0, currency, currency, 1);
    }
    
    public static Product of(final Document doc) {
        return new Product(doc.get(PRODUCT_ID_FIELD_NAME, Long.class), doc.get(PRODUCT_NAME_FIELD_NAME, String.class),
                doc.get(PRODUCT_PRICE_FIELD_NAME, Double.class),
                Currency.getCurrencyByName(doc.get(CURRENCY_FIELD_NAME, String.class), Currency.USD));
    }
    
    public static String getIdFieldName() {
        return Product.PRODUCT_ID_FIELD_NAME;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public double getProductPrice() {
        return productPrice;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    @Override
    public String toString() {
        return this.toString(baseRate);
    }
    
    public String toString(CurrencyExchangeRate rate) {
        return "product: {" + "productId: " + productId + ", productName: '" + productName + '\'' + ", productPrice: " + "'" + (productPrice * rate.rate()) + rate.bought()
                .getName() + "'}";
    }
    
    @Override
    public Document getModel() {
        return new Document(PRODUCT_ID_FIELD_NAME, productId).append(PRODUCT_NAME_FIELD_NAME, productName)
                .append(PRODUCT_PRICE_FIELD_NAME, productPrice)
                .append(CURRENCY_FIELD_NAME, currency.getName());
    }
    
    @Override
    public long id() {
        return this.productId;
    }
}
