package com.example.cuba2;

public class FoodConstructor {

    String imageurl;
    String name,type;
    String calories;

    public FoodConstructor(String name, String calories, String type,String imageurl) {
        this.name = name;
        this.calories = calories;
        this.type = type;
        this.type=imageurl;
    }

    public FoodConstructor()
    {

    }

    public String getName() {
        return name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
