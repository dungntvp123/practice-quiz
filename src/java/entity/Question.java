/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 *
 * @author Asus
 */

public class Question {
    private int id;
    private int subjectId;
    private String description;
    private int chapter;
    private int lectureid;
    private int status;

    public Question() {
    }

    public Question(int id, int subjectId, String description, int chapter, int lectureid, int status) {
        this.id = id;
        this.subjectId = subjectId;
        this.description = description;
        this.chapter = chapter;
        this.lectureid = lectureid;
        this.status = status;
    }

    public Question(int subjectId, String description, int chapter, int teacherid, int status) {
        this.subjectId = subjectId;
        this.description = description;
        this.chapter = chapter;
        this.lectureid = teacherid;
        this.status = status;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getLectureid() {
        return lectureid;
    }

    public void setLectureid(int lectureid) {
        this.lectureid = lectureid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
//        return "{" + "\"id\":" + id + ", \"subjectId\":" + subjectId + ", \"description\":\"" + description + "\", \"chapter\":" + chapter + ", \"lectureId\":" + lectureid + ", \"status\":" + status + "}";
        JsonObject json = new JsonObject();
        json.add("id", new JsonPrimitive(id));
        json.add("subjectId",new JsonPrimitive(subjectId));
        JsonArray array = new JsonArray();
        String[] descriptions = description.split("\n");
        for (String d : descriptions){
            array.add(d);
        }
        json.add("description", array);
        json.add("chapter",new JsonPrimitive(chapter));
        json.add("lectureId",new JsonPrimitive(lectureid));
        json.add("status",new JsonPrimitive(status));
        return json.toString();
    }

    

    
    
}
