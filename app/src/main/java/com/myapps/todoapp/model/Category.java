package com.myapps.todoapp.model;

public class Category {

    private int id;
    private String userID, name;

    public Category(int id, String userID, String name) {
        this.id = id;
        this.userID = userID;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
