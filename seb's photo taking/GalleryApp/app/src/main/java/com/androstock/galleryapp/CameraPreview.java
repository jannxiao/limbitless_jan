package com.androstock.galleryapp;


import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    Camera mCamera;
    SurfaceHolder holder;
    public final String TAG = "CameraPreview";

    public CameraPreview(CameraActivity context, Camera camera){
        super(context);
        this.mCamera = camera;
        holder = getHolder();
        holder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

            Camera.Parameters params = mCamera.getParameters();

            //We are getting the picture resolution
            List<Camera.Size> sizes = params.getSupportedPictureSizes();
            Camera.Size mSize = null;
            for(Camera.Size size: sizes) {
                mSize = size;
            }

        if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){

            // Sets to portrait
            params.set("orientation","portrait");
            mCamera.setDisplayOrientation(90);
            params.setRotation(90);

        }
        else{
            //Sets to landscape
            params.set("orientation","landscape");
            mCamera.setDisplayOrientation(0);
            params.setRotation(0);
        }
        // We set the resolution in params
        params.setPictureSize(mSize.width, mSize.height);

        // We start the preview of the camera in the phone
        try {
            mCamera.getParameters();
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();

    }
}
