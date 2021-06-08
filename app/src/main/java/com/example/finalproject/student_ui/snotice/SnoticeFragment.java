package com.example.finalproject.student_ui.snotice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.finalproject.admin_ui.Notice;
import com.example.finalproject.student_ui.schat.SchatViewModel;
import com.example.finalproject.teacher_ui.chat.MessageAdapter;
import com.example.finalproject.teacher_ui.chat.MyMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SnoticeFragment extends Fragment
{

    RecyclerView recyclerView;
    private SnoticeViewModel snoticeViewModel;
    ArrayList<Notice> al = new ArrayList<Notice>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        snoticeViewModel =
                ViewModelProviders.of(this).get(SnoticeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_snotice, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        recyclerView=root.findViewById(R.id.noticerev);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        DatabaseReference myref = database.getReference("Notice");
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent = getActivity().getIntent();
                String Sid = intent.getStringArrayExtra("message")[0];
                al.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    if(Sid.equals(snapshot.getKey()))
                    {
                        for(DataSnapshot s: snapshot.getChildren())
                        {

                            al.add(s.getValue(Notice.class));
                        }
                    }
                }
                recyclerView.setAdapter(new NoticeAdapter(al));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }


}