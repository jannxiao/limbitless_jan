package com.example.limbitless;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureActivity extends AppCompatActivity {

    private Button btnGallery, btnCapture;                        //Declaring btnStart as an instance of Button
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
                Intent intent = new Intent(CaptureActivity.this,   //On click, a new
                        GalleryActivity.class);                                 // activity is open
                startActivity(intent);
            }
        });

        btnCapture = findViewById(R.id.btn_to_capture);
        btnCapture.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                albumName = "SESSION_" + timeStamp + "_";
                picDirectory = makePrivateAlbumStorageDir(albumName);
                dispatchTakePictureIntent();
            }
        });

    }

    public File makePrivateAlbumStorageDir(String albumName) {
        // Get the directory for the app's private pictures directory.
//        String path = context.getExternalFilesDir(
//                Environment.DIRECTORY_PICTURES), albumName;
//        System.out.println(path);
        File file = new File(getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);

        if (!file.mkdirs()) {
            Log.e(FILE_TAG, "Directory not created");
        }

        return file;
    }

    static final int REQUEST_TAKE_PHOTO = 1;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getApplicationContext(), "Error while saving picture.", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.limbitless.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = picDirectory;
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
