package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class SchoolList extends AppCompatActivity implements SchoolListInterface {
    private RecyclerView recyclerView;

    ArrayList<School> schools = new ArrayList<>();
    SchoolListAdaptor schoolListAdaptor = new SchoolListAdaptor(this);
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_school_list);
        progressBar = (ProgressBar) findViewById(R.id.schoolListProgressBar);
        String dis = (String) getIntent().getStringExtra("dis");
        String sub = (String) getIntent().getStringExtra("sub");
        fetchSchools(dis,sub);
        recyclerView = (RecyclerView) findViewById(R.id.school_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(schoolListAdaptor);

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("okay",String.valueOf(R.id.home));
        if(R.id.home == 2131362076)
        {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}