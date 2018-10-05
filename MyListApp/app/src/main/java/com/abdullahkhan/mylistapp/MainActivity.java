package com.abdullahkhan.mylistapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] Exercises = {"Bench Press","Incline Bench Press","Dumbell Press","Dumbell Incline Press", "Butter Fly","Push Ups"};
        ListAdapter myAdapter = new CustomAdapter(this,Exercises);

        ListView myListView = (ListView) findViewById(R.id.myListView);

        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fruit = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(MainActivity.this,fruit,Toast.LENGTH_LONG).show();

                if(position==0){

                    Intent intent = new Intent(MainActivity.this,Activity0.class);
                    startActivityForResult(intent,0);
                }
                if(position==1) {
                    Intent intent = new Intent(MainActivity.this,Activity1.class);
                    startActivityForResult(intent,1);
                }
                if(position==2) {
                    Intent intent = new Intent(MainActivity.this,Activity2.class);
                    startActivityForResult(intent,2);
                }
            }
        });
    }
}