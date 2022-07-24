package com.example.cctvstreaming;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class SchoolList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText editText;
    private Button button;
    String serverIp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        setContentView(R.layout.activity_school_list);
        editText = (EditText) findViewById(R.id.editTextTextPersonName);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLink();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.school_list_recycler_view);

    }
    public void createLink()
    {
        serverIp = editText.getText().toString();ArrayList<School> schools = new ArrayList<>();
        schools.add(new School("School-1",serverIp));
        schools.add(new School("School-2",serverIp));
        SchoolListAdaptor schoolListAdaptor = new SchoolListAdaptor(schools);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(schoolListAdaptor);

    }
}