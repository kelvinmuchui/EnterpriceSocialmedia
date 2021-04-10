package com.kelvin.enterprice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kelvin.enterprice.Adapters.AdapterComment;
import com.kelvin.enterprice.Model.ModelComment;
import com.kelvin.enterprice.Model.ModelPost;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {


    TextView uNameTv, pTimeTv, pDescTv, pLikesTv, pTitle;
    ImageButton moreBtn;
    Button likeBtn, shareBtn;
    LinearLayout profileLayout;
    FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    EditText commentEt;
    ImageButton sendBtn;
    RecyclerView recyclerView;
    List<ModelComment> commentList;
    AdapterComment adapterComment;

    String pId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);


        recyclerView = findViewById(R.id.recyclerViewComment);
        pDescTv = findViewById(R.id.pDescriptionTv);
        pTimeTv = findViewById(R.id.uTimeTv);
        uNameTv = findViewById(R.id.uNameTv);
        db = FirebaseFirestore.getInstance();
        firebaseAuth =FirebaseAuth.getInstance();
        commentEt = findViewById(R.id.edtComment);
        sendBtn = findViewById(R.id.sendBtn);
        Intent intent = getIntent();

        pId = intent.getStringExtra("postId");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        CollectionReference reRef = db.collection("Post");
        Query postQuery = reRef.whereEqualTo("post_id", pId);
        postQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    ModelPost post = documentSnapshot.toObject(ModelPost.class);
                    pDescTv.setText(post.getPost_message());
                    pTimeTv.setText(post.getPost_date_time());
                }
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment();
            }
        });

    }

    private void Comment(){
        final String timestamp = String.valueOf(System.currentTimeMillis());
        DocumentReference dbpost = db.collection("Comment").document();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String edtComent = commentEt.getText().toString();

        final ModelComment comment = new ModelComment();

        comment.setComment_date(timestamp);
        comment.setComment_id(dbpost.getId());
        comment.setComment_message(edtComent);
        comment.setComment_post_id(pId);
        comment.setComment_user_id(user.getUid());
        dbpost.set(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(PostDetailActivity.this, "comment published", Toast.LENGTH_SHORT).show();
                commentEt.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostDetailActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readComment(){
        commentList = new ArrayList<>();
        CollectionReference chatref =  db.collection("Comment");

        Query comQuery = chatref.whereEqualTo("Comment_post_id", pId);
        comQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                commentList.clear();
                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    ModelComment comment = documentSnapshot.toObject(ModelComment.class);

                    commentList.add(comment);
                    adapterComment = new AdapterComment(PostDetailActivity.this,commentList);
                    adapterComment.notifyDataSetChanged();
                    recyclerView.setAdapter(adapterComment);
                }
            }
        });


    }
}
