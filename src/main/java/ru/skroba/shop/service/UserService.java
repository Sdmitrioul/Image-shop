package ru.skroba.shop.service;

import ru.skroba.shop.model.Product;
import ru.skroba.shop.model.User;
import ru.skroba.shop.repository.CurrencyExchangeRepository;
import ru.skroba.shop.repository.Repository;
import rx.Observable;

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
    
    public Observable<User> getUsers() {
        return userRepository.findAll();
    }
    
    public Observable<Boolean> registerUser(final User user) {
        return userRepository.addEntity(user);
    }
    
    public Observable<Product> getProductsForUser(final long userId) {
        return userRepository.findById(userId)
                .map(User::getUserCurrency)
                .flatMap(userCurrency -> productsRepository.findAll()
                        .flatMap(product -> currencyExchangeRepository.getRateBySoldBought(product.getCurrency(),
                                        userCurrency)
                                .map(rate -> {
                                    product.setBaseRate(rate);
                                    return product;
                                })
                        
                        ));
    }
}
