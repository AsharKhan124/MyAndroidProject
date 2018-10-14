package com.asharkhan.rubikscube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DetectedColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detected_color);
    }

    public void solve(View view) {
        Intent intent = new Intent(this,RubiksCubeActivity.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this,CameraActivity.class);
        startActivity(intent);
    }
}
