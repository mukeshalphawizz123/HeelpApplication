package com.frelance.messageListPkg.MessageTablayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.frelance.R;
import com.frelance.messageListPkg.MessageListFragmentPkg.MessageNonLusFragment;
import com.frelance.messageListPkg.MessageListFragmentPkg.MessageToutFragment;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.CheckNetwork;
import com.google.android.material.tabs.TabLayout;


public class MessageListTablayoutFragment extends Fragment implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivnotificationmsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tablayout_messagelist, container, false);
        addTabs(view);
        return view;
    }

    private void addTabs(View view) {
        ivnotificationmsg=view.findViewById(R.id.ivnotificationmsgId);
        ivnotificationmsg.setOnClickListener(this);
        tabLayout = view.findViewById(R.id.tabLayoutId);
        viewPager = view.findViewById(R.id.viewPagerId);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new MessageToutFragment(), getResources().getString(R.string.All));
        adapter.addFrag(new MessageNonLusFragment(), getResources().getString(R.string.Unread));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivnotificationmsgId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;

        }

    }
}
