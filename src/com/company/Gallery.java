package com.company;

public class Gallery implements java.io.Serializable {
    private int id;
    private String mode;
    private String link;
    private String type;

    public Gallery(int id, String mode, String link, String type){
        this.id = id;
        this.mode = mode;
        this.link = link;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
