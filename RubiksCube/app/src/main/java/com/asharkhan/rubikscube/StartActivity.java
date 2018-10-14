package com.asharkhan.rubikscube;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {
ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void Play(View view) {
        Intent intent = new Intent(this,CameraActivity.class);
        startActivity(intent);
    }
}
