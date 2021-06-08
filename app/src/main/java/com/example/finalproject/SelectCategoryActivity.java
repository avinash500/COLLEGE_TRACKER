package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.w3c.dom.Text;


public class SelectCategoryActivity extends AppCompatActivity
{
    CardView Teacher,Student,Admin,clubs;

    Animation anim_cardview;
    private static int TimeOut=5000;
    String ADMINNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        Teacher=findViewById(R.id.teacher);
        Student=findViewById(R.id.student);
        Admin=findViewById(R.id.admin);
        clubs=findViewById(R.id.clubs);
        SharedPreferences sharedpreferences = getSharedPreferences(AdminLoginActivity.mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(AdminLoginActivity.NAME))
        {
            // CHECKING IF THE SHARED PREFERENCE HAS A key NAMED "admin"
            ADMINNAME = sharedpreferences.getString(AdminLoginActivity.NAME, "");
            if (ADMINNAME.equalsIgnoreCase("admin")) {
                Intent intent = new Intent(SelectCategoryActivity.this, AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(SelectCategoryActivity.this,AdminLoginActivity.class);
                startActivity(intent);


            }
        });

        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(SelectCategoryActivity.this,StudentLoginActivity.class);
                startActivity(intent);


            }
        });

        Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(SelectCategoryActivity.this,TeacherLoginActivity.class);
                startActivity(intent);


            }
        });
        clubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(SelectCategoryActivity.this,ClubLoginActivity.class);
                startActivity(intent);


            }
        });

        anim_cardview=AnimationUtils.loadAnimation(this, R.anim.slide_from_right);
        Teacher.setAnimation(anim_cardview);
        Student.setAnimation(anim_cardview);
        Admin.setAnimation(anim_cardview);

    }
    @Override
    public void onBackPressed()
    {
        dialogboxExit();
    }

    private void dialogboxExit()
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
