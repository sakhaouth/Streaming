package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class SubDistrictList extends AppCompatActivity implements SubDistrictInterface {
    private String district;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SubDistrictListAdaptor subDistrictListAdaptor;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sub_district_list);
//        ActionBar actionBar = getSupportActionBar();
        district = (String) getIntent().getStringExtra("dis");
        recyclerView = (RecyclerView) findViewById(R.id.sub_district_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.sub_district_progressBar);
        Log.d("district",district);
        fetchData(district);
        subDistrictListAdaptor = new SubDistrictListAdaptor(district);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(subDistrictListAdaptor);

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
        Log.d("okay",String.valueOf(R.id.home));
        if(R.id.home == 2131362103)
        {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}