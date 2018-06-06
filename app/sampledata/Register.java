package com.example.zioerjens.fitbet;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.support.v7.widget.AppCompatDrawableManager.get;

public class Register extends AppCompatActivity {

    private Boolean used;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                val_register();
            }
        });
    }

    private void val_register(){

        Boolean password2valid = true;
        Boolean passwordvalid = true;
        Boolean emailvalid = true;
        Boolean usernamevalid = true;

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password1);
        EditText password2 = (EditText) findViewById(R.id.password2);

        String usernameTxt = username.getText().toString();
        String passwordTxt = password.getText().toString();
        String password2Txt = password2.getText().toString();

        if (!passwordTxt.equals(password2Txt)){
            password2valid = false;
        }

        //TODO check if pw1 is valid

        if (!isValidPassword(passwordTxt)){
            passwordvalid = false;
        }

        //TODO check if email is valid

        if (isValidUsername(usernameTxt) && !nameExistsAlready(usernameTxt)){
            Log.e("sven", "USERNAMEVALID IF");
            usernamevalid = false;
        }
        //TODO encrypt passwordTxt

        if (password2valid && passwordvalid && emailvalid && usernamevalid) {
            addUserToDb(usernameTxt, passwordTxt);
        } else {
            Toast.makeText(this,"Data not valid",Toast.LENGTH_SHORT).show();
        }
    }

    private void addUserToDb(String username, String password){
        Toast.makeText(this,"adding User...",Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user");
        DatabaseReference usersRef = ref.push();

        //usersRef.child("user").setValue(new User(username, password));
        usersRef.setValue(new User(username, password));


        Toast.makeText(this,"User added...",Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmailAddress(String email) {
        if(email.matches("[a-zA-Z\\d]+@[a-zA-Z]{2,}.[a-zA-Z]+")){
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidUsername(String name) {
        if(name.matches("[a-zA-Z\\d]{4,}")){
            //TODO check if username is already taken
            Log.e("sven", "Name matches regex");
            return true;
        } else {
            Log.e("sven", "Name does not match regex");
            return false;
        }
    }

    private boolean isValidPassword(String pw){
        if(pw.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")){
            return true;
        } else {
            return false;
        }
    }

    private boolean nameExistsAlready(String username){

        Log.e("sven","nameExistsAlready");

        //Extracted for AT from here
        GetUserAT getUserAT = new GetUserAT(username, this);
        getUserAT.execute();
        Log.e("bla", "blablablablablablablablablablabla");
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();
        try {
            Thread.sleep(1000);
        } catch (Exception e){
            //do nothing
        }
        // To dismiss the dialog
        progress.dismiss();
        return used;
    }

    public void setUsed(Boolean b){
        Log.e("sven", "Setting used");
        used = b;
    }
}
