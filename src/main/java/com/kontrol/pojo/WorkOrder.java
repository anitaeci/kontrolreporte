package com.kontrol.pojo;

import java.util.Date;

/**
 * Representa una orden de trabajo 
 * @author anita
 */
public class WorkOrder {
    // Identificador de la orden de tabajo
    private String id;
    // Cliente 
    private String client;
    // Fecha
    private Date date;
    // Equipo
    private String asset;
    // Técnico
    private String Technician;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getAsset() {
        return asset;
    }
    public void setAsset(String asset) {
        this.asset = asset;
    }
    public String getTechnician() {
        return Technician;
    }
    public void setTechnician(String technician) {
        Technician = technician;
    }

    
    
}
