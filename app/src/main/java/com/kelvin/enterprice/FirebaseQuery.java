package com.kelvin.enterprice;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kelvin.enterprice.Model.ModelUser;

public class FirebaseQuery {


    FirebaseFirestore db  = FirebaseFirestore.getInstance();

    String Username;




    CollectionReference reRef = db.collection("User");


    public String getUsername(String uid){

        Query query = reRef.whereEqualTo("user_id", uid);

            reRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                        ModelUser user = documentSnapshot.toObject(ModelUser.class);

                        Username = user.getUser_name();
                    }
                }
            });


        return Username;
    }
}
