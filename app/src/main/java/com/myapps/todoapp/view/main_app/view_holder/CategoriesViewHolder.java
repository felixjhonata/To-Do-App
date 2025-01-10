package com.myapps.todoapp.view.main_app.view_holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.todoapp.R;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    public TextView categoryLbl;

    public CategoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryLbl = itemView.findViewById(R.id.categoryLbl);
    }
}
