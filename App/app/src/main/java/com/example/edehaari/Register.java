package com.example.edehaari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {

    TextInputEditText nameInput, phoneNoInput, passwordInput, retypePasswordInput;
    View coordinatorLayout;

    // Firebase Stuff


    UserModel newUser;
    int selectedType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initializing Objects
        nameInput = findViewById(R.id.nameInput);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        phoneNoInput = findViewById(R.id.phoneNoInput);
        passwordInput = findViewById(R.id.passwordInput);
        retypePasswordInput = findViewById(R.id.retypePasswordInput);
        newUser = new UserModel();

        // Register Button onClickListener
        findViewById(R.id.registerBtn).setOnClickListener(this::registerUser);

        // Login Page Redirect through onClickListener
        findViewById(R.id.loginBtn).setOnClickListener((v) -> startActivity(new Intent(this, Login.class)));
    }

    private void registerUser(View v) {
        // get values from the input fields
        String nameVal = nameInput.getText().toString(),
                phoneNoVal = phoneNoInput.getText().toString(),
                passwordVal = passwordInput.getText().toString(),
                retypePasswordVal = retypePasswordInput.getText().toString();

        // validate values
        if (nameVal.equals("")) {
            Snackbar.make(coordinatorLayout, "Please Enter a Name", Snackbar.LENGTH_LONG).show();
            return;
        }
        if ( phoneNoVal.equals("") ) {
            Snackbar.make(coordinatorLayout, "Please Enter a Phone No", Snackbar.LENGTH_LONG).show();
            return;
        }
        if ( passwordVal.equals("") ) {
            Snackbar.make(coordinatorLayout, "Please Enter a Password", Snackbar.LENGTH_LONG).show();
            return;
        }
        if ( retypePasswordVal.equals("") ) {
            Snackbar.make(coordinatorLayout, "Please Enter Password Again", Snackbar.LENGTH_LONG).show();
            return;
        }
        if ( !passwordVal.equals(retypePasswordVal) ) {
            Snackbar.make(coordinatorLayout, "Passwords do not match", Snackbar.LENGTH_LONG).show();
            return;
        }

        newUser.setName(nameVal);
        newUser.setPhoneNo(phoneNoVal);
        newUser.setPassword(passwordVal);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account Type");

        String[] types = {"Client", "Worker"};

        builder.setSingleChoiceItems(types, 0, (dialogInterface, i) -> newUser.setWorker(i==1));
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            Intent intent = new Intent(this, PhoneVerification.class);
            intent.putExtra("UserModel", newUser);
            startActivity(intent);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }
}