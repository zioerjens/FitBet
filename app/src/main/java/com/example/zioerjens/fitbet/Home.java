package com.example.zioerjens.fitbet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {


    /**
     * Home Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        checkLoggedIn();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        final Button buttonStat = findViewById(R.id.btnStatistik);
        buttonStat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Bei der Statistikaktivität soll der eingeloggte User angezeigt werden
                Intent intent = new Intent(getApplicationContext(),TestJsonParse.class);
                intent.putExtra("userID",FirebaseAuth.getInstance().getCurrentUser().getUid());
                intent.putExtra("userName",FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                startActivity(intent);
            }
        });

        final Button buttonTippen = findViewById(R.id.btnTippen);
        buttonTippen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TippenAllGames.class);
                startActivity(intent);
            }
        });

        final Button buttonGruppen = findViewById(R.id.btnGruppen);
        buttonGruppen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Gruppenauswahl.class);
                startActivity(intent);
            }
        });

        final Button buttonSport = findViewById(R.id.btnSport);
        buttonSport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Sport.class);
                startActivity(intent);
            }
        });

        final Button buttonLogout = findViewById(R.id.btnLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void checkLoggedIn(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            Intent intent = new Intent(getApplicationContext(),SignIn.class);
            startActivity(intent);
            finish();
        }
    }

    private void signOut(){
        Toast.makeText(this,"Successfully logged out!", Toast.LENGTH_SHORT).show();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignIn.getClient(this, gso).signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        Intent intent = new Intent(getApplicationContext(),SignIn.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
