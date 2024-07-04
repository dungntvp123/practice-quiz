/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Asus
 */
public class Subject {
    private int id;
    private String name;
    private int numOfChapter;

    public Subject() {
    }

    public Subject(int id, String name, int numOfChapter) {
        this.id = id;
        this.name = name;
        this.numOfChapter = numOfChapter;
    }

    public Subject(String name, int numOfChapter) {
        this.name = name;
        this.numOfChapter = numOfChapter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfChapter() {
        return numOfChapter;
    }

    public void setNumOfChapter(int numOfChapter) {
        this.numOfChapter = numOfChapter;
    }

    @Override
    public String toString() {
        return "Subject{" + "id=" + id + ", name=" + name + ", numOfChapter=" + numOfChapter + '}';
    }

    
}
