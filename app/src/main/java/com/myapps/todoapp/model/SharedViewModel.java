package com.myapps.todoapp.model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myapps.todoapp.controller.TaskController;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<String> selectedCategory = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Task>> taskArrayList = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<String> getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String userID, String categoryName, Context context) {
        selectedCategory.setValue(categoryName);

        TaskController tc = new TaskController(context);

        taskArrayList.setValue(tc.getTasks(userID, categoryName));
    }

    public MutableLiveData<ArrayList<Task>> getTaskArrayList() {
        return taskArrayList;
    }

}
