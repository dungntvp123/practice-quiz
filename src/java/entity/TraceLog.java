/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dao.UserDAO;
import java.sql.Timestamp;

/**
 *
 * @author Asus
 */
public class TraceLog {
    private int id;
    private int actor;
    private int status;
    private Timestamp lastLog;

    public TraceLog() {
    }

    public TraceLog(int id, int actor, int status, Timestamp lastLog) {
        this.id = id;
        this.actor = actor;
        this.status = status;
        this.lastLog = lastLog;
    }

    public TraceLog(int actor, int status, Timestamp lastLog) {
        this.actor = actor;
        this.status = status;
        this.lastLog = lastLog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActor() {
        return actor;
    }

    public void setActor(int actor) {
        this.actor = actor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getLastLog() {
        return lastLog;
    }

    public void setLastLog(Timestamp lastLog) {
        this.lastLog = lastLog;
    }

    @Override
    public String toString() {
        JsonObject json = new JsonObject();
        json.add("id", new JsonPrimitive(id));
        json.add("actor",new JsonPrimitive(UserDAO.getInstance().getUser(String.valueOf(actor)).getUsername()));
        
        json.add("status",new JsonPrimitive(status));
        json.add("lastLog",new JsonPrimitive(utility.Utility.dateAgo(lastLog)));
        return json.toString();
    }
    
    
}
