package com.company;

public class Photo {
    private int id;
    private String shMode;
    private String link;

    public Photo(int id, String shMode, String link) {
        this.id = id;
        this.link = link;
        this.shMode = shMode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShMode() {
        return shMode;
    }

    public void setShMode(String shMode) {
        this.shMode = shMode;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
