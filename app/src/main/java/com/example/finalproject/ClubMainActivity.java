package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalproject.Administration.AsssignSubject;
import com.example.finalproject.GetterSetter.Batch;
import com.example.finalproject.student_ui.sforum.ForumAddQuestion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClubMainActivity extends AppCompatActivity {
    FloatingActionButton postfab;
    TextView title;
    RecyclerView rv;
    ArrayList<Event> arr = new ArrayList<Event>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_main);
        postfab = findViewById(R.id.postfab);
        title = findViewById(R.id.title);
        Intent intent = getIntent();
        String tit  = intent.getStringExtra("clubname");
        title.setText(tit);
        rv = findViewById(R.id.postrev);
        rv.setLayoutManager(new LinearLayoutManager(this));
        
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Event");
        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.getValue() !=null) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Event b = snapshot.getValue(Event.class);
                        arr.add(b);
                    }
                }
                rv.setAdapter(new EventAdapter(arr));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        postfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String clubName = getIntent().getStringExtra("clubname");
                Intent intent=new Intent(ClubMainActivity.this, EventUpload.class);
                intent.putExtra("clubName",clubName);
                startActivity(intent);
            }
        });
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

    @Override
    public void onBackPressed()
    {
        dialogboxExit();
    }

}