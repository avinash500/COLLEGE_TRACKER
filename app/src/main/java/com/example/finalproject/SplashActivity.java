package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity
{

    private ImageView logo;
    private TextView title;
    private static int splashTimeOut=2000;
    Animation topAnim,bottomAnim,myanim;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        title=findViewById(R.id.title);
        logo=findViewById(R.id.logo);



        new Handler().postDelayed(new  Runnable()
        {

            @Override
            public void run()
            {

                Intent intent= new Intent(SplashActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        },splashTimeOut);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);




        title.setAnimation(bottomAnim);

        myanim= AnimationUtils.loadAnimation(this,R.anim.mysplashanimation);
        logo.setAnimation(myanim);



    }

}