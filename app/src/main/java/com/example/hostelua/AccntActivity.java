package com.example.hostelua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccntActivity extends AppCompatActivity {
TextView sName, fNAme, bloodG, Category,gender, email, mobilenum, enrollnum, aadharnum, whatsappnum, hostelnum, address, gName, branch, year, clas;
 DatabaseReference mRef;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accnt);

        sName = findViewById(R.id.input_name);
        fNAme = findViewById(R.id.db_Fname);
        bloodG = findViewById(R.id.db_blood);
        Category = findViewById(R.id.db_category);
        gender = findViewById(R.id.db_gender);
        email = findViewById(R.id.db_email);
        mobilenum = findViewById(R.id.db_mobile);
        enrollnum = findViewById(R.id.db_enroll);
        aadharnum = findViewById(R.id.db_aadhar);
        whatsappnum = findViewById(R.id.db_whatsapp);
        hostelnum = findViewById(R.id.db_room);
        address = findViewById(R.id.db_address);
        gName = findViewById(R.id.db_guardian);
        clas = findViewById(R.id.db_class);
        year = findViewById(R.id.db_year);
        branch = findViewById(R.id.db_branch);
        mUser = FirebaseAuth.getInstance().getCurrentUser();


        mRef = FirebaseDatabase.getInstance().getReference("personaldata").child(cleanEmail(mUser.getEmail()));
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            sName.setText(dataSnapshot.child("name").getValue(String.class));
            mobilenum.setText(dataSnapshot.child("phonenum").getValue(String.class));
            aadharnum.setText(dataSnapshot.child("Aadhar").getValue(String.class));
            fNAme.setText(dataSnapshot.child("fathername").getValue(String.class));
            enrollnum.setText(dataSnapshot.child("enrollnum").getValue(String.class));
                address.setText(dataSnapshot.child("address").getValue(String.class));
                gName.setText(dataSnapshot.child("guardian").getValue(String.class));
                whatsappnum.setText(dataSnapshot.child("whatsapp").getValue(String.class));
                hostelnum.setText(dataSnapshot.child("roomnum").getValue(String.class));
                Category.setText(dataSnapshot.child("category").getValue(String.class));
                bloodG.setText(dataSnapshot.child("bloodgroup").getValue(String.class));
                gender.setText(dataSnapshot.child("gender").getValue(String.class));
                clas.setText(dataSnapshot.child("class").getValue(String.class));
                year.setText(dataSnapshot.child("year").getValue(String.class));
                branch.setText(dataSnapshot.child("branch").getValue(String.class));
                email.setText(dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private String cleanEmail(String originalEmail) {
        return originalEmail.replaceAll("\\.", ",");
    }

}