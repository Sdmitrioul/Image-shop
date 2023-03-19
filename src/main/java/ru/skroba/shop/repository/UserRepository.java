package ru.skroba.shop.repository;

import org.bson.Document;
import ru.skroba.shop.model.User;

public class UserRepository extends BaseRepository<User> {
    private static final String COLLECTION_NAME = "users";
    
    public UserRepository(final Database database) {
        super(COLLECTION_NAME, database);
    }
    
    @Override
    protected User factory(final Document doc) {
        return User.of(doc);
    }
    
    @Override
    protected String getIdFieldName() {
        return User.getIdFieldName();
    }
}
