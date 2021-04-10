package com.kelvin.enterprice.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kelvin.enterprice.Adapters.AdapterMeeting;
import com.kelvin.enterprice.MeetingActivity;
import com.kelvin.enterprice.Model.ModelMeeting;
import com.kelvin.enterprice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeetingFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    FirebaseAuth firebaseAuth;
    List<ModelMeeting> meetingList;
    AdapterMeeting adapterMeeting;
    FirebaseFirestore db;

    public MeetingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.meeting_recyclerView);
        fab =view.findViewById(R.id.fab);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);

        meetingList = new ArrayList<>();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //set title
                builder.setTitle("Add meeting");
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setPadding(10,10,10,10);

                final EditText editText1 = new EditText(getActivity());
                editText1.setHint("Enter Title");
                linearLayout.addView(editText1);
                final EditText editText2 = new EditText(getActivity());
                editText2.setHint("Enter Link");
                linearLayout.addView(editText2);
                final EditText editText3 = new EditText(getActivity());
                editText3.setHint("Enter Duration");
                linearLayout.addView(editText3);
                builder.setView(linearLayout);

                builder.setPositiveButton("Add Meeting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String timestamp = String.valueOf(System.currentTimeMillis());
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        DocumentReference dpMeeting = db.collection("Meeting").document();
                        ModelMeeting meeting = new ModelMeeting();

                        meeting.setMeeting_id(dpMeeting.getId());
                        meeting.setMeeting_date_time(timestamp);
                        meeting.setMeeting_title(editText1.getText().toString());
                        meeting.setMeeting_User_id(user.getUid());
                        meeting.setMeeting_link(editText2.getText().toString());
                        meeting.setMeeting_duration(editText3.getText().toString());

                        dpMeeting.set(meeting).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Meeting added", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();


            }
        });
        loadMeeting();
        return view;
    }
    private void loadMeeting() {

        CollectionReference reRef = db.collection("Meeting");



        reRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                meetingList.clear();
                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    ModelMeeting post = documentSnapshot.toObject(ModelMeeting.class);

                    meetingList.add(post);

                    adapterMeeting = new AdapterMeeting(getActivity(), meetingList);
                    recyclerView.setAdapter(adapterMeeting);
                }


            }
        });

    }
}
