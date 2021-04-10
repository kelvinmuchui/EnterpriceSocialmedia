package com.kelvin.enterprice.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelvin.enterprice.Model.ModelPledge;
import com.kelvin.enterprice.PaymentActivity;
import com.kelvin.enterprice.PostDetailActivity;
import com.kelvin.enterprice.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterPledge extends RecyclerView.Adapter<AdapterPledge.MyHolder> {

    Context context;
    List<ModelPledge> pledgeList;

    public AdapterPledge(Context context, List<ModelPledge> pledgeList) {
        this.context = context;
        this.pledgeList = pledgeList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_pledge, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {


        String uid = pledgeList.get(i).getFundpledge_User_id();
        final String pid = pledgeList.get(i).getFundpledge_id();
        String pTimeStamp = pledgeList.get(i).getFundpledge_date();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        holder.amount.setText(pledgeList.get(i).getFundpledge_amount());
        holder.date.setText(pTime);
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("pledgeId", pid);
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return pledgeList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{


        TextView name, date , amount;
        Button pay;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.plName);
            date = itemView.findViewById(R.id.plDate);
            amount =itemView.findViewById(R.id.plAmount);
            pay = itemView.findViewById(R.id.plPaybtn);
        }
    }
}
