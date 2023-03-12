package ru.skroba.shop.configuration;

import ru.skroba.shop.service.CurrencyService;
import ru.skroba.shop.service.ProductService;
import ru.skroba.shop.service.UserService;

public class ServiceConfigurator {
    private final RepositoryConfigurator repositoryConfigurator = new RepositoryConfigurator();
    
    private CurrencyService currencyService;
    private ProductService productService;
    
    private UserService userService;
    
    CurrencyService getCurrencyService() {
        if (currencyService == null) {
            currencyService = new CurrencyService(repositoryConfigurator.getCurrencyExchangeRepository());
        }
        
        return currencyService;
    }
    
    ProductService getProductService() {
        if (productService == null) {
            productService = new ProductService(repositoryConfigurator.getProductRepository());
        }
        
        return productService;
    }
    
    UserService getUserService() {
        if (userService == null) {
            userService = new UserService(repositoryConfigurator.getUserRepository(),
                    repositoryConfigurator.getProductRepository(),
                    repositoryConfigurator.getCurrencyExchangeRepository());
        }
        
        return userService;
    }
}
