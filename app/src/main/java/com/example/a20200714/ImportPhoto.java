package com.example.a20200714;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImportPhoto extends AppCompatActivity {
    String currentPhotoPath;
    Uri photoURI;
    int fromCamera = 1;
    int fromLibrary = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_photo);
        camera();
        library();
    }


    public void camera(){
        Button camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent shot = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(v.getContext(), "com.example.a20200714.provider", photoFile);
                    shot.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                }
                startActivityForResult(shot,fromCamera);
            }

        });
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void library(){
        Button library = findViewById(R.id.library);
        library.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), fromLibrary);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == fromLibrary){
            photoURI = data.getData();
        }
        Intent toCheckPhoto = new Intent(ImportPhoto.this, CheckPhoto.class);
        toCheckPhoto.putExtra("img",photoURI);
        startActivity(toCheckPhoto);
    }

}

