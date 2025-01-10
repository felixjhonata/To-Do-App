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
import com.myapps.todoapp.controller.CategoryController;
import com.myapps.todoapp.model.Category;
import com.myapps.todoapp.model.SharedViewModel;
import com.myapps.todoapp.view.main_app.adapter.CategoriesAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class CategoriesFragment extends Fragment {

    RecyclerView recyclerView;
    TextView noCategoriesLbl;
    SharedViewModel sharedViewModel;

    CategoryController cc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        recyclerView = view.findViewById(R.id.categoriesRV);
        noCategoriesLbl = view.findViewById(R.id.noCategoriesLbl);

        cc = new CategoryController(getContext());

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        ArrayList<Category> categoryArrayList = cc.getCategories(userID);

        if(categoryArrayList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noCategoriesLbl.setVisibility(View.VISIBLE);
        } else {
            sharedViewModel.setSelectedCategory(userID, categoryArrayList.get(0).getName(), getContext());

            recyclerView.setVisibility(View.VISIBLE);
            noCategoriesLbl.setVisibility(View.GONE);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(new CategoriesAdapter(getContext(), categoryArrayList, sharedViewModel));
        }
    }
}