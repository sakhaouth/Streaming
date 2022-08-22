package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

public class SignupActivity extends AppCompatActivity implements SignUpInterface{
    private TextInputLayout emailTextView,passwordTextView, confirmPassTextView;
    private TextView message;
    private Button signup,signin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#674AAE"));
        actionBar.setBackgroundDrawable(colorDrawable);
        emailTextView = findViewById(R.id.client_sign_email);
        passwordTextView = findViewById(R.id.client_sign_password);
        confirmPassTextView = findViewById(R.id.client_sign_password_2);
        message = findViewById(R.id.client_sign_message_view);
        signin = findViewById(R.id.client_signin_button);
        signup = findViewById(R.id.client_sign_button);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.sign_progress);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LogIn.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                String email,password,password_2;
                email = emailTextView.getEditText().getText().toString();
                password = passwordTextView.getEditText().getText().toString();
                password_2 =  confirmPassTextView.getEditText().getText().toString();



                if (password.compareTo(password_2) == 0) {
                    if(TextUtils.isEmpty(email)) {
                        emailTextView.setError("Please Provide Email");
                        emailTextView.requestFocus();
                    }else if(TextUtils.isEmpty(password)){
                        passwordTextView.setError("Please Type Password");
                        passwordTextView.requestFocus();
                    }else if(TextUtils.isEmpty(password_2)) {
                        confirmPassTextView.setError("Please Confirm The Password");
                        confirmPassTextView.requestFocus();
                    }else {
                        Log.d("pass",email+password+password_2);
                        createuser(email,password);
                    }
                }else {
                    Log.d("pass","No Match");
                    Log.d("pass",email+password+password_2);
                    message.setText(R.string.pass_no_match);
                }
            }
        });

    }

    private void createuser(String email, String password) {
        DatabaseController.signUp(email,password,this);
    }

    @Override
    public void onCompleteSignUp(String message) {
        progressBar.setVisibility(View.GONE);
        signup.setVisibility(View.VISIBLE);
        if(message.compareTo("ok") == 0)
        {
            Toast.makeText(SignupActivity.this,"User Registered Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),LogIn.class));
        }
        else
        {
            Toast.makeText(SignupActivity.this,"User Registration Failed" + message, Toast.LENGTH_SHORT).show();
        }

    }
}