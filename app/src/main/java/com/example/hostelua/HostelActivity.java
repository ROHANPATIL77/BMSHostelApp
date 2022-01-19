package com.example.hostelua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HostelActivity extends AppCompatActivity {
Button mComplaint, mOutpass, mLateArrival;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel);

        mComplaint = findViewById(R.id.complaint);
        mOutpass = findViewById(R.id.outpass);
        mLateArrival = findViewById(R.id.late);

        mComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostelActivity.this, ComplaintActivity.class);
                startActivity(intent);
            }
        });

        mLateArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostelActivity.this, LateArrivalActivity.class);
                startActivity(intent);
            }
        });

        mOutpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostelActivity.this, OutpassActivity.class);
                startActivity(intent);
            }
        });
    }
}