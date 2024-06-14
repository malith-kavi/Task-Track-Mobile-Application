package com.example.ticktask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    Button btn_signup;
    TextView btn_login;
    EditText editText_name, editText_email, editText_password;
    SharedPreferences sharedPreferences;

    // Shared Preferences keys
    private static final String SHARED_PREF_NAME = "TaskTrack";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Direct to login activity
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        // Handle registration
        btn_signup = findViewById(R.id.btn_signup);
        editText_name = findViewById(R.id.name);
        editText_email = findViewById(R.id.email);
        editText_password = findViewById(R.id.password);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        // Check if user is already logged in
//        boolean isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
//        if (isLoggedIn) {
//            Intent intent = new Intent(getApplicationContext(), NavigationView.class);
//            startActivity(intent);
//            finish();
//        }

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String name = editText_name.getText().toString();
                String email = editText_email.getText().toString();
                String password = editText_password.getText().toString();

                // Validate user input
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    // Save data to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_NAME, name);
                    editor.putString(KEY_EMAIL, email);
                    editor.putString(KEY_PASSWORD, password);
                    editor.putBoolean(KEY_IS_LOGGED_IN, true); // Set login flag
                    editor.apply();

                    // Clear tasks data
                    clearTasksSharedPreferences();

                    // Start Login activity
                    Intent intent = new Intent(getApplicationContext(), NavigationView.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearTasksSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("tasks");
        editor.apply();
    }
}
