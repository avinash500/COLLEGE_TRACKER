package com.example.finalproject.teacher_ui.attendance;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AttendanceFragment extends Fragment
{

    private AttendanceViewModel attendanceViewModel;
    Button btn;
    String Tid;
    TextView TeachName,Date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        attendanceViewModel =
                ViewModelProviders.of(this).get(AttendanceViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_attendance, container, false);
        btn=root.findViewById(R.id.takeAttendance);
        TeachName=root.findViewById(R.id.tid);
        Date=root.findViewById(R.id.date);
        Intent intent1 = getActivity().getIntent();
        Tid  = intent1.getStringArrayExtra("message")[0];
        TeachName.setText(intent1.getStringArrayExtra("message")[1]);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final String currentDate = sdf.format(new Date());
        Date.setText(currentDate);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getContext(),TakeAttendance.class);
                intent.putExtra("id",Tid);

                startActivity(intent);


            }
        });

        return root;
    }
}