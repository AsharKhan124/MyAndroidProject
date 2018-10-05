package com.asharkhan.tictactoe;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText etInviteEmail;
    EditText etMyEmail;
    Button buLogin;

    // Firesbase
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String MyEmail;
    private String uid;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInviteEmail = (EditText)findViewById(R.id.etInviteEmail);
        etMyEmail = (EditText)findViewById(R.id.etMyEmail);
        buLogin = (Button) findViewById(R.id.buLogin);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public String Tag = "Login";

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(Tag,"onAuthStateChanged:signed_in:"+ uid);
                    MyEmail = user.getEmail();
                    buLogin.setEnabled(false);
                    etMyEmail.setText(MyEmail);
                    uid = user.getUid();

                    myRef.child("Users").child(BeforeAt(MyEmail)).child("Request").setValue(uid);

                    IncomingRequest();
                }
                else{
                    Log.d(Tag,"onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public String BeforeAt(String Email){
        String[] split = Email.split("@");
        return split[0];
    }
    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void UserLogin(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public static final String TAG = "Register" ;

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Login failed.",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    public void buInvite(View view) {

        Log.d("Invite",etInviteEmail.getText().toString());
        myRef.child("Users").child(BeforeAt(etInviteEmail.getText().toString())).child("Request").push().setValue(MyEmail);

        StartGame(BeforeAt(etInviteEmail.getText().toString()) + ":" + BeforeAt(MyEmail));
        MySample = "def";
    }

    public void IncomingRequest(){

        // Read from the database
        myRef.child("Users").child(BeforeAt(MyEmail)).child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{

                    HashMap<String,Object> td = (HashMap<String,Object>) dataSnapshot.getValue();

                    if(td != null){

                        String value;
                        for(String key : td.keySet()){
                            value = (String) td.get(key);
                            Log.d("User Request",value);
                            etInviteEmail.setText(value);
                            ButtonColor();
                            myRef.child("Users").child(BeforeAt(MyEmail)).child("Request").setValue(uid);
                            break;
                        }
                    }

                }catch(Exception ex){

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }

    public void ButtonColor(){





        etInviteEmail.setBackgroundColor(Color.RED);
    }

    public void buAccept(View view) {

        Log.d("Accept",etInviteEmail.getText().toString());
        myRef.child("Users").child(BeforeAt(etInviteEmail.getText().toString())).child("Request").push().setValue(MyEmail);

        StartGame(BeforeAt( BeforeAt(MyEmail)+ ":" + etInviteEmail.getText().toString() ));
        MySample = "abc";
    }

    String PlayerSession = "";
    int CounterSteps = 0;
    String MySample = "def";
    public void StartGame(String PlayerGameID){
        PlayerSession = PlayerGameID;




        myRef.child("Playing").child(PlayerGameID).removeValue();

        // Read from the database
        myRef.child("Playing").child(PlayerGameID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{

                    Player1.clear();
                    Player2.clear();
                    ActivePlayer = 2;

                    HashMap<String,Object> td = (HashMap<String,Object>) dataSnapshot.getValue();

                    if(td != null){

                        String value;
                      //  String FirstPlayer = BeforeAt(MyEmail);
                        for(String key : td.keySet()){
                            value = (String) td.get(key);
                            if(!value.equals(BeforeAt(MyEmail)))
                                ActivePlayer = MySample == "def"?1:2;
                            else
                                ActivePlayer = MySample == "def"?2:1;
                            String[] splitID = key.split(":");
                            AutoPlay(Integer.parseInt(splitID[1]));
                        }
                    }
                    CounterSteps = td.size();

                }catch(Exception ex){

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });}

    public void buLogin(View view) {

        Log.d("Login",etMyEmail.getText().toString());
        UserLogin(etMyEmail.getText().toString(),"ashash");
    }


    public void BuClick(View view) {

        // game not started yet
        if(PlayerSession.length()<=0)
            return;





        Button buSelected= (Button) view;
        int CellID=0;
        switch ((buSelected.getId())){

            case R.id.bu1:
                CellID=1;
                break;

            case R.id.bu2:
                CellID=2;
                break;

            case R.id.bu3:
                CellID=3;
                break;

            case R.id.bu4:
                CellID=4;
                break;

            case R.id.bu5:
                CellID=5;
                break;

            case R.id.bu6:
                CellID=6;
                break;

            case R.id.bu7:
                CellID=7;
                break;

            case R.id.bu8:
                CellID=8;
                break;

            case R.id.bu9:
                CellID=9;
                break;
        }
        myRef.child("Playing").child(PlayerSession).child("CellID:" + CellID).setValue(BeforeAt(MyEmail));
    }


    int ActivePlayer=1; // 1- for first , 2 for second
    ArrayList<Integer> Player1= new ArrayList<Integer>();// hold player 1 data
    ArrayList<Integer> Player2= new ArrayList<Integer>();// hold player 2 data
   public void PlayGame(int CellID,Button buSelected){

        Log.d("Player:",String.valueOf(CellID));

        if (ActivePlayer==1){
            buSelected.setText("X");
            buSelected.setBackgroundColor(Color.GREEN);
            Player1.add(CellID);
           ActivePlayer=2;

           // AutoPlay();
        }
        else if (ActivePlayer==2){
            buSelected.setText("O");
            buSelected.setBackgroundColor(Color.BLUE);
            Player2.add(CellID);
           ActivePlayer=1;

        }

        buSelected.setEnabled(false);
        CheckWiner();

        
       //StartGame(BeforeAt( BeforeAt(MyEmail)+ ":" + etInviteEmail.getText().toString() ));
    }

  public  void CheckWiner(){
        int Winer=-1;
        //row 1
        if (Player1.contains(1) && Player1.contains(2)  && Player1.contains(3))  {
            Winer=1 ;
        }
        if (Player2.contains(1) && Player2.contains(2)  && Player2.contains(3))  {
            Winer=2 ;
        }

        //row 2
        if (Player1.contains(4) && Player1.contains(5)  && Player1.contains(6))  {
            Winer=1 ;
        }
        if (Player2.contains(4) && Player2.contains(5)  && Player2.contains(6))  {
            Winer=2 ;
        }

        //row 3
        if (Player1.contains(7) && Player1.contains(8)  && Player1.contains(9))  {
            Winer=1 ;
        }
        if (Player2.contains(7) && Player2.contains(8)  && Player2.contains(9))  {
            Winer=2 ;
        }


        //col 1
        if (Player1.contains(1) && Player1.contains(4)  && Player1.contains(7))  {
            Winer=1 ;
        }
        if (Player2.contains(1) && Player2.contains(4)  && Player2.contains(7))  {
            Winer=2 ;
        }

        //col 2
        if (Player1.contains(2) && Player1.contains(5)  && Player1.contains(8))  {
            Winer=1 ;
        }
        if (Player2.contains(2) && Player2.contains(5)  && Player2.contains(8))  {
            Winer=2 ;
        }


        //col 3
        if (Player1.contains(3) && Player1.contains(6)  && Player1.contains(9))  {
            Winer=1 ;
        }
        if (Player2.contains(3) && Player2.contains(6)  && Player2.contains(9))  {
            Winer=2 ;
        }


        if ( Winer !=-1){
            // We have winer

            if (Winer==1){
                Toast.makeText(this,"Player 1 is winner",Toast.LENGTH_LONG).show();
            }

            if (Winer==2){
                Toast.makeText(this,"Player 2 is winner",Toast.LENGTH_LONG).show();
            }

        }

    }

  public  void AutoPlay(int CellID){

       /* ArrayList<Integer> EmptyCells= new ArrayList<Integer>(); // all un selected cells
        //Find empty cells

        for (int cellID=1; cellID<10;cellID++){
            if (!( Player1.contains(cellID) || Player2.contains(cellID))){
                EmptyCells.add(cellID);
            }
        }

        Random r= new Random();
        int  RandIndex=r.nextInt(EmptyCells.size()- 0)+ 0; // if size =3 , select (0,1,2)
        int CellID=EmptyCells.get(RandIndex);
        */

        Button buSelected;
        switch (CellID){

            case 1 :
                buSelected=(Button) findViewById(R.id.bu1);
                break;

            case 2:
                buSelected=(Button) findViewById(R.id.bu2);
                break;

            case 3:
                buSelected=(Button) findViewById(R.id.bu3);
                break;

            case 4:
                buSelected=(Button) findViewById(R.id.bu4);
                break;

            case 5:
                buSelected=(Button) findViewById(R.id.bu5);
                break;

            case 6:
                buSelected=(Button) findViewById(R.id.bu6);
                break;

            case 7:
                buSelected=(Button) findViewById(R.id.bu7);
                break;

            case 8:
                buSelected=(Button) findViewById(R.id.bu8);
                break;

            case 9:
                buSelected=(Button) findViewById(R.id.bu9);
                break;
            default:
                buSelected=(Button) findViewById(R.id.bu1);
                break;

        }
        PlayGame(CellID, buSelected);
    }




}
