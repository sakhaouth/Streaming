package com.example.cctvstreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SubmitAccessForm extends AppCompatActivity {

    private TextView formInit;
    private TextInputLayout firstName,lastName, email, designation, about, msg;
    private Spinner accessLevel, district, upozilla, institute;
    private CheckBox checkInfo;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_access_form);


        formInit = findViewById(R.id.form_init_text);
        firstName = findViewById(R.id.form_first_name);
        lastName = findViewById(R.id.form_last_name);
        email = findViewById(R.id.form_email);
        designation = findViewById(R.id.form_designation);
        about = findViewById(R.id.form_about);
        msg = findViewById(R.id.form_message);

        accessLevel = findViewById(R.id.form_access_level);
        district = findViewById(R.id.districts);
        upozilla = findViewById(R.id.upozilla);
        institute = findViewById(R.id.instituttion);

        checkInfo = findViewById(R.id.check_info);

        submit = findViewById(R.id.access_form_submit);

        List<String> footballPlayers = new ArrayList<>();
        footballPlayers.add(0, "Select Access Level");
        footballPlayers.add("Layer 1 (Deputy Commissioner)");
        footballPlayers.add("Layer 2 (UNO)");
        footballPlayers.add("Layer 3 (HeadMaster)");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, footballPlayers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accessLevel.setAdapter(arrayAdapter);
        accessLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select Access Level")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Create a user with status == false...
            }
        });


    }


}