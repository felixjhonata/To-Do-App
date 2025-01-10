package com.myapps.todoapp.model;

public class Task {

    private int id, categoryID;
    private String userID, name, deadline;
    private boolean isCompleted;

    public Task(int id, int categoryID, String userID, String name, String deadline, boolean isCompleted) {
        this.id = id;
        this.categoryID = categoryID;
        this.userID = userID;
        this.name = name;
        this.deadline = deadline;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
