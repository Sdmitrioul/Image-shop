package ru.skroba.shop.configuration;

import ru.skroba.shop.repository.CurrencyExchangeRepository;
import ru.skroba.shop.repository.Database;
import ru.skroba.shop.repository.ProductRepository;
import ru.skroba.shop.repository.UserRepository;

public class RepositoryConfigurator {
    private final Database database;
    private CurrencyExchangeRepository currencyExchangeRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    
    public RepositoryConfigurator() {
        int port = Integer.parseInt(System.getenv("DB_PORT"));
        String dbName = System.getenv("DB_NAME");
        this.database = new Database(port, dbName);
    }
    
    CurrencyExchangeRepository getCurrencyExchangeRepository() {
        return new CurrencyExchangeRepository(database);
    }
    
    ProductRepository getProductRepository() {
        return new ProductRepository(database);
    }
    
    UserRepository getUserRepository() {
        return new UserRepository(database);
    }
}
