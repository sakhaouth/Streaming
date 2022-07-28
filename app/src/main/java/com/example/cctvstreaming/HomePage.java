package com.example.cctvstreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity {
    private Button streamButton;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        streamButton = (Button) findViewById(R.id.stream_button);
        imageView = (ImageView) findViewById(R.id.imageView);

        streamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SchoolList.class);
                //intent.putExtra("uri",uri);
                intent.putExtra("dis","Cumilla");
                intent.putExtra("sub","Chandina");
                startActivity(intent);
            }
        });
        
    }
}