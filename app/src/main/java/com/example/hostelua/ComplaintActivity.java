package com.example.hostelua;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintActivity extends AppCompatActivity {
    private SparseArray<View> viewHashMap;
    Button mSumt;
    EditText mname, hostelno, phn, edt;
   DatabaseReference mRef;
   FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        ActionBar actionBar= getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#384950")));
        actionBar.setTitle("Register Complaint");

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mSumt = findViewById(R.id.sbmt);
        mname = findViewById(R.id.name);
        hostelno = findViewById(R.id.hostel_room);
        phn = findViewById(R.id.phn);
        edt = findViewById(R.id.medit);
        mSumt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateinput()){

                    mRef = FirebaseDatabase.getInstance().getReference("Complaint").child(cleanEmail(mUser.getEmail()));
                    mRef.child("name").setValue(mname.getText().toString());
                    mRef.child("hostelnum").setValue(hostelno.getText().toString());
                    mRef.child("phnnum").setValue(phn.getText().toString());
                    mRef.child("complaint").setValue(edt.getText().toString());
                    Toast.makeText(ComplaintActivity.this,"complaint registered", Toast.LENGTH_LONG).show();
                    mname.setText("");
                    hostelno.setText("");
                    phn.setText("");
                    edt.setText("");
                }
            }
        });

        viewHashMap = new SparseArray<>();


    }

    public boolean validateinput(){
        if(TextUtils.isEmpty(mname.getText().toString())){
            Toast.makeText(ComplaintActivity.this,"Hostel name required",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (TextUtils.isEmpty(hostelno.getText().toString())) {
            Toast.makeText(ComplaintActivity.this,"hostel and room number required",Toast.LENGTH_LONG).show();

            return false;
        }
       else if (!InputHelper.verifyMobileNumber(phn.getText().toString())) {
            Toast.makeText(ComplaintActivity.this,"phone numer required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (TextUtils.isEmpty(edt.getText().toString())) {
            Toast.makeText(ComplaintActivity.this,"complaint required",Toast.LENGTH_LONG).show();

            return false;
        }
        return true;
    }
    public void setILError(int viewId, String error) {
        TextInputLayout inputLayout = ((TextInputLayout) getView(viewId));
        if (inputLayout != null)
            inputLayout.setError(error);
    }

    public String fetchText(int veiwId) {
        EditText editText = ((EditText) getView(veiwId));
        if (editText != null) return editText.getText().toString();
        return "";
    }

    public View getView(int resId) {
        if (viewHashMap.get(resId) == null) {
            viewHashMap.append(resId, findViewById(resId));
        }
        return viewHashMap.get(resId);
    }

    private String cleanEmail(String originalEmail) {
        return originalEmail.replaceAll("\\.", ",");
    }
}