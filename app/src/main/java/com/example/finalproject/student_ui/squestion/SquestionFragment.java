package com.example.finalproject.student_ui.squestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.student_ui.sattendance.GraphAttendanceAdapter;
import com.example.finalproject.student_ui.sforum.ForumAdapter;
import com.example.finalproject.student_ui.sforum.ForumAddQuestion;
import com.example.finalproject.student_ui.sforum.Question;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


public class SquestionFragment extends Fragment implements AdapterView.OnItemSelectedListener
{

    private SquestionViewModel sforumViewModel;
    FloatingActionButton fab;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();;
    DatabaseReference myref1,myref2;
    ArrayList<Question> list = new ArrayList<Question>();
    ArrayList<Question> list1 = new ArrayList<Question>();
    RecyclerView recyclerView;
    Spinner quesspin;
    List<String> arr1;
    String subject;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sforumViewModel =
                ViewModelProviders.of(this).get(SquestionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sforum, container, false);

        fab=root.findViewById(R.id.fab);
        quesspin=root.findViewById(R.id.quesspin);
        recyclerView=root.findViewById(R.id.questrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        arr1 = new ArrayList<String>();
        arr1.add("Choose a Subject");
        arr1.add("Machine Learning");
        arr1.add("Operating System");
        arr1.add("Data Structure");
        arr1.add("DBMS");
        arr1.add("Embedded System");
        arr1.add("Software Engineering");
        arr1.add("Miscellaneous");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.student_spin_layout, arr1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quesspin.setAdapter(dataAdapter);
        Intent intent = getActivity().getIntent();
        final String Sid = intent.getStringArrayExtra("message")[0];
//        Sname = intent.getStringArrayExtra("message")[1];
//        Mid = intent.getStringArrayExtra("message")[2];
        myref1 = firebaseDatabase.getReference("Student Forum").child(Sid);


        quesspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = parent.getItemAtPosition(position).toString();
                if(position != 0)
                {
                    list1.clear();
                    myref2 =firebaseDatabase.getReference("Student Forum").child(Sid).child(subject);
                    myref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildren() != null) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    for (DataSnapshot s : snapshot.getChildren()) {
                                        Question q = s.getValue(Question.class);
                                        list1.add(q);
                                    }
                                }

                            }
                            Intent intent = getActivity().getIntent();
                            final String[] message = intent.getStringArrayExtra("message");
                            recyclerView.setAdapter(new ForumAdapter((ArrayList<Question>) list1,message));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    myref1.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() !=null) {
                                list.clear();
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                    for(DataSnapshot s : snapshot.getChildren())
                                    {
                                        for(DataSnapshot sd: s.getChildren()) {
                                           // String key = sd.getKey().toString();
                                            //System.out.println(key+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                                            Question q = sd.getValue(Question.class);

                                            //System.out.println(q+"******************************************************");
                                            list.add(q);
                                        }
                                    }
                                }
                            }
                            Intent intent = getActivity().getIntent();
                            final String[] message = intent.getStringArrayExtra("message");
                            recyclerView.setAdapter(new ForumAdapter((ArrayList<Question>)list,message));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }


        });
        
        return root;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}