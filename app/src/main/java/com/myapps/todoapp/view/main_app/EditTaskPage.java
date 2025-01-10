package com.myapps.todoapp.view.main_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.myapps.todoapp.R;
import com.myapps.todoapp.controller.CategoryController;
import com.myapps.todoapp.controller.TaskController;
import com.myapps.todoapp.model.Category;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class EditTaskPage extends AppCompatActivity {

    CategoryController cc;
    TaskController tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_task_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        int taskID = intent.getIntExtra("taskID", -1);
        int categoryID = intent.getIntExtra("categoryID", -1);
        String dueDate = intent.getStringExtra("dueDate");
        String taskName = intent.getStringExtra("taskName");
        boolean isCompleted = intent.getBooleanExtra("isCompleted", false);

        ImageButton dueDateBtn = findViewById(R.id.dueDateBtn);
        EditText selectedDate = findViewById(R.id.dueDateEdt);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);

        tc = new TaskController(getApplicationContext());
        cc = new CategoryController(getApplicationContext());

        configureBackBtn();
        configureDueDate(dueDateBtn, selectedDate, dueDate);
        configureCategory(categorySpinner, categoryID);

        ImageButton deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tc.deleteTask(taskID);
                finish();
            }
        });

        EditText nameEdt = findViewById(R.id.taskNameEdt);
        nameEdt.setText(taskName);

        Button editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                if(tc.updateTask(taskID, userID, categorySpinner, nameEdt, selectedDate, isCompleted)) {
                    finish();
                }
            }
        });

    }

    private void configureBackBtn() {
        FloatingActionButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configureDueDate(ImageButton dueDateBtn, EditText selectedDate, String date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        selectedDate.setText(date);

        dueDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditTaskPage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                String date = String.format("%04d-%02d-%02d", year, month + 1, day);
                                selectedDate.setText(date);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });
    }

    private void configureCategory(Spinner categorySpinner, int categoryID) {
        String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        ArrayList<Category> categoryArrayList = cc.getCategories(userID);
        String categoryName = "";

        ArrayList<String> spinnerItems = new ArrayList<>();

        if(categoryArrayList.isEmpty()) {
            Toast.makeText(EditTaskPage.this, "You must make a category first!", Toast.LENGTH_SHORT).show();
            finish();
        }

        for(Category category : categoryArrayList) {
            spinnerItems.add(category.getName());
            if(category.getId() == categoryID) categoryName = category.getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, spinnerItems);

        categorySpinner.setAdapter(adapter);
        if(!categoryName.isEmpty()) categorySpinner.setSelection(adapter.getPosition(categoryName));
    }
}