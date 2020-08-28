package com.example.a20200714;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CheckPhoto extends AppCompatActivity {
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//glide
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_photo);
        preview();
        reselect();
        confirm();
    }


    public void preview(){
        Intent loadPhoto = getIntent();
        Uri imageUri = loadPhoto.getParcelableExtra("img");
        ImageView preview = findViewById(R.id.preview);
        preview.setImageURI(imageUri);
    }


    public void reselect(){
        Button reselect = findViewById(R.id.reselect);
        reselect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toImportPhoto = new Intent(CheckPhoto.this, ImportPhoto.class);
                startActivity(toImportPhoto);
            }
        });
    }


    public void confirm(){
        Button confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toOCRResult = new Intent(CheckPhoto.this, OCRResult.class);
                photoOCR();
                toOCRResult.putExtra("text", text);
                startActivity(toOCRResult);
            }
        });
    }


    public void photoOCR() {
        //Dummy code: put photoOCR code here
        text = "Hello world!";
    }

}