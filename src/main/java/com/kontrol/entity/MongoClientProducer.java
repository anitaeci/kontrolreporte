package com.kontrol.entity;


import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
/**
 * Esta clase se usa para producir conexiones a la base de datos y en el 
 * servicio se pueda inyectar
 * @author anita
 */
@ApplicationScoped
public class MongoClientProducer {
    
    /**
     * Inyección de la dirección de la base de datos
     */
    @Inject
    @ConfigProperty(name="mongodb.url")
    private String mongodbUrl;

    /**
     * Produce una conexión a la base de datos para que pueda ser utilizada
     * @return 
     */
    @Produces
    @ApplicationScoped
    public MongoClient createMongoClient(){
        return MongoClients.create(mongodbUrl);
    }

}
