package com.kelvin.enterprice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kelvin.enterprice.Adapters.AdapterChat;
import com.kelvin.enterprice.Model.ModelConversation;
import com.kelvin.enterprice.Model.ModelUser;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView profileIv;
    TextView nameTv, userStatusTv;
    EditText messageEt;
    ImageButton sendBtn;
    List<ModelConversation> chatList;
    AdapterChat adapterChat;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    String hisUid;
    String myUid;
    String hisImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        recyclerView = findViewById(R.id.chat_recyclerView);
        profileIv = findViewById(R.id.profileIv);
        nameTv = findViewById(R.id.nameTv);
        userStatusTv = findViewById(R.id.userStatusTv);
        messageEt = findViewById(R.id.messageEt);
        sendBtn = findViewById(R.id.sendBtn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        firebaseAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();

        hisUid = intent.getStringExtra("hisUid");

        db = FirebaseFirestore.getInstance();

        CollectionReference reRef = db.collection("User");

        Query userQuery = reRef.whereEqualTo("user_id", hisUid);

        userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    ModelUser user = documentSnapshot.toObject(ModelUser.class);

                    nameTv.setText(user.getUser_name());
                }
            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // notify =true;
                //get text

                String message = messageEt.getText().toString().trim();
                //check if text is empty
                if (TextUtils.isEmpty(message)){
                    Toast.makeText(ChatActivity.this, "Can not end an empty text....", Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(message);
                }
                //clear text edit
                messageEt.setText("");
            }
        });

        readMessages();

    }

    private void readMessages() {


            chatList = new ArrayList<>();

            CollectionReference chatref =  db.collection("Conversation");
            chatref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    chatList.clear();
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        ModelConversation conversation = documentSnapshot.toObject(ModelConversation.class);
                        if (conversation.getConversation_from_users_id().equals(myUid)&& conversation.getConversation_to_user_id().equals(hisUid)
                        || conversation.getConversation_to_user_id().equals(myUid)&& conversation.getConversation_from_users_id().equals(hisUid)
                        ){
                            chatList.add(conversation);
                        }
                        adapterChat = new AdapterChat(ChatActivity.this,chatList,hisImage);
                        adapterChat.notifyDataSetChanged();
                        recyclerView.setAdapter(adapterChat);
                    }
                }
            });
    }

    private void sendMessage(String message) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        myUid = user.getUid();
        DocumentReference dbConvers = db.collection("Conversation").document();
        String timestamp = String.valueOf(System.currentTimeMillis());
        ModelConversation conversation = new ModelConversation();

        conversation.setConversation_date(timestamp);
        conversation.setConversation_to_user_id(hisUid);
        conversation.setConversation_from_users_id(myUid);
        conversation.setConversation_message(message);
        conversation.setConversation_id(dbConvers.getId());
        conversation.setConversation_user_id(myUid);

        dbConvers.set(conversation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                messageEt.setText("");
            }
        });

    }
}
