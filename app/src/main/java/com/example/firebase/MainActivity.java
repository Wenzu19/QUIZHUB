package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    int permissionCoe = 123;

    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.CAMERA, Manifest.permission.INTERNET};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED);
            requestPermissions(permission, permissionCoe);
        }

        binding.reviewBtn.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, MainActivity2.class));
            finish();
        });

        binding.logoutBtn.setOnClickListener(View -> {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        });




        binding.changePassBtn.setOnClickListener(view -> {
            changePasswordDialog();
        });
    }

    public void startGame(View view) {

        Intent intent = new Intent(MainActivity.this, StartGame.class);
        startActivity(intent);

        finish();
    }

    private void changePasswordDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.layout_chnage_password);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText oldPass = dialog.findViewById(R.id.oldPass);
        EditText newPass = dialog.findViewById(R.id.newPass);
        EditText reEnterNewPass = dialog.findViewById(R.id.re_enter_newPass);
        Button updatePass = dialog.findViewById(R.id.changePassBtn);

        updatePass.setOnClickListener(view -> {
            String oldPassStr = oldPass.getText().toString();
            String newPassStr = newPass.getText().toString();
            String reEnterNewPassStr = reEnterNewPass.getText().toString();

            if (TextUtils.isEmpty(oldPassStr))
            {
                oldPass.setError("Enter Your Old Password");
            } else if (oldPassStr.length()<6) {
                Toast.makeText(MainActivity.this, "Password must be 6 characters..", Toast.LENGTH_SHORT).show();
            } else if (newPassStr.isEmpty()) {
                newPass.setError("Field can't be empty");
            } else if (newPassStr.length()<6) {
                Toast.makeText(MainActivity.this, "Password must be 6 character..", Toast.LENGTH_SHORT).show();
            } else if (reEnterNewPassStr.isEmpty()) {
                reEnterNewPass.setError("Field can't be empty");
            } else if (reEnterNewPassStr.length()<6) {
                Toast.makeText(MainActivity.this, "Password must be 6 character..", Toast.LENGTH_SHORT).show();
            } else if (newPassStr.compareTo(reEnterNewPassStr) != 0) {
                Toast.makeText(MainActivity.this, "Password and confirm password should be same", Toast.LENGTH_SHORT).show();
            } else {
                updatePassword(oldPassStr, newPassStr);
            }

        });
        dialog.show();

    }

    private void updatePassword(String oldPassStr, String newPassStr) {
        dialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldPassStr);

        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dialog.dismiss();
                user.updatePassword(newPassStr).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Password Update Successfully!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}