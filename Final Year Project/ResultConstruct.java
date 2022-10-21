package com.example.cuba2;

public class ResultConstruct {

    String bmi;
    String health_id;
    String tdee;
    String bmr;
    String bmidet;
    String tty;

    public ResultConstruct()
    {

    }



    public String getBmi() {
        return bmi;
    }

    public String getHealth_id()
    {
        return health_id;
    }

    public String getTdee() {
        return tdee;
    }

    public String getBmr() {
        return bmr;
    }

    public String getBmidet() {
        return bmidet;
    }

    public String getTty() {
        return tty;
    }


    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public void setTdee(String tdee) {
        this.tdee = tdee;
    }

    public void setBmr(String bmr) {
        this.bmr = bmr;
    }

    public void setBmidet(String bmidet) {
        this.bmidet = bmidet;
    }

    public void setTty(String tty) {
        this.tty = tty;
    }

    public void setHealth_id (String health_id)
    {
        this.health_id=health_id;
    }
}

