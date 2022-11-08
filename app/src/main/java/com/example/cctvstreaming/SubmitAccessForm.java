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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SubmitAccessForm extends AppCompatActivity implements ListInterface{

    private TextView topText, bellCount;
    private TextInputLayout firstName,lastName, email, about, msg,phoneNumber,message;

    private ImageView backArrow;
    private ViewGroup bell;
    private User user;
    private ImageButton help_access_level,help_district,help_upozilla,help_school,help_form_designation;

    private Spinner accessLevel, district, upozilla, institute,designation;
    private CheckBox checkInfo;
    private Button submit;
    private ArrayList<String> disList = new ArrayList<>();
    private ArrayList<String> subDisList = new ArrayList<>();
    private ArrayList<String> schoolList = new ArrayList<>();
    private ArrayList<String> designationList = new ArrayList<>();
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
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#674AAE"));
//        actionBar.setBackgroundDrawable(colorDrawable);
        idText = getIntent().getStringExtra("id");
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

        topText = findViewById(R.id.top_text);

        topText.setText(R.string.request_access);


        backArrow = findViewById(R.id.back_icon_image);

        bellCount = findViewById(R.id.notification_count);
        bell = findViewById(R.id.notification_bell);



        help_access_level = findViewById(R.id.help_access_level);
        help_district = findViewById(R.id.help_district);
        help_upozilla = findViewById(R.id.help_upozilla);
        help_school = findViewById(R.id.help_instituttion);
        help_form_designation = findViewById(R.id.help_form_designation);


        help_form_designation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.help_popup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                TextView helpText = popupView.findViewById(R.id.help_text);
                helpText.setText("This a Designation Suggestion");
                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
        help_access_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.help_popup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                TextView helpText = popupView.findViewById(R.id.help_text);
                helpText.setText("This a Access Level Suggestion");
                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        help_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.help_popup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                TextView helpText = popupView.findViewById(R.id.help_text);
                helpText.setText("District Level Suggestion");
                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        help_upozilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.help_popup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                TextView helpText = popupView.findViewById(R.id.help_text);
                helpText.setText("Upozilla Level Suggestion");
                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        help_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.help_popup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                TextView helpText = popupView.findViewById(R.id.help_text);
                helpText.setText("School Level Suggestion");
                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
//                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        List<String> designationString = new ArrayList<>();
        designationString.add(0, "Select Your Designation");
        designationString.add("UNO");
        designationString.add("Headmaster");
        designationString.add("Lab Assistant");
        designationString.add("District Commissioner");
        designationString.add("ICT Teacher");
        designationString.add("AC Land");
        ArrayAdapter<String> arrayAdapterDesignation = new ArrayAdapter(this, android.R.layout.simple_list_item_1, designationString);
        arrayAdapterDesignation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        designation.setAdapter(arrayAdapterDesignation);
        designation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select Your Designation")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    recognitionText = item;
//                    DatabaseController.getDistrictList(listInterface);
//                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        bell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatabaseController.updateVal(user.getId(),Long.valueOf(-1));
//                Toast.makeText(SubmitAccessForm.this, "Notification Called", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(SubmitAccessForm.this,RequestList.class);
//                intent.putExtra("id",user.getId());
//                startActivity(intent);
//            }
//        });

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                String fName = firstName.getEditText().getText().toString();
                String lName = lastName.getEditText().getText().toString();
                nameText = fName + " "+ lName;
//                recognitionText = designation.getEditText().getText().toString();
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
//                        Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
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