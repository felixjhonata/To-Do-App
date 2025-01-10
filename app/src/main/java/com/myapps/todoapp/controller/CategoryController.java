package com.myapps.todoapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.widget.EditText;

import com.myapps.todoapp.model.Category;
import com.myapps.todoapp.model.LocalDatabase;

import java.util.ArrayList;

public class CategoryController {

    LocalDatabase db;

    public CategoryController(Context context) {
        db = new LocalDatabase(context);
    }

    public boolean validateInput(String userID, EditText categoryNameEdt) {
        String categoryName = categoryNameEdt.getText().toString().trim();

        if(categoryName.isEmpty()) {
            categoryNameEdt.setError("Category name must not be empty!");
            return false;
        }

        if(db.getCategoryIDByName(categoryName, userID) != -1) {
            categoryNameEdt.setError("Category name already exist!");
            return false;
        }

        return true;
    }

    public boolean addCategory(String userID, EditText categoryNameEdt) {

        String categoryName = categoryNameEdt.getText().toString().trim();

        if(validateInput(userID, categoryNameEdt)) return db.insertCategory(categoryName, userID);
        return false;
    }

    public ArrayList<Category> getCategories(String userID) {

        ArrayList<Category> categoryArrayList = new ArrayList<>();

        Cursor cursor = db.getCategories(userID);

        while(cursor.moveToNext()) {
            Category category = new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            categoryArrayList.add(category);
        }
        return categoryArrayList;
    }

    public void deleteCategory(int categoryID) {
        db.deleteCategory(categoryID);
    }

    public boolean updateCategory(String userID, int categoryID, EditText categoryNameEdt) {
        String categoryName = categoryNameEdt.getText().toString().trim();
        if(validateInput(userID, categoryNameEdt)) return db.updateCategory(categoryID, categoryName);
        return false;
    }

}
