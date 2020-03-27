package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.freelanceapp.homeTablayout.ViewPagerAdapter;
import com.freelanceapp.R;
import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.TransactionCreditFragment;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.TransactionDebitFragment;
import com.freelanceapp.utility.CheckNetwork;
import com.google.android.material.tabs.TabLayout;


public class TransactionsTablayoutFragment extends Fragment implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivdashboardcreditcardback, ivnotificationcreditcard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tablayout_transaction, container, false);
        addTabs(view);
        return view;
    }

    private void addTabs(View view) {
        ivnotificationcreditcard = view.findViewById(R.id.ivnotificationcreditcardId);
        ivnotificationcreditcard.setOnClickListener(this);
        ivdashboardcreditcardback = view.findViewById(R.id.ivdashboardcreditcardbackId);
        ivdashboardcreditcardback.setOnClickListener(this);
        tabLayout = view.findViewById(R.id.tabLayoutId);
        viewPager = view.findViewById(R.id.viewPagerId);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new TransactionCreditFragment(), getResources().getString(R.string.In));
        adapter.addFrag(new TransactionDebitFragment(), getResources().getString(R.string.Out));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivdashboardcreditcardbackId:
                removeThisFragment();
                // CheckNetwork.backScreenWithouFinish(getActivity());
                //  CheckNetwork.goTobackScreen(PrizePoolActivity.this, DashboardPaymentActivity.class);
                // addFragment(new DashboardPaymentActivity(), false, Constants.DASHBOARD_PAYMENT_ACTIVITY);
                // CheckNetwork.backScreenWithouFinish(getActivity(), DashboardPaymentActivity.class);
                break;
            case R.id.ivnotificationcreditcardId:
                // replaceFragement(new NotificationActivity());
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;

        }
    }

    public void removeThisFragment() {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStackImmediate();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.flHomeId, fragment, tag);
        ft.commitAllowingStateLoss();
    }
}
