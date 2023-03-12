package ru.skroba.shop.model;

import org.bson.Document;
import ru.skroba.shop.base.Currency;

public record CurrencyExchangeRate(long id, Currency sold, Currency bought, double rate) implements MongoModel {
    public static final String CER_SOLD_CUR_FIELD_NAME = "cerSold";
    public static final String CER_BOUGHT_CUR_FIELD_NAME = "cerBought";
    private static final String CER_ID_FIELD_NAME = "cerId";
    private static final String CER_RATE_FIELD_NAME = "cerRate";
    
    public static CurrencyExchangeRate of(final Document document) {
        return new CurrencyExchangeRate(document.get(CER_ID_FIELD_NAME, Long.class),
                Currency.getCurrencyByName(document.get(CER_SOLD_CUR_FIELD_NAME, String.class), Currency.USD),
                Currency.getCurrencyByName(document.get(CER_BOUGHT_CUR_FIELD_NAME, String.class), Currency.USD),
                document.get(CER_RATE_FIELD_NAME, Double.class));
    }
    
    public static CurrencyExchangeRate of(Currency sold, Currency bought, Double rate) {
        return new CurrencyExchangeRate((sold.getName() + bought.getName()).hashCode(), sold, bought, rate);
    }
    
    public static String getIdFieldName() {
        return CER_ID_FIELD_NAME;
    }
    
    @Override
    public Document getModel() {
        return new Document().append(CER_ID_FIELD_NAME, id)
                .append(CER_SOLD_CUR_FIELD_NAME, sold.getName())
                .append(CER_BOUGHT_CUR_FIELD_NAME, bought.getName())
                .append(CER_RATE_FIELD_NAME, rate);
    }
}
