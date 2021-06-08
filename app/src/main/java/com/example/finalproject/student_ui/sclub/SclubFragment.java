package com.example.finalproject.student_ui.sclub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Event;
import com.example.finalproject.EventAdapter;
import com.example.finalproject.R;
import com.example.finalproject.student_bottom_nav_ui.dashboard.DashboardFragment;
import com.example.finalproject.student_bottom_nav_ui.home.HomeFragment;
import com.example.finalproject.student_bottom_nav_ui.notifications.NotificationsFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SclubFragment extends Fragment {

    private com.example.finalproject.student_ui.sclub.SclubViewModel mViewModel;


    TextView title;
    RecyclerView rv;
    ArrayList<Event> arr = new ArrayList<Event>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mViewModel =
                ViewModelProviders.of(this).get(SclubViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sclub, container, false);

        title = root.findViewById(R.id.title);

        rv = root.findViewById(R.id.postrev);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));


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




        return root;
    }



}

