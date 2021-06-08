package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproject.Administration.AsssignSubject;

import java.util.ArrayList;
import java.util.List;

public class AddNotice extends AppCompatActivity
{

    Spinner spinner;
    EditText title, desc;
    String user="a";
    List<String> arr1;
    Button  send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        send=findViewById(R.id.send);
        title=findViewById(R.id.noticetitle);
        desc=findViewById(R.id.noticedesc);


        spinner=findViewById(R.id.adminnotice);
        arr1 = new ArrayList<String>();
        arr1.add("All");
        arr1.add("Teacher");
        arr1.add("Student");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNotice.this,R.layout.student_spin_layout,arr1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                user= parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String[] notice = new String[3];
                notice[0] = title.getText().toString();
                notice[1] = desc.getText().toString();
                notice[2] = user;
                if(notice[0].equals("") || notice[1].equals(""))
                {
                    Toast.makeText(AddNotice.this, "Fill All the Details", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(AddNotice.this, AdminSelectUserType.class);
                    intent.putExtra("Notice", notice);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}