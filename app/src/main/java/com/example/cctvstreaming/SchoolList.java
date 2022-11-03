package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class SchoolList extends AppCompatActivity implements SchoolListInterface {
    private RecyclerView recyclerView;

    ArrayList<School> schools = new ArrayList<>();
    SchoolListAdaptor schoolListAdaptor = new SchoolListAdaptor(this);
    private ProgressBar progressBar;
    private ImageView backArrow;
    private TextView topText,districtName,upozillaName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getSupportActionBar();
//
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#674AAE"));
//        actionBar.setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.activity_school_list);
        progressBar = (ProgressBar) findViewById(R.id.schoolListProgressBar);
        String dis = (String) getIntent().getStringExtra("dis");
        String sub = (String) getIntent().getStringExtra("sub");
        String school = (String) getIntent().getStringExtra("school");
        if(school == null)
        {
            fetchSchools(dis,sub);
        }
        else
        {
            DatabaseController.getIndSchool(dis,sub,school,this);
        }

        recyclerView = (RecyclerView) findViewById(R.id.school_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(schoolListAdaptor);

        backArrow = findViewById(R.id.back_icon_image);

        topText = findViewById(R.id.top_text);

        topText.setText("Sub Districts");

        districtName = findViewById(R.id.district_name);
        upozillaName = findViewById(R.id.upozilla_name);
        districtName.setText(dis);
        upozillaName.setText(sub);



        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void fetchSchools(String district,String subDistrict)
    {
        DatabaseController.getSchool(district,subDistrict,this);
    }
    public void createLink()
    {


	    ArrayList<String> cameraNames = new ArrayList<>();
	    cameraNames.add("Digital Lab");
	    cameraNames.add("IP Lab");
	    School school = new School("Cahndina Pilot School","Cumilla","Chandina",cameraNames);
        schools.add(school);

        DatabaseController.saveSchool(school);
//        User user = new User("aa","Cumilla","Chandina","dc","222");



    }

    @Override
    public void setSchool(ArrayList<School> schools) {

        schoolListAdaptor.setSchools(schools);
        progressBar.setVisibility(View.GONE);

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
}