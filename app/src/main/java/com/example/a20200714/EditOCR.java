package com.example.a20200714;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class EditOCR extends AppCompatActivity {
    EditText OCRResult;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_ocr);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        editText();
        save();
    }


    public void editText(){
        OCRResult = findViewById(R.id.OCRresults);

        text = getIntent().getStringExtra("text");
        OCRResult.setText(text);
    }


    public void save(){
        Button save = findViewById(R.id.ok);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String title = getIntent().getStringExtra("title");
                text = OCRResult.getText().toString();

                Intent toOCRResult = new Intent(EditOCR.this, OCRResult.class);
                toOCRResult.putExtra("title", title);
                toOCRResult.putExtra("text", text);
                startActivity(toOCRResult);
            }
        });
    }

}