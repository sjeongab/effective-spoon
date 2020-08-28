package com.example.a20200714;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Database extends AppCompatActivity {
    TextView list = null;
    ListView listview = null;
    boolean multipleChoice = false;
    ArrayList<String> LIST = null;
    ArrayAdapter adapter = null;
    Button home;
    Button editList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database);

        home();
        callList();
        clickList();
        editList();
    }


    public void home() {
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toHome = new Intent(Database.this, Home.class);
                startActivity(toHome);}
        });
    }


    public void callList(){
        List<HealthResult> healthResult = HealthDatabase.getInstance(this).healthDao().getAll();
        LIST = new ArrayList<String>();
        for (int i=0; i<healthResult.size(); i++) {
            LIST.add(healthResult.get(i).title);
        }

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,LIST);
        listview = (ListView) findViewById(R.id.healthList);
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_NONE);
        listview.setItemChecked(1, true);
    }


    public void clickList(){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                if(multipleChoice==false){
                    callData(parent, position, v);
                }
                else{
                    deleteData(parent, position, v);
                }
            }
        }) ;
    }


    public void callData(AdapterView parent, int position, View v){
        String strText = (String) parent.getItemAtPosition(position);
        List<HealthResult> item = (List<HealthResult>) HealthDatabase.getInstance(v.getContext()).healthDao().loadAllByIds(strText);
        Intent toOCRResult= new Intent(Database.this, OCRResult.class);
        toOCRResult.putExtra("title", item.get(0).title);
        toOCRResult.putExtra("text", item.get(0).health);
        startActivity(toOCRResult);
    }


    public void deleteData(final AdapterView parent, final int position, final View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("삭제하시겠습니까?");
        builder.setNeutralButton("아니오", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) { }
        });

        builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                delete(parent, position, v);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void delete(AdapterView parent, int position, View v){
        String strText = (String) parent.getItemAtPosition(position) ;
        List<HealthResult> item = (List<HealthResult>) HealthDatabase.getInstance(v.getContext()).healthDao().loadAllByIds(strText);
        HealthDatabase.getInstance(v.getContext()).healthDao().delete(item.get(0));
        List<HealthResult> hr = HealthDatabase.getInstance(v.getContext()).healthDao().getAll();
        LIST.clear();
        for (int i=0; i<hr.size(); i++) {
            LIST.add(hr.get(i).title);
        }
        adapter.notifyDataSetChanged();
    }


    public void editList(){
        list = findViewById(R.id.list);
        editList = findViewById(R.id.editList);
        editList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(multipleChoice==false){
                    home.setEnabled(false);
                    home.setVisibility(View.INVISIBLE);
                    list.setText("삭제하려는 항목을 선택해주세요");
                    editList.setText("완료");
                    multipleChoice = true;
                }
                else{
                    home.setEnabled(true);
                    home.setVisibility(View.VISIBLE);
                    list.setText("건강 기록");
                    editList.setText("편집");
                    multipleChoice = false;
                }
            }
        });
    }

}