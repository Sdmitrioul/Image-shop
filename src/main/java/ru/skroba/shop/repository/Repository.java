package ru.skroba.shop.repository;

import com.mongodb.rx.client.Success;
import ru.skroba.shop.model.MongoModel;
import rx.Observable;

import java.util.List;

public interface Repository<E extends MongoModel> {
    Observable<E> findById(long id);
    
    Observable<List<E>>  findAll();
    
    Success addEntity(E model);
}
