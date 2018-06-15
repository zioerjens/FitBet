package com.example.zioerjens.fitbet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import static org.junit.Assert.*;

public class SportTest {

    String uid = "TestUID";
    SportData sd;

    @Test
    public void insertIntoFirebase() {
        DatabaseReference actualData = FirebaseDatabase.getInstance().getReference("sportler");
        actualData.child(uid).setValue(new SportData(1206, 3.4, 24, 20d));
    }

    @Test
    public void loadFromFirebase() {
        DatabaseReference actualData = FirebaseDatabase.getInstance().getReference("sportler").child(uid);
        actualData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sd = dataSnapshot.getValue(SportData.class);
                assertEquals(3.4,sd.multiplier,0.01);
                assertEquals(20,sd.altitude,0.01);
                assertEquals(1206,sd.distance,0.01);
                assertEquals(24,sd.counter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}