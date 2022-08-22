package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity implements SignInterface {
    TextInputLayout emailTextView,passwordTextView;
    private TextView messageTexView,loginTextView;
    private Button loginButton,signupButton;
    private ProgressBar progressBar;
    private AlertDialog dialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#674AAE"));
        actionBar.setBackgroundDrawable(colorDrawable);
        emailTextView = (TextInputLayout) findViewById(R.id.client_login_email);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        passwordTextView = (TextInputLayout) findViewById(R.id.client_login_password);
        loginButton = (Button) findViewById(R.id.client_login_button);
        messageTexView = (TextView) findViewById(R.id.client_login_message_view);
        signupButton = findViewById(R.id.signUpButton);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.loginProgressBar) ;
//        ArrayList<String> cameraNames = new ArrayList<>();
//        cameraNames.add("Physics Lab");
//        cameraNames.add("Bioa Lab");
//        School school = new School("Keronkhal School and College","Cumilla","Chandina",cameraNames);
//        DatabaseController.saveSchool(school);
//        school = new School("Dr. Firoza Girls High School","Cumilla","Chandina",cameraNames);
//        DatabaseController.saveSchool(school);
//        User user = new User("Sakhaouth","X","Y","p","055","aa","kk");
//        DatabaseController.saveUser(user);
//        DatabaseController.getDc("Comilla",getApplicationContext());
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"as",Toast.LENGTH_SHORT);
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
//        DatabaseController.sendNotification("aa");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                //email = emailTextView.getText().toString();

                email = emailTextView.getEditText().getText().toString();
                password = passwordTextView.getEditText().getText().toString();
                if(TextUtils.isEmpty(email)) {
                    emailTextView.setError("Please Provide Email");
                    emailTextView.requestFocus();
                }else if(TextUtils.isEmpty(password)){
                    passwordTextView.setError("Please Type Password");
                    passwordTextView.requestFocus();
                }else {
                    Log.d("pass",email+password);
                    callDatabase(email,password);
                }


            }

        });



    }

    private void callDatabase(String email, String password) {
        loginButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        DatabaseController.signIn(email,password,this);

    }

    @Override
    public void onComplete(User user, String message) {
        progressBar.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);

        if(user == null)
        {
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        }
        else if(user.getName() == null)
        {
            Intent intent = new Intent(getApplicationContext(), LayerAuthentication.class);
            intent.putExtra("id",user.getId());
            intent.putExtra("state","pending");
            startActivity(intent);
        }
        else if(user.getStatus().compareTo("requested") == 0)
        {
            Intent intent = new Intent(getApplicationContext(), LayerAuthentication.class);
            intent.putExtra("id",user.getId());
            intent.putExtra("state","requested");
            intent.putExtra("about",user.getAccessLabel());
            startActivity(intent);
        }
        else if(user.getStatus().compareTo("ok") == 0)
        {
            Toast.makeText(LogIn.this,"User Logged In Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),HomePage.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }

    }
}