package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ClubLoginActivity extends AppCompatActivity
{
    EditText ClubId, ClubPassword;
    String id,pass;
    Button LogInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_login);
        ClubId=findViewById(R.id.ClubId);
        ClubPassword=findViewById(R.id.clubPassword);
        LogInBtn=findViewById(R.id.logInBtn);
        LogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=ClubId.getText().toString();
                pass=ClubPassword.getText().toString();
                if ( (ClubId.getText().toString().equalsIgnoreCase("Robotics") || ClubId.getText().toString().equalsIgnoreCase("Anubhuti") || ClubId.getText().toString().equalsIgnoreCase("SoCSE")) && ClubPassword.getText().toString().equalsIgnoreCase("123"))
                {
                    Toast.makeText(ClubLoginActivity.this, "Logging in....", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ClubLoginActivity.this,ClubMainActivity.class);
                    intent.putExtra("clubname",id);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ClubLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}