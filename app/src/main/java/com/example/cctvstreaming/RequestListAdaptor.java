package com.example.cctvstreaming;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class RequestListAdaptor extends RecyclerView.Adapter<RequestListAdaptor.ViewHolder> {
    private ArrayList<Notification> notifications;
    String dis,sub;
    AcceptInterface acceptInterface;
    Activity activity;
    Context context;
    public RequestListAdaptor(Context context)
    {
        this.notifications = new ArrayList<>();
        this.context = context;
    }
    @NonNull
    @Override
    public RequestListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.request_view,parent,false);
        return new RequestListAdaptor.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RequestListAdaptor.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.reqDes.setText(notifications.get(position).getDescription());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        Log.d("time",);
        LocalDateTime dateTime = LocalDateTime.parse(notifications.get(position).getReqTime(),dateTimeFormatter);
        Log.d("time",dateTime.toString());
        holder.reqTime.setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateTime));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                notifications.get(position).getSenderId()
                Intent intent = new Intent(context,ShowAccessForm.class);
                intent.putExtra("id",notifications.get(position).getSenderId());
                context.startActivity(intent);
            }
        });
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView reqDes;
        private TextView reqTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reqDes = (TextView) itemView.findViewById(R.id.reqDes);
            reqTime = (TextView) itemView.findViewById(R.id.reqTime);

        }
    }
    public void setList(ArrayList<Notification> notifications)
    {
        this.notifications = notifications;
        notifyDataSetChanged();
    }
}
