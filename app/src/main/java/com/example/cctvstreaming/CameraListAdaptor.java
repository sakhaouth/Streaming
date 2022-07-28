package com.example.cctvstreaming;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CameraListAdaptor extends RecyclerView.Adapter<CameraListAdaptor.ViewHolder> {
    ArrayList<String> cameraList;
    String link;
    public CameraListAdaptor(ArrayList<String> cameraList,String link)
    {
        this.cameraList = cameraList;
        this.link = link;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.school_view,parent,false);
        return new CameraListAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.cameraName.setText(cameraList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = link + "-" + cameraList.get(position).replaceAll(" ","-").toLowerCase();

                Intent intent = new Intent(holder.itemView.getContext(),MediaPlayer.class);
                intent.putExtra("uri",uri);
                holder.itemView.getContext().startActivity(intent);


            }
        });
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return cameraList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cameraName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cameraName = (TextView) itemView.findViewById(R.id.school_name);
        }
    }
}
