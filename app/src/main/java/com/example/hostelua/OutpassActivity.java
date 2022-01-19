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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OutpassActivity extends AppCompatActivity {
    EditText name, usn, hostelnum, dreturn, dleaving, mobileS, mobileP, reason, address, caretaker;
    Button mSbmt;
    Spinner mclass, myear, mranch;
    private SparseArray<View> viewHashMap;
    DatabaseReference mRef;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outpass);

        ActionBar actionBar= getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#384950")));
        actionBar.setTitle("Outpass Form");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        viewHashMap = new SparseArray<>();

        name = findViewById(R.id.nameO);
        usn = findViewById(R.id.usn);
        hostelnum = findViewById(R.id.hostel_roomo);
        dleaving = findViewById(R.id.datel);
        dreturn = findViewById(R.id.dater);
        mobileS = findViewById(R.id.mobile_no);
        mobileP = findViewById(R.id.moile_nop);
        reason = findViewById(R.id.meditr);
        address = findViewById(R.id.adrs);
        caretaker = findViewById(R.id.care_taker);
        mclass = findViewById(R.id.sp_classo);
        mranch = findViewById(R.id.sp_brancho);
        myear = findViewById(R.id.sp_yearo);
        mSbmt = findViewById(R.id.submit_outpas);

        mSbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateinput()){

                    mRef = FirebaseDatabase.getInstance().getReference("Outpass").child(cleanEmail(mUser.getEmail()));
                    mRef.child("name").setValue(name.getText().toString());
                    mRef.child("usn").setValue(usn.getText().toString());
                    mRef.child("hostelnum").setValue(hostelnum.getText().toString());
                    mRef.child("phnnum").setValue(mobileS.getText().toString());
                    mRef.child("parentnum").setValue(mobileP.getText().toString());
                    mRef.child("reason").setValue(reason.getText().toString());
                    mRef.child("dleaving").setValue(dleaving.getText().toString());
                    mRef.child("dreturn").setValue(dreturn.getText().toString());
                    mRef.child("caretaker").setValue(caretaker.getText().toString());
                    mRef.child("address").setValue(address.getText().toString());
                    mRef.child("year").setValue(getSpinner(R.id.sp_yearo).getSelectedItem());
                    mRef.child("class").setValue(getSpinner(R.id.sp_classo).getSelectedItem());
                    mRef.child("branch").setValue(getSpinner(R.id.sp_brancho).getSelectedItem());
                    Toast.makeText(OutpassActivity.this,"submited",Toast.LENGTH_LONG).show();
                    name.setText("");
                    usn.setText("");
                    hostelnum.setText("");
                    dreturn.setText("");
                    dleaving.setText("");
                    mobileS.setText("");
                    mobileP.setText("");
                    reason.setText("");
                    address.setText("");
                    caretaker.setText("");

                }
            }
        });
    }

    public boolean validateinput(){
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(OutpassActivity.this,"Hostel name required",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (TextUtils.isEmpty(hostelnum.getText().toString())) {
            Toast.makeText(OutpassActivity.this,"hostel and room number required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (TextUtils.isEmpty(usn.getText().toString())) {
            Toast.makeText(OutpassActivity.this,"usn number required",Toast.LENGTH_LONG).show();

            return false;
        } else if (TextUtils.isEmpty(dleaving.getText().toString())) {
            Toast.makeText(OutpassActivity.this,"date required",Toast.LENGTH_LONG).show();

            return false;
        } else if (TextUtils.isEmpty(dreturn.getText().toString())) {
            Toast.makeText(OutpassActivity.this,"date required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (TextUtils.isEmpty(address.getText().toString())) {
            Toast.makeText(OutpassActivity.this,"address required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (!InputHelper.verifyMobileNumber(mobileS.getText().toString())) {
            Toast.makeText(OutpassActivity.this,"phone numer required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (!InputHelper.verifyMobileNumber(mobileP.getText().toString())) {
            Toast.makeText(OutpassActivity.this,"parent phone numer required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (TextUtils.isEmpty(reason.getText().toString())) {
            Toast.makeText(OutpassActivity.this,"reason required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (TextUtils.isEmpty(caretaker.getText().toString())) {
            Toast.makeText(OutpassActivity.this,"reason required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (getSpinner(R.id.sp_classo).getSelectedItemPosition() == 0) {
            Toast.makeText(OutpassActivity.this,"care taker name required",Toast.LENGTH_LONG).show();

            return false;
        }     else if (getSpinner(R.id.sp_yearo).getSelectedItemPosition() == 0) {
            Toast.makeText(OutpassActivity.this,"year required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (getSpinner(R.id.sp_brancho).getSelectedItemPosition() == 0) {
            Toast.makeText(OutpassActivity.this,"branch required",Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }

    private String cleanEmail(String originalEmail) {
        return originalEmail.replaceAll("\\.", ",");
    }

    public Spinner getSpinner(int veiwId) {
        Spinner spinner = ((Spinner) getView(veiwId));
        if (spinner.getSelectedItemPosition() == 0) {
            spinner.setFocusable(true);
            spinner.setFocusableInTouchMode(true);
            spinner.requestFocus();
        }
        return spinner;
    }

    public View getView(int resId) {
        if (viewHashMap.get(resId) == null) {
            viewHashMap.append(resId, findViewById(resId));
        }
        return viewHashMap.get(resId);
    }
}