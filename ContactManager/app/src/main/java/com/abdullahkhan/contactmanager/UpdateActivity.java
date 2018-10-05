package com.abdullahkhan.contactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    EditText name,number,email;
    Button btnUpdate;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        databaseHelper = new DatabaseHelper(this);

        name = (EditText) findViewById(R.id.etname);
        number = (EditText) findViewById(R.id.etnumber);
        email = (EditText) findViewById(R.id.etemail);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updateData(name.getText().toString(),number.getText().toString(),email.getText().toString());

            }
        });
    }
}
