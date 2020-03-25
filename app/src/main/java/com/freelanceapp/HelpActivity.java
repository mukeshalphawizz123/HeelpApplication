package com.freelanceapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestOngoingPkg.MyRequestOngoingActivity;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.DashboardSupportActivity;
import com.freelanceapp.plusMorePkg.PlusMoreFragment;
import com.freelanceapp.utility.CheckNetwork;

public class HelpActivity extends Fragment implements View.OnClickListener {
    private TextView tvmail;
    private ImageView ivnotification,ivdashboardback;
    private RelativeLayout rlcontact;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  fragmentMessageToutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_tout, container, false);
        View view = inflater.inflate(R.layout.activity_help, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ivdashboardback = view.findViewById(R.id.ivdashboardbackId);
        ivdashboardback.setOnClickListener(this);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        rlcontact = view.findViewById(R.id.rlcontactId);
        rlcontact.setOnClickListener(this);
        tvmail = view.findViewById(R.id.tvmailidid);
        SpannableString content = new SpannableString("contact@heelp.com");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvmail.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivnotificationId:
                // replaceFragement(new NotificationActivity());
                CheckNetwork.goTobackScreen(getActivity(), NotificationActivity.class);
                break;
            case R.id.rlcontactId:
                replaceFragement(new DashboardSupportActivity());
                //  CheckNetwork.goTobackScreen(getActivity(), DashboardSupportActivity.class);
                break;
            case R.id.ivdashboardbackId:
                removeThisFragment();
                //replaceFragement(new MyRequestOngoingActivity());
                break;

        }

    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_left,R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void removeThisFragment() {
        final FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();

        // Toast.makeText(this, ""+manager, Toast.LENGTH_SHORT).show();
    }
}
