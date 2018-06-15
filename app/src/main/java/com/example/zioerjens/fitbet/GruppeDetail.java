package com.example.zioerjens.fitbet;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GruppeDetail extends AppCompatActivity {

    private String gruppenName;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List <GruppeDetailUser> gruppeDatailUserList;
    private DatabaseReference databaseGruppeDetail;
    private FirebaseUser user;
    Intent intent;

    /**
     * Setzten des Titels
     * Zugreifen auf Firebase-Objekt "gruppe_user"
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppe_detail);

        gruppeDatailUserList = new ArrayList<>();

        Intent intent = getIntent();
        gruppenName = intent.getStringExtra("gruppe");
        setTitle(gruppenName);

        databaseGruppeDetail = FirebaseDatabase.getInstance().getReference().child("gruppe_user");



    }

    /**
     * Auslesen der Firebasedaten.
     * Jedes Objekt von Firebase wird als GruppeDetailUser-Objekt in die Liste gruppenDetailUserList gespeichert
     */
    @Override
    public void onResume() {
        super.onResume();

        ValueEventListener gruppe_userListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gruppeDatailUserList.clear();

                for(DataSnapshot grSnapshot : dataSnapshot.getChildren()) {
                    // Get GruppeDetailUser object and use the values to update the UI
                    GruppeDetailUser gdu = grSnapshot.getValue(GruppeDetailUser.class);
                    // [START_EXCLUDE]
                    gruppeDatailUserList.add(gdu);
                }
                getDataUserGroup();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseGruppeDetail.addValueEventListener(gruppe_userListener);

    }

    /**
     *Listet alle User einer Gruppe auf.
     */
    public void getDataUserGroup(){


        final List<GruppeDetailUser> userGruppe = userIngroup();

        //recycler View wird gefüllt und jedem Item wird ein TouchListener hinzugefügt
        recyclerView = (RecyclerView) findViewById(R.id.listGroupMember);
        adapter = new RecyclerAdapter(this,userGruppe);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {

            /**
             * Wird ausgeführt, wenn kurz auf ein Item gecklickt wird
             *
             * @param view Aktuelles view
             * @param position Stelle an dem gedrückt wurde
             */
            @Override
            public void onClick(View view, int position) {
                intent = new Intent(getApplicationContext(),TestJsonParse.class);
                User selectedUser = userGruppe.get(position).user;
                Snackbar.make(view,selectedUser.username, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                intent.putExtra("userID",selectedUser.userID);
                intent.putExtra("userName",selectedUser.username);

                //Neue Aktivität wird gestartet
                startActivity(intent);
            }

            /**
             * Wird ausgeführt, wenn lange auf ein Item gecklickt wird
             *
             * @param view Aktuelle View
             * @param position Stelle an dem gedrückt wurde
             */
            @Override
            public void onLongClick(View view, int position) {
                Snackbar.make(view, "Drücke kurz auf ein Mitglied, damit du dessen Statistik sehen kannst", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }));
    }

    /**
     * Clicklistener Interface wird für den TouchListener benötigt
     */
    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    /**
     * Wird für den Touchlistener benötigt, erkennt die Gesten
     */

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    /**
     * Filtert aus allen Objekten die benötigten raus (Die die in dieser Gruppe sind)
     * @return List<GruppeDetailUser> alle user einer Gruppe sind in dieser Liste
     */
    public List<GruppeDetailUser> userIngroup(){
        final List<GruppeDetailUser> userGruppe = new ArrayList<>();
        //users = FirebaseAuth.getInstance().
        for(GruppeDetailUser gdu : gruppeDatailUserList){
            if(gdu.gruppe.name.equals(gruppenName)){
                //gdu.userID=user.
                userGruppe.add(gdu);
            }
        }
        return userGruppe;
    }


}
