package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.teacher_ui.notes.uploadPdf;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class EventUpload extends AppCompatActivity {
    EditText newComment;
    Button newImage, newPost;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Uri data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_upload);
        newComment = findViewById(R.id.newComment);
        newImage = findViewById(R.id.newImage);
        newPost = findViewById(R.id.newPost);
        newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImg();
            }


        });
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = newComment.getText().toString();
                if(data != null && comment != null)
                    uploadPdfFile(data, comment);
                else
                    Toast.makeText(EventUpload.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Event");
    }
    
    private void selectImg(){
        Intent intent=new Intent();
        intent.setType("image/*");
//        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select IMG FIle"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            this.data = data.getData();
            //uploadPdfFile(data.getData());
        }
    }

    private void uploadPdfFile(Uri data, final String comment)
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        StorageReference reference=storageReference.child("Notes/"+System.currentTimeMillis()+".jpg").child("Event").child(Objects.requireNonNull(databaseReference.push().getKey()));
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url=uri.getResult();
                        String clubName = getIntent().getStringExtra("clubName");
                        Event event = new Event(clubName, url.toString(), comment, 0);
//                        uploadPdf uploadPdf=new uploadPdf(pdfName.getText().toString(),url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(event);
                        Toast.makeText(EventUpload.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
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
}