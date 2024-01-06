package com.ahsan.mycontacts;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText userIdEditText;
    private EditText passwordEditText;
    private CheckBox rememberUserIdCheckBox;
    private CheckBox rememberPasswordCheckBox;
    private Button exitButton;
    private Button goButton;
    private TextView signupTextView;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USER_ID_KEY = "userId";
    public static final String PASSWORD_KEY = "password";
    public static final String REMEMBER_USER_ID_KEY = "rememberUserId";
    public static final String REMEMBER_PASSWORD_KEY = "rememberPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userIdEditText = findViewById(R.id.user_id);
        passwordEditText = findViewById(R.id.pwd);
        rememberUserIdCheckBox = findViewById(R.id.checkUserId);
        rememberPasswordCheckBox = findViewById(R.id.checkRememberPwd);
        exitButton = findViewById(R.id.exit);
        goButton = findViewById(R.id.golog);
        signupTextView = findViewById(R.id.sgnPage);

        loadRememberedCredentials();

        exitButton.setOnClickListener(v -> finish());

        goButton.setOnClickListener(v -> {
            String userId = userIdEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (validateCredentials(userId, password)) {
                rememberCredentials(userId, password, rememberUserIdCheckBox.isChecked(), rememberPasswordCheckBox.isChecked());
                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, ContactListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        signupTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateCredentials(String userId, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String storedUserId = sharedPreferences.getString(USER_ID_KEY, null);
        String storedPassword = sharedPreferences.getString(PASSWORD_KEY, null);

        return userId.equals(storedUserId) && password.equals(storedPassword);
    }

    private void rememberCredentials(String userId, String password, boolean rememberUserId, boolean rememberPassword) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (rememberUserId) {
            editor.putString(USER_ID_KEY, userId);
        } else {
            editor.remove(USER_ID_KEY);
        }

        if (rememberPassword) {
            editor.putString(PASSWORD_KEY, password);
        } else {
            editor.remove(PASSWORD_KEY);
        }

        editor.putBoolean(REMEMBER_USER_ID_KEY, rememberUserId);
        editor.putBoolean(REMEMBER_PASSWORD_KEY, rememberPassword);

        editor.apply();
    }

    private void loadRememberedCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        boolean rememberUserId = sharedPreferences.getBoolean(REMEMBER_USER_ID_KEY, false);
        boolean rememberPassword = sharedPreferences.getBoolean(REMEMBER_PASSWORD_KEY, false);

        if (rememberUserId) {
            String userId = sharedPreferences.getString(USER_ID_KEY, "");
            userIdEditText.setText(userId);
            rememberUserIdCheckBox.setChecked(true);
        }

        if (rememberPassword) {
            String password = sharedPreferences.getString(PASSWORD_KEY, "");
            passwordEditText.setText(password);
            rememberPasswordCheckBox.setChecked(true);
        }
    }
}
