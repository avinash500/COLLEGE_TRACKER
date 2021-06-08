package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalproject.Administration.AsssignSubject;
import com.example.finalproject.Administration.CreateBatch;
import com.example.finalproject.Administration.CreateBranch;
import com.example.finalproject.Administration.CreateSubject;

public class AddAdmin extends AppCompatActivity {
    CardView CreateSubjectCV,CreateBatchCV,CreateBranchCV,AssignSubjectCV;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        CreateSubjectCV=findViewById(R.id.createsubjectId);
        CreateBatchCV=findViewById(R.id.createbatchId);
        CreateBranchCV=findViewById(R.id.createbranchId);
        AssignSubjectCV=findViewById(R.id.assignsubjectId);

        CreateSubjectCV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent (AddAdmin.this, CreateSubject.class);
                startActivity(intent);

            }
        });

        CreateBatchCV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent (AddAdmin.this, CreateBatch.class);
                startActivity(intent);

            }
        });
        CreateBranchCV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent (AddAdmin.this, CreateBranch.class);
                startActivity(intent);

            }
        });
        AssignSubjectCV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent (AddAdmin.this, AsssignSubject.class);
                startActivity(intent);

            }
        });

    }
}
