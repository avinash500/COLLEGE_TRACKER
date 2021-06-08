package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminMainActivity extends AppCompatActivity
{
    Button Logout;
    CardView TeacherCardView,StudentCardView,AdminstrationCardView, AdminGenerateNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Logout=findViewById(R.id.logout);
        TeacherCardView=findViewById(R.id.teacherCardId);
        StudentCardView=findViewById(R.id.studentCardId);
        AdminstrationCardView=findViewById(R.id.admincardview);
        AdminGenerateNotice=findViewById(R.id.admingeneratenotice);

        Logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Intent in=new Intent(AdminMainActivity.this,SelectCategoryActivity.class);
                startActivity(in);
                Toast.makeText(AdminMainActivity.this,"Logging out",Toast.LENGTH_SHORT).show();
            }
        });
        TeacherCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent (AdminMainActivity.this,AddTeacher.class);
                startActivity(intent);

            }
        });
        StudentCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent (AdminMainActivity.this,AddStudent.class);
                startActivity(intent);
            }
        });
        AdminGenerateNotice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent (AdminMainActivity.this,AddNotice.class);
                startActivity(intent);
            }
        });

        AdminstrationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent (AdminMainActivity.this,AddAdmin.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit");
        builder.setMessage("Do you want to Exit? ");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });
        builder.show();
    }
}
