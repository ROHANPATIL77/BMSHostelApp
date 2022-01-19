package com.example.hostelua;

import androidx.appcompat.app.AppCompatActivity;

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

public class LateArrivalActivity extends AppCompatActivity {
EditText name, usn, hostelnum, treturn, dreturn, mobileS, mobileP, reason;
Button mSbmt;
Spinner mclass, myear, mranch;
    private SparseArray<View> viewHashMap;
DatabaseReference mRef;
FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_late_arrival);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        viewHashMap = new SparseArray<>();

        name = findViewById(R.id.nameL);
        usn = findViewById(R.id.usnl);
        hostelnum = findViewById(R.id.hostel_rooml);
        treturn = findViewById(R.id.datell);
        dreturn = findViewById(R.id.daterl);
        mobileS = findViewById(R.id.mobile_nol);
        mobileP = findViewById(R.id.moile_nopl);
        reason = findViewById(R.id.meditrl);
        mSbmt = findViewById(R.id.submit_late);
        mclass = findViewById(R.id.sp_classl);
        mranch = findViewById(R.id.sp_branchl);
        myear = findViewById(R.id.sp_yearl);

        mSbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateinput()) {
                    mRef = FirebaseDatabase.getInstance().getReference("Late arrival").child(cleanEmail(mUser.getEmail()));
                    mRef.child("name").setValue(name.getText().toString());
                    mRef.child("usn").setValue(usn.getText().toString());
                    mRef.child("hostelnum").setValue(hostelnum.getText().toString());
                    mRef.child("phnnum").setValue(mobileS.getText().toString());
                    mRef.child("parentnum").setValue(mobileP.getText().toString());
                    mRef.child("reason").setValue(reason.getText().toString());
                    mRef.child("timereturn").setValue(treturn.getText().toString());
                    mRef.child("dreturn").setValue(dreturn.getText().toString());
                    mRef.child("year").setValue(getSpinner(R.id.sp_yearl).getSelectedItem());
                    mRef.child("class").setValue(getSpinner(R.id.sp_classl).getSelectedItem());
                    mRef.child("branch").setValue(getSpinner(R.id.sp_branchl).getSelectedItem());
                    Toast.makeText(LateArrivalActivity.this,"submited",Toast.LENGTH_LONG).show();
                    name.setText("");
                    usn.setText("");
                    hostelnum.setText("");
                    dreturn.setText("");
                    treturn.setText("");
                    mobileS.setText("");
                    mobileP.setText("");
                    reason.setText("");

                }
            }
        });
    }

    public boolean validateinput(){
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(LateArrivalActivity.this,"Hostel name required",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (TextUtils.isEmpty(hostelnum.getText().toString())) {
            Toast.makeText(LateArrivalActivity.this,"hostel and room number required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (TextUtils.isEmpty(usn.getText().toString())) {
            Toast.makeText(LateArrivalActivity.this,"usn number required",Toast.LENGTH_LONG).show();

            return false;
        } else if (TextUtils.isEmpty(treturn.getText().toString())) {
            Toast.makeText(LateArrivalActivity.this,"time required",Toast.LENGTH_LONG).show();

            return false;
        } else if (TextUtils.isEmpty(dreturn.getText().toString())) {
            Toast.makeText(LateArrivalActivity.this,"date required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (!InputHelper.verifyMobileNumber(mobileS.getText().toString())) {
            Toast.makeText(LateArrivalActivity.this,"phone numer required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (!InputHelper.verifyMobileNumber(mobileP.getText().toString())) {
            Toast.makeText(LateArrivalActivity.this,"parent phone numer required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (TextUtils.isEmpty(reason.getText().toString())) {
            Toast.makeText(LateArrivalActivity.this,"reason required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (getSpinner(R.id.sp_classl).getSelectedItemPosition() == 0) {
            Toast.makeText(LateArrivalActivity.this,"class required",Toast.LENGTH_LONG).show();

            return false;
        }     else if (getSpinner(R.id.sp_yearl).getSelectedItemPosition() == 0) {
            Toast.makeText(LateArrivalActivity.this,"year required",Toast.LENGTH_LONG).show();

            return false;
        }
        else if (getSpinner(R.id.sp_branchl).getSelectedItemPosition() == 0) {
            Toast.makeText(LateArrivalActivity.this,"branch required",Toast.LENGTH_LONG).show();

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