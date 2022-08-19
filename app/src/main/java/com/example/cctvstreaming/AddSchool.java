package com.example.cctvstreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddSchool extends AppCompatActivity {
    EditText name,district,subDis,camera;
    Button save;
    ArrayList<String> cameraNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school);
        name = (EditText) findViewById(R.id.nameText);
        district = (EditText) findViewById(R.id.disText);
        subDis = (EditText) findViewById(R.id.subDisText);
        camera = (EditText) findViewById(R.id.cameraText);
        save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String schoolName = name.getText().toString();
                String disName = district.getText().toString();
                String subName = subDis.getText().toString();
                String cameraName = camera.getText().toString();
                cameraNames.add(cameraName);
                School school = new School(schoolName,disName,subName,cameraNames);
                DatabaseController.storeSchool(school);
                cameraNames.clear();

            }
        });
    }
}