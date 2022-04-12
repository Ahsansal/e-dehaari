package com.example.edehaari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String verificationCode, verificationId;

    View coordinatorLayout;
    TextInputEditText codeInput;
    Button verifyBtn;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        try {
            user = (UserModel) getIntent().getSerializableExtra("UserModel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        mAuth = FirebaseAuth.getInstance();

        codeInput = findViewById(R.id.codeInput);
        verifyBtn = findViewById(R.id.verifyBtn);
        verifyBtn.setOnClickListener((v) -> {
            verificationCode = codeInput.getText().toString();
            signIn();
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                Log.d("CODE", "Sent. Id: " + verificationId);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verificationCode = phoneAuthCredential.getSmsCode();
                Toast.makeText(PhoneVerification.this, verificationCode, Toast.LENGTH_LONG).show();
                Log.d("CODE", "Received: " + verificationCode);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Snackbar.make(coordinatorLayout, "Could not send Code to this Phone No.\nTry Again Later.", Snackbar.LENGTH_LONG).show();
                Log.d("CODE", "Could Not Send");
            }
        };
        verify(user.getPhoneNo());
    }

    private void verify(String phoneNo) {
        PhoneAuthOptions options = PhoneAuthOptions
                .newBuilder(mAuth)
                .setPhoneNumber(phoneNo)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signIn() {

        if ( verificationCode != null ) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, "123456");
            mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
                if ( task.isSuccessful() ) {
                    Toast.makeText(PhoneVerification.this, "Authenticated", Toast.LENGTH_SHORT).show();
                    if (user == null) return;

                    // store to database
                    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//                    DatabaseReference reference = rootNode.getReference("users");
//                    reference.child(user.getId()).setValue(user);

                } else {
                    Toast.makeText(PhoneVerification.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(PhoneVerification.this, "Null", Toast.LENGTH_SHORT).show();
        }
    }
}