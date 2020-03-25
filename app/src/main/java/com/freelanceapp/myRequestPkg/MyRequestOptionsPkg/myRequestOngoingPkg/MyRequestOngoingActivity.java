package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestOngoingPkg;

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
import com.freelanceapp.myRequestPkg.FragmentPkg.MyRequestFragment;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestLiveryPkg.Adapter.MyRequestLiveryAdapter;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestOngoingPkg.Adapter.MyRequestOngoingAdapter;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.DashboardSupportActivity;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;

public class MyRequestOngoingActivity extends Fragment implements MyRequestOngoingAdapter.MyRequestOngoingAppOnClickListener, View.OnClickListener {

    private MyRequestOngoingAdapter myRequestOngoingAdapter;
    private RecyclerView rvmyreqongoingfileupload;
    private ImageView ivongoingreqdashboardback, ivnotification;
    private RelativeLayout rlreqongoingviewdetails, rlproblemstatement;
    private TextView tvviewprofile;

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_ongoing);*/

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_request_ongoing, container, false);
        init(view);
        return view;


    }

    private void init(View view) {
        rlproblemstatement = view.findViewById(R.id.rlproblemstatementid);
        rlproblemstatement.setOnClickListener(this);

        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);

        ivongoingreqdashboardback = view.findViewById(R.id.ivongoingreqdashboardbackId);
        ivongoingreqdashboardback.setOnClickListener(this);

        rlreqongoingviewdetails = view.findViewById(R.id.rlreqongoingviewdetailsid);
        rlreqongoingviewdetails.setOnClickListener(this);

        rvmyreqongoingfileupload = view.findViewById(R.id.rvmyreqongoingfileuploadid);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rvmyreqongoingfileupload.setLayoutManager(layoutManager);
        MyRequestOngoingAdapter myRequestOngoingAdapter = new MyRequestOngoingAdapter(getActivity(), this);
        rvmyreqongoingfileupload.setAdapter(myRequestOngoingAdapter);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivongoingreqdashboardbackId:
                removeThisFragment();
                //  replaceFragementWithoutStack(new MyRequestFragment());
                break;
            case R.id.rlreqongoingviewdetailsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.rlproblemstatementid:
                // CheckNetwork.goTobackScreen(getActivity(), DashboardSupportActivity.class);
                replaceFragement(new HelpActivity());
                break;


        }

    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "MyReqEncours");
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
