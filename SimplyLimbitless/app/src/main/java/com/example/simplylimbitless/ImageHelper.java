package com.example.simplylimbitless;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//decreases image size for better performance
public class ImageHelper {

    public static int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int HEIGHT = options.outHeight;
        final int WIDTH = options.outWidth;
        int inSampleSize = 1;

        if(HEIGHT > reqHeight && WIDTH > reqWidth){
            final int HALF_HEIGHT = HEIGHT / 2;
            final int HALF_WIDTH = WIDTH / 2;

            while((HALF_HEIGHT / inSampleSize) >= reqHeight && (HALF_WIDTH / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }

        return inSampleSize;

    }

    public static Bitmap decodeSampledBitmapFromPath(String pathName, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }
}
