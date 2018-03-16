package com.cloudaping.cloudaping_android;

/**
 * Created by reggie on 13/03/18.
 */

public class MyData {

    private int id;
    private String description,image_link;

    public MyData(int id, String description, String image_link) {
        this.id = id;
        this.description = description;
        this.image_link = image_link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_link() {
        return "http://cloudaping.com/assets/images/"+image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}