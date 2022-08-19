package com.example.cctvstreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreean extends AppCompatActivity implements SignInterface {
    FirebaseAuth mAuth;
    SignInterface signInterface = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screean);
//        DatabaseController.setDc("Comilla","xBjDfLKET0N9RNdkyytLo5yqMuw2");
        mAuth = FirebaseAuth.getInstance();
        Log.d("noman","kire");
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DatabaseController.checkAccess(signInterface);
            }
        },1000);
    }

    @Override
    public void onComplete(User user, String message) {
        if(user == null)
        {
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
        }
        else if(user.getName() == null)
        {
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
//            Log.d("newMe","Seek access");
//            Intent intent = new Intent(getApplicationContext(), LayerAuthentication.class);
//            intent.putExtra("id",user.getId());
//            intent.putExtra("state","pending");
//            startActivity(intent);
        }
        else if (user.getStatus().compareTo("requested") == 0)
        {
            Intent intent = new Intent(getApplicationContext(), LayerAuthentication.class);
            intent.putExtra("id",user.getId());
            intent.putExtra("state","requested");
            intent.putExtra("about",user.getAccessLabel());
            startActivity(intent);
        }
        else if(user.getStatus().compareTo("ok") == 0)
        {
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }
        finish();
    }
}