package com.abdullahkhan.mylistapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Hp on 4/13/2018.
 */

 class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(@NonNull Context context, String[] ChestExercises) {
        super(context,R.layout.customrow ,ChestExercises);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View CustomView =  myInflator.inflate(R.layout.customrow,parent,false);

        //Get a Reference
        String singleExerciseItem = getItem(position);
        TextView myText = (TextView) CustomView.findViewById(R.id.myText);
        ImageView myImage = (ImageView) CustomView.findViewById(R.id.myImage);

        myText.setText(singleExerciseItem);
        myImage.setImageResource(R.drawable.bicepcover);
        return CustomView;
    }
}
