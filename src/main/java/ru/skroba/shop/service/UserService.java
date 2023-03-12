package ru.skroba.shop.service;

import ru.skroba.shop.model.Product;
import ru.skroba.shop.model.User;
import rx.Observable;


//TODO: implement
public class UserService {
    
    public Observable<User> getUsers() {
        return null;
    }
    
    public Observable<Product> getUserProducts(final long userId) {
        return null;
    }
    
    public Observable<Boolean> registerUser(final User user) {
        return null;
    }
    
    public Observable<Product> getProductsForUser(final long userId) {
        return null;
    }
}
