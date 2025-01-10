package com.myapps.todoapp.view.main_app;

import android.app.DatePickerDialog;
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

public class AddTaskPage extends AppCompatActivity {

    private CategoryController cc;
    private TaskController tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cc = new CategoryController(getApplicationContext());
        tc = new TaskController(getApplicationContext());

        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        ImageButton dueDateBtn = findViewById(R.id.dueDateBtn);
        EditText selectedDate = findViewById(R.id.dueDateEdt);
        EditText name = findViewById(R.id.taskNameEdt);

        configureCategory(categorySpinner);
        configureBackBtn();
        configureDueDate(dueDateBtn, selectedDate);

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                if(tc.addTask(userID, categorySpinner, name, selectedDate)) {
                    finish();
                } else {
                    Toast.makeText(AddTaskPage.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
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

    private void configureDueDate(ImageButton dueDateBtn, EditText selectedDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        selectedDate.setText(String.format("%04d-%02d-%02d", year, month + 1, day));

        dueDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTaskPage.this,
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

    private void configureCategory(Spinner categorySpinner) {
        String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        ArrayList<Category> categoryArrayList = cc.getCategories(userID);

        ArrayList<String> spinnerItems = new ArrayList<>();

        if(categoryArrayList.isEmpty()) {
            Toast.makeText(AddTaskPage.this, "You must make a category first!", Toast.LENGTH_SHORT).show();
            finish();
        }

        for(Category category : categoryArrayList) {
            spinnerItems.add(category.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, spinnerItems);

        categorySpinner.setAdapter(adapter);
    }
}