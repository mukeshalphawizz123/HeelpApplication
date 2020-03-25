package com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.liveryPkg;

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
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.liveryPkg.Adapter.LiveryAdapter;
import com.freelanceapp.myRequestPkg.FragmentPkg.MyRequestFragment;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;

public class MyMissionLiveryActivity extends Fragment implements LiveryAdapter.LiveryAppOnClickListener, View.OnClickListener {

    private LiveryAdapter liveryAdapter;
    private RecyclerView rvLiveryfileupload;
    private ImageView ivmissionliverydashboardback, ivnotification;
    private RelativeLayout rlmissliveryviewdetails;
    private TextView tvviewprofile, tvmymissionliverytext;
    private String livree;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mission_livery, container, false);
        init(view);
        return view;
    }


    private void init(View view) {
        tvmymissionliverytext = view.findViewById(R.id.tvmymissionliverytextid);
        tvmymissionliverytext.setOnClickListener(this);

        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);

        ivmissionliverydashboardback = view.findViewById(R.id.ivmissionliverydashboardbackId);
        ivmissionliverydashboardback.setOnClickListener(this);

        rlmissliveryviewdetails = view.findViewById(R.id.rlmissliveryviewdetailsid);
        rlmissliveryviewdetails.setOnClickListener(this);

        rvLiveryfileupload = view.findViewById(R.id.rvLiveryfileuploadid);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        rvLiveryfileupload.setLayoutManager(layoutManager);
        LiveryAdapter liveryAdapter = new LiveryAdapter(getActivity(), this);
        rvLiveryfileupload.setAdapter(liveryAdapter);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);

        SpannableString content1 = new SpannableString(getResources().getString(R.string.report_a_problem));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvmymissionliverytext.setText(content1);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivmissionliverydashboardbackId:
                removeThisFragment();
                // replaceWithoutAddToStack(new MyMissionFragment());
                break;
            case R.id.rlmissliveryviewdetailsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.tvmymissionliverytextid:
                replaceFragement(new HelpActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
        }

    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "Livree");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void replaceWithoutAddToStack(Fragment fragment) {
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
