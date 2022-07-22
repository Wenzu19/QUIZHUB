package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.firebase.databinding.ActivityMain2Binding;
import com.example.firebase.databinding.ActivityMainBinding;
import com.example.firebase.databinding.ActivityShowDataBinding;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
    RecyclerView rcv;
    myadapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        rcv = (RecyclerView) findViewById(R.id.recview);
        adapter = new myadapter(dataqueue(),getApplicationContext());
        rcv.setAdapter(adapter);


        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        rcv.setLayoutManager(gridLayoutManager);

        binding.exitBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity2.this, MainActivity.class));
            finish();
        });


    }

    public ArrayList<Model> dataqueue()
    {
        ArrayList<Model> holder=new ArrayList<>();

        Model ob1=new Model();
        ob1.setHeader("C Programming");
        ob1.setDesc("Desktop Programming");
        ob1.setImgname(R.drawable.cp);
        holder.add(ob1);

        Model ob2=new Model();
        ob2.setHeader("C++ Programming");
        ob2.setDesc("Desktop Programming Language");
        ob2.setImgname(R.drawable.cpp);
        holder.add(ob2);

        Model ob3=new Model();
        ob3.setHeader("Java Programming");
        ob3.setDesc("Desktop Programming Language");
        ob3.setImgname(R.drawable.java);
        holder.add(ob3);

        Model ob4=new Model();
        ob4.setHeader("PHP Programming");
        ob4.setDesc("Web Programming Language");
        ob4.setImgname(R.drawable.php);
        holder.add(ob4);

        Model ob5=new Model();
        ob5.setHeader(".NET Programming");
        ob5.setDesc("Desktop/Web Programming Language");
        ob5.setImgname(R.drawable.dotnet);
        holder.add(ob5);

        Model ob6=new Model();
        ob6.setHeader("Wordpress Framework");
        ob6.setDesc("PHP based Blogging Framework");
        ob6.setImgname(R.drawable.wordpress);
        holder.add(ob6);

        Model ob7=new Model();
        ob7.setHeader("Magento Framework");
        ob7.setDesc("PHP Based e-Comm Framework");
        ob7.setImgname(R.drawable.magento);
        holder.add(ob7);

        Model ob8=new Model();
        ob8.setHeader("Shopify Framework");
        ob8.setDesc("PHP based e-Comm Framework");
        ob8.setImgname(R.drawable.shopify);
        holder.add(ob8);

        Model ob9=new Model();
        ob9.setHeader("Angular Programming");
        ob9.setDesc("Web Programming");
        ob9.setImgname(R.drawable.angular);
        holder.add(ob9);

        Model ob10=new Model();
        ob10.setHeader("Python Programming");
        ob10.setDesc("Desktop/Web based Programming");
        ob10.setImgname(R.drawable.python);
        holder.add(ob10);

        Model ob11=new Model();
        ob11.setHeader("Node JS Programming");
        ob11.setDesc("Web based Programming");
        ob11.setImgname(R.drawable.nodejs);
        holder.add(ob11);

        Model ob12=new Model();
        ob12.setHeader("A+ Programming");
        ob12.setDesc("Array programming language");
        ob12.setImgname(R.drawable.aplus_logo   );
        holder.add(ob12);

        Model ob13=new Model();
        ob13.setHeader("Cg Programming");
        ob13.setDesc("High-Level Shader Language");
        ob13.setImgname(R.drawable.cg);
        holder.add(ob13);

        Model ob14=new Model();
        ob14.setHeader("C# Programming");
        ob14.setDesc("Multi-paradigm programming language");
        ob14.setImgname(R.drawable.csharp);
        holder.add(ob14);

        Model ob15=new Model();
        ob15.setHeader("Clean Programming");
        ob15.setDesc("General-purpose purely functional computer programming language");
        ob15.setImgname(R.drawable.clean);
        holder.add(ob15);

        Model ob16=new Model();
        ob16.setHeader("D Programming");
        ob16.setDesc("Compilers with the expressive power of modern dynamic and functional programming languages");
        ob16.setImgname(R.drawable.dprog);
        holder.add(ob16);

        Model ob17=new Model();
        ob17.setHeader("Fortran Programming");
        ob17.setDesc("numeric computation and scientific computing");
        ob17.setImgname(R.drawable.fortran);
        holder.add(ob17);

        Model ob18=new Model();
        ob18.setHeader("POV-Ray Programming");
        ob18.setDesc("Cross-platform ray-tracing");
        ob18.setImgname(R.drawable.povray);
        holder.add(ob18);

        Model ob19=new Model();
        ob19.setHeader("Rexx Programming");
        ob19.setDesc("Interpreted or compiled programming");
        ob19.setImgname(R.drawable.rex);
        holder.add(ob19);

        Model ob20=new Model();
        ob20.setHeader("Rust Programming");
        ob20.setDesc("Multi-paradigm, general-purpose");
        ob20.setImgname(R.drawable.rust);
        holder.add(ob20);


        return holder;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem item=menu.findItem(R.id.menu_search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}