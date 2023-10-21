package com.kontrol.services;

import com.kontrol.entity.WorkOrderDAO;
import com.kontrol.pojo.WorkOrder;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;

    
/**
 * Definición del recurso ordenes de trabajo
 * @author anita
 */
@Path("orders")
public class WorkOrderServices {
    
    /**
     * Injección de dependencias para la comunicación con la base de datos
     */
    @Inject 
    private WorkOrderDAO workOrderDAO;
    
    /**
     * Operacion get para consultar todas las ordenes de trabajo
     * @return 
     */
    @GET
    public Response getWorkOrders(){
        return Response
                .ok(workOrderDAO.getAllWorkOrders())
                .build();        
    }
    /**
     * Operación get para consultar una orden de trabajo en particular
     * por su identificador
     * @param id
     * @return 
     */
    @GET
    @Path("/{id}")
    public Response gegWorkOrder(@PathParam("id") String id){
        WorkOrder workOrder = workOrderDAO.getWokOrder(id);
        if(null != workOrder){
            return Response
                .ok(workOrder).build();
        }
        else {
            // Retorna el estado sin respuesta
            return Response.noContent().build();
        }
        
    }
    /**
     * Operación para insertar una orden de trabajo
     * @param requestJson
     * @return 
     */
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
    /**
     * Operación para eliminar una orden de trabajo
     * @param id: Identificador de la orde de trabajo
     * @return 
     */
    @DELETE
    @Path("/{id}")
    public Response deleteWorkOrder(@PathParam("id") String id){
        System.out.println("id en el request "+id);
        workOrderDAO.deleteWorkOrder(id);
        return Response.ok().build();
    }
    @PATCH
    @Path("/{id}")
    public Response updateWorkOrder(@PathParam("id") String id,
            JsonObject requestJson){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(id);
        if(requestJson.containsKey("asset")){
            workOrder.setAsset(requestJson.getString("asset"));
        }
        if(requestJson.containsKey("client")){
            workOrder.setClient(requestJson.getString("client"));
        }
        if(requestJson.containsKey("date")){
            String dateString = requestJson.getString("date");
            try {
                workOrder.setDate(sdf.parse(dateString));
            } catch (ParseException ex) {
                workOrder.setDate(null);
            }
        }
        if(requestJson.containsKey("technician")){
            workOrder.setTechnician(requestJson.getString("technician"));
        }
        System.out.println(workOrder.toString());
        workOrderDAO.updateWorkOrder(workOrder);
        return Response.ok(workOrder).build();
    }
}
