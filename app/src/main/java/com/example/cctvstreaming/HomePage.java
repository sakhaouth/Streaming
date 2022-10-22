package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import com.google.android.material.navigation.NavigationView;
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
    private FirebaseAuth mAuth;
    private TextView bellCount;
    private ViewGroup bell;
    private ViewGroup menuBar;
    private ViewGroup calender;
    private Button addSchoolButton;
    private Button editAccessButton;
    private User user;

    private NavigationView navigationView,navigationView_bot;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TextView userName;
    private TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        streamButton = (Button) findViewById(R.id.stream_button);
        addSchoolButton = (Button) findViewById(R.id.addSchoolButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        logout = findViewById(R.id.logout);
        editAccessButton = findViewById(R.id.editAccess);

        menuBar = findViewById(R.id.menu_bar_icon);
//
//
//
        toolbar = findViewById(R.id.homeToolbar);
//        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        navigationView_bot = findViewById(R.id.nav_view_bot);
        drawer = findViewById(R.id.drawer_layout);
        navigationView.setItemIconTintList(null);
        navigationView_bot.setItemIconTintList(null);
//        Log.d("Null Check", "onCreate: "+navigationView);



        bellCount = findViewById(R.id.notification_count);
        bell = findViewById(R.id.notification_bell);
        mAuth = FirebaseAuth.getInstance();
        user = (User) getIntent().getSerializableExtra("user");

        calender = findViewById(R.id.calender_icon);
        Toast.makeText(getApplicationContext(),user.getName(),Toast.LENGTH_SHORT).show();
        init();
//        DatabaseController.getDc("Comilla",getBaseContext());
//        DatabaseController.updateVal("aa");

//        toolbar.setNavigationIcon(R.drawable.ic_menu_bar);
        toggle = new ActionBarDrawerToggle(HomePage.this,drawer,toolbar,R.string.open,R.string.close);
//        toggle = new ActionBarDrawerToggle()
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);



//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_bar);

        toggle.syncState();
        menuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Menu Called", Toast.LENGTH_SHORT).show();
                drawer.open();
                userName = (TextView) findViewById(R.id.user_name);
                userEmail = findViewById(R.id.user_email);
                userName.setText(user.getName());
                userEmail.setText(user.getEmail());

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_live_stream:
                        Toast.makeText(HomePage.this, "Live Streaming Called", Toast.LENGTH_SHORT).show();

                        drawer.closeDrawer(GravityCompat.START);
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
                        break;
                }
                return true;
            }
        });
        navigationView_bot.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.logout_nav:
                        Toast.makeText(HomePage.this, "Log Out Called", Toast.LENGTH_SHORT).show();
                        drawer.closeDrawer(GravityCompat.START);
                        Toast.makeText(getApplicationContext(),"Logging Out",Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                        startActivity(new Intent(getApplicationContext(),LogIn.class));
                        break;
                    case R.id.request_nav:
                        Toast.makeText(HomePage.this, "Request Access Called", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SubmitAccessForm.class);
                        intent.putExtra("id",user.getId());
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                }
                return true;
            }
        });


        editAccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubmitAccessForm.class);
                intent.putExtra("id",user.getId());
                startActivity(intent);
            }
        });

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Calender Called", Toast.LENGTH_SHORT).show();
            }
        });
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseController.updateVal(user.getId(),Long.valueOf(-1));
                Toast.makeText(HomePage.this, "Notification Called", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomePage.this,RequestList.class);
                intent.putExtra("id",user.getId());
                startActivity(intent);
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