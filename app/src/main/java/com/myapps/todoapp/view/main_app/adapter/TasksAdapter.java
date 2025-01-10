package com.myapps.todoapp.view.main_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.todoapp.R;
import com.myapps.todoapp.controller.TaskController;
import com.myapps.todoapp.model.Task;
import com.myapps.todoapp.view.main_app.EditTaskPage;
import com.myapps.todoapp.view.main_app.view_holder.TasksViewHolder;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksViewHolder> {

    Context context;
    ArrayList<Task> taskArrayList;
    TaskController tc;

    public TasksAdapter(Context context, ArrayList<Task> taskArrayList) {
        this.context = context;
        this.taskArrayList = taskArrayList;
        this.tc = new TaskController(context);
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TasksViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        Task task = taskArrayList.get(position);

        float scale = holder.itemView.getContext().getResources().getDisplayMetrics().density;
        int margin = dpToPixels(10, scale);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();

        if(position == 0) {
            layoutParams.setMargins(0, margin, 0, margin);
        } else {
            layoutParams.setMargins(0, 0, 0, margin);
        }

        decorateTask(task, holder);

        holder.taskNameTV.setText(task.getName());
        holder.dueDateTV.setText(task.getDeadline());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tc.toggleTaskComplete(task);
                task.setCompleted(!task.isCompleted());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, EditTaskPage.class);
                intent.putExtra("taskID", task.getId());
                intent.putExtra("dueDate", task.getDeadline());
                intent.putExtra("categoryID", task.getCategoryID());
                intent.putExtra("isCompleted", task.isCompleted());
                intent.putExtra("taskName", task.getName());
                context.startActivity(intent);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    private void decorateTask(Task task, TasksViewHolder holder) {
        if(task.isCompleted()) {
            holder.itemView.setBackgroundColor(Color.parseColor("#333333"));
            holder.taskNameTV.setPaintFlags(holder.taskNameTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#DC143C"));
            holder.taskNameTV.setPaintFlags(holder.taskNameTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public int dpToPixels(int marginInDp, float scale) {
        return (int) (marginInDp * scale + 0.5f);
    }
}
