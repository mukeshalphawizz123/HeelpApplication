package com.freelanceapp.detailsPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.detailsPkg.Adapter.DetailsAdapter;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.MyMissionInDisputeActivity;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.completeePkg.MyMissionCompleteActivity;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.liveryPkg.MyMissionLiveryActivity;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.MyMissionOngoingActivity;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.proposePkg.MyMissionProposeeActivity;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.MyRequestOpenlitigationActivity;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestCompletePkg.MyRequestCompleteeActivity;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestLiveryPkg.MyRequestLiveryActivity;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestOngoingPkg.MyRequestOngoingActivity;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestPublishedPkg.MyRequestPublishedTablayoutFragment;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;

public class DetailsActivity extends Fragment implements DetailsAdapter.DetailsAppOnClickLister, View.OnClickListener {
    private DetailsAdapter detailsAdapter;
    private RecyclerView rvdetails;
    private ImageView ivdetailsback, ivnotification;
    private String onGoing;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_details, container, false);
        init(view);
        getPreference();
        return view;
    }

    private void init(View view) {
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        ivdetailsback = view.findViewById(R.id.ivdetailsbackId);
        ivdetailsback.setOnClickListener(this);
        rvdetails = view.findViewById(R.id.rvdetailsid);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 6);
        rvdetails.setLayoutManager(layoutManager);
        DetailsAdapter detailsAdapter = new DetailsAdapter(getActivity(), this);
        rvdetails.setAdapter(detailsAdapter);
    }

    private void getPreference() {
        onGoing = AppSession.getStringPreferences(getActivity(), "OnGoing");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivdetailsbackId:
                if (onGoing.equalsIgnoreCase("OnGoing")) {
                    removeThisFragment();
                    // replaceFragement(new MyMissionOngoingActivity());
                   // removeAllFragment(new MyMissionOngoingActivity(), false, Constants.MY_MISSION_ONGOING_ACTIVITY);
                    //   removeThisFragment();
                } else if (onGoing.equalsIgnoreCase("Complete")) {
                    //replaceFragement(new MyMissionCompleteActivity());
                    removeThisFragment();
                    //addFragment(new MyMissionCompleteActivity(), false, Constants.MY_MISSION_COMPLETE_ACTIVITY);

                } else if (onGoing.equalsIgnoreCase("litige")) {
                    // replaceFragement(new MyMissionInDisputeActivity());
                    removeThisFragment();
                  //  addFragment(new MyMissionInDisputeActivity(), false, Constants.MY_MISSION_DISPUTE_ACTIVITY);

                } else if (onGoing.equalsIgnoreCase("Proposee")) {
                    // replaceFragement(new MyMissionProposeeActivity());
                    removeThisFragment();
                   // addFragment(new MyMissionProposeeActivity(), false, Constants.MY_MISSION_PROPOSEE_ACTIVITY);

                } else if (onGoing.equalsIgnoreCase("Livree")) {
                    removeThisFragment();
                    // replaceFragement(new MyMissionLiveryActivity());
                   // addFragment(new MyMissionLiveryActivity(), false, Constants.MY_MISSION_LIVERY_ACTIVITY);

                } else if (onGoing.equalsIgnoreCase("MyReqPubliee")) {
                    removeThisFragment();
                    // replaceFragement(new MyRequestPublishedTablayoutFragment());
                    addFragment(new MyRequestPublishedTablayoutFragment(), false, Constants.MY_REQUEST_PUBLISHED_TABLAYOUT_FRAGMENT);

                } else if (onGoing.equalsIgnoreCase("MyReqEncours")) {
                    removeThisFragment();
                    // replaceFragement(new MyRequestOngoingActivity());
                  //  addFragment(new MyRequestOngoingActivity(), false, Constants.MY_REQUEST_ONGOING_FRAGMENT);

                } else if (onGoing.equalsIgnoreCase("MyReqLivree")) {
                    removeThisFragment();
                    //replaceFragement(new MyRequestLiveryActivity());
                   // addFragment(new MyRequestLiveryActivity(), false, Constants.MY_REQUEST_LIVERY_FRAGMENT);

                } else if (onGoing.equalsIgnoreCase("MyReqCompletee")) {
                    removeThisFragment();
                    // replaceFragement(new MyRequestCompleteeActivity());
                   // addFragment(new MyRequestCompleteeActivity(), false, Constants.MY_REQUEST_COMPLETE_FRAGMENT);

                } else if (onGoing.equalsIgnoreCase("MyReqEnlitige")) {
                    removeThisFragment();
                    // replaceFragement(new MyRequestOpenlitigationActivity());
                   // addFragment(new MyRequestOpenlitigationActivity(), false, Constants.MY_REQUEST_OPENLITIGATION_FRAGMENT);

                }
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
        }
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
    }

    public void removeThisFragment() {
        final FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
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
