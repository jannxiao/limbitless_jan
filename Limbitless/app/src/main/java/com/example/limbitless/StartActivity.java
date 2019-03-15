package com.example.limbitless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartActivity extends AppCompatActivity {

    private Button btnGallery, btnPrecap;                        //Declaring btnStart as an instance of Button
    public final String FILE_TAG = "Img did not save";
    String albumName;
    File picDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        btnGallery = findViewById(R.id.btn_to_gallery);
        btnGallery.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(StartActivity.this,   //On click, a new
                        GalleryActivity.class);                                 // activity is open
                startActivity(intent);
            }
        });

        btnPrecap = findViewById(R.id.btn_to_precapture);
        btnPrecap.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(StartActivity.this,   //On click, a new
                        PrecaptureActivity.class);                                 // activity is open

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                albumName = "SESSION_" + timeStamp + "_";
                picDirectory = makePrivateAlbumStorageDir(albumName);

                intent.putExtra("picDirectory", picDirectory);
                startActivity(intent);
            }
        });
    }

    public File makePrivateAlbumStorageDir(String albumName) {
        File file = new File(getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);

        if (!file.mkdirs()) {
            Log.e(FILE_TAG, "Directory not created");
        }

        return file;
    }

}
