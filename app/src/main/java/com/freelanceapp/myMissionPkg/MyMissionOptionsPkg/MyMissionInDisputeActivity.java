package com.freelanceapp.myMissionPkg.MyMissionOptionsPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.detailsPkg.DetailsActivity;
import com.freelanceapp.myMissionPkg.FragmentPkg.MyMissionFragment;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.proposePkg.Adapter.ProposeAdapter;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;

public class MyMissionInDisputeActivity extends Fragment implements View.OnClickListener {
    private ImageView ivmissionfeedbackdashboardback, ivnotification;
    private TextView tvviewprofile;
    private RelativeLayout rlmissproposeviewdetails;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mission_in_dispute, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);

        ivmissionfeedbackdashboardback = view.findViewById(R.id.ivmissionfeedbackdashboardbackId);
        ivmissionfeedbackdashboardback.setOnClickListener(this);

        rlmissproposeviewdetails = view.findViewById(R.id.rlmissproposeviewdetailsid);
        rlmissproposeviewdetails.setOnClickListener(this);

        tvviewprofile = view.findViewById(R.id.tvviewprofileid);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivmissionfeedbackdashboardbackId:
                removeThisFragment();
                //replaceFragementWithoutStack(new MyMissionFragment());
                break;
            case R.id.rlmissproposeviewdetailsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;

        }
    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "litige");
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
        // Toast.makeText(this, ""+manager, Toast.LENGTH_SHORT).show();
    }

}
