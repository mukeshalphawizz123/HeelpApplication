package com.freelanceapp.homeTablayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.freelanceapp.homeTablayout.fragment.HomeCategoryFrament;
import com.freelanceapp.homeTablayout.fragment.HomeMissionFragment;

import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;

import com.freelanceapp.utility.CheckNetwork;
import com.google.android.material.tabs.TabLayout;


public class HomeTablayoutFragment extends Fragment implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivnotificationHome, ivSerachHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tablayout_home, container, false);
        addTabs(view);
        return view;
    }

    private void addTabs(View view) {
        ivSerachHome = view.findViewById(R.id.ivSerachHomeId);
        ivnotificationHome = view.findViewById(R.id.ivnotificationHomeId);
        tabLayout = view.findViewById(R.id.tabLayoutId);
        viewPager = view.findViewById(R.id.viewPagerId);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new HomeCategoryFrament(), getResources().getString(R.string.post_a_demand));
        adapter.addFrag(new HomeMissionFragment(), getResources().getString(R.string.find_a_mission));
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
        clickListenerSetup();
    }

    private void clickListenerSetup() {
        ivnotificationHome.setOnClickListener(this);
        ivSerachHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivnotificationHomeId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.ivSerachHomeId:
                CheckNetwork.callToast(getActivity());
                break;
        }
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
    }

}
