package com.example.hostelua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private SparseArray<View> viewHashMap;
    private FirebaseAuth auth;
    Button mBtn;
    String email, password;
    DatabaseReference dvref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = findViewById(R.id.signUp);
        viewHashMap = new SparseArray<>();
        auth = FirebaseAuth.getInstance();

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(validateInputs()){
                    email = fetchText(R.id.et_email);
                    password = fetchText(R.id.et_pass);
                   auth.createUserWithEmailAndPassword(email, password)
                           .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {

                                   if (!task.isSuccessful())
                                   {
                                       Toast.makeText(MainActivity.this, "Sign Up failed." + task.getException(),
                                               Toast.LENGTH_SHORT).show();
                                   } else {

                                        savetodb();
                                       //    Toast.makeText(SignupActivity.this,"lid:"+did,Toast.LENGTH_LONG).show();
                                       Intent intent1 = new Intent(MainActivity.this, DashboardActivity.class);
                                       startActivity(intent1);
                                       finish();
                                   }
                               }
                           });
                   Toast.makeText(MainActivity.this," Registered!!",Toast.LENGTH_LONG).show();
               }
            }
        });
    }

    private void savetodb(){
dvref = FirebaseDatabase.getInstance().getReference("personaldata").child(cleanEmail(fetchText(R.id.et_email)));
dvref.child("name").setValue(fetchText(R.id.et_name));
dvref.child("phonenum").setValue(fetchText(R.id.et_mobileNo));
dvref.child("Aadhar").setValue(fetchText(R.id.et_adhar));
dvref.child("fathername").setValue(fetchText(R.id.et_FatherName));
dvref.child("enrollnum").setValue(fetchText(R.id.et_enrollNo));
dvref.child("address").setValue(fetchText(R.id.et_address));
dvref.child("guardian").setValue(fetchText(R.id.et_guardian));
dvref.child("whatsapp").setValue(fetchText(R.id.et_whatsapp_no));
dvref.child("roomnum").setValue(fetchText(R.id.et_room));

dvref.child("category").setValue(getSpinner(R.id.sp_category).getSelectedItem());
dvref.child("bloodgroup").setValue(getSpinner(R.id.sp_blood).getSelectedItem());
dvref.child("gender").setValue(getSpinner(R.id.sp_gender).getSelectedItem());
dvref.child("class").setValue(getSpinner(R.id.sp_class).getSelectedItem());
dvref.child("year").setValue(getSpinner(R.id.sp_year).getSelectedItem());
dvref.child("branch").setValue(getSpinner(R.id.sp_branch).getSelectedItem());
Log.v("sel","pos"+getSpinner(R.id.sp_category).getSelectedItem());

    }

    private boolean validateInputs() {


        if (TextUtils.isEmpty(fetchText(R.id.et_name))) {
            setILError(R.id.input_Sname, "Required");
            getView(R.id.et_name).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_FatherName))) {
            setILError(R.id.input_Fname, "Required");
            getView(R.id.et_FatherName).requestFocus();
            return false;
        } else if (getSpinner(R.id.sp_blood).getSelectedItemPosition() == 0) {
            toastMsg("Please select blood group");
            return false;
        } else if (getSpinner(R.id.sp_category).getSelectedItemPosition() == 0) {
            toastMsg("Please select Category");
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_email))) {
            setILError(R.id.input_email, "Required");
            getView(R.id.et_email).requestFocus();
            return false;
        } else if (!InputHelper.verifyEMail(fetchText(R.id.et_email))) {
            setILError(R.id.input_email, "Invalid");
            toastMsg("Please Enter valid email");
            getView(R.id.et_email).requestFocus();
            return false;
        } else if (!InputHelper.verifyMobileNumber(fetchText(R.id.et_mobileNo))) {
            setILError(R.id.input_mobile, "Please enter 10 digit mobile No");
            getView(R.id.et_mobileNo).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_enrollNo))) {
            setILError(R.id.input_enroll, "Required");
            getView(R.id.et_enrollNo).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_adhar))||fetchText(R.id.et_adhar).length()!=12) {
            setILError(R.id.input_aadhar, "Invalid adhaar number");
            getView(R.id.et_adhar).requestFocus();
            return false;
        } else if (!InputHelper.verifyMobileNumber(fetchText(R.id.et_whatsapp_no))) {
            setILError(R.id.input_whatsapp, "Please enter whatsapp No");
            getView(R.id.et_whatsapp_no).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_room))) {
            setILError(R.id.input_room, "Required");
            getView(R.id.et_room).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_address))) {
            setILError(R.id.input_address, "Required");
            getView(R.id.et_address).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_guardian))) {
            setILError(R.id.input_guardian, "Required");
            getView(R.id.et_guardian).requestFocus();
        } else if (getSpinner(R.id.sp_class).getSelectedItemPosition() == 0) {
            toastMsg("Please select Class");
        } else if (getSpinner(R.id.sp_year).getSelectedItemPosition() == 0) {
            toastMsg("Please select Year");
            return false;
        } else if (getSpinner(R.id.sp_branch).getSelectedItemPosition() == 0) {
            toastMsg("Please select Branch");
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
    public Spinner getSpinner(int veiwId) {
        Spinner spinner = ((Spinner) getView(veiwId));
        if (spinner.getSelectedItemPosition() == 0) {
            spinner.setFocusable(true);
            spinner.setFocusableInTouchMode(true);
            spinner.requestFocus();
        }
        return spinner;
    }
    public void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private String cleanEmail(String originalEmail) {
        return originalEmail.replaceAll("\\.", ",");
    }


}