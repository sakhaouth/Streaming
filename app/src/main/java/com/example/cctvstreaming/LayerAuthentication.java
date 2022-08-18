package com.example.cctvstreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LayerAuthentication extends AppCompatActivity {
    private TextView welcome,webPortal,userManual, verify;
    private Button requestAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layer_authentication);

        welcome = findViewById(R.id.welcome_note);
        webPortal = findViewById(R.id.web_portal);
        userManual = findViewById(R.id.user_manual);
        verify = findViewById(R.id.verify_text);

        requestAccess = findViewById(R.id.request_access);

        requestAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SubmitAccessForm.class));
            }
        });


    }
}