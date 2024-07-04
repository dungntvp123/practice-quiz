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
public class Recorder {
    private int id;
    private int actor;
    private String action;
    private int actionArea;
    private Timestamp date;

    public Recorder() {
    }

    public Recorder(int id, int actor, String action, int actionArea, Timestamp date) {
        this.id = id;
        this.actor = actor;
        this.action = action;
        this.actionArea = actionArea;
        this.date = date;
    }

    public Recorder(int actor, String action, int actionArea, Timestamp date) {
        this.actor = actor;
        this.action = action;
        this.actionArea = actionArea;
        this.date = date;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getActionArea() {
        return actionArea;
    }

    public void setActionArea(int actionArea) {
        this.actionArea = actionArea;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        JsonObject json = new JsonObject();
        json.add("id", new JsonPrimitive(id));
        json.add("actor",new JsonPrimitive(UserDAO.getInstance().getUser(String.valueOf(actor)).getUsername()));
        
        json.add("action",new JsonPrimitive(action));
        json.add("actionArea",new JsonPrimitive(utility.Utility.numberRepresentTable(actionArea)));
        json.add("date",new JsonPrimitive(date.toString()));
        return json.toString();
    }

   
    
    
}
