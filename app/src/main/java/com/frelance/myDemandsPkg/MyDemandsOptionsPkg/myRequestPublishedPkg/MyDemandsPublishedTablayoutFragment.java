package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.frelance.homeTablayout.ViewPagerAdapter;
import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.MyRequestPublishedBudgetFragment;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.MyRequestPublishedDateFragment;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.MyRequestPublishedNoteFragment;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.google.android.material.tabs.TabLayout;


public class MyDemandsPublishedTablayoutFragment extends Fragment implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivpublisheddashboardback, ivnotification;
    private RelativeLayout rlreqpublishviewdetails;
    private TextView tvviewprofile;
    private String projectId, mission_demand_title;
    private AppCompatTextView tvPublishedTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tablayout__my_request_published, container, false);
        try {
            projectId = this.getArguments().getString("projectId");
            Toast.makeText(getActivity(), projectId, Toast.LENGTH_LONG).show();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        AppSession.setStringPreferences(getActivity(), "projectid", projectId);
        mission_demand_title = AppSession.getStringPreferences(getActivity(), "mission_demand_title");
        addTabs(view);
        return view;
    }

    private void addTabs(View view) {
        tvPublishedTitle = view.findViewById(R.id.tvPublishedTitleId);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);

        ivpublisheddashboardback = view.findViewById(R.id.ivpublisheddashboardbackId);
        ivpublisheddashboardback.setOnClickListener(this);

        rlreqpublishviewdetails = view.findViewById(R.id.rlreqpublishviewdetailsid);
        rlreqpublishviewdetails.setOnClickListener(this);

        tabLayout = view.findViewById(R.id.tabLayoutId);
        viewPager = view.findViewById(R.id.viewPagerId);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new MyRequestPublishedNoteFragment(), "Note");
        adapter.addFrag(new MyRequestPublishedBudgetFragment(), "Budget");
        adapter.addFrag(new MyRequestPublishedDateFragment(), "Date");
        viewPager.setAdapter(adapter);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
        tvPublishedTitle.setText(mission_demand_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivpublisheddashboardbackId:
                removeThisFragment();
                // replaceFragementWithoutStack(new MyDemandFragment());
                break;
            case R.id.rlreqpublishviewdetailsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
        }
    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "MyReqPubliee");
        Bundle bundle = new Bundle();
        bundle.putString("missionId", projectId);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void replaceFragementWithoutStack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
    }


    public void removeThisFragment() {
        final FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();
    }

}
