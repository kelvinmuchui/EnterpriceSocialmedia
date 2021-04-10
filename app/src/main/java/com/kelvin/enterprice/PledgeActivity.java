package com.kelvin.enterprice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kelvin.enterprice.Adapters.AdapterPledge;
import com.kelvin.enterprice.Model.ModelPledge;
import com.kelvin.enterprice.Model.ModelPost;

import java.util.ArrayList;
import java.util.List;

public class PledgeActivity extends AppCompatActivity {
    ActionBar actionBar;
    FirebaseAuth firebaseAuth;
    EditText amountEt, descEt;
    Button uploadBtn;
    ProgressDialog pd;
    private FirebaseFirestore db;



    RecyclerView recyclerView;

    List<ModelPledge>pledgeList;
    AdapterPledge adapterPledge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Pledge");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        firebaseAuth =FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.pledge_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);

        pledgeList = new ArrayList<>();

        descEt = findViewById(R.id.pDescriptionEt);
        amountEt = findViewById(R.id.pAmount);
        uploadBtn = findViewById(R.id.pUploadbtn);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = descEt.getText().toString().trim();
                String amount = amountEt.getText().toString().trim();
                if(TextUtils.isEmpty(desc)){
                    Toast.makeText(PledgeActivity.this, "Enter description....", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    uploadData( desc,amount);

                }
            }
        });

        loadPledge();
    }

    private void loadPledge() {
        CollectionReference reRef = db.collection("Fundplegde");
        reRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                pledgeList.clear();
                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    ModelPledge pledge = documentSnapshot.toObject(ModelPledge.class);
                    pledgeList.add(pledge);

                    adapterPledge = new AdapterPledge(PledgeActivity.this,pledgeList);
                    recyclerView.setAdapter(adapterPledge);
                }
            }
        });

    }

    private void uploadData(String desc, String amount) {
        pd.setMessage("Publishing pledge...");


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
                Toast.makeText(PledgeActivity.this, "Pledge published", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(PledgeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        DocumentReference dbpelge = db.collection("Fundplegde").document();

        ModelPledge modelPledge = new ModelPledge();

        modelPledge.setFundpledge_amount(amount);
        modelPledge.setFundpledge_date(timestamp);
        modelPledge.setFundpledge_id(dbpelge.getId());
        modelPledge.setFundpledge_Post_id(dbpost.getId());
        modelPledge.setFundpledge_User_id(Uid);

        dbpelge.set(modelPledge).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(PledgeActivity.this, "Pledge published", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PledgeActivity.this, Main2Activity.class));
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(PledgeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
