package com.example.edehaari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(
                () -> {
                    Toast.makeText(this, "Moving On", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Register.class));
                },
                3000
        );
    }
}