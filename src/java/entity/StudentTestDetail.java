/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Asus
 */
public class StudentTestDetail {
    private int id;
    private int STid, TDid;
    private String selected;
    private double score;

    public StudentTestDetail() {
    }

    public StudentTestDetail(int id, int STid, int TDid, String selected, double score) {
        this.id = id;
        this.STid = STid;
        this.TDid = TDid;
        this.selected = selected;
        this.score = score;
    }

    public StudentTestDetail(int STid, int TDid, String selected, double score) {
        this.STid = STid;
        this.TDid = TDid;
        this.selected = selected;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSTid() {
        return STid;
    }

    public void setSTid(int STid) {
        this.STid = STid;
    }

    public int getTDid() {
        return TDid;
    }

    public void setTDid(int TDid) {
        this.TDid = TDid;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "StudentTestDetail{" + "id=" + id + ", CTid=" + STid + ", TDid=" + TDid + ", selected=" + selected + ", score=" + score + '}';
    }

    
    
    
}
