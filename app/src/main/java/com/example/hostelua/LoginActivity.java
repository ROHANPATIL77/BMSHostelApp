package com.example.hostelua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private EditText mUsername,mPassword;
    Button mSignin;
    Button mReg, mForget;
    private SparseArray<View> viewHashMap;
    public FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar= getSupportActionBar();
        actionBar.hide();
        viewHashMap = new SparseArray<>();
        auth = FirebaseAuth.getInstance();
        mReg = findViewById(R.id.btn_register);
        mForget = findViewById(R.id.btn_forgot_password);
        mSignin = findViewById(R.id.btn_login);
        mUsername = (TextInputEditText)getView(R.id.et_user_name);
        mPassword = (TextInputEditText)getView(R.id.et_password);
        final TextInputLayout mUsernameLayout = (TextInputLayout)getView(R.id.txtInputUsername);
        final TextInputLayout mPasswordLayout = (TextInputLayout)getView(R.id.txtInputPassword);

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUsernameLayout.setError(null);
                mPasswordLayout.setError(null);
                String userName= mUsername.getText().toString();
                String password = mPassword.getText().toString();
                if(!InputHelper.verifyEMail(userName)) mUsernameLayout.setError("Invalid email address");
                else if(InputHelper.verifyInputField(password,6)==InputHelper.EMPTY_FIELD)
                    mPasswordLayout.setError("Required");
                else if(InputHelper.verifyInputField(password,6)==InputHelper.SHORT_LENGTH)
                    mPasswordLayout.setError("Length should be at least "+6);
else{

                    //authenticate user
                    auth.signInWithEmailAndPassword(userName, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Incorrect Email ID/Password", Toast.LENGTH_SHORT).show();
                                    } else {
                                        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                if (databaseError != null) {
                                                    Toast.makeText(LoginActivity.this, "Error Signing In", Toast.LENGTH_SHORT).show();

                                                }


                                            }
                                        };

                                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                        startActivity(intent);

                                    }
                                }
                            });
}
            }
        });



        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

    }
    public View getView(int resId) {
        if (viewHashMap.get(resId) == null) {
            viewHashMap.append(resId, findViewById(resId));
        }
        return viewHashMap.get(resId);
    }
}