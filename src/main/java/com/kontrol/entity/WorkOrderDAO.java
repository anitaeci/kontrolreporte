package com.kontrol.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.kontrol.pojo.WorkOrder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.InsertOneResult;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;


@ApplicationScoped
public class WorkOrderDAO {
    
    private MongoClient mongoClient;

    @Inject
    @ConfigProperty(name = "mongodb.url")
    private String mongodbUrl;

    @Inject
    @ConfigProperty(name = "mongodb.database")
    private String databaseName;

    private MongoCollection<Document> getCollection(){
        mongoClient = MongoClients.create(mongodbUrl);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        return database.getCollection("WorkOrders");
    }

    public List<WorkOrder> getAllWorkOrders(){
        List<WorkOrder> workOrders = new ArrayList<WorkOrder>();
        MongoCollection<Document> collection = getCollection();
        for(Document document : collection.find()){
            workOrders.add(documentToWorkOrder(document));
        }
        return workOrders;

    }
    
    public WorkOrder getWokOrder(String id){
        WorkOrder result = null;
        Bson filter = eq("_id",new ObjectId(id));
        
        FindIterable<Document> documents = this.getCollection().find(filter);
        if(documents.iterator().hasNext()){
            result = documentToWorkOrder(documents.first());
        }
        return result;
    }
    
    public String insertWorkOrder(WorkOrder workOrder){
        Document document = this.workOrderToDocument(workOrder);
        InsertOneResult ior = this.getCollection().insertOne(document);
        return ior.getInsertedId().asObjectId().getValue().toString();
    }
    
    public void deleteWorkOrder(String id){
        Bson query = eq("_id",new ObjectId(id));
        System.out.println(query);
        this.getCollection().deleteOne(query);
    }
    
    private WorkOrder documentToWorkOrder (Document document){
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(document.getObjectId("_id").toString());
        workOrder.setClient(document.getString("client"));
        workOrder.setAsset(document.getString("asset"));
        workOrder.setDate(document.getDate("date"));
        workOrder.setTechnician(document.getString("technician"));
        return workOrder;
    }
    
    private Document workOrderToDocument (WorkOrder workOrder){
        Document document = new Document();
        document.append("asset", workOrder.getAsset());
        document.append("client", workOrder.getClient());
        document.append("date", workOrder.getDate());
        document.append("technician", workOrder.getTechnician());
        return document;
    }
    
}
