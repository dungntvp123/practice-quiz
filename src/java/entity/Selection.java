/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author ACER NITRO
 */
public class Selection {

    private int id;
    private int questionId;
    private double coefficent;
    private String description;
    private String charId;

    public Selection() {
    }

    public Selection(int id, int questionId, double coefficent, String description, String charId) {
        this.id = id;
        this.questionId = questionId;
        this.coefficent = coefficent;
        this.description = description;
        this.charId = charId;
    }

    public Selection(int questionId, double coefficent, String description, String charId) {
        this.questionId = questionId;
        this.coefficent = coefficent;
        this.description = description;
        this.charId = charId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public double getCoefficent() {
        return coefficent;
    }

    public void setCoefficent(double coefficent) {
        this.coefficent = coefficent;
    }

    public String getCharId() {
        return charId;
    }

    public void setCharId(String charId) {
        this.charId = charId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Selection{" + "id=" + id + ", questionId=" + questionId + ", coefficent=" + coefficent + ", description=" + description + ", charId=" + charId + '}';
    }

    public String toJSString() {
//        return "{" + "\"charid\":\'" + charId + "\', \"description\":\"" + description + "\"}";
        JsonObject json = new JsonObject();
        json.add("charid",new JsonPrimitive(charId));
        json.add("description",new JsonPrimitive(StringEscapeUtils.escapeJavaScript(description)));
        json.add("coefficent", new JsonPrimitive(coefficent));
        return json.toString();

    }
}
