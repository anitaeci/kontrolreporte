package com.kontrol.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.kontrol.pojo.WorkOrder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Comunicación con la base de datos
 * @author anita
 */
@ApplicationScoped
public class WorkOrderDAO {
    /**
     * Libreria para comunicar con la base de datos
     */
    @Inject
    private MongoClient mongoClient;

    /**
     * Inyección de la propiedad con el nombre de la base de datos
     */
    @Inject
    @ConfigProperty(name = "mongodb.database")
    private String databaseName;

    /**
     * Metodo para obtener la colección de ordenes de trabajo
     * @return 
     */
    private MongoCollection<Document> getCollection(){
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        return database.getCollection("WorkOrders");
    }

    /**
     * Método para retornar todas las ordenes de trabajo
     * @return 
     */
    public List<WorkOrder> getAllWorkOrders(){
        List<WorkOrder> workOrders = new ArrayList<WorkOrder>();
        MongoCollection<Document> collection = getCollection();
        for(Document document : collection.find()){
            workOrders.add(documentToWorkOrder(document));
        }
        return workOrders;

    }
    /**
     * Metodo para retonar una orden de trabajo
     * @param id: Identificador de la orde de trabajo
     * @return 
     */
    public WorkOrder getWokOrder(String id){
        WorkOrder result = null;
        Bson filter = eq("_id",new ObjectId(id));
        
        FindIterable<Document> documents = this.getCollection().find(filter);
        if(documents.iterator().hasNext()){
            result = documentToWorkOrder(documents.first());
        }
        return result;
    }
    /**
     * Método para insertar una orden de trabajo
     * @param workOrder
     * @return 
     */
    public String insertWorkOrder(WorkOrder workOrder){
        Document document = this.workOrderToDocument(workOrder);
        InsertOneResult ior = this.getCollection().insertOne(document);
        return ior.getInsertedId().asObjectId().getValue().toString();
    }
    /**
     * Metodo para eliminar una orden de trabajo
     * @param id 
     */
    public void deleteWorkOrder(String id){
        Bson query = eq("_id",new ObjectId(id));
        System.out.println(query);
        this.getCollection().deleteOne(query);
    }
    
    public void updateWorkOrder(WorkOrder workOrder){
        Bson query = eq("_id",new ObjectId(workOrder.getId()));
        List<Bson> updates = new ArrayList<>();
        if(null!=workOrder.getAsset()) updates.add(Updates.set("asset", workOrder.getAsset()));
        if(null!=workOrder.getClient()) updates.add(Updates.set("client", workOrder.getClient()));
        if(null!=workOrder.getDate()) updates.add(Updates.set("date", workOrder.getDate()));
        if(null!=workOrder.getTechnician()) updates.add(Updates.set("technician", workOrder.getTechnician()));
        Bson update = Updates.combine(updates);
        this.getCollection().updateOne(query, update);
    }
    
    /**
     * Método para convertir de la base de datos a el objeto java
     * @param document
     * @return 
     */
    private WorkOrder documentToWorkOrder (Document document){
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(document.getObjectId("_id").toString());
        workOrder.setClient(document.getString("client"));
        workOrder.setAsset(document.getString("asset"));
        workOrder.setDate(document.getDate("date"));
        workOrder.setTechnician(document.getString("technician"));
        return workOrder;
    }
    /**
     * Metodo para convertir el objeto java a la base de datos
     * @param workOrder
     * @return 
     */
    private Document workOrderToDocument (WorkOrder workOrder){
        Document document = new Document();
        if(null != workOrder.getAsset()){
            document.append("asset", workOrder.getAsset());
        }
        if(null != workOrder.getClient()){
            document.append("client", workOrder.getClient());
        }
        if(null != workOrder.getDate()){
            document.append("date", workOrder.getDate());
        }
        if(null != workOrder.getTechnician()){
            document.append("technician", workOrder.getTechnician());
        }
        return document;
    }
    
}
