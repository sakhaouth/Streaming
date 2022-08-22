package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SubmitAccessForm extends AppCompatActivity implements ListInterface{

    private TextView formInit;
    private TextInputLayout firstName,lastName, email, designation, about, msg,phoneNumber,message;
    private Spinner accessLevel, district, upozilla, institute;
    private CheckBox checkInfo;
    private Button submit;
    private ArrayList<String> disList = new ArrayList<>();
    private ArrayList<String> subDisList = new ArrayList<>();
    private ArrayList<String> schoolList = new ArrayList<>();
    private String nameText;
    private String districtText;
    private String subDistrictText;
    private String recognitionText;
    private String numberText;
    private String statusText;
    private String idText;
    private String emailText;
    private String aboutText;
    private String accessLabelText;
    private String schoolName;
    private ProgressBar progressBar;
    ListInterface listInterface = this;
    private TextView formError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_access_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#674AAE"));
        actionBar.setBackgroundDrawable(colorDrawable);
        idText = getIntent().getStringExtra("id");
        formInit = findViewById(R.id.form_init_text);
        firstName = findViewById(R.id.form_first_name);
        lastName = findViewById(R.id.form_last_name);
        email = findViewById(R.id.form_email);
        designation = findViewById(R.id.form_designation);
        about = findViewById(R.id.form_about);
        msg = findViewById(R.id.form_message);
        phoneNumber = findViewById(R.id.form_number);
        accessLevel = findViewById(R.id.form_access_level);
        district = findViewById(R.id.districts);
        upozilla = findViewById(R.id.upozilla);
        institute = findViewById(R.id.instituttion);
        formError = (TextView) findViewById(R.id.formError);
        checkInfo = findViewById(R.id.check_info);
        progressBar = findViewById(R.id.submit_progress);
        submit = findViewById(R.id.access_form_submit);

        List<String> footballPlayers = new ArrayList<>();
        footballPlayers.add(0, "Select Access Level");
        footballPlayers.add("District");
        footballPlayers.add("Upozilla");
        footballPlayers.add("Institution");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, footballPlayers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accessLevel.setAdapter(arrayAdapter);
        accessLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select Access Level")){
                }else {
                    disList.clear();
                    subDisList.clear();
                    schoolList.clear();
                    String item = parent.getItemAtPosition(position).toString();
                    accessLabelText = item;
                    DatabaseController.getDistrictList(listInterface);
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                String fName = firstName.getEditText().getText().toString();
                String lName = lastName.getEditText().getText().toString();
                nameText = fName + " "+ lName;
                recognitionText = designation.getEditText().getText().toString();
                emailText = email.getEditText().getText().toString();
                numberText = phoneNumber.getEditText().getText().toString();
                statusText = "pending";
//                Create a user with status == false...
                if(TextUtils.isEmpty(nameText))
                {
                    formError.setText("*Set Name");
                    return;
                }
                if(TextUtils.isEmpty(emailText))
                {
                    formError.setText("*Set Email");
                    return;
                }
                if(TextUtils.isEmpty(numberText))
                {
                    formError.setText("*Set Number");
                    return;
                }
                if(TextUtils.isEmpty(recognitionText))
                {
                    formError.setText("*Set Recognition");
                    return;
                }
                if(TextUtils.isEmpty(accessLabelText))
                {
                    formError.setText("*Set Access Level");
                    return;
                }
                if(TextUtils.isEmpty(districtText))
                {
                    formError.setText("*Set District");
                    return;
                }
                if(accessLabelText.compareToIgnoreCase("upozilla") == 0)
                {
                    if(TextUtils.isEmpty(accessLabelText))
                    {
                        formError.setText("*Set Upozilla");
                        return;
                    }
                }
                if(accessLabelText.compareToIgnoreCase("institution") == 0)
                {
                    if(TextUtils.isEmpty(schoolName))
                    {
                        formError.setText("*Set Institution");
                        return;
                    }
                }
                if(!checkInfo.isChecked())
                {
                    formError.setText("*Everything is correct?");
                    return;
                }
                submit.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                User user = new User(nameText,districtText,subDistrictText,recognitionText,numberText,idText,emailText,statusText,aboutText,accessLabelText,schoolName);

                String messageText = msg.getEditText().getText().toString();
                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = localDateTime.format(myFormatObj);
                String temp = new String();
                if(accessLabelText.compareTo("district") == 0)
                {
                    temp = districtText;
                }
                else if(accessLabelText.compareTo("upozilla") == 0)
                {
                    temp = subDistrictText;
                }
                else if(accessLabelText.compareTo("institution") == 0)
                {
                    temp = schoolName;
                }
                Notification notification = new Notification(String.format("New access request from %s %s for %s %s access",user.getRecognition(),user.getName(),temp,user.getAccessLabel()),user.getId(),user.getName(),formattedDate,user.getRecognition());
                DatabaseController.saveUser(user,notification,listInterface);
            }
        });





    }


    @Override
    public void setDistrict(ArrayList<String> districts) {
        if(districts != null)
        {
            this.disList.add("Select District");
            this.disList.addAll(districts);
            ArrayAdapter<String> disAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, disList);
            disAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            district.setAdapter(disAdapter);
            district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("Select District")){
                    }else {
                        subDisList.clear();
                        schoolList.clear();
                        String item = parent.getItemAtPosition(position).toString();
                        districtText = item;
                        if(accessLabelText.compareTo("Upozilla") == 0 || accessLabelText.compareTo("Institution") == 0)
                        {
                            DatabaseController.getSubDisList(districtText,listInterface);
                        }
                        Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        }
        else
        {

        }
    }

    @Override
    public void setSubDis(ArrayList<String> subDistricts) {
        if(subDistricts != null)
        {
            this.subDisList.add("Select Upozilla");
            this.subDisList.addAll(subDistricts);
            ArrayAdapter<String> subDisAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subDisList);
            subDisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            upozilla.setAdapter(subDisAdapter);
            upozilla.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("Select Upozilla")){
                    }else {
                        schoolList.clear();
                        String item = parent.getItemAtPosition(position).toString();
                        subDistrictText = item;
                        if(accessLabelText.compareTo("Institution") == 0)
                        {
                            DatabaseController.getSchoolDisList(districtText,subDistrictText,listInterface);
                        }
                        Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


        }
        else
        {

        }
    }

    @Override
    public void setSchool(ArrayList<String> schools) {
        if(schools != null)
        {
            this.schoolList.add("Select School");
            this.schoolList.addAll(schools);
            ArrayAdapter<String> schoolAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, schoolList);
            schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            institute.setAdapter(schoolAdapter);
            institute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("Select School")){
                    }else {
                        String item = parent.getItemAtPosition(position).toString();
                        schoolName = item;
                        Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    @Override
    public void setSubmit(String message) {
        progressBar.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
        Intent intent = new Intent(getApplicationContext(), LayerAuthentication.class);
        intent.putExtra("id",idText);
        intent.putExtra("state","requested");
        intent.putExtra("about",accessLabelText);
        startActivity(intent);
//        finish();

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