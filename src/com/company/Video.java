package com.company;

public class Video implements java.io.Serializable {
    private int id;
    private String vMode;
    private String link;

    public Video(int id, String vMode, String link) {
        this.id = id;
        this.link = link;
        this.vMode = vMode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVMode() {
        return vMode;
    }

    public void setVMode(String vMode) {
        this.vMode = vMode;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
