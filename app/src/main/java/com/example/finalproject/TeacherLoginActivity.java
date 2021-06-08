package com.example.finalproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject.GetterSetter.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TeacherLoginActivity extends AppCompatActivity
{
    Button Login;

    EditText TeacherId, Password;
    String TeachID, TeachPassword;
    FirebaseDatabase database;
    DatabaseReference myRef1;
    ProgressBar progressBar;
    ArrayList<Teacher> arrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        Login = findViewById(R.id.logInBtn);
        Password = findViewById(R.id.TeacherPassword);
        TeacherId = findViewById(R.id.TeacherId);
        progressBar=findViewById(R.id.pBar);


//        SharedPreferences sharedpreferences = getSharedPreferences(AdminLoginActivity.mypreference, Context.MODE_PRIVATE);
//        if (sharedpreferences.contains(AdminLoginActivity.NAME))
//        {
//            // CHECKING IF THE SHARED PREFERENCE HAS A key NAMED "admin"
//            String ADMINNAME = sharedpreferences.getString(AdminLoginActivity.NAME, "");
//            if (ADMINNAME.equalsIgnoreCase("admin")) {
//                Intent intent = new Intent(TeacherLoginActivity.this, AdminMainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }


        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                myRef1 = database.getReference("Teacher");
                TeachID = TeacherId.getText().toString();
                TeachPassword = Password.getText().toString();

                if(TeachID.isEmpty() || TeachPassword.isEmpty())
                {
                    Toast.makeText(TeacherLoginActivity.this, "Fill out all the fields ", Toast.LENGTH_SHORT).show();
                }
                else
                {    progressBar.setVisibility(View.VISIBLE);
                    readUser();
                }
            }


        });
    }
    public void readUser()
    {
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
//                if(dataSnapshot.getKey().toString() !=null)  /*dataSnapshot.getkey().toString();*/
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    arrayList.add(userSnapshot.getValue(Teacher.class));
                }
                int c =0 ;
                for(Teacher u : arrayList)
                {
                    if(u.getTeacherId().equals(TeachID) && u.getTeachPassword().equals(TeachPassword))
                    {
                        Intent intent = new Intent(TeacherLoginActivity.this, TeacherMainActivity.class);
                        c = 1;
                        String x[] = {u.getTeacherId(),u.getTeacherName()};
                       intent.putExtra("message", x);
                       //intent.putExtra("id",u.getTeacherId());
                        startActivity(intent);
                        finish();
                    }
                }
                if(c == 0)
                    Toast.makeText(TeacherLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}



/*
 myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapsort : dataSnapshot.getChildren()) {
                            arrayList.add(userSnapsort.getValue(Teacher.class));
                        }
                        for (Teacher u : arrayList) {
                            Log.e("Binit","Avinash");
                            Log.e(u.getTeacherId(),u.getTeachPassword());
                            //Toast.makeText(TeacherLoginActivity.this, u.getTeacherId(), Toast.LENGTH_SHORT).show();
                            if (u.getTeacherId().equals(TeachID) && u.getTeachPassword().equals(TeachPassword)) {
                                Intent intent = new Intent(TeacherLoginActivity.this, TeacherMainActivity.class);
                                intent.putExtra("message", TeachID);
                                intent.putExtra("tName",u.getTeacherName());
                                startActivity(intent);
                                finish();
                            }
                        }
                        Toast.makeText(TeacherLoginActivity.this, "Wrong Data", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
 */

//////////////////////////////////

//
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    final DatabaseReference myRef1=database.getReference("Teachers");
//                myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
//@Override
//public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//        {
//        if(dataSnapshot.exists())
//        {
//        Toast.makeText(TeacherLoginActivity.this, "user exits", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//        Toast.makeText(TeacherLoginActivity.this, "user doesn't exits", Toast.LENGTH_SHORT).show();
//
//        }
//
//        }
//
//@Override
//public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//        });
//////////////////////////