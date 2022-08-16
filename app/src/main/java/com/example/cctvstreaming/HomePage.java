package com.example.cctvstreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity {
    private Button streamButton,logout;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        streamButton = (Button) findViewById(R.id.stream_button);
        imageView = (ImageView) findViewById(R.id.imageView);
        logout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Logging Out",Toast.LENGTH_LONG).show();
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LogIn.class));
            }
        });

        streamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SchoolList.class);
                //intent.putExtra("uri",uri);
                intent.putExtra("dis","Cumilla");
                intent.putExtra("sub","Cumilla Sadar");
                startActivity(intent);
            }
        });
        
    }
}