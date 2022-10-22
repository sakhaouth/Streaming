package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ShowAccessForm extends AppCompatActivity implements AcceptInterface,SignInterface{

    private TextView formError,profileInit,nameShow,profileEmail,number,designation, about, accessLevel,district,subdistrict,message;
    private Button accept, reject;
    private String id;
    ProgressBar progressBar;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#674AAE"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_square_left);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_access_form);
        id = getIntent().getStringExtra("id");
        profileInit = findViewById(R.id.profile_init_text);
        nameShow = findViewById(R.id.profile_name_show);
        profileEmail = findViewById(R.id.profile_email);
        number =findViewById(R.id.profile_number);
        progressBar = (ProgressBar) findViewById(R.id.profile_progress);
        designation = findViewById(R.id.profile_designation);
//        about = findViewById(R.id.profile_about);
//        accessLevel = findViewById(R.id.profile_access);
        district = findViewById(R.id.profile_district);
        subdistrict = findViewById(R.id.profile_subdistrict);
//        message = findViewById(R.id.profile_message);

        accept = findViewById(R.id.profile_accept);
        reject = findViewById(R.id.profile_reject);

        progressBar.setVisibility(View.VISIBLE);
        accept.setVisibility(View.GONE);
        DatabaseController.getUser(id,this);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null)
                {
                    DatabaseController.acceptReq(user.getDistrict(), user.getSubDistrict(),user.getId(),user.getRecognition(),ShowAccessForm.this);
                }
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onComplete(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onComplete(User user, String msg) {
        progressBar.setVisibility(View.GONE);
        if(user == null)
        {
            return;
        }
        this.user = user;
        nameShow.setText(user.getName());
        nameShow.setInputType(0);
        profileEmail.setText(user.getEmail());
        profileEmail.setInputType(0);

        number.setText(user.getNumber());
        number.setInputType(0);
        designation.setText(user.getRecognition());
        designation.setInputType(0);


        district.setText(user.getDistrict());
        district.setInputType(0);
        subdistrict.setText(user.getSubDistrict());
        subdistrict.setInputType(0);
        if(user.getStatus().compareToIgnoreCase("ok") != 0)
        {
            accept.setVisibility(View.VISIBLE);
        }

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