package ru.skroba.shop.service;

import ru.skroba.shop.model.Product;
import ru.skroba.shop.repository.Repository;
import rx.Observable;

public class ProductService {
    private final Repository<Product> productRepository;
    
    public ProductService(final Repository<Product> productRepository) {
        this.productRepository = productRepository;
    }
    
    public Observable<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Observable<Boolean> addProduct(final Product product) {
        return productRepository.addEntity(product);
    }
}
