package com.example.cctvstreaming;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SchoolListAdaptor extends RecyclerView.Adapter<SchoolListAdaptor.ViewHolder> {
    ArrayList<School> schools;
    private Context context;
    public SchoolListAdaptor(Context context)
    {
        schools = new ArrayList<>();
        this.context = context;
    }
    public SchoolListAdaptor(ArrayList<School> schools)
    {
        this.schools = schools;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.school_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.schoolName.setText(schools.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.camera_list);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                RecyclerView recyclerView = dialog.findViewById(R.id.camera_list_recycler_view);
                CameraListAdaptor cameraListAdaptor = new CameraListAdaptor(schools.get(position).getCameraNames(),schools.get(position).getLink());
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(cameraListAdaptor);
                dialog.show();


            }
        });
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return schools.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView schoolName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            schoolName = (TextView) itemView.findViewById(R.id.school_name);

        }
    }
    public void setSchools(ArrayList<School> schools)
    {
        this.schools = schools;
        notifyDataSetChanged();
    }
}
