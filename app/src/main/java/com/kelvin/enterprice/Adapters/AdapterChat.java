package com.kelvin.enterprice.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.kelvin.enterprice.Model.ModelConversation;
import com.kelvin.enterprice.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder> {


    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;

    Context context;
    List<ModelConversation> chatList;
    String imageUri;

    FirebaseUser fUser;

    public AdapterChat(Context context, List<ModelConversation> chatList, String imageUri) {
        this.context = context;
        this.chatList = chatList;
        this.imageUri = imageUri;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        if (i == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return new MyHolder(view);

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return new MyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        String message = chatList.get(position).getConversation_message();
        String timeStamp = chatList.get(position).getConversation_date();

        //convert time stamp
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timeStamp));
        String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();


        //set data
        holder.messageTv.setText(message);
        holder.timeTv.setText(dateTime);

        //to show delete dialog
        holder.messaagelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure to delete this message?");

                //delete button
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMessage(position);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
        });

        // click t

        //set seen


            holder.isSeenTv.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    private void deleteMessage(int i) {

        final String myUID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        String msgTimeStamp = chatList.get(i).getConversation_date();
//        CollectionReference refconv =
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
//        Query query = dbRef.orderByChild("timestamp").equalTo(msgTimeStamp);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                    if (ds.child("sender").getValue().equals(myUID)) {
//                        ds.getRef().removeValue();
//
//                        // method two
////                        HashMap<String, Object> hashMap = new HashMap<>();
////                        hashMap.put("message","This message was deleted...");
////                        ds.getRef().updateChildren(hashMap);
//
//                        Toast.makeText(context, "message deleted...", Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        Toast.makeText(context, "You can delete only your messages ...", Toast.LENGTH_SHORT).show();
//
//                    }

//
 //               }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    @Override
    public int getItemViewType(int position) {

        //get current user
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        if (chatList.get(position).getConversation_from_users_id().equals(fUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    //View Holder class

    class MyHolder extends RecyclerView.ViewHolder {

        //views
        ImageView profileIv;
        TextView messageTv, timeTv, isSeenTv;

        LinearLayout messaagelayout;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            messaagelayout = itemView.findViewById(R.id.messageLayout);
            profileIv = itemView.findViewById(R.id.profileIv);
            messageTv = itemView.findViewById(R.id.messageTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            isSeenTv = itemView.findViewById(R.id.isSeenTv);
        }
    }
}

