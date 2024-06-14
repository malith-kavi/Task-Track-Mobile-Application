package com.example.ticktask;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button btn_login;
    EditText editText_email, editText_password;
    SharedPreferences sharedPreferences;

    TextView btn_signup;

    // Shared Preferences keys
    private static final String SHARED_PREF_NAME = "tickTask";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        editText_email = findViewById(R.id.email);
        editText_password = findViewById(R.id.password);
        btn_signup = findViewById(R.id.btn_signup);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        // Check if user is already logged in
        boolean isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
        if (isLoggedIn) {
            Intent intent = new Intent(getApplicationContext(), NavigationView.class);
            startActivity(intent);
            finish(); // Finish the current activity to prevent going back
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String email = editText_email.getText().toString();
                String password = editText_password.getText().toString();

                // Retrieve stored credentials
                String storedEmail = sharedPreferences.getString(KEY_EMAIL, null);
                String storedPassword = sharedPreferences.getString(KEY_PASSWORD, null);

                // Validate credentials
                if (email.equals(storedEmail) && password.equals(storedPassword)) {
                    // Set login flag
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEY_IS_LOGGED_IN, true);
                    editor.apply();

                    // Navigate to NavigationView
                    Intent login = new Intent(getApplicationContext(), NavigationView.class);
                    startActivity(login);
                    finish(); // Finish the current activity
                } else {
                    // Display error message
                    Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
