package com.example.finalproject.teacher_ui.notes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class NotesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private NotesViewModel notesViewModel;

    List<String> arr1;
    List<String> arr2;
    List<String> arr3;
    Button btn_upload,btn_view;
    TextView pdfName,semester;
    Spinner BatchSpin,BranchSpin,SubSpin;
    String Subject, Branch,  Batch;


    StorageReference storageReference;
    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        notesViewModel =
                ViewModelProviders.of(this).get(NotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        btn_upload=root.findViewById(R.id.upload);
        btn_view=root.findViewById(R.id.viewNotes);
        BatchSpin =root.findViewById(R.id.batchspinner);
        BranchSpin =root.findViewById(R.id.branchspinner);
        SubSpin =root.findViewById(R.id.subspinner);
        pdfName=root.findViewById(R.id.pdf_name);
        semester=root.findViewById(R.id.semester);




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


        ////////////////////////firebase

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Notes");
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



        /////////////////////////////////////////////////////


                btn_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectPdf();
                    }
                });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),ViewPdf.class);
                startActivity(intent);
            }
        });








//        notesViewModel.getView().observe(getViewLifecycleOwner(), new Observer<String>()
//        {
//            @Override
//            public void onChanged(@Nullable String s)
//            {
//
//            }
//        });
        return root;
    }

    private void selectPdf()
    {

        Intent intent=new Intent();
        intent.setType("application/pdf");
//        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF FIle"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uploadPdfFile(data.getData());
        }
    }

    private void uploadPdfFile(Uri data)
    {
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final String sem=semester.getText().toString();

        StorageReference reference=storageReference.child("Notes/"+System.currentTimeMillis()+".pdf").child(Batch).child(sem).child(Branch).child(Subject).child(Objects.requireNonNull(databaseReference.push().getKey()));
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url=uri.getResult();

                        uploadPdf uploadPdf=new uploadPdf(pdfName.getText().toString(),url.toString());
                        databaseReference.child(Batch).child(sem).child(Branch).child(Subject).child(databaseReference.push().getKey()).setValue(uploadPdf);
                        Toast.makeText(getContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded: "+(int)progress+" %");
                    }
                });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        Branch= parent.getItemAtPosition(position).toString();
        Subject= parent.getItemAtPosition(position).toString();
        Toast.makeText(getActivity(), "Subject -"+Subject, Toast.LENGTH_SHORT).show();
        Batch= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



