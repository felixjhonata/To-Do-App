package com.myapps.todoapp.view.authentication_page;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.myapps.todoapp.R;
import com.myapps.todoapp.controller.UserController;
import com.myapps.todoapp.model.AlertFactory;

public class RegisterPage extends AppCompatActivity {

    private FloatingActionButton backBtn;
    private EditText emailEdt, passwordEdt, confirmPasswordEdt;
    private Button registerBtn;
    private CheckBox showPasswordCB;

    private UserController uc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        uc = new UserController();

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        emailEdt = findViewById(R.id.emailEdt);
        passwordEdt = findViewById(R.id.passwordEdt);
        confirmPasswordEdt = findViewById(R.id.confirmPasswordEdt);
        showPasswordCB = findViewById(R.id.showPasswordCB);

        showPasswordCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int inputType = b ?
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;

                passwordEdt.setInputType(inputType);
                confirmPasswordEdt.setInputType(inputType);
            }
        });

        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog progressDialog = AlertFactory.showProgressDialog(RegisterPage.this);

                if(uc.validateRegisterField(emailEdt, passwordEdt, confirmPasswordEdt)) {

                    uc.Register(emailEdt, passwordEdt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });


                } else {
                    progressDialog.dismiss();
                }
            }
        });
    }
}