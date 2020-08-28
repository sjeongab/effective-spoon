package com.example.a20200714;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.*;

public class OCRResult extends AppCompatActivity {
    String title;
    String text;
    TextView OCRResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocr_result);

        setText();
        edit();
        retake();
        save();
    }


    public void setText(){
        OCRResult = findViewById(R.id.OCRresult);
        setTitle();
        text = getIntent().getStringExtra("text");
        OCRResult.setText(text);
    }


    public void setTitle(){
        title = getIntent().getStringExtra("title");
        if(title==null || title == ""){
            title = getTime();
        }
    }


    public String getTime(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = simpleDate.format(mDate);
        return getTime;
    }


    public void edit(){
        Button edit = (Button)findViewById(R.id.edit);
        edit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toEditOCR = new Intent(OCRResult.this, EditOCR.class);
                toEditOCR.putExtra("title", title);
                toEditOCR.putExtra("text", text);
                startActivity(toEditOCR);
            }
        });
    }


    public void retake(){
        Button retake = findViewById(R.id.retake);
        retake.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toImportPhoto = new Intent(OCRResult.this, ImportPhoto.class);
                startActivity(toImportPhoto);
            }
        });
    }


    public void save(){
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                alarm();
            }
        });
    }


    public void alarm(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("제목을 입력하세요.");
        final EditText edit_title = new EditText(this);
        edit_title.setText(title);
        alert.setView(edit_title);
        if(edit_title.getText()!= null && edit_title.getText().toString() != ""){
        alert.setPositiveButton("완료", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {title = edit_title.getText().toString();database();}
        });}
        alert.show();
    }


    public void database(){
        HealthResult health = new HealthResult();
        health.title = title;
        health.health = text;
        HealthDatabase.getInstance(this).healthDao().insert(health);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("성공적으로 저장되었습니다.");
        builder.setNeutralButton("새로 추가하기", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Intent toImportPhoto = new Intent(OCRResult.this, ImportPhoto.class);
                startActivity(toImportPhoto);
            }
        });

        builder.setPositiveButton("기록으로 이동", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Intent toDatabase = new Intent(OCRResult.this, Database.class);
                startActivity(toDatabase);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}