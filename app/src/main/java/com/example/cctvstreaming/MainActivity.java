package com.example.cctvstreaming;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    TextInputLayout emailTextView,passwordTextView;
    private TextView messageTexView,loginTextView;
    private Button loginButton;
    private ProgressBar progressBar;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailTextView = (TextInputLayout) findViewById(R.id.client_login_email);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        passwordTextView = (TextInputLayout) findViewById(R.id.client_login_password);
        loginButton = (Button) findViewById(R.id.client_login_button);
        messageTexView = (TextView) findViewById(R.id.client_login_message_view);
        loginTextView = (findViewById(R.id.client_login_text));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                //email = emailTextView.getText().toString();
                email = emailTextView.getEditText().getText().toString();
                password = passwordTextView.getEditText().getText().toString();
                if(email.compareTo("") == 0)
                {
                    messageTexView.setText("Type email");
                    return;
                }
                if(password.compareTo("") == 0)
                {
                    messageTexView.setText("Type Password");
                    return;
                }
                callDatabase(email,password);

            }




        });



    }

    private void callDatabase(String email, String password) {
        if(email.compareTo("shn065@gmail.com") == 0 && password.compareTo("noman065") == 0)
        {
            String uri = "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1";
            Intent intent = new Intent(getApplicationContext(),SchoolList.class);
            //intent.putExtra("uri",uri);
            startActivity(intent);
        }
        else
        {
            messageTexView.setText("LogIn error");
        }

    }

}