package com.example.finalproject.teacher_ui.settings;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.TeacherMainActivity;
import com.example.finalproject.teacher_ui.attendance.AssignTeachGetInput;
import com.example.finalproject.teacher_ui.attendance.AttendanceInformation;
import com.example.finalproject.teacher_ui.attendance.AttendanceRecordAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SettingsFragment extends Fragment
{

    //TODO: THIS LAYOUT IS TO BE DONE IN VIEW ATTENDANCE FRAGMENT RATHER THAN SETTINGS FRAGMENT .CREATE A NEW ONE LATER
    private SettingsViewModel settingsViewModel;
    Spinner spinner;

    List<String> arr;
    ArrayList<AssignTeachGetInput> arr2;
    String teacher_id, batch, section;
    DatabaseReference myRef1;
    FirebaseDatabase database;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TextView t;
    String date_of_record;
    List<AttendanceInformation> list;
    String A="s";
    int flag=0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);



        t =root.findViewById(R.id.tttt1);



        batch = "blah";
        section = "blah";
        final RecyclerView recyclerView =root.findViewById(R.id.reclycle1);








        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Material_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int m = month + 1;
                String mm, dd;
                mm = "01";
                dd = ""+dayOfMonth;
                String showdate = dayOfMonth + "/" + m + "/" + year;
                if (m < 10) {
                    mm = "0" + m;
                }
                if (dayOfMonth < 10) {
                    dd = "0" + dayOfMonth;
                }
                t.setText(showdate);
                date_of_record = year + " " + mm + " " + dd;

                spinner =root.findViewById(R.id.spin1);
                database = FirebaseDatabase.getInstance();
                Intent intent1 = getActivity().getIntent();
                String Tid  = intent1.getStringArrayExtra("message")[0];
                final DatabaseReference myRef = database.getReference("TeacherSubject").child(Tid);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arr = new ArrayList<String>();
                        arr2 = new ArrayList<AssignTeachGetInput>();
                        if (dataSnapshot.getValue() != null) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                AssignTeachGetInput b = snapshot.getValue(AssignTeachGetInput.class);
                                arr.add(b.getSubject() + "|" + b.getSemester() + b.getBranch() + b.getSection() + "(" + b.getBatch() + ")");
                                arr2.add(b);
                            }
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),R.layout.student_spin_layout, arr);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        list=new ArrayList<AttendanceInformation>();
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        final AssignTeachGetInput obj = arr2.get(position);
                        String batch,branch,semester,subject;
                        batch=obj.getBatch();
                        branch=obj.getBranch();
                        semester=obj.getSemester();
                        subject=obj.getSubject();
                        final DatabaseReference myRef2 = database.getReference("Attendance Record").child(batch).child(branch).child(semester).child(subject).child(date_of_record);
                        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        final AttendanceInformation information= new AttendanceInformation();
                                        String n=snapshot.getKey().toString();
                                        information.setName(n);
                                        Log.e("parent",""+n);
                                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                                            String attendance = snapshot1.getValue(String.class);
                                            information.setAttendance(attendance);
                                            Log.e("attendance",""+attendance);
                                            list.add(information);
                                        }

                                    }
                                }
                                recyclerView.setAdapter(new AttendanceRecordAdapter((ArrayList<AttendanceInformation>) list));


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



            }


        };
        return root;
    }
}