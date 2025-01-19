package com.myapps.todoapp.controller;

import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserController {

    public boolean validateRegisterField(EditText emailEdt, EditText passwordEdt, EditText confirmPasswordEdt) {
        boolean error = false;

        String email = emailEdt.getText().toString();
        String password = passwordEdt.getText().toString();
        String confirmPassword = confirmPasswordEdt.getText().toString();

        if(email.trim().isEmpty()) {
            emailEdt.setError("Email cannot be empty!");
            error = true;
        } else if (!email.trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            emailEdt.setError("Email invalid!");
            error = true;
        }

        if(password.trim().isEmpty()) {
            passwordEdt.setError("Password cannot be empty!");
            error = true;
        } else if (password.trim().length() < 8) {
            passwordEdt.setError("Password must be 8 characters long!");
            error = true;
        }

        if(!confirmPassword.trim().equals(password.trim())) {
            confirmPasswordEdt.setError("Password doesn't match!");
            error = true;
        }

        return !error;
    }

    public Task<AuthResult> Register(EditText emailEdt, EditText passwordEdt) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        String email = emailEdt.getText().toString();
        String password = passwordEdt.getText().toString();

        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> Login(EditText emailEdt, EditText passwordEdt) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        String email = emailEdt.getText().toString();
        String password = passwordEdt.getText().toString();

        try{
            return firebaseAuth.signInWithEmailAndPassword(email, password);
        } catch (Exception e) {
            return Tasks.forException(e);
        }
    }

    public void Logout() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signOut();
    }

}
