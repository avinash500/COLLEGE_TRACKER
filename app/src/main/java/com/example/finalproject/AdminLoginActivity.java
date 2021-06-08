package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity
{
    String ADMINNAME = ""; // FOR STORING LOGIN SEESION STATUS
    EditText AdminId,AdminPassword;
    TextView ForgotPassword;
    Button LogInBtn;
    String ADMINID;
    ProgressBar progressBar;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String NAME = "nameKey";
    public static final String PASSWORD = "PassKey";
    private static int TimeOut=3000;
    DatabaseReference UsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        AdminId=findViewById(R.id.adminId);
        AdminPassword=findViewById(R.id.adminPassword);
        ForgotPassword=findViewById(R.id.forwardPassword);
        LogInBtn=findViewById(R.id.logInBtn);
        ADMINID=AdminId.getText().toString();
        progressBar=findViewById(R.id.pBar);

        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        if (sharedpreferences.contains(NAME))
        {
            // CHECKING IF THE SHARED PREFERENCE HAS A key NAMED "admin"
            ADMINNAME = sharedpreferences.getString(NAME, "");
            if (ADMINNAME.equalsIgnoreCase("admin")) {
                Intent intent = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        LogInBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                //STORING THE PREFERENCE
                String n = AdminId.getText().toString();
                String e = AdminPassword.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(NAME, n);
                editor.putString(PASSWORD, e);
                editor.commit();

                //checking if the admin and password is admin and admin or not
                if(AdminId.getText().toString().equalsIgnoreCase("admin") && AdminPassword.getText().toString().equalsIgnoreCase("admin")){
                    Toast.makeText(AdminLoginActivity.this,"Logging in....",Toast.LENGTH_SHORT).show();


                    progressBar.setVisibility(View.VISIBLE);
                            Intent intent=new Intent(AdminLoginActivity.this,AdminMainActivity.class);
                            startActivity(intent);
                            finish();

                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(AdminLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
