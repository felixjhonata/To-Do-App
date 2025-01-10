package com.myapps.todoapp.view.main_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.myapps.todoapp.R;
import com.myapps.todoapp.controller.TaskController;
import com.myapps.todoapp.model.SharedViewModel;
import com.myapps.todoapp.model.Task;
import com.myapps.todoapp.view.main_app.adapter.TasksAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class TasksFragment extends Fragment {

    TextView noTaskLbl;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();

        noTaskLbl = view.findViewById(R.id.noTaskLbl);
        recyclerView = view.findViewById(R.id.tasksRV);

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        TaskController tc = new TaskController(getContext());

        String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        String categoryName = sharedViewModel.getSelectedCategory().getValue();

        ArrayList<Task> taskArrayList = tc.getTasks(userID, categoryName);

        changeLayout(taskArrayList);

        if(!taskArrayList.isEmpty()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new TasksAdapter(getContext(), taskArrayList));

            sharedViewModel.getTaskArrayList().observe(getViewLifecycleOwner(), tasks -> {
                recyclerView.setAdapter(new TasksAdapter(getContext(), tasks));
                changeLayout(tasks);
            });
        }
    }

    public void changeLayout(ArrayList<Task> taskArrayList) {
        if(taskArrayList.isEmpty()) {
            noTaskLbl.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noTaskLbl.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}