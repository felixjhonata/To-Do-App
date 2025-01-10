package com.myapps.todoapp.view.main_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.myapps.todoapp.R;
import com.myapps.todoapp.model.Category;
import com.myapps.todoapp.model.SharedViewModel;
import com.myapps.todoapp.view.main_app.EditCategoryPage;
import com.myapps.todoapp.view.main_app.view_holder.CategoriesViewHolder;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    Context context;
    ArrayList<Category> categoryArrayList;
    SharedViewModel sharedViewModel;

    public CategoriesAdapter(Context context, ArrayList<Category> categoryArrayList, SharedViewModel sharedViewModel) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        Category category = categoryArrayList.get(position);

        holder.categoryLbl.setText(category.getName());

        float scale = holder.itemView.getContext().getResources().getDisplayMetrics().density;

        int margin = dpToPixels(10, scale);
        int bigMargin = dpToPixels(25, scale);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();

        if(position == 0) {
            layoutParams.setMargins(bigMargin, 0, margin, 0);
        } else if (position == categoryArrayList.size() - 1) {
            layoutParams.setMargins(margin, 0, bigMargin, 0);
        } else {
            layoutParams.setMargins(margin, 0, margin, 0);
        }

        String selectedCategory = sharedViewModel.getSelectedCategory().getValue();

        if(category.getName().equals(selectedCategory)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#DC143C"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#333333"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                sharedViewModel.setSelectedCategory(userID, category.getName(), context);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, EditCategoryPage.class);
                intent.putExtra("categoryID", category.getId());
                intent.putExtra("categoryName", category.getName());
                context.startActivity(intent);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public int dpToPixels(int marginInDp, float scale) {
        return (int) (marginInDp * scale + 0.5f);
    }
}
