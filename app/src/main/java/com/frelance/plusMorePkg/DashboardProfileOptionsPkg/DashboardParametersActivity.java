package com.frelance.plusMorePkg.DashboardProfileOptionsPkg;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.frelance.OptionActivity;
import com.frelance.R;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.plusMorePkg.PlusMoreFragment;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

public class DashboardParametersActivity extends Fragment implements View.OnClickListener {
    private RelativeLayout rltermsandcondition, rlpowerbtn;
    private ImageView ivdashboardparametersback, ivnotificationparameters;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard_parameters, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ivnotificationparameters = view.findViewById(R.id.ivnotificationparametersId);
        ivnotificationparameters.setOnClickListener(this);

        rlpowerbtn = view.findViewById(R.id.rlpowerbtnid);
        rlpowerbtn.setOnClickListener(this);

        rltermsandcondition = view.findViewById(R.id.rltermsandconditionid);
        rltermsandcondition.setOnClickListener(this);

        ivdashboardparametersback = view.findViewById(R.id.ivdashboardparametersbackId);
        ivdashboardparametersback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rltermsandconditionid:
                addFragmentnext(new DashboardParametersTermsActivity(), true, Constants.DASHBOARD_PARAMETERS_TERMS_ACTIVITY);
                break;
            case R.id.rlpowerbtnid:
                AppSession.clearAllSharedPreferences(getActivity());
                CheckNetwork.goTobackScreen(getActivity(), OptionActivity.class);
                break;
            case R.id.ivdashboardparametersbackId:
                removeAllFragment(new PlusMoreFragment(), false, Constants.PLUS_MORE_FRAGMENT);
                break;
            case R.id.ivnotificationparametersId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;


        }
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        // fragmentTransaction.addToBackStack();
        fragmentTransaction.commit();

    }

    public void addFragmentnext(Fragment fragment, boolean addToBackStack,
                                String tag) {
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
