package com.example.firebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myviewholder> implements Filterable
{
    ArrayList<Model> data;
    ArrayList<Model> backup;
    Context context;

    public myadapter(ArrayList<Model> data, Context context)

    {
        this.data = data;
        this.context=context;
        backup=new ArrayList<>(data);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, int position)
    {
        final Model temp=data.get(position);

        holder.t1.setText(data.get(position).getHeader());
        holder.t2.setText(data.get(position).getDesc());
        holder.img.setImageResource(data.get(position).getImgname());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,MainActivity3.class);
                intent.putExtra("imagename",temp.getImgname());
                intent.putExtra("header",temp.getHeader());
                intent.putExtra("desc",temp.getDesc());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        // background thread
        protected FilterResults performFiltering(CharSequence keyword)
        {
            ArrayList<Model> filtereddata=new ArrayList<>();

            if(keyword.toString().isEmpty())
                filtereddata.addAll(backup);
            else
            {
                for(Model obj : backup)
                {
                    if(obj.getHeader().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }

            FilterResults results=new FilterResults();
            results.values=filtereddata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            data.clear();
            data.addAll((ArrayList<Model>)results.values);
            notifyDataSetChanged();
        }
    };
}
