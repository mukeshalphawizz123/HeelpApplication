package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestCompletePkg;

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

import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.detailsPkg.DetailsActivity;
import com.freelanceapp.myRequestPkg.FragmentPkg.MyRequestFragment;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestCompletePkg.Adapter.MyRequestCompleteAdapter;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestLiveryPkg.Adapter.MyRequestLiveryAdapter;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestLiveryPkg.MyRequestLiveryActivity;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;

public class MyRequestCompleteeActivity extends Fragment implements MyRequestCompleteAdapter.MyRequestCompleteAppOnClickListener, View.OnClickListener {


    private MyRequestCompleteAdapter myRequestCompleteAdapter;
    private RecyclerView rvmyreqcompletefileupload;
    private ImageView ivdashboardcompleteback, ivnotification;
    private RelativeLayout rlreqcompleteviewdetils;
    private TextView tvviewprofile;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_completee);*/

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_request_completee, container, false);
        init(view);
        return view;


    }

    private void init(View view) {
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);

        tvviewprofile = view.findViewById(R.id.tvviewprofileid);

        ivdashboardcompleteback = view.findViewById(R.id.ivdashboardcompletebackId);
        ivdashboardcompleteback.setOnClickListener(this);

        rlreqcompleteviewdetils = view.findViewById(R.id.rlreqcompleteviewdetilsid);
        rlreqcompleteviewdetils.setOnClickListener(this);

        rvmyreqcompletefileupload = view.findViewById(R.id.rvmyreqcompletefileuploadid);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rvmyreqcompletefileupload.setLayoutManager(layoutManager);
        MyRequestCompleteAdapter myRequestCompleteAdapter = new MyRequestCompleteAdapter(getActivity(), this);
        rvmyreqcompletefileupload.setAdapter(myRequestCompleteAdapter);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivdashboardcompletebackId:
                removeThisFragment();
                // replaceFragementWithoutStack(new MyRequestFragment());
                break;
            case R.id.rlreqcompleteviewdetilsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
        }

    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "MyReqCompletee");
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
