package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestLiveryPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freelanceapp.HelpActivity;
import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.detailsPkg.DetailsActivity;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.liveryPkg.Adapter.LiveryAdapter;
import com.freelanceapp.myRequestPkg.FragmentPkg.MyRequestFragment;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestLiveryPkg.Adapter.MyRequestLiveryAdapter;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;

public class MyRequestLiveryActivity extends Fragment implements MyRequestLiveryAdapter.MyRequestLiveryAppOnClickListener, View.OnClickListener {

    private MyRequestLiveryAdapter myRequestLiveryAdapter;
    private RecyclerView rvmyreqliveryfileupload;
    private ImageView ivlivreedashboardback, ivnotification;
    private RelativeLayout rlreqlivreeviewdetail;
    private TextView tvviewprofile, tvliverycontact;
    private AppCompatCheckBox radioid, radiograyid;

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_livery);*/

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_request_livery, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        tvliverycontact = view.findViewById(R.id.tvliverycontactid);
        tvliverycontact.setOnClickListener(this);
        radioid = view.findViewById(R.id.radioid);
        radiograyid = view.findViewById(R.id.radiograyid);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);
        ivlivreedashboardback = view.findViewById(R.id.ivlivreedashboardbackId);
        ivlivreedashboardback.setOnClickListener(this);
        rlreqlivreeviewdetail = view.findViewById(R.id.rlreqlivreeviewdetailid);
        rlreqlivreeviewdetail.setOnClickListener(this);
        rvmyreqliveryfileupload = view.findViewById(R.id.rvmyreqliveryfileuploadid);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rvmyreqliveryfileupload.setLayoutManager(layoutManager);
        MyRequestLiveryAdapter myRequestLiveryAdapter = new MyRequestLiveryAdapter(getActivity(), this);
        rvmyreqliveryfileupload.setAdapter(myRequestLiveryAdapter);
        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
        SpannableString content1 = new SpannableString(getResources().getString(R.string.report_a_problem));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvliverycontact.setText(content1);
        radioid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radiograyid.setChecked(false);
                }
            }
        });
        radiograyid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioid.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivlivreedashboardbackId:
                removeThisFragment();
                // replaceFragementWithoutStack(new MyRequestFragment());
                break;
            case R.id.rlreqlivreeviewdetailid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.tvliverycontactid:
                replaceFragement(new HelpActivity());
                break;
        }

    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "MyReqLivree");
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
