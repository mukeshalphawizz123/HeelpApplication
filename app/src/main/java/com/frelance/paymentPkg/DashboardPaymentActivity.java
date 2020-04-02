package com.frelance.paymentPkg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.frelance.R;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.CreditCardActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.PrizePoolActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.TransactionsTablayoutFragment;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

public class DashboardPaymentActivity extends Fragment implements View.OnClickListener {
    private ImageView ivdashboardpaymentback, ivnotification;
    private RelativeLayout rlprizepoolpayment, rlcreditcardpayment, rltransactionpayment;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard_payment, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ivnotification = view.findViewById(R.id.ivnotificationId);
        rlprizepoolpayment = view.findViewById(R.id.rlprizepoolpaymentid);
        rlcreditcardpayment = view.findViewById(R.id.rlcreditcardpaymentid);
        rltransactionpayment = view.findViewById(R.id.rltransactionpaymentid);
        ivdashboardpaymentback = view.findViewById(R.id.ivdashboardpaymentbackId);
        clickListenerSetup();
    }

    private void clickListenerSetup() {
        ivnotification.setOnClickListener(this);
        rlprizepoolpayment.setOnClickListener(this);
        rlcreditcardpayment.setOnClickListener(this);
        rltransactionpayment.setOnClickListener(this);
        ivdashboardpaymentback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivdashboardpaymentbackId:
                removeThisFragment();
                //removeAllFragment(new PlusMoreFragment(), false, Constants.PLUS_MORE_FRAGMENT);
                break;
            case R.id.rlprizepoolpaymentid:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), PrizePoolActivity.class);
                break;
            case R.id.rlcreditcardpaymentid:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), CreditCardActivity.class);
                break;
            case R.id.rltransactionpaymentid:
                addFragmentnext(new TransactionsTablayoutFragment(), true, Constants.TRANSACTIONS_TAB_LAYOUT);
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;


        }
    }

    public void addFragmentnext(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        } else {
            manager = getActivity().getSupportFragmentManager();
            manager.popBackStackImmediate();
        }
        ft.replace(R.id.flHomeId, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
    }

    public void removeThisFragment() {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStackImmediate();

        // Toast.makeText(this, ""+manager, Toast.LENGTH_SHORT).show();
    }

    private void replaceFragementMain(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void removeAllFragment(Fragment replaceFragment,
                                  boolean addToBackStack, String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        manager.popBackStackImmediate(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.flHomeId, replaceFragment);
        ft.commit();
    }
}
