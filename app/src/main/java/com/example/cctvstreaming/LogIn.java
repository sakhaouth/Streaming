package com.example.cctvstreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LogIn extends AppCompatActivity {
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
        emailTextView = (TextInputLayout) findViewById(R.id.client_login_email);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        passwordTextView = (TextInputLayout) findViewById(R.id.client_login_password);
        loginButton = (Button) findViewById(R.id.client_login_button);
        messageTexView = (TextView) findViewById(R.id.client_login_message_view);
        signupButton = findViewById(R.id.signUpButton);
        mAuth = FirebaseAuth.getInstance();
//        ArrayList<String> cameraNames = new ArrayList<>();
//        cameraNames.add("Physics Lab");
//        cameraNames.add("Bioa Lab");
//        School school = new School("Keronkhal School and College","Cumilla","Chandina",cameraNames);
//        DatabaseController.saveSchool(school);
//        school = new School("Dr. Firoza Girls High School","Cumilla","Chandina",cameraNames);
//        DatabaseController.saveSchool(school);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LogIn.this,"User Logged In Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),HomePage.class));
                }else {
                    Toast.makeText(LogIn.this,"User Logging Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}