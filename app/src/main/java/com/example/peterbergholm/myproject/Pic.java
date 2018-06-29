package com.example.peterbergholm.myproject;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pic extends AppCompatActivity {
    public long userId;
    public long picId;
    public long projectId;
    public String description;
    public String picName; // Includes the absolute path of the image file on the mobile phone

    public Pic (long userId, long picId, long projectId, String picName, String description){
        this.userId=userId;
        this.picId=picId;
        this.projectId=projectId;
        this.description=picName;
        this.description=description;
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
