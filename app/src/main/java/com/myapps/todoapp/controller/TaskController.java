package com.myapps.todoapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.Spinner;

import com.myapps.todoapp.model.LocalDatabase;
import com.myapps.todoapp.model.Task;

import java.util.ArrayList;

public class TaskController {

    LocalDatabase db;

    public TaskController(Context context) {
        db = new LocalDatabase(context);
    }

    public boolean addTask(String userID, Spinner categorySpinner, EditText nameEdt, EditText dueDateEdt) {

        String category = (String) categorySpinner.getSelectedItem();
        String name = nameEdt.getText().toString().trim();
        String dueDate = dueDateEdt.getText().toString().trim();

        if(name.isEmpty()) {
            nameEdt.setError("Name must not be empty!");
            return false;
        }

        int categoryID = db.getCategoryIDByName(category, userID);

        return db.insertTask(userID, categoryID, name, dueDate, false);
    }

    public ArrayList<Task> getTasks(String userID, String categoryName) {
        ArrayList<Task> taskArrayList = new ArrayList<>();

        // Kalau belum ada category yg ke-select
        if(categoryName == null) {
            return taskArrayList;
        }

        int categoryID = db.getCategoryIDByName(categoryName, userID);

        Cursor cursor = db.getTasks(userID, categoryID);

        while(cursor.moveToNext()) {
            Task task = new Task(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5) == 1);
            taskArrayList.add(task);
        }

        return taskArrayList;
    }

    public void toggleTaskComplete(Task task) {
        db.updateTask(task.getId(), task.getCategoryID(), task.getName(), task.getDeadline(), !task.isCompleted());
    }

    public void deleteTask(int taskID) {
        db.deleteTask(taskID);
    }

    public boolean updateTask(int taskID, String userID, Spinner categorySpinner, EditText nameEdt, EditText dueDateEdt, boolean isCompleted) {
        String category = (String) categorySpinner.getSelectedItem();
        String name = nameEdt.getText().toString().trim();
        String dueDate = dueDateEdt.getText().toString().trim();

        if(name.isEmpty()) {
            nameEdt.setError("Name must not be empty!");
            return false;
        }

        int categoryID = db.getCategoryIDByName(category, userID);

        return db.updateTask(taskID, categoryID, name, dueDate, isCompleted);
    }

}
