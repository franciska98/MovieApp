package com.example.dmlabos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText emailRegisterEditText;
    EditText passwordRegisterEditText;
    TextView backToLoginTextView;
    Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailRegisterEditText=findViewById(R.id.emailRegisterEditText);
        passwordRegisterEditText=findViewById(R.id.passwordRegisterEditText);
        backToLoginTextView=findViewById(R.id.backToLoginTextView);
        registerButton=findViewById(R.id.registerButton);

        mAuth= FirebaseAuth.getInstance();
    }

    public void register(View view){
        String email=emailRegisterEditText.getText().toString();
        String password=passwordRegisterEditText.getText().toString();

        if(TextUtils.isEmpty(email)){
            emailRegisterEditText.setError("You must enter your email");
            emailRegisterEditText.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            passwordRegisterEditText.setError("You must enter your password");
            passwordRegisterEditText.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this, "Registration Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void backToLogin(View view){
        finish();
    }
}