package com.kelvin.enterprice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kelvin.enterprice.Model.ModelPost;

public class AddPostActivity extends AppCompatActivity {
    ActionBar actionBar;
    FirebaseAuth firebaseAuth;
    EditText titleEt, descEt;
    Button uploadBtn;
    ProgressDialog pd;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Add New Post");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        firebaseAuth =FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();


        descEt = findViewById(R.id.pDescriptionEt);
        uploadBtn = findViewById(R.id.pUploadbtn);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = descEt.getText().toString().trim();
                if(TextUtils.isEmpty(desc)){
                    Toast.makeText(AddPostActivity.this, "Enter description....", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    uploadData( desc);

                }
            }
        });

    }


    private void uploadData(String desc){
        pd.setMessage("Publishing post...");


        final String timestamp = String.valueOf(System.currentTimeMillis());
        DocumentReference dbpost = db.collection("Post").document();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        ModelPost modelPost = new ModelPost();
        String Uid = user.getUid();

        modelPost.setPost_id(dbpost.getId());
        modelPost.setPost_message(desc);
        modelPost.setPost_date_time(timestamp);
        modelPost.setPost_user_id(Uid);

        dbpost.set(modelPost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddPostActivity.this, "Post published", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddPostActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
