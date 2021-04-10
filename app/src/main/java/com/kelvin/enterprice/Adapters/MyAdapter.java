package com.kelvin.enterprice.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kelvin.enterprice.Fragment.HomeFragment;
import com.kelvin.enterprice.Fragment.MeetingFragment;
import com.kelvin.enterprice.Fragment.ProfileFragment;
import com.kelvin.enterprice.Fragment.UsersFragment;
import com.kelvin.enterprice.MeetingActivity;

public class MyAdapter extends FragmentStatePagerAdapter {

    Context context;
    int totalTabs;

    public MyAdapter(@NonNull FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                UsersFragment footballFragment = new UsersFragment();
                return footballFragment;
            case 1:
                MeetingFragment cricketFragment = new MeetingFragment();
                return cricketFragment;
            case 2:
                ProfileFragment nbaFragment = new ProfileFragment();
                return nbaFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }


}
