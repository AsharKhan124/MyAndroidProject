package com.asharkhan.camcolordetect;

import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements OnClickListener {


    //keep track of camera capture intent
    final int CAMERA_CAPTURE = 1;
    //captured picture uri
    private Uri picUri;

    //keep track of cropping intent
    final int PIC_CROP = 2;

    ImageView picView;
    TextView textView;
    Bitmap thePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //retrieve a reference to the UI button
        Button captureBtn = (Button) findViewById(R.id.capture_btn);
         picView = (ImageView) findViewById(R.id.imageView);
         textView = (TextView) findViewById(R.id.textView);
        //retrieve a reference to the ImageView

        //handle button clicks
        captureBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.capture_btn) {

            try {
                //use standard intent to capture an image
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //we will handle the returned data in onActivityResult

                /*File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String picname = "Input";
                File imageFile = new File(externalStoragePublicDirectory, picname);
                Uri picUri = Uri.fromFile(imageFile);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);*/

                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            } catch (ActivityNotFoundException anfe) {
                //display an error message
                String errorMessage = "Whoops - your device doesn't support capturing images!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }


        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            //user is returning from capturing an image using the camera
            if (requestCode == CAMERA_CAPTURE) {


                File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String picname = "Input";
                File imageFile = new File(externalStoragePublicDirectory, picname);
                Uri picUri = Uri.fromFile(imageFile);

                //call the standard crop action intent (the user device may not support it)
                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                //indicate image type and Uri
                cropIntent.setDataAndType(picUri, "image/*");
                //set crop properties
                cropIntent.putExtra("crop", "true");
                //indicate aspect of desired crop
                cropIntent.putExtra("aspectX", 1);
                cropIntent.putExtra("aspectY", 1);
                //indicate output X and Y
                cropIntent.putExtra("outputX", 256);
                cropIntent.putExtra("outputY", 256);
                //retrieve data on return
                cropIntent.putExtra("return-data", true);
                //start the activity - we handle returning in onActivityResult
                startActivityForResult(cropIntent, PIC_CROP);


            }
            //user is returning from cropping the image
            else if (requestCode == PIC_CROP) {
                //get the returned data
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                 Bitmap thePic = extras.getParcelable("data");
                // Bitmap thePic = (Bitmap)  extras.get ("data");


                //retrieve a reference to the ImageView
              //  ImageView picView = (ImageView) findViewById(R.id.imageView);
                //display the returned cropped image
                picView.setImageBitmap(thePic);


             //TextView   textView = (TextView) findViewById(R.id.textView);

                picView.setDrawingCacheEnabled(true);
                picView.buildDrawingCache(true);

                picView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {




                        if(event.getAction()== MotionEvent.ACTION_DOWN ){

                            Bitmap bitmap = picView.getDrawingCache();
                            int pixel = bitmap.getPixel((int)event.getX(),(int)event.getY());

                            int r = Color.red(pixel);
                            int g = Color.green(pixel);
                            int b = Color.blue(pixel);

                            textView.setBackgroundColor(Color.rgb(r,g,b));
                            textView.setText("R("+r+")\n"+"G("+g+")\n"+"B("+b+")");

                        }
                        return true;

                    }
                });




            }

        }
    }


}