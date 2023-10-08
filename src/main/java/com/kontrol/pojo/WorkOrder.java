package com.kontrol.pojo;

import java.util.Calendar;
import java.util.Date;


public class WorkOrder {
    private String id;
    private String client;
    private Date date;
    private String asset;
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
