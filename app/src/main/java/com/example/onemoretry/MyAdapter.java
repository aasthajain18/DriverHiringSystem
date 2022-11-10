package com.example.onemoretry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<DriverDetails> list;

    public MyAdapter(Context context,ArrayList<DriverDetails> list){
        this.context = context;
        this.list = list;
    }
   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int version){
        View v = LayoutInflater.from(context).inflate(R.layout.driver_list,parent,false);
       return new MyViewHolder(v);
   }
   @Override
   public void onBindViewHolder(@NonNull MyViewHolder holder,int position){
       DriverDetails driverDetails = list.get(position);
       holder.Name.setText(driverDetails.getName());
       holder.Location.setText(driverDetails.getLoc());
       holder.Pincode.setText(driverDetails.getPin());
       holder.ContactNo.setText(driverDetails.getCon());
   }
   @Override
   public int getItemCount(){
       return list.size();
   }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView Name,ContactNo,Location,Pincode;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.txtDriverName);
            ContactNo= itemView.findViewById(R.id.txtDriverContact);
           Location = itemView.findViewById(R.id.txtDriverLocation);
            Pincode = itemView.findViewById(R.id.txtDriverPin);
        }
    }
}
