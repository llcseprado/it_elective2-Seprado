package com.example.it_elective2_seprado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText mDispName, mEmail_reg ,mPassword_reg;
    Button mRegLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDispName = findViewById(R.id.displayname);
        mEmail_reg = findViewById(R.id.email_reg);
        mPassword_reg = findViewById(R.id.pw_reg);
        mRegLoginBtn = findViewById(R.id.reglogin_btn);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);

        //checks if naka log in na daan ang user para di na sya mag reg usab,
        //then redirect to Main Activity na page
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        //user registration
        mRegLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dispname = mDispName.getText().toString().trim();
                String email = mEmail_reg.getText().toString().trim();
                String password = mPassword_reg.getText().toString().trim();
                if (TextUtils.isEmpty(dispname)) {
                    mDispName.setError("Display Name is Required.");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    mEmail_reg.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword_reg.setError("Password is Required.");
                    return;
                }
                if (password.length() <6){
                    mPassword_reg.setError("Password must be equal or greater than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error Occured!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
         });
    }
}
