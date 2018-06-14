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

    @Override
    public void onResume() {
        super.onResume();

        ValueEventListener gruppe_userListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gruppeDatailUserList.clear();

                for(DataSnapshot grSnapshot : dataSnapshot.getChildren()) {
                    // Get Post object and use the values to update the UI
                    GruppeDetailUser post = grSnapshot.getValue(GruppeDetailUser.class);
                    // [START_EXCLUDE]
                    gruppeDatailUserList.add(post);
                }
                getDataUserGroup();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseGruppeDetail.addValueEventListener(gruppe_userListener);

    }

    public void getDataUserGroup(){

        List<GruppeDetailUser> userGruppe = new ArrayList<>();
        //users = FirebaseAuth.getInstance().
        for(GruppeDetailUser gdu : gruppeDatailUserList){
            if(gdu.gruppe.name.equals(gruppenName)){
                //gdu.userID=user.
                userGruppe.add(gdu);
            }
        }


        recyclerView = (RecyclerView) findViewById(R.id.listGroupMember);
        adapter = new RecyclerAdapter(this,userGruppe);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Snackbar.make(view, "Drücke kurz auf ein Mitglied, damit du dessen Statistik sehen kannst", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }));
    }
    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

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


}
