package com.androstock.galleryapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button btnGallery, btnCapture;                        //Declaring btnStart as an instance of Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        btnGallery = (Button) findViewById(R.id.btn_to_gallery);
        btnGallery.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(StartActivity.this,   //On click, a new
                        GalleryActivity.class);                                 // activity is open
                startActivity(intent);
            }
        });

//        btnNewProject = findViewById(R.id.mNewProject);
//        btnNewProject.setOnClickListener( new View.OnClickListener() {
//
//            public void onClick(View view){
//                Intent intent = new Intent(StartActivity.this,   //On click, a new
//                        CameraActivity.class);                                 // activity is open
//                startActivity(intent);
//            }
//        });

        btnCapture=(Button)findViewById(R.id.btn_to_capture);
        btnCapture.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(StartActivity.this,   //On click, a new
                        CameraActivity.class);                                 // activity is open
                startActivity(intent);
            }
        });



    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
    
    // necessary!
    // Camera.release()
}
