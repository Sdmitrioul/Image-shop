package ru.skroba.shop.model;

import org.bson.Document;
import ru.skroba.shop.base.Currency;

public class User implements MongoModel {
    private static final String USER_ID_FIELD_NAME = "productId";
    private static final String USER_NAME_FIELD_NAME = "productName";
    private static final String CURRENCY_FIELD_NAME = "currency";
    private final long userId;
    
    private final String userName;
    private final Currency userCurrency;
    
    public User(final long userId, final String userName, final Currency userCurrency) {
        this.userId = userId;
        this.userName = userName;
        this.userCurrency = userCurrency;
    }
    
    public static User of(final Document doc) {
        return new User(doc.get(USER_ID_FIELD_NAME, Long.class), doc.get(USER_NAME_FIELD_NAME, String.class),
                Currency.getCurrencyByName(doc.get(CURRENCY_FIELD_NAME, String.class)));
    }
    
    public String getUserName() {
        return userName;
    }
    
    public Currency getUserCurrency() {
        return userCurrency;
    }
    
    @Override
    public String toString() {
        return "user: {" + "userId: " + userId + ", userName: '" + userName + '\'' + ", userCurrency: " + userCurrency.getName() + '}';
    }
    
    @Override
    public Document getModel() {
        return new Document(USER_ID_FIELD_NAME, userId).append(USER_NAME_FIELD_NAME, userName)
                .append(CURRENCY_FIELD_NAME, userCurrency.getName());
    }
    
    @Override
    public long getId() {
        return this.userId;
    }
    
    public String getIdFieldName() {
        return User.USER_ID_FIELD_NAME;
    }
}
