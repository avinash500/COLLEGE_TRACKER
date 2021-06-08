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

        import com.example.finalproject.GetterSetter.Batch;
        import com.example.finalproject.GetterSetter.Branch;
        import com.example.finalproject.R;
        import com.example.finalproject.GetterSetter.Subject;
        import com.example.finalproject.TeacherSubject;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

public class AsssignSubject extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    EditText tId, sec,sem;
    FirebaseDatabase database;
    List<String> arr1;
    List<String> arr2;
    List<String> arr3,arr4;
    Button assignSub;
    Spinner BatchSpin,BranchSpin,SubSpin;
    String Subject="a", Branch="a", Section="a",Semester="a", Batch="a", teacherId="a";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asssign_subjects);
        tId = findViewById(R.id.tIdEdittext);
        sec = findViewById(R.id.sec);
        sem=findViewById(R.id.sem);

        BatchSpin = findViewById(R.id.batchspinner);
        BranchSpin = findViewById(R.id.branchspinner);
        SubSpin = findViewById(R.id.subspinner);
        assignSub = findViewById(R.id.addSub);

//        SubSpin.setOnItemSelectedListener(this);
        arr1 = new ArrayList<String>();
        arr2 = new ArrayList<String>();
        arr3 = new ArrayList<String>();
        arr4= new  ArrayList<String>();

        ///////////SPINNER ON ITEM SELECTED/////////////////////////

        BatchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Batch= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
            }
        });
        BranchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Branch= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
            }
        });
        SubSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Subject= parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });
        /////////////////////////////////////////////////////////////
        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1=database.getReference("Batch");
        final DatabaseReference myRef2=database.getReference("Subject");
        final DatabaseReference myRef3=database.getReference("Branch");
        final DatabaseReference myRef4=database.getReference("TeacherSubject");

        myRef1.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.getValue() !=null) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        com.example.finalproject.GetterSetter.Batch b = snapshot.getValue(Batch.class);
                        arr1.add(b.getBatch());
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AsssignSubject.this,R.layout.student_spin_layout,arr1);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                BatchSpin.setAdapter(dataAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        myRef2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.getValue() !=null)
                {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        com.example.finalproject.GetterSetter.Subject b = snapshot.getValue(Subject.class);
                        arr2.add(b.getCourse_name());
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AsssignSubject.this,R.layout.student_spin_layout,arr2);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SubSpin.setAdapter(dataAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        myRef3.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.getValue() !=null)
                {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        com.example.finalproject.GetterSetter.Branch b = snapshot.getValue(Branch.class);
                        arr3.add(b.getBranch_name());
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AsssignSubject.this,R.layout.student_spin_layout,arr3);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                BranchSpin.setAdapter(dataAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        assignSub.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        teacherId = tId.getText().toString();
                        Section = sec.getText().toString().toUpperCase();
                        Semester=sem.getText().toString();
                        if(teacherId.equals("")|| Section.equals("") || Semester.equals(""))
                        {
                            Toast.makeText(AsssignSubject.this, "Fill all details", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            TeacherSubject in = new TeacherSubject();
                            in.setBranch(Branch);
                            in.setSection(Section);
                            in.setSubject(Subject);
                            in.setBatch(Batch);
                            in.setSemester(Semester);
                            myRef4.child(teacherId).push().setValue(in);
                            // Toast.makeText(AsssignSubject.this, ""+ in.getBatch()+""+in.getBranch(), Toast.LENGTH_SHORT).show();

                            DatabaseReference myRef5 = database.getReference("Sections").child(Batch).child(Section);
                            myRef5.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    arr4.clear();
                                    if (dataSnapshot.getValue() != null) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            String s = snapshot.getValue(String.class);
                                            arr4.add(s);
                                        }
//                            Toast.makeText(AsssignSubject.this, "Arr4 size -- "+arr4.size(), Toast.LENGTH_SHORT).show();
                                        DatabaseReference myRef6 = database.getReference("Student Attendance Record").child(Batch).child(Branch).child(Semester);
                                        // Toast.makeText(AsssignSubject.this, ""+arr4.size(), Toast.LENGTH_SHORT).show();
                                        for (int i = 0; i < arr4.size(); i++) {
                                            // Toast.makeText(AsssignSubject.this, "Testing Toast",  Toast.LENGTH_SHORT).show();
                                            // Toast.makeText(AsssignSubject.this, ""+arr4.get(i)+" "+Subject, Toast.LENGTH_LONG).show();
                                            myRef6.child(arr4.get(i)).child(Subject).child("Total Present").push().setValue(0);
                                            myRef6.child(arr4.get(i)).child(Subject).child("Total Classes").push().setValue(0);
                                        }

                                    } else {
                                        //Toast.makeText(AsssignSubject.this, "no data", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
//                DatabaseReference myRef6=database.getReference("Student Attendance Record").child(Batch).child(Branch).child(Semester);

                            Toast.makeText(AsssignSubject.this, "Subject Assigned to the teacher", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        Branch= parent.getItemAtPosition(position).toString();
        Subject= parent.getItemAtPosition(position).toString();
        Toast.makeText(AsssignSubject.this, "Subject -"+Subject, Toast.LENGTH_SHORT).show();
        Batch= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
