package com.asharkhan.rubikscube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CameraActivity extends AppCompatActivity {
    Button autoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        autoInput=(Button) findViewById(R.id.autoInput);
    }

    public void autoInput(View view) {
        Intent intent = new Intent(this,DetectedColorActivity.class);
        startActivity(intent);

    }
}
