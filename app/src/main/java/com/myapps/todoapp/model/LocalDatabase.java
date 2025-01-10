package com.myapps.todoapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocalDatabase extends SQLiteOpenHelper {
    public LocalDatabase(@Nullable Context context) {
        super(context, "To Do Database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS category" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userID TEXT NOT NULL, " +
                "name TEXT NOT NULL);";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " +
                "task(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "categoryID INTEGER NOT NULL, " +
                "userID TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "deadline TEXT NOT NULL, " +
                "status INTEGER NOT NULL, " +
                "FOREIGN KEY (categoryID) REFERENCES category(id));";
        db.execSQL(query);

        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS category;";
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS task;";
        db.execSQL(query);

        onCreate(db);
    }

    public boolean insertCategory(String name, String userID) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("userID", userID);

        long status = db.insert("category", null, contentValues);
        db.close();

        return status != -1;
    }

    public boolean insertTask(String userID, int categoryID, String name, String deadline, boolean status) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryID", categoryID);
        contentValues.put("userID", userID);
        contentValues.put("name", name);
        contentValues.put("deadline", deadline);
        contentValues.put("status", status);

        long insertStatus = db.insert("task", null, contentValues);
        db.close();

        return insertStatus != -1;
    }

    public boolean updateCategory(int categoryID, String name) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);

        String whereClause = "id = ?";

        int rowUpdated = db.update("category", contentValues, whereClause, new String[]{String.valueOf(categoryID)});
        db.close();

        return rowUpdated > 0;
    }

    public boolean updateTask(int taskID, int categoryID, String name, String deadline, boolean status) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryID", categoryID);
        contentValues.put("name", name);
        contentValues.put("deadline", deadline);
        contentValues.put("status", status);

        String whereClause = "id = ?";

        int rowAffected = db.update("task", contentValues, whereClause, new String[]{String.valueOf(taskID)});
        db.close();
        return rowAffected > 0;
    }

    public Cursor getCategories(String userID) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM category WHERE userID = ?";

        return db.rawQuery(query, new String[]{userID});
    }

    public int getCategoryIDByName(String categoryName, String userID) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT id FROM category WHERE userID = ? AND name = ? LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{userID, categoryName});

        int id = -1;

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();

        return id;
    }

    public Cursor getTasks(String userID, int categoryID) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM task WHERE userID = ? AND categoryID = ?";

        return db.rawQuery(query, new String[]{userID, String.valueOf(categoryID)});
    }

    public void deleteCategory(int categoryID) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = "id = ?";

        db.delete("category", whereClause, new String[]{String.valueOf(categoryID)});
        db.close();
    }

    public void deleteTask(int taskID) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = "id = ?";

        db.delete("task", whereClause, new String[]{String.valueOf(taskID)});
        db.close();
    }
}
