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

//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button mRegBtn, mLoginBtn;
    EditText mEmailLog,mPWLog;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    private Button google;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        google = findViewById(R.id.google_btn);
//        client = GoogleSignIn.getClient(this.options);
        mRegBtn = findViewById(R.id.reg_btn);

        mEmailLog = findViewById(R.id.email_login);
        mPWLog = findViewById(R.id.pw_login);
        mLoginBtn = findViewById(R.id.login_btn);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                String email = mEmailLog.getText().toString().trim();
                String password = mPWLog.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmailLog.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPWLog.setError("Password is Required.");
                    return;
                }
                if (password.length() <6){
                    mPWLog.setError("Password must be equal or greater than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Error Occured!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

//        google.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClickGoogle (View view){
//
//            }
//        });

    }
}