package com.abdullahkhan.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button AddNewContact;

    Button searchData;

    Button btnDelete;

    Button update;

    DatabaseHelper databaseHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        AddNewContact = (Button) findViewById(R.id.button2);

        AddNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewContact.class);
                startActivity(intent);
            }
        });

        searchData = (Button) findViewById(R.id.button1);

        searchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchData.class);
                startActivity(intent);
            }
        });

        btnDelete = (Button) findViewById(R.id.button3);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DeleteData.class);
                startActivity(intent);
            }
        });

        update = (Button) findViewById(R.id.button4);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
                startActivity(intent);
            }
        });
    }
}
