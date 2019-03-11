package com.androstock.galleryapp;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;


public class CameraActivity extends AppCompatActivity {

    android.hardware.Camera camera;
    FrameLayout frameLayout;
    CameraPreview cameraPreview;
    Button mGallery;
    static final int MEDIA_TYPE_IMAGE = 1;

    public final String errTAG = "Camera did not open";


    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        frameLayout = (FrameLayout) findViewById(R.id.framelayout1);



        mGallery = (Button) findViewById(R.id.mGallery);
        mGallery.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(CameraActivity.this,   //On click, a new
                        GalleryActivity.class);                                 // activity is open
                startActivity(intent);
            }
        });



        // Add a listener to the Capture button
        Button captureButton = (Button) findViewById(R.id.mTake);
        captureButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        camera.takePicture(null, null, mPictureCallback);
                    }
                }
        );


        /* This block of code opens the camera facing back, but it also can open the camera facing
         * in the front by changing CAMERA_FACING_BACK to CAMERA_FACING_FRONT
         */
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                try {
                    camera = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e(errTAG, "Camera failed to open: " + e.toString());
                }
            }
        }

        // Calls the cameraprevie class and sets info into cameraPreview
        cameraPreview = new CameraPreview(this, camera);
        // Places the layout of the camera inside framelayout
        frameLayout.addView(cameraPreview);



    }


    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            if (pictureFile == null){
                Log.d(errTAG, "Error creating media file, check storage permissions");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
//                System.out.println("trying");
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(errTAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(errTAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }


    private void releaseCamera(){
        if (camera != null){
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }



}
