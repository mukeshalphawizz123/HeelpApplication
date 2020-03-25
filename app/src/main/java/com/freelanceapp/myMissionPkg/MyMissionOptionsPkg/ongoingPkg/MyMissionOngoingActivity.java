package com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.ongoingPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freelanceapp.HelpActivity;
import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.detailsPkg.DetailsActivity;
import com.freelanceapp.myMissionPkg.FragmentPkg.MyMissionFragment;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.completeePkg.Adapter.CompleteeFileUploadAdapter;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.Adapter.OngoingAdapter;
import com.freelanceapp.myRequestPkg.FragmentPkg.MyRequestFragment;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;

public class MyMissionOngoingActivity extends Fragment implements OngoingAdapter.OngoingAppOnClickListener, View.OnClickListener {

    private CompleteeFileUploadAdapter completeeFileUploadAdapter;
    private RecyclerView rvongoingfileupload;
    private ImageView ivmissionongoingdashboardback, ivnotification;
    private RelativeLayout rlmissongoingviewdetails;
    private TextView tvviewprofile, tvmymissionongoingtext;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mission_ongoing, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        tvmymissionongoingtext = view.findViewById(R.id.tvmymissionongoingtextid);
        tvmymissionongoingtext.setOnClickListener(this);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);
        ivmissionongoingdashboardback = view.findViewById(R.id.ivmissionongoingdashboardbackId);
        ivmissionongoingdashboardback.setOnClickListener(this);
        rlmissongoingviewdetails = view.findViewById(R.id.rlmissongoingviewdetailsid);
        rlmissongoingviewdetails.setOnClickListener(this);
        rvongoingfileupload = view.findViewById(R.id.rvongoingfileuploadid);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rvongoingfileupload.setLayoutManager(layoutManager);
        OngoingAdapter ongoingAdapter = new OngoingAdapter(getActivity(), this);
        rvongoingfileupload.setAdapter(ongoingAdapter);
        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
        SpannableString content1 = new SpannableString(getResources().getString(R.string.report_a_problem));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvmymissionongoingtext.setText(content1);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivmissionongoingdashboardbackId:
                removeThisFragment();
                // backReplaceFragement(new MyMissionFragment());
                break;
            case R.id.rlmissongoingviewdetailsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.tvmymissionongoingtextid:
                replaceFragement(new HelpActivity());
                break;
        }

    }

    public void removeThisFragment() {
        final FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();

        // Toast.makeText(this, ""+manager, Toast.LENGTH_SHORT).show();
    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "OnGoing");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void backReplaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
    }
}
