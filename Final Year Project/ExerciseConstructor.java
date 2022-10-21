package com.example.cuba2;

public class ExerciseConstructor {

    String name;
    String burnedcalories;
    String minutes;

    public ExerciseConstructor(String name, String burnedcalories, String minutes) {
        this.name = name;
        this.burnedcalories = burnedcalories;
        this.minutes = minutes;
    }

    public ExerciseConstructor()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBurnedcalories() {
        return burnedcalories;
    }

    public void setBurnedcalories(String burnedcalories) {
        this.burnedcalories = burnedcalories;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }
}
