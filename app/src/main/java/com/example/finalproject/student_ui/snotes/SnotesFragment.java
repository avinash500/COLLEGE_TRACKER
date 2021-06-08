package com.example.finalproject.student_ui.snotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalproject.R;
import com.example.finalproject.teacher_ui.notes.uploadPdf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SnotesFragment extends Fragment implements AdapterView.OnItemSelectedListener
{

    private SnotesViewModel snotesViewModel;
    ListView pdfview;
    DatabaseReference databaseReference;
    EditText semester;
    List<uploadPdf> uploadPdfs;
    Button Btn_viewPdf;
    Spinner BatchSpin,BranchSpin,SubSpin;
    String Subject, Branch,  Batch;
    List<String> arr1;
    List<String> arr2;
    List<String> arr3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        snotesViewModel =
                ViewModelProviders.of(this).get(SnotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_snotes, container, false);


        semester =root.findViewById(R.id.semester);
        BatchSpin =root.findViewById(R.id.batchspinner);
        BranchSpin =root.findViewById(R.id.branchspinner);
        SubSpin =root.findViewById(R.id.subspinner);
        pdfview=root.findViewById(R.id.listview);

        uploadPdfs=new ArrayList<>();
        Btn_viewPdf=root.findViewById(R.id.Btn_viewPdf);


        arr1 = new ArrayList<String>();
        arr2 = new ArrayList<String>();
        arr3 = new ArrayList<String>();
        BatchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Batch= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });
        BranchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Branch= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });
        SubSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Subject= parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1=database.getReference("Batch");
        final DatabaseReference myRef2=database.getReference("Subject");
        final DatabaseReference myRef3=database.getReference("Branch");





        myRef1.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.getValue() !=null) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        com.example.finalproject.GetterSetter.Batch b = snapshot.getValue(com.example.finalproject.GetterSetter.Batch.class);
                        arr1.add(b.getBatch());
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),R.layout.student_spin_layout,arr1);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                BatchSpin.setAdapter(dataAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        myRef2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.getValue() !=null) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        com.example.finalproject.GetterSetter.Subject b = snapshot.getValue(com.example.finalproject.GetterSetter.Subject.class);
                        arr2.add(b.getCourse_name());
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),R.layout.student_spin_layout,arr2);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SubSpin.setAdapter(dataAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        myRef3.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.getValue() !=null)
                {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        com.example.finalproject.GetterSetter.Branch b = snapshot.getValue(com.example.finalproject.GetterSetter.Branch.class);
                        arr3.add(b.getBranch_name());
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),R.layout.student_spin_layout,arr3);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                BranchSpin.setAdapter(dataAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        Btn_viewPdf.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                viewAllFiles();
                pdfview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        uploadPdf uploadPdf=uploadPdfs.get(i);
                        Intent intent=new Intent();
                        Uri file = Uri.parse(uploadPdf.getUrl());
                        intent.setDataAndType(file, "application/pdf");
                        Log.d("stoopid", file.toString());
                        startActivity(intent);
                    }
                });
            }
        });

        return root;
    }

    private void viewAllFiles()
    {

        final String sem=semester.getText().toString();
//        final String mysubject=subject.getText().toString();
//        final String mybranch=branch.getText().toString();
//        final String mybatch=batch.getText().toString();

        databaseReference= FirebaseDatabase.getInstance().getReference("Notes").child(Batch).child(sem).child(Branch).child(Subject);

        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                uploadPdfs.clear();
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    uploadPdf uploadPdf=postSnapshot.getValue(com.example.finalproject.teacher_ui.notes.uploadPdf.class);
                    uploadPdfs.add(uploadPdf);
                }
                String[] uploads=new String[uploadPdfs.size()];
                for (int i=0;i<uploads.length;i++){
                    uploads[i]=uploadPdfs.get(i).getName();
                }


                Log.d("uploads", Arrays.toString(uploads));
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,uploads){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
                    {
//                        pdfview.setTextColor(Color.WHITE);
//                        text.setText(items.get(position));
//                        text.setBackgroundColor(Color.RED);
//                        int color = Color.argb( 200, 255, 64, 64 );
//                        text.setBackgroundColor( color );
                        return super.getView(position, convertView, parent);
                    }
                };
                pdfview.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        Branch= parent.getItemAtPosition(position).toString();
        Subject= parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(), "Subject -"+Subject, Toast.LENGTH_SHORT).show();
        Batch= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}