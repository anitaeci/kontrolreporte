package com.kontrol.services;

import com.kontrol.entity.WorkOrderDAO;
import com.kontrol.pojo.WorkOrder;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.mail.internet.MailDateFormat;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

    

@Path("orders")
public class WorkOrderServices {
    
    
    @Inject 
    private WorkOrderDAO workOrderDAO;
    
    @GET
    public Response getWorkOrders(){
        return Response
                .ok(workOrderDAO.getAllWorkOrders())
                .build();        
    }
    
    @GET
    @Path("/{id}")
    public Response gegWorkOrder(@PathParam("id") String id){
        WorkOrder workOrder = workOrderDAO.getWokOrder(id);
        if(null != workOrder){
            return Response
                .ok(workOrder).build();
        }
        else {
            return Response.noContent().build();
        }
        
    }
    
    @POST
    public Response insertWorkOrder(JsonObject requestJson){
        System.out.println(requestJson);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        WorkOrder workOrder = new WorkOrder();
        workOrder.setAsset(requestJson.getString("asset"));
        workOrder.setClient(requestJson.getString("client"));
        String dateString = requestJson.getString("date");
        try {
            workOrder.setDate(sdf.parse(dateString));
        } catch (ParseException ex) {
            workOrder.setDate(null);
        }
        workOrder.setTechnician(requestJson.getString("technician"));
        String id = workOrderDAO.insertWorkOrder(workOrder);
        workOrder.setId(id);
        return Response.ok(workOrder).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteWorkOrder(@PathParam("id") String id){
        System.out.println("id en el request "+id);
        workOrderDAO.deleteWorkOrder(id);
        return Response.ok().build();
    }
    
    
    
    
}
