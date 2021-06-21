package com.example.internprojectday1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Method to add data to Firebase Database
    //
    private void addData(String id, String name, String mobile , String email, String des, String reportingTo, String DOJ, String rights){
        mRef = FirebaseDatabase.getInstance().getReference("employees");

        HashMap<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("mobile",mobile);
        map.put("email",email);
        map.put("Designation",des);
        map.put("Reporting To",reportingTo);
        map.put("DOJ To",DOJ);
        map.put("Rights",rights);

        mRef.push().updateChildren(map);
    }

    // Method to get all data from database and show in the logcat
    private void getDataFromDB(){
        mRef = FirebaseDatabase.getInstance().getReference();

        mRef.child("employees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    String key = postSnapshot.getKey();
                    Log.i("Key" , key);

                    FirebaseDatabase.getInstance().getReference().child("employees").child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = snapshot.child("id").getValue().toString();
                            String name = snapshot.child("name").getValue().toString();
                            String mobile = snapshot.child("mobile").getValue().toString();
                            String email = snapshot.child("email").getValue().toString();
                            String Designation = snapshot.child("Designation").getValue().toString();
                            String reportingTo = snapshot.child("Reporting To").getValue().toString();
                            String DOJTo = snapshot.child("DOJ To").getValue().toString();
                            String Rights = snapshot.child("Rights").getValue().toString();
                            Log.i("Info", id + " " +name + " " + mobile + " " + email + " " +Designation + " " + reportingTo + " " + DOJTo + " " + Rights);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }
        });
    }
}