package com.example.finalproject.student_ui.sforum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ForumShowComment extends AppCompatActivity
{
    TextView cmtquestion,name,date;
    ListView listView;
    ArrayList<String> arr = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_show_comment);
        Intent intent = getIntent();
        final Question q = (Question) intent.getSerializableExtra("bin");
//        Toast.makeText(this, q.toString(), Toast.LENGTH_SHORT).show();

        cmtquestion=findViewById(R.id.cmtquestionforum);
        name=findViewById(R.id.cmtnameforum);
        date=findViewById(R.id.cmtdateforum);

        cmtquestion.setText(q.getQUESTION());
        name.setText(q.getName());
        date.setText(q.getDate());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Solution").child(q.getSubject()).child(q.getQNo());
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildren()!= null){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Solution s = snapshot.getValue(Solution.class);
                        arr.add(s.getName()+"\n"+s.getAnswer());
                    }
                }
//                System.out.println("*************************************"+arr);

                String str[] = new String[arr.size()];

                for(int i = 0 ; i < arr.size(); i++)
                {
                    str[i] = arr.get(i);

                }
                ArrayAdapter adapter = new ArrayAdapter<String>(ForumShowComment.this,R.layout.listviewshowcomment,str);
                listView = findViewById(R.id.listview);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}