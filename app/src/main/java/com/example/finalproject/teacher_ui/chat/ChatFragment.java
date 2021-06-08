package com.example.finalproject.teacher_ui.chat;

import  android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment
{
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    RecyclerView rv;
    EditText onemessage;
    ImageButton send;
    String sonem, TName;
    ArrayList<MyMessage> al = new ArrayList<>();
    String Tid,Tname;


    private ChatViewModel chatViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        chatViewModel =ViewModelProviders.of(this).get(ChatViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        ///////////

        Intent intent = getActivity().getIntent();
        Tname = intent.getStringArrayExtra("message")[1];
        //Toast.makeText(getContext(), Tname, Toast.LENGTH_SHORT).show();
        Tid = intent.getStringArrayExtra("message")[0];

        //Toast.makeText(getContext(), Tid, Toast.LENGTH_SHORT).show();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Chats").child(Tid);
        onemessage = root.findViewById(R.id.onemessage);
        send = root.findViewById(R.id.send);
        rv = root.findViewById(R.id.allmessage);
        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);





        readMessage();

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                sonem = onemessage.getText().toString();
                onemessage.setText("");
                if (!sonem.isEmpty()) {
                    MyMessage mymessage = new MyMessage(Tid, Tname, sonem);
                    String key = mRef.push().getKey();
                    mRef.child(key).setValue(mymessage);
                }
            }
        });

        return root;
    }

    public void readMessage() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                al.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    al.add(userSnapshot.getValue(MyMessage.class));
                }
                rv.setAdapter(new MessageAdapter(al, Tname, Tid));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}