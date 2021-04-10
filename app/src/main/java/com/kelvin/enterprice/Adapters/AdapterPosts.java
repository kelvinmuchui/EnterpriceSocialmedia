package com.kelvin.enterprice.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelvin.enterprice.ChatActivity;
import com.kelvin.enterprice.FirebaseQuery;
import com.kelvin.enterprice.Model.ModelPost;
import com.kelvin.enterprice.Model.ModelUser;
import com.kelvin.enterprice.PostDetailActivity;
import com.kelvin.enterprice.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.MyHolder> {


    Context context;
    List<ModelPost> postList;
    List<ModelUser> userList;



    public AdapterPosts(Context context, List<ModelPost> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_posts, parent, false);


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int i) {
        final String uid = postList.get(i).getPost_user_id();
//        String uName = postList.get(i).getuName();
        final String pId = postList.get(i).getPost_id();
        String pDescr = postList.get(i).getPost_message();

        String pTimeStamp = postList.get(i).getPost_date_time();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();


        //set data
        holder.uNameTv.setText("username");
        holder.pTimeTv.setText(pTime);
        holder.pTitle.setText("");
        holder.pDescTv.setText(pDescr);
        //set dp


        //handle clicks
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "More", Toast.LENGTH_SHORT).show();
            }
        });

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();
            }
        });
        holder.comentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                        Intent intent = new Intent(context, PostDetailActivity.class);
                        intent.putExtra("postId", pId);
                        context.startActivity(intent);




            }
        });

        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show();
            }
        });
        holder.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(context, ThereProfileActivity.class);
//                intent.putExtra("uid", uid);
//                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    //View Holder Class
    class MyHolder extends RecyclerView.ViewHolder {


        ImageView uPictureIv, pImageIv;
        TextView uNameTv, pTimeTv, pDescTv, pLikesTv, pTitle;
        ImageButton moreBtn;
        Button likeBtn, comentBtn, shareBtn;
        LinearLayout profileLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);


            uPictureIv = itemView.findViewById(R.id.uPictureIv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.uTimeTv);
            pTitle = itemView.findViewById(R.id.pTitleTv);
            pDescTv = itemView.findViewById(R.id.pDescriptionTv);
            pLikesTv = itemView.findViewById(R.id.pLikesTv);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            moreBtn = itemView.findViewById(R.id.moreBtn);
            comentBtn = itemView.findViewById(R.id.commentBtn);
            shareBtn = itemView.findViewById(R.id.shareBtn);
           profileLayout = itemView.findViewById(R.id.parofileLayout);


        }
    }
}
