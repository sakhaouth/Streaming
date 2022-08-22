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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class RequestList extends AppCompatActivity implements GetNotificationInterface,AcceptInterface{
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private  RequestListAdaptor requestListAdaptor;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#674AAE"));
        actionBar.setBackgroundDrawable(colorDrawable);
        recyclerView = (RecyclerView) findViewById(R.id.requestListRecyclerView);
        id = getIntent().getStringExtra("id");
        progressBar = (ProgressBar) findViewById(R.id.reqListProgressBar);
        requestListAdaptor = new RequestListAdaptor(this);
//        requestListAdaptor.setVal(dis,sub,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(requestListAdaptor);
        callDatabase();

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
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}