package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebase.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    String emailStr, passwordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");

        if (auth.getCurrentUser() != null)
        {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }
        binding.registerBtn.setOnClickListener(view ->{
            startActivity(new Intent(SignInActivity.this, RegisterUserActivity.class));
        });

        binding.logInBtn.setOnClickListener(View ->{
            logInUser();

        });

    }
    private Boolean validateEmail() {
        String val = binding.email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            binding.email.setError("Field cannot empty");
            return false;
        }
        else if (!val.matches(emailPattern)){
            binding.email.setError("Invalid email address");
            return false;
        }else{
            binding.email.setError(null);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = binding.password.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +
                // "(?=.*[a-z])" +
                // "(?=.*[A-Z])" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";

        if (val.isEmpty()){
            binding.password.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(passwordVal)){
            binding.password.setError("Password is too weak");
            return false;
        }
        else {
            binding.password.setError(null);
            return true;
        }
    }
    private void logInUser() {
        if (!validateEmail() || !validatePassword())
        {
            return;
        }
        else{
            emailStr = binding.email.getText().toString();
            passwordStr = binding.password.getText().toString();
            dialog.show();

            logInUsers(emailStr, passwordStr);
        }
    }
    private void logInUsers(String emailStr, String passwordStr) {
        auth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    dialog.dismiss();
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignInActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
    }
}