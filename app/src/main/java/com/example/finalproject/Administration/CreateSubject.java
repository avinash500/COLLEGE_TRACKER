package com.example.finalproject.Administration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproject.GetterSetter.Branch;
import com.example.finalproject.R;
import com.example.finalproject.GetterSetter.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateSubject extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText e1,e2;
    Spinner s;
    Subject sub;
    Button b;
    String subname,subid,subbranch;
    ArrayList<Branch> arrayList = new ArrayList<Branch>();
    ArrayList<String> arr = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subject);
        e1=findViewById(R.id.subjectname);
        e2=findViewById(R.id.subject_id);
        s=findViewById(R.id.sbranch) ;
        b=findViewById(R.id.btn);
        s.setOnItemSelectedListener(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1=database.getReference("Subject");
        final DatabaseReference myRef2=database.getReference("Branch");

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() !=null) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Branch b = snapshot.getValue(Branch.class);
                        arrayList.add(b);
                        arr.add(b.getBranch_name());
                    }
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CreateSubject.this,R.layout.student_spin_layout,arr);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                sub = new Subject();
                subname = e1.getText().toString().toUpperCase();
                subid = e2.getText().toString();
                if (subname.equals("") || subid.equals("")) {
                    Toast.makeText(CreateSubject.this, "Fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    sub.setCourse_name(subname);
                    sub.setCourse_id(subid);
                    sub.setBranch(subbranch);
                    myRef1.push().setValue(sub);
                    Toast.makeText(CreateSubject.this, "" + subname + " Subject Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        subbranch= parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
