package com.kontrol.entity;


import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;

@ApplicationScoped
public class MongoClientProducer {
    
    @ConfigProperty(name="mongodb.url")
    private String mongodbUrl;

    @Produces
    @ApplicationScoped
    public MongoClient createMongoClient(){
        return MongoClients.create(mongodbUrl);
    }

}
