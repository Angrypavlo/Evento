package com.example.evento;

public class TripController {

    public String description;
    public String name;
    public String image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public TripController(){
        description = "";
        name = "";
        image = "";
    }
    public TripController(String description, String name, String image){
        this.description = description;
        this.name = name;
        this.image = image;
    }

}
