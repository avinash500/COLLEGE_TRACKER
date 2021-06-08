package com.example.finalproject.student_ui.sattendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.GetterSetter.Student;
import com.example.finalproject.student_ui.schat.SchatFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SattendanceFragment extends Fragment {

    RecyclerView rv;
    private SattendanceViewModel sattendanceViewModel;
    FirebaseDatabase database;
    ArrayList<GraphData> gData = new ArrayList<GraphData>();
    Student s;
    String Sid="";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sattendanceViewModel =
                ViewModelProviders.of(this).get(SattendanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sattendance, container, false);

        rv = root.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        Intent intent = getActivity().getIntent();
        Sid = intent.getStringArrayExtra("message")[0];

//        Button test=root.findViewById(R.id.btntest);
//        test.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//                SchatFragment schatFragment=new SchatFragment();
//                FragmentTransaction fragmentTransaction = getActivity()
//                        .getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.nav_host_student_fragment, schatFragment);
//                fragmentTransaction.commit();
//            }
//        });

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1=database.getReference("Student").child(Sid);

        myRef1.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                gData.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    s= userSnapshot.getValue(Student.class);
                }
                DatabaseReference myRef6=database.getReference("Student Attendance Record").child(s.getBatch()).child(s.getBranch()).child(s.getSemester()).child(Sid);
                myRef6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildren()!= null){

                            for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                GraphData gd = new GraphData();
                                String subject_name = snapshot.getKey().toString();
                                //Toast.makeText(getContext(), ""+subject_name, Toast.LENGTH_SHORT).show();
                                //Log.e("Binittttttttttttttttt",subject_name);
                                gd.setSubjectName(subject_name);
                                for (DataSnapshot s : snapshot.child("Total Present").getChildren()) {
                                    long total_present = s.getValue(Long.class);
                                    gd.setTotalPresent(total_present);

                                }
                                for (DataSnapshot s : snapshot.child("Total Classes").getChildren()) {
                                    long total_classes = s.getValue(Long.class);
                                    gd.setTotalAbsent(total_classes - gd.getTotalPresent());
                                }
                                //Log.e("Binittttttttttttttttttt",gd.toString());
                                gData.add(gd);
                            }
                            for(GraphData x: gData)
                            {
                                System.out.println("************************************************************************************"+x.toString());
                            }
                        }
                        rv.setAdapter(new GraphAttendanceAdapter(gData));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        return root;
    }
}