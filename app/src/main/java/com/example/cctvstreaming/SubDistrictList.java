package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubDistrictList extends AppCompatActivity implements SubDistrictInterface {
    private String district;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SubDistrictListAdaptor subDistrictListAdaptor;
    private ImageView backArrow;
    private TextView topText, districtName;
    private String userId;
    private TextView bellCount;
    private ViewGroup bell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getSupportActionBar();
//
//        assert actionBar != null;
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#674AAE"));
//        actionBar.setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.activity_sub_district_list);
//        ActionBar actionBar = getSupportActionBar();
        district = (String) getIntent().getStringExtra("dis");
        userId = (String) getIntent().getStringExtra("id");

        recyclerView = (RecyclerView) findViewById(R.id.sub_district_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.sub_district_progressBar);
        Log.d("district",district);
        fetchData(district);
        subDistrictListAdaptor = new SubDistrictListAdaptor(district, userId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(subDistrictListAdaptor);
        backArrow = findViewById(R.id.back_icon_image);

        Log.d("start", "start");
        bellCount = findViewById(R.id.notification_count);
        bell = findViewById(R.id.notification_bell);
        Log.d("end", userId);
        init();


        topText = findViewById(R.id.top_text);

        topText.setText("Sub Districts");

        districtName = findViewById(R.id.district_name);
        districtName.setText(district);


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseController.updateVal(userId,Long.valueOf(-1));
                Intent intent = new Intent(SubDistrictList.this,RequestList.class);
                intent.putExtra("id",userId);
                startActivity(intent);
            }
        });

    }

    @Override
    public void getSubDistrict(ArrayList<SubDistrict> subDistricts) {
        subDistrictListAdaptor.setSubDistricts(subDistricts);
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void fetchData(String district)
    {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseController.getSubDistricts(district,this);

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


    private void init()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long notificationNo = snapshot.getValue(Long.class);
                bellCount.setText(String.valueOf(notificationNo));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}