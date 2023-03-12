package ru.skroba.shop.repository;

import com.mongodb.rx.client.MongoCollection;
import org.bson.Document;
import ru.skroba.shop.model.MongoModel;
import rx.Observable;

import static com.mongodb.client.model.Filters.eq;

public abstract class BaseRepository<E extends MongoModel> implements Repository<E> {
    private final String collectionName;
    private final Database database;
    
    public BaseRepository(final String collectionName, final Database database) {
        this.collectionName = collectionName;
        this.database = database;
    }
    
    @Override
    public Observable<E> findById(final long id) {
        return getCollection().find(eq(E.getIdFieldName(), id))
                .first()
                .map(this::factory);
    }
    
    @Override
    public Observable<E> findAll() {
        return getCollection().find()
                .toObservable()
                .map(this::factory);
    }
    
    @Override
    public Observable<Boolean> addEntity(final E model) {
        return getCollection().find(eq(E.getIdFieldName(), model.getId()))
                .toObservable()
                .singleOrDefault(null)
                .flatMap(doc -> doc != null ? Observable.just(false) : insert(model));
    }
    
    private Observable<Boolean> insert(final E model) {
        return getCollection().insertOne(model.getModel())
                .asObservable()
                .isEmpty()
                .map(it -> !it);
    }
    
    private MongoCollection<Document> getCollection() {
        return database.getDatabase()
                .getCollection(collectionName);
    }
    
    protected abstract E factory(final Document doc);
}
