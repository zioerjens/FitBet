package com.example.zioerjens.fitbet;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Gruppenauswahl extends AppCompatActivity {

    private DatabaseReference databaseGruppen;
    private DatabaseReference databaseGruppenUser;
    private ArrayList<Gruppe> listgruppe;
    private ArrayList<Gruppe_User> gruppe_usersList;
    private ArrayAdapter gruppenAdapter;
    private ListView lw;

    /**
     * Gruppenauswahl: Zwei Dialoge, bei denen man eine Gruppe erstellen kann oder einer Gruppe beitreten kann
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppenauswahl);
        addOnItemL();
        //Verbeinden mit dem Firebase
        databaseGruppen = FirebaseDatabase.getInstance().getReference().child("gruppe");
        databaseGruppenUser = FirebaseDatabase.getInstance().getReference().child("gruppe_user");

        listgruppe = new ArrayList<>();
        gruppe_usersList = new ArrayList<>();
        gruppenAdapter = new ArrayAdapter<Gruppe>(this, android.R.layout.simple_list_item_1);

        final Button newGroup = (Button) findViewById(R.id.btnGrErstellen);
        Button joinGroup = (Button) findViewById(R.id.btnGrBeitreten);
        newGroup.setOnClickListener(gruppeErstellen());

        joinGroup.setOnClickListener(gruppeBeitreten());



    }

    /**
     * Liest alle Gruppen und Gruppen_User aus.
     * Es werden nur die Gruppen dargestellt, in denen der User ist
     */

    @Override
    public void onResume() {
        super.onResume();

        // Add value event listener to the post
        // [START post_value_event_listener]

        //Ausledsen von Gruppe_User
        ValueEventListener gruppe_userListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gruppe_usersList.clear();

                for(DataSnapshot grSnapshot : dataSnapshot.getChildren()) {
                    // Get Post object and use the values to update the UI
                    Gruppe_User post = grSnapshot.getValue(Gruppe_User.class);
                    // [START_EXCLUDE]
                    gruppe_usersList.add(post);
                }
                ValueEventListener postListener = getGrFirebase();
                databaseGruppen.addValueEventListener(postListener);
                setListView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseGruppenUser.addValueEventListener(gruppe_userListener);

    }

    /**
     * Stellt die Gruppen in die ListView, in welcher sich der aktuelle User befindet.
     */
    private void setListView(){
        lw = (ListView) findViewById(R.id.lw);
        gruppenAdapter = new ArrayAdapter<Gruppe>(Gruppenauswahl.this,android.R.layout.simple_list_item_1);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = user.getUid();

        for(Gruppe_User gu : gruppe_usersList){
            if(gu.user.userID.equals(currentUserID)){
                gruppenAdapter.add(gu.gruppe);
                //Log.w("sdh",g.toString());

            }


            //gruppenAdapter.add(g);
        }
        lw.setAdapter(gruppenAdapter);
    }

    /**
     * Auslesen von Gruppe
     * @return
     */
    private ValueEventListener getGrFirebase(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listgruppe.clear();

                for(DataSnapshot grSnapshot : dataSnapshot.getChildren()) {
                    // Get Post object and use the values to update the UI
                    Gruppe post = grSnapshot.getValue(Gruppe.class);
                    // [START_EXCLUDE]
                    listgruppe.add(post);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Challo", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(Gruppenauswahl.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
    }

    /**
     * KlicklListener auf die Gruppen Items. Der User gelangt zu gruppendetailansicht wenn er
     * auf eine Gruppe drückt
     */
    public void addOnItemL() {
        AdapterView.OnItemClickListener mListClickedHandler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), GruppeDetail.class);
                String selected = parent.getItemAtPosition(position).toString();

                Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();

                //übergibt den Namen der ausgewählten Gruppe
                intent.putExtra("gruppe", selected);
                startActivity(intent);

            }
        };
        ListView gruppen = (ListView) findViewById(R.id.lw);
        gruppen.setOnItemClickListener(mListClickedHandler);
    }

    /**
     * Dialog zum erstellen einer Gruppe erscheint, die Gruppe wird erstellt wenn der Button gedrückt wird
     * @return
     */
    public View.OnClickListener gruppeErstellen(){
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Gruppenauswahl.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_new_group,null);
                final EditText mName = (EditText) mView.findViewById(R.id.groupname);
                final EditText mPassword = (EditText) mView.findViewById(R.id.password);
                Button mNewGroupe = (Button) mView.findViewById(R.id.btnNewGr);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mNewGroupe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Speichern der Infos auf Firebase

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        User currentUser = new User(user.getDisplayName(),user.getUid(),user.getEmail());
                        //String currentUser = user.getDisplayName();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("gruppe");
                        DatabaseReference usersRef = ref.push();

                        Gruppe g = new Gruppe(mName.getText().toString(),mPassword.getText().toString());
                        usersRef.setValue(g);

                        //Add current user to new Group
                        DatabaseReference ref2 = database.getReference("gruppe_user");
                        DatabaseReference usersRef2 = ref2.push();


                        Gruppe_User gu = new Gruppe_User(g,currentUser);
                        usersRef2.setValue(gu);

                        Log.e("CurrentUserId",user.getDisplayName());



                        dialog.dismiss();




                    }
                });
            }
        };
        return click;
    }

    /**
     * Beitreten in eine Gruppe. Wird in Firebase gespeichert.
     * @return
     */

    public View.OnClickListener gruppeBeitreten(){
        View.OnClickListener click =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Gruppenauswahl.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_join_group,null);
                final EditText mName = (EditText) mView.findViewById(R.id.groupnameJoin);
                final EditText mPassword = (EditText) mView.findViewById(R.id.passwordJoin);
                Button mjoinGroupe = (Button) mView.findViewById(R.id.btnJoinGr);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mjoinGroupe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dialog.dismiss();
                        for(Gruppe g : listgruppe){
                            Log.w("Namelll:",g.name);
                            Log.w("eingabe:",mName.getText().toString());
                            //Kontrollieren, ob das Passwort richtig ist
                            if(mName.getText().toString().equals(g.name)){
                                if(mPassword.getText().toString().equals(g.passwort)){

                                    //Speichern der Infos auf firebase

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    User currentUser = new User(user.getDisplayName(),user.getUid(),user.getEmail());
                                    //String currentUser = user.getDisplayName();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = database.getReference("gruppe").child(g.name);
                                    //DatabaseReference usersRef = ref.child()
                                    Log.w("challo:",ref.getKey());


                                    DatabaseReference ref2 = database.getReference("gruppe_user");
                                    DatabaseReference usersRef2 = ref2.push();

                                    Gruppe_User gu = new Gruppe_User(g,currentUser);
                                    usersRef2.setValue(gu);

                                    dialog.dismiss();
                                }
                                else{
                                    Toast.makeText(Gruppenauswahl.this, "Passwort Falsch",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }

                        }
                    }
                });

            }
        };
        return click;
    }
}