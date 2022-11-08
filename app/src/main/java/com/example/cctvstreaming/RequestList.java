package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Sortbyroll implements Comparator<Notification>
{
    // Used for sorting in ascending order of
    // roll number


    @Override
    public int compare(Notification notification, Notification t1) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(notification.getReqTime(),dateTimeFormatter);
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse(t1.getReqTime(),dateTimeFormatter1);
//        Log.d("ttime",dateTime.toString());
//        Log.d("ttime",notification.getReqTime());
        return dateTime1.compareTo(dateTime);
    }
}
public class RequestList extends AppCompatActivity implements GetNotificationInterface,AcceptInterface{
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private  RequestListAdaptor requestListAdaptor;
    private String id;
    private TextView topText;
    private ImageView backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
//        ActionBar actionBar = getSupportActionBar();
//
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
////        actionBar.setIcon(R.drawable.ic_arrow_square_left);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_square_left);
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#674AAE"));
//        actionBar.setBackgroundDrawable(colorDrawable);
        topText = findViewById(R.id.top_text);

        topText.setText("Notifications");

        recyclerView = (RecyclerView) findViewById(R.id.requestListRecyclerView);
        id = getIntent().getStringExtra("id");
        progressBar = (ProgressBar) findViewById(R.id.reqListProgressBar);
        requestListAdaptor = new RequestListAdaptor(this);
//        requestListAdaptor.setVal(dis,sub,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(requestListAdaptor);
        backArrow = findViewById(R.id.back_icon_image);
        callDatabase();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void callDatabase()
    {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseController.getReceiveNotifications(id,this);

    }
    @Override
    public void onComplete(ArrayList<Notification> notifications) {
        progressBar.setVisibility(View.GONE);
        if(notifications == null)
        {
            return;
        }
        Collections.sort(notifications,new Sortbyroll());
        requestListAdaptor.setList(notifications);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
        }

        return super.onOptionsItemSelected(item);



    }

    @Override
    public void onComplete(String message) {
//        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}