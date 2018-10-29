package com.example.imagepicker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    private TextView textView;

    public static final int REQUEST_IMAGE = 100;
    public static final int REQUEST_PERMISSION = 200;
    private String imageFilePath = "";
    //private  static final int PIC_CROP = 2;
   // Uri photoUri;
    Bitmap bitmap;

    ArrayList<RGB>  RGB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.textView);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraIntent();
            }
        });

    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

   boolean startofcube(RGB color){
        RGB red = new RGB(255,0,0);
        RGB blue = new RGB(0,0,255);
        RGB green = new RGB(0,255,0);
        RGB yellow = new RGB(255,255,0);
        RGB orange = new RGB(255,140,0);
        RGB white = new RGB(255,255,255);
        if(color == red || color == blue || color == green || color == yellow || color == orange || color == white ){
            return true;
        }
        return false;
    }

    private void readimage(){

        try {
             bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),Uri.parse(imageFilePath));
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] cubevalues = new int[9];
        int temp;
        for (int y = 0; y < imageView.getHeight(); y+=3){
            for (int x = 0; x < imageView.getWidth(); x+=5){
                int pixel = bitmap.getPixel(x,y);
                Log.i("x", x+"");
                Log.i("y", y+"");
                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);
                RGB color  = originalcolor(r,g,b);
                Log.i("original color"," " + color);
                if(startofcube(color)) {
                    for (int i = 0; i < cubevalues.length; i++) {
                        RGB red = new RGB(255, 0, 0);
                        RGB blue = new RGB(0, 0, 255);
                        RGB green = new RGB(0, 255, 0);
                        RGB yellow = new RGB(255, 255, 0);
                        RGB orange = new RGB(255, 140, 0);
                        RGB white = new RGB(255, 255, 255);

                        //int i = 0;
                        if (color == red) {
                            cubevalues[i] = 6;
                        }
                        else if (color == blue) {
                            cubevalues[i] = 5;

                        }
                        else if(color == green){
                            cubevalues[i] = 4;
                        }
                        else if(color == yellow){
                            cubevalues[i] = 3;
                        }
                        else if(color == orange){
                            cubevalues[i] = 2;
                        }
                        else if(color == white){
                            cubevalues[i] = 1;
                        }
                    }
                }

                Log.i("color","R("+r+")\n"+"G("+g+")\n"+"B("+b+")");
                textView.setBackgroundColor(Color.rgb(r,g,b));
                textView.setText("R("+r+")\n"+"G("+g+")\n"+"B("+b+")");
            }
        }
    }

    public RGB originalcolor(int r,int g,int b){
        RGB color= new RGB(0,0,0);
                if(r>=100 && r<=255  && g<=100 && b<=100 ){
                    RGB red = new RGB(255,0,0);
                    return red;
                }
                else if(r==0 && g>=20 || g<=255 && b==0){
                    RGB green = new RGB(0,255,0);
                    return green;
                }
                else if(r==0 && g==0 && b>=30 && b<=255){
                    RGB blue = new RGB(0,0,255);
                    return blue;
                }
                else if(r>=240 && r<=255 && g>=230 && g<=255 && b<=100 ) {
                    RGB yellow = new RGB(255,255,0);
                    return yellow;

                }
               /* else if(){ // Adding Orange RGB Range

                    RGB orange = new RGB(255,140,0);
                    return orange;
                }
                else if(){ // Adding White RGB Range

                    RGB white = new RGB(255,255,255);
                    return white;
                }*/

        return color;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                //imageView.setImageURI(Uri.parse(imageFilePath));

              readimage();


              //  imageView.setDrawingCacheEnabled(true);
               // imageView.buildDrawingCache(true);


            }



            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }

          /*  else if(requestCode == PIC_CROP){

                Bundle extras = data.getExtras();
                Bitmap thePic = extras.getParcelable("data");
                imageView.setImageBitmap(thePic);
            }*/

        }
    }

  /*  private void Crop() {

        Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(pictureIntent, REQUEST_IMAGE);
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //cropIntent .putExtra(MediaStore.EXTRA_OUTPUT,picUri);///////
        //indicate image type and Uri
        cropIntent.setDataAndType(photoUri, "image/*");
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
*/
    private File createImageFile() throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = "file:" + image.getAbsolutePath();

        return image;
    }



}
