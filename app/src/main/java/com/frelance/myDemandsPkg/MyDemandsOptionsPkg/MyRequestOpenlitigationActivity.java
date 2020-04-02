package com.frelance.myDemandsPkg.MyDemandsOptionsPkg;

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

import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;

public class MyRequestOpenlitigationActivity extends Fragment implements View.OnClickListener {
    private RelativeLayout rlreqfeedbackviewdetails;
    private TextView tvviewprofile;
    private ImageView ivtextfeedbackreqdashboardback, ivnotification;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_request_openlitigation, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        rlreqfeedbackviewdetails = view.findViewById(R.id.rlreqfeedbackviewdetailsid);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);
        ivtextfeedbackreqdashboardback = view.findViewById(R.id.ivtextfeedbackreqdashboardbackId);
        ivtextfeedbackreqdashboardback.setOnClickListener(this);
        rlreqfeedbackviewdetails.setOnClickListener(this);
        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivtextfeedbackreqdashboardbackId:
                removeThisFragment();
                //replaceFragementWithoutStack(new MyDemandFragment());
                break;
            case R.id.rlreqfeedbackviewdetailsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;

        }
    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "MyReqEnlitige");
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
