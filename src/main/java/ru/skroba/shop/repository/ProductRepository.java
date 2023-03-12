package ru.skroba.shop.repository;

import org.bson.Document;
import ru.skroba.shop.model.Product;

public class ProductRepository extends BaseRepository<Product> {
    private static final String COLLECTION_NAME = "products";
    
    public ProductRepository(final Database database) {
        super(COLLECTION_NAME, database);
    }
    
    @Override
    protected Product factory(final Document doc) {
        return Product.of(doc);
    }
}
