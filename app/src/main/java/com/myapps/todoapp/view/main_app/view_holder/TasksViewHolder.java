package com.myapps.todoapp.view.main_app.view_holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.todoapp.R;

public class TasksViewHolder extends RecyclerView.ViewHolder {

    public TextView taskNameTV, dueDateTV;

    public TasksViewHolder(@NonNull View itemView) {
        super(itemView);

        taskNameTV = itemView.findViewById(R.id.taskNameTV);
        dueDateTV = itemView.findViewById(R.id.dueDateTV);
    }
}
