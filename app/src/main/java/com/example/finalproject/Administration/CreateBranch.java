package com.example.finalproject.Administration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.GetterSetter.Branch;
import com.example.finalproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateBranch extends AppCompatActivity
{

    EditText e1;
    Button b;
    Branch br;
    String branchname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_branch);
        e1=findViewById(R.id.edittext);
        b=findViewById(R.id.btn);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef=database.getReference("Branch");

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                br = new Branch();
                branchname = e1.getText().toString().toUpperCase();
                if (branchname.equals("")) {
                    Toast.makeText(CreateBranch.this, "Fill all details", Toast.LENGTH_SHORT).show();
                }
                else {
                    br.setBranch_name(branchname);
                    myRef.push().setValue(br);
                    Toast.makeText(CreateBranch.this, "" + branchname + " Branch Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
