package com.example.cctvstreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LayerAuthentication extends AppCompatActivity {
    private TextView welcome,webPortal,userManual, verify;
    private Button requestAccess;
    private String id;
    private String stat;
    private TextView message;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layer_authentication);

        welcome = findViewById(R.id.welcome_note);
        webPortal = findViewById(R.id.web_portal);
        userManual = findViewById(R.id.user_manual);
        verify = findViewById(R.id.verify_text);
        id = (String) getIntent().getStringExtra("id");
        stat = (String) getIntent().getStringExtra("state");
        requestAccess = findViewById(R.id.request_access);
        if(stat.compareTo("requested") == 0)
        {
            requestAccess.setText("Edit Access");
            message = (TextView) findViewById(R.id.authMessage);
            message.setText("You have already requested for "+getIntent().getStringExtra("about") + " access");
        }
        requestAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubmitAccessForm.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });


    }
}