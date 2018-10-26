package com.ict311task2dawkins.android.myrecieptmanager.model;

import java.util.Date;
import java.util.UUID;

public class Reciept {
    //Strings
    private String title;
    private String shopeName;
    private String comment;
    private String report;

    //other
    private Date date;
    private String location;
    private UUID uuid;
    private String image;

    public Reciept(){
        this(UUID.randomUUID());
    }

    public Reciept(UUID id){
        uuid = id;
        title = "Title";
        shopeName = "Shop Name";
        comment = "Comment";
        date = new Date();
        report = generateReport();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShopeName() {
        return shopeName;
    }

    public void setShopeName(String shopeName) {
        this.shopeName = shopeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UUID getUuid() {
        return uuid;
    }

    private String generateReport(){
        return title+"\n"+
                shopeName+"\n"+
                comment+"\n"+
                date.toString()+"\n";
    }

    public String getReport() {
        report = generateReport();
        return report;
    }
}
