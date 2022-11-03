package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LayerAuthentication extends AppCompatActivity {
    private TextView welcome,webPortal,userManual, verify;
    private Button requestAccess,signOut;
    private String id;
    private String stat;
    private TextView message;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#674AAE"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_square_left);
        setContentView(R.layout.activity_layer_authentication);
        signOut = (Button) findViewById(R.id.request_signOut);
//        welcome = findViewById(R.id.welcome_note);
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
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseController.signOut();
                startActivity(new Intent(getApplicationContext(),LogIn.class));
            }
        });


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