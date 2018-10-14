package com.asharkhan.rubikscube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RubiksCubeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubiks_cube);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this,DetectedColorActivity.class);
        startActivity(intent);
    }

    public void playAgain(View view) {
        Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
    }
}
