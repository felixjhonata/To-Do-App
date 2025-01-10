package com.myapps.todoapp.view.main_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapps.todoapp.R;
import com.myapps.todoapp.controller.UserController;
import com.myapps.todoapp.view.LandingPage;
import com.myapps.todoapp.view.main_app.fragments.CategoriesFragment;
import com.myapps.todoapp.view.main_app.fragments.TasksFragment;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton logoutBtn = findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserController uc = new UserController();
                Intent intent = new Intent(HomePage.this, LandingPage.class);

                uc.Logout();
                startActivity(intent);
                finish();
            }
        });

        CategoriesFragment categoriesFragment = new CategoriesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.categoriesFragment, categoriesFragment)
                .commit();

        TasksFragment tasksFragment = new TasksFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tasksFragment, tasksFragment)
                .commit();

        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }

    private void showPopupMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.add_pop_up_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.action_add_category) {
                Intent intent = new Intent(HomePage.this, AddCategoryPage.class);
                startActivity(intent);
                return true;
            }

            if (item.getItemId() == R.id.action_add_task) {
                Intent intent = new Intent(HomePage.this, AddTaskPage.class);
                startActivity(intent);
                return true;
            }

            return false;
        });

        popupMenu.show();
    }
}