package com.kelvin.enterprice.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelvin.enterprice.Model.ModelMeeting;
import com.kelvin.enterprice.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterMeeting extends RecyclerView.Adapter<AdapterMeeting.MyHolder> {
    Context context;
    List<ModelMeeting> meetingList;

    public AdapterMeeting(Context context, List<ModelMeeting> meetingList) {
        this.context = context;
        this.meetingList = meetingList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_meeting, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        String pTimeStamp = meetingList.get(i).getMeeting_date_time();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();


        holder.time.setText(pTime);
        holder.link.setText(meetingList.get(i).getMeeting_link());
        holder.ctitle.setText(meetingList.get(i).getMeeting_title());
        holder.duration.setText(meetingList.get(i).getMeeting_duration());
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView username, duration, link, time, ctitle;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            duration = itemView.findViewById(R.id.duration);
            link = itemView.findViewById(R.id.link);
            time = itemView.findViewById(R.id.timeTv);
            ctitle = itemView.findViewById(R.id.title);
        }
    }
}
