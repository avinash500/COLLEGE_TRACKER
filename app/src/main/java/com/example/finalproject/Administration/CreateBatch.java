package com.example.finalproject.Administration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.GetterSetter.Batch;
import com.example.finalproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateBatch extends AppCompatActivity
{



        EditText e1;
        Button btn;
        String batchname;
        Batch ba;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_batch);
            e1=findViewById(R.id.e1);
            btn= findViewById(R.id.btn);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef=database.getReference("Batch");

            btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    ba=new Batch();
                    batchname=e1.getText().toString();
                    if(batchname.equals(""))
                    {
                        Toast.makeText(CreateBatch.this, "Fill all detail", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ba.setBatch(batchname);
                        myRef.push().setValue(ba);
                        Toast.makeText(CreateBatch.this, "Batch " + batchname + " Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }
 }
