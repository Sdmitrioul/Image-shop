package ru.skroba.shop.model;

import org.bson.Document;

public interface MongoModel {
    Document getModel();
    
    long id();
    
    static String getIdFieldName() {
        return "id";
    }
}
