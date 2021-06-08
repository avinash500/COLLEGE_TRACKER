package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproject.GetterSetter.Batch;
import com.example.finalproject.GetterSetter.Branch;
import com.example.finalproject.GetterSetter.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddStudent extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5, e6;
    Button b;
    String sre, sn, sp, ss, ssec, sbatch, sbranch, menid;
    List<String> arr1, arr2;
    Student s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        e1 = findViewById(R.id.redg_no);
        e2 = findViewById(R.id.student_name);
        e3 = findViewById(R.id.student_pass);
        e4 = findViewById(R.id.student_sem);
        e5 = findViewById(R.id.student_sec);
        e6 = findViewById(R.id.Metor_id);
        final Spinner s1 = findViewById(R.id.student_batch);
        final Spinner s2 = findViewById(R.id.student_branch);
        b = findViewById(R.id.stub);

        arr1 = new ArrayList<String>();
        arr2 = new ArrayList<String>();
        ///////////SPINNER ON ITEM SELECTED/////////////////////////

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sbatch = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sbranch = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });


        /////////////////////////////////////////////////////////////
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1 = database.getReference("Batch");
        final DatabaseReference myRef2 = database.getReference("Branch");
        final DatabaseReference myRef3 = database.getReference("Student");
        final DatabaseReference myRef4 = database.getReference("Sections");
        final DatabaseReference myRef5 = database.getReference("Mentors");

        //for inflating the batch spinner by storing data in arr1 from Batch node
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Batch b = snapshot.getValue(Batch.class);
                        arr1.add(b.getBatch());
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddStudent.this, R.layout.student_spin_layout, arr1);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s1.setAdapter(dataAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Branch b = snapshot.getValue(Branch.class);
                        arr2.add(b.getBranch_name());
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddStudent.this, R.layout.student_spin_layout, arr2);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s2.setAdapter(dataAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = new Student();
                sre = e1.getText().toString();
                sn = e2.getText().toString();
                sp = e3.getText().toString();
                ss = e4.getText().toString();
                ssec = e5.getText().toString().toUpperCase();
                menid = e6.getText().toString();
                if (sre.equals("") || sn.equals("") || sp.equals("") || ss.equals("") || ssec.equals("") || menid.equals("")) {
                    Toast.makeText(AddStudent.this, "FIll all the details", Toast.LENGTH_SHORT).show();
                } else {
                    s.setRedg_No(sre);
                    s.setName(sn);
                    s.setPassword(sp);
                    s.setBatch(sbatch);
                    s.setBranch(sbranch);
                    s.setSemester(ss);
                    s.setSection(ssec);
                    s.setMentorId(menid);
                    myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int c = 0;
                            if (dataSnapshot.getChildren() != null) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (sre.equalsIgnoreCase(snapshot.getKey().toString())) {
                                        c = 1;
                                        Toast.makeText(AddStudent.this, "Student already exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (c == 0) {
                                    myRef3.child(sre).push().setValue(s);
                                    myRef4.child(sbatch).child(ssec).push().setValue(sre);
                                    myRef5.child(menid).push().setValue(sre);
                                    Log.e("check", "run");
                                    Toast.makeText(AddStudent.this, "Student Added Succesfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddStudent.this, AdminMainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AddStudent.this, "Student already exist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


    }
}
