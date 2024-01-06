package com.ahsan.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText userIdEditText;
    private EditText passwordEditText;
    private EditText rePasswordEditText;
    private Button exitButton;
    private Button goButton;
    private TextView loginPageTextView;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME_KEY = "name";
    public static final String EMAIL_KEY = "email";
    public static final String PHONE_KEY = "phone";
    public static final String USER_ID_KEY = "userId";
    public static final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        userIdEditText = findViewById(R.id.user_id);
        passwordEditText = findViewById(R.id.pwd);
        rePasswordEditText = findViewById(R.id.repwd);
        exitButton = findViewById(R.id.exit);
        goButton = findViewById(R.id.go);
        loginPageTextView = findViewById(R.id.lgnPage);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String userId = userIdEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String rePassword = rePasswordEditText.getText().toString();

                if (validateInputs(name, email, phone, userId, password, rePassword)) {
                    saveAccountInformation(name, email, phone, userId, password);
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(SignupActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginPageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the login activity
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs(String name, String email, String phone, String userId, String password, String rePassword) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || userId.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveAccountInformation(String name, String email, String phone, String userId, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NAME_KEY, name);
        editor.putString(EMAIL_KEY, email);
        editor.putString(PHONE_KEY, phone);
        editor.putString(USER_ID_KEY, userId);
        editor.putString(PASSWORD_KEY, password);
        editor.apply();
    }
}
