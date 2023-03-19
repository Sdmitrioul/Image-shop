package ru.skroba.shop.service;

import ru.skroba.shop.model.Product;
import ru.skroba.shop.model.User;
import ru.skroba.shop.repository.CurrencyExchangeRepository;
import ru.skroba.shop.repository.Repository;
import rx.Observable;

import java.util.List;

public class UserService {
    private final Repository<User> userRepository;
    
    private final Repository<Product> productsRepository;
    
    private final CurrencyExchangeRepository currencyExchangeRepository;
    
    public UserService(final Repository<User> userRepository, final Repository<Product> productsRepository,
                       final CurrencyExchangeRepository currencyExchangeRepository) {
        this.userRepository = userRepository;
        this.productsRepository = productsRepository;
        this.currencyExchangeRepository = currencyExchangeRepository;
    }
    
    public Observable<List<User>> getUsers() {
        return userRepository.findAll().toList();
    }
    
    public Observable<Boolean> registerUser(final User user) {
        return userRepository.addEntity(user);
    }
    
    public Observable<List<Product>> getProductsForUser(final long userId) {
        return userRepository.findById(userId)
                .flatMap(user -> productsRepository.findAll()
                        .flatMap(product -> currencyExchangeRepository.getRateBySoldBought(product.getCurrency(),
                                        user.getUserCurrency())
                                .map(rate -> {
                                    product.setBaseRate(rate);
                                    return product;
                                })
                        
                        )).toList();
    }
}
