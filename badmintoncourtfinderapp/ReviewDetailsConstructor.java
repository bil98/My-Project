package com.example.badmintoncourtfinderapp;

public class ReviewDetailsConstructor {

    String reviewid, reviewdate, reviewtext, userid,reviewrate, courtid;


    public ReviewDetailsConstructor(){}

    public ReviewDetailsConstructor(String reviewid, String reviewdate, String reviewtext,
                                    String reviewrate, String userid, String courtid){
        this.reviewid =reviewid;
        this.reviewdate = reviewdate;
        this.reviewtext = reviewtext;
        this.reviewrate =reviewrate;
        this.userid = userid;
        this.courtid=courtid;
    }

    public String getCourtid() {
        return courtid;
    }

    public void setCourtid(String courtid) {
        this.courtid = courtid;
    }

    public String getReviewid() {
        return reviewid;
    }

    public void setReviewid(String reviewid) {
        this.reviewid = reviewid;
    }

    public String getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(String reviewdate) {
        this.reviewdate = reviewdate;
    }

    public String getReviewtext() {
        return reviewtext;
    }

    public void setReviewtext(String reviewtext) {
        this.reviewtext = reviewtext;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReviewrate() {
        return reviewrate;
    }

    public void setReviewrate(String reviewrate) {
        this.reviewrate = reviewrate;
    }
}
