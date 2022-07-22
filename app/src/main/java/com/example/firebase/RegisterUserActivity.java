package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.example.firebase.databinding.ActivityRegisterUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterUserActivity extends AppCompatActivity {
    ActivityRegisterUserBinding binding;
    private String emailStr, passStr, confirmPasswordStr, nameStr, phoneStr;
    ProgressDialog dialog;
    private String deviceId;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        deviceId = Settings.Secure.getString(RegisterUserActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(RegisterUserActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");


        binding.register.setOnClickListener(view -> {
            registerUser();
            if (binding.mobileNo.length()>9)
            {
                binding.mobileNo.setError("Mobile number is invalid ");
            }

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
    private Boolean validatePhoneNo() {
        String val = binding.mobileNo.getText().toString();

        if (val.isEmpty()){
            binding.mobileNo.setError("Field cannot be empty");
            return false;
        }
        else {
            binding.mobileNo.setError(null);
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
    private Boolean validateConfirmPassword() {
        String val = binding.confirmPassword.getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";

        if (val.isEmpty()){
            binding.confirmPassword.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(passwordVal)){
            binding.password.setError("Password is too weak");
            return false;
        }
        else {
            binding.confirmPassword.setError(null);
            return true;
        }
    }

    private void registerUser() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Userdata");
        final FirebaseUser user = auth.getCurrentUser();

        if (!validateEmail() || !validatePhoneNo() || !validatePassword() || !validatePassword() || !validateConfirmPassword())
        {
            return;
        }
        nameStr = binding.name.getEditableText().toString();
        emailStr = binding.email.getEditableText().toString();
        phoneStr = binding.mobileNo.getEditableText().toString();
        passStr = binding.password.getEditableText().toString();
        confirmPasswordStr = binding.confirmPassword.getEditableText().toString();

        Query query = reference.orderByChild("deviceId").equalTo(deviceId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    Toast.makeText(RegisterUserActivity.this, "Device already registered with us", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (validate())
                    {
                        signUpUser(user, nameStr, emailStr, passStr);
                        dialog.show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void signUpUser(FirebaseUser user, String nameStr, String emailStr, String passStr) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Userdata");

        auth.createUserWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    dialog.dismiss();
                    String userId = auth.getCurrentUser().getUid();

                    HashMap<String , Object> map = new HashMap<>();

                    map.put("Email", emailStr);
                    map.put("Name", nameStr);
                    map.put("Password", passStr);
                    map.put("Mobile", phoneStr);
                    map.put("userId", userId);

                    reference.child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(RegisterUserActivity.this, OtpActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                }

            }
        });


    }

    private boolean validate() {
        if (passStr.compareTo(confirmPasswordStr) !=0)
        {
            Toast.makeText(RegisterUserActivity.this, "Password Not Same", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}