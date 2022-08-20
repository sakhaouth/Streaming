package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity {
    private Button streamButton,logout;
    private ImageView imageView;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private TextView bellCount;
    private ViewGroup bell;
    Button addSchoolButton;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        streamButton = (Button) findViewById(R.id.stream_button);
        addSchoolButton = (Button) findViewById(R.id.addSchoolButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        logout = findViewById(R.id.logout);
        toolbar = findViewById(R.id.homeToolbar);
        bellCount = findViewById(R.id.notification_count);
        bell = findViewById(R.id.notification_bell);
        mAuth = FirebaseAuth.getInstance();
        user = (User) getIntent().getSerializableExtra("user");
        Toast.makeText(getApplicationContext(),user.getName(),Toast.LENGTH_SHORT).show();
        init();
//        DatabaseController.getDc("Comilla",getBaseContext());
//        DatabaseController.updateVal("aa");



        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Notification Called", Toast.LENGTH_SHORT).show();
            }
        });

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
                if(user.getAccessLabel().compareToIgnoreCase("district") == 0)
                {
                    Intent intent = new Intent(getApplicationContext(),SubDistrictList.class);
                    //intent.putExtra("uri",uri);
                    intent.putExtra("dis",user.getDistrict());
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                if(user.getAccessLabel().compareToIgnoreCase("upozilla") == 0)
                {
                    Intent intent = new Intent(getApplicationContext(),SchoolList.class);
                    //intent.putExtra("uri",uri);
                    intent.putExtra("dis",user.getDistrict());
                    intent.putExtra("sub",user.getSubDistrict());
                    startActivity(intent);
                }
                if(user.getAccessLabel().compareToIgnoreCase("institution") == 0)
                {
                    Intent intent = new Intent(getApplicationContext(),SchoolList.class);
                    //intent.putExtra("uri",uri);
                    intent.putExtra("dis",user.getDistrict());
                    intent.putExtra("sub",user.getSubDistrict());
                    intent.putExtra("school",user.getInstitution());
                    startActivity(intent);
                }

            }
        });
        addSchoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddSchool.class);
                //intent.putExtra("uri",uri);
                startActivity(intent);
            }
        });
        
    }



    private void init()
    {
        Log.d("noman","initiated");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(user.getId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long notificationNo = snapshot.getValue(Long.class);
                bellCount.setText(String.valueOf(notificationNo));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}