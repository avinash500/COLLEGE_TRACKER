package com.example.finalproject.student_ui.sforum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.*;

public class ForumAddComment extends AppCompatActivity
{
    Button cmtsubmit;
    TextView cmtquestion,name,date;
    EditText cmtadd;
    String name1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_add_comment);
        Intent intent = getIntent();

        final Question q = (Question) intent.getSerializableExtra("bin");
//        Toast.makeText(this, q.toString(), Toast.LENGTH_SHORT).show();


        Intent intent1 =getIntent();
        name1 = intent1.getStringArrayExtra("message")[1];

        cmtsubmit=findViewById(R.id.cmtbutton);
        cmtquestion=findViewById(R.id.cmtquestionforum);
        name=findViewById(R.id.cmtnameforum);
        date=findViewById(R.id.cmtdateforum);
        cmtadd=findViewById(R.id.cmtadd);
        cmtquestion.setText(q.getQUESTION());
        name.setText(q.getName());
        date.setText(q.getDate());


        cmtsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = cmtadd.getText().toString();
                Solution sol = new Solution(name1,s);
                if(!s.equals(""))
                {
                    DatabaseReference myRef;
                    FirebaseDatabase database;
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Solution").child(q.getSubject()).child(q.getQNo());
                    myRef.push().setValue(sol);
                    finish();
                }
            }
        });
    }
}