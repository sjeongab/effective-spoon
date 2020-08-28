package com.example.a20200714;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        addRecord();
        toHealthList();
    }


    public void addRecord(){
        Button addRecord = findViewById(R.id.addRecord);
        addRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toImportPhoto = new Intent(Home.this, ImportPhoto.class);
                startActivity(toImportPhoto);
            }
        });
    }


    public void toHealthList(){
        Button toHealthList = findViewById(R.id.toHealthList);
        toHealthList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toDatabase = new Intent(Home.this, Database.class);
                startActivity(toDatabase);
            }
        });
    }

}