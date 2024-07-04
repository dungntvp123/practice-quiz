package entity;

import java.sql.Timestamp;

public class Notification {
    int id;
    int publisher;
    int classID;

    String title;
    String content;
    Timestamp timeOfPublish;
    int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimeOfPublish() {
        return timeOfPublish;
    }

    public void setTimeOfPublish(Timestamp timeOfPublish) {
        this.timeOfPublish = timeOfPublish;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Notification(int publisher, int classID, String title, String content, Timestamp timeOfPublish, int status) {
        this.publisher = publisher;
        this.classID = classID;
        this.title = title;
        this.content = content;
        this.timeOfPublish = timeOfPublish;
        this.status = status;
    }

    public Notification(int id, int publisher, int classID, String title, String content, Timestamp timeOfPublish, int status) {
        this.id = id;
        this.publisher = publisher;
        this.classID = classID;
        this.title = title;
        this.content = content;
        this.timeOfPublish = timeOfPublish;
        this.status = status;
    }
}
