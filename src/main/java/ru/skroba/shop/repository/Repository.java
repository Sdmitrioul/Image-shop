package ru.skroba.shop.repository;

import ru.skroba.shop.model.MongoModel;
import rx.Observable;

public interface Repository<E extends MongoModel> {
    Observable<E> findById(long id);
    
    Observable<E> findAll();
    
    Observable<Boolean> addEntity(E model);
}
