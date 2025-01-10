package com.myapps.todoapp.view.main_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.myapps.todoapp.R;
import com.myapps.todoapp.controller.CategoryController;

import java.util.Objects;

public class EditCategoryPage extends AppCompatActivity {

    CategoryController cg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_category_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cg = new CategoryController(getApplicationContext());

        Intent intent = getIntent();
        int categoryID = intent.getIntExtra("categoryID", -1);
        String categoryName = intent.getStringExtra("categoryName");

        if(categoryID == -1) {
            finish();
        }

        configureBackBtn();

        ImageButton deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cg.deleteCategory(categoryID);
                finish();
            }
        });

        EditText categoryNameField = findViewById(R.id.categoryNameEdt);
        categoryNameField.setText(categoryName);

        Button editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                if(cg.updateCategory(userID, categoryID, categoryNameField)) {
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
}