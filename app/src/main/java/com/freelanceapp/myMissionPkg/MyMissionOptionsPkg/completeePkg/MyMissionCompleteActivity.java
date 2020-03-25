package com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.completeePkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.Toast;

import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.chatPkg.Adapter.ChatAdapter;
import com.freelanceapp.detailsPkg.DetailsActivity;
import com.freelanceapp.myMissionPkg.FragmentPkg.MyMissionFragment;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.completeePkg.Adapter.CompleteeFileUploadAdapter;
import com.freelanceapp.myRequestPkg.FragmentPkg.MyRequestFragment;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;

public class MyMissionCompleteActivity extends Fragment implements CompleteeFileUploadAdapter.CompleteeFileUploadAppOnClickListener, View.OnClickListener {
    private CompleteeFileUploadAdapter completeeFileUploadAdapter;
    private RecyclerView rvfileupload;
    private ImageView ivmissioncompleterdashboardback, ivnotification;
    private RelativeLayout rlmisscompleteviewdetails, rldummyimgid;
    private TextView tvviewprofile;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mission_complete, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);

        ivmissioncompleterdashboardback = view.findViewById(R.id.ivmissioncompleterdashboardbackId);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);
        rldummyimgid = view.findViewById(R.id.rldummyimgid);
        rlmisscompleteviewdetails = view.findViewById(R.id.rlmisscompleteviewdetailsId);

        ivmissioncompleterdashboardback.setOnClickListener(this);
        rlmisscompleteviewdetails.setOnClickListener(this);
        rldummyimgid.setOnClickListener(this);

        rvfileupload = view.findViewById(R.id.rvfileuploadid);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        rvfileupload.setLayoutManager(layoutManager);
        CompleteeFileUploadAdapter completeeFileUploadAdapter = new CompleteeFileUploadAdapter(getActivity(), this);
        rvfileupload.setAdapter(completeeFileUploadAdapter);


        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rldummyimgid:
                break;
            case R.id.ivmissioncompleterdashboardbackId:
                removeThisFragment();
                // replaceFragementWithoutStack(new MyMissionFragment());
                break;
            case R.id.rlmisscompleteviewdetailsId:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
        }

    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "Complete");
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
