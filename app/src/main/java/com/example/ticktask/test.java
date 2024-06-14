package com.example.ticktask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class test extends AppCompatActivity {
    TextView textView_name, textView_email, textView_password;
    Button btn_logout;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "tickTask";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView_name= findViewById(R.id.name);
        textView_email= findViewById(R.id.email);
        textView_password= findViewById(R.id.password);
        btn_logout = findViewById(R.id.button);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_NAME,null);
        String email = sharedPreferences.getString(KEY_EMAIL,null);
        String password = sharedPreferences.getString(KEY_PASSWORD,null);

        if (name != null || email != null || password != null){
            textView_name.setText("Name = "+ name);
            textView_email.setText("Email = "+ email);
            textView_password.setText("Password = "+ password);
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
}