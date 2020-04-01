package com.freelanceapp.detailsPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;
import com.freelanceapp.detailsPkg.Adapter.DetailsAdapter;
import com.freelanceapp.detailsPkg.detailModlePkg.MissionViewDetailModle;
import com.freelanceapp.detailsPkg.detailModlePkg.YourMission;
import com.freelanceapp.myDemandsPkg.MyRequestOptionsPkg.myRequestPublishedPkg.MyDemandsPublishedTablayoutFragment;
import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends Fragment implements DetailsAdapter.DetailsAppOnClickLister, View.OnClickListener {
    private DetailsAdapter detailsAdapter;
    private RecyclerView rvdetails;
    private ImageView ivdetailsback, ivnotification;
    private String onGoing, missionId;
    private ProgressBar pbMymissionDetail;
    private ApiServices apiServices;
    private List<YourMission> yourMissionList;
    private AppCompatTextView etdescription, ettitletext, etbudget;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_details, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        try {
            missionId = this.getArguments().getString("missionId");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        init(view);
        getPreference();
       // Toast.makeText(getActivity(), "" + missionId, Toast.LENGTH_LONG).show();
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myMissionViewDetail(missionId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private void init(View view) {
        etbudget = view.findViewById(R.id.etbudgetid);
        ettitletext = view.findViewById(R.id.ettitletextid);
        etdescription = view.findViewById(R.id.etdescriptionid);
        pbMymissionDetail = view.findViewById(R.id.pbMymissionDetailId);
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
                    // replaceFragement(new MyMissionDeliveryActivity());
                    // addFragment(new MyMissionDeliveryActivity(), false, Constants.MY_MISSION_LIVERY_ACTIVITY);

                } else if (onGoing.equalsIgnoreCase("MyReqPubliee")) {
                    removeThisFragment();
                    // replaceFragement(new MyDemandsPublishedTablayoutFragment());
                    addFragment(new MyDemandsPublishedTablayoutFragment(), false, Constants.MY_REQUEST_PUBLISHED_TABLAYOUT_FRAGMENT);

                } else if (onGoing.equalsIgnoreCase("MyReqEncours")) {
                    removeThisFragment();
                    // replaceFragement(new MyDemandsOngoingActivity());
                    //  addFragment(new MyDemandsOngoingActivity(), false, Constants.MY_REQUEST_ONGOING_FRAGMENT);

                } else if (onGoing.equalsIgnoreCase("MyReqLivree")) {
                    removeThisFragment();
                    //replaceFragement(new MyDemandsDeliveryActivity());
                    // addFragment(new MyDemandsDeliveryActivity(), false, Constants.MY_REQUEST_LIVERY_FRAGMENT);

                } else if (onGoing.equalsIgnoreCase("MyReqCompletee")) {
                    removeThisFragment();
                    // replaceFragement(new MyDemandsCompleteeActivity());
                    // addFragment(new MyDemandsCompleteeActivity(), false, Constants.MY_REQUEST_COMPLETE_FRAGMENT);

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


    private void myMissionViewDetail(String myMissionId) {
        pbMymissionDetail.setVisibility(View.VISIBLE);
        apiServices.myMisionViewDetail(myMissionId).enqueue(new Callback<MissionViewDetailModle>() {
            @Override
            public void onResponse(Call<MissionViewDetailModle> call, Response<MissionViewDetailModle> response) {
                if (response.isSuccessful()) {
                    pbMymissionDetail.setVisibility(View.GONE);
                    MissionViewDetailModle missionViewDetailModle = response.body();
                    if (missionViewDetailModle.getStatus() == true) {
                        yourMissionList = missionViewDetailModle.getYourMissions();
                        etdescription.setText(yourMissionList.get(0).getMissionDescription());
                        ettitletext.setText(yourMissionList.get(0).getCategoryTitle());
                        etbudget.setText(yourMissionList.get(0).getMissionBudget());
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbMymissionDetail.setVisibility(View.GONE);
                                String message = jsonObject.getString("message");
                                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MissionViewDetailModle> call, Throwable t) {
                pbMymissionDetail.setVisibility(View.GONE);
            }
        });

    }

}
