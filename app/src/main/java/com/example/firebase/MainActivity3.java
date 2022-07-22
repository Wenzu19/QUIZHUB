package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebase.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {
    ActivityMain3Binding binding;
    ImageView img;
    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        img=(ImageView)findViewById(R.id.desc_img);
        tv1=(TextView)findViewById(R.id.desc_header);
        tv2=(TextView)findViewById(R.id.desc_desc);

        binding.backBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity3.this, MainActivity2.class));
            finish();
        });


    }
}
