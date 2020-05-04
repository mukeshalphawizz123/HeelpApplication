package com.frelance.myMissionPkg.FragmentPkg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.databinding.ActivityMymissionsBinding;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg.MyMissionCompleteActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.liveryPkg.MyMissionDeliveryActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.MyMissionInDisputeActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.MyMissionOngoingActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.proposePkg.MyMissionProposeeActivity;
import com.frelance.myMissionPkg.MymissionAdapter.MyMissionAdapter;
import com.frelance.myMissionPkg.MymissionAdapter.MyMissionsecAdapter;
import com.frelance.myMissionPkg.Mymissionmodle.mymissionModle;
import com.frelance.myMissionPkg.myMissionModlePkg.MyMissionModel;
import com.frelance.myMissionPkg.myMissionModlePkg.YourMission;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMissionFragment extends Fragment implements MyMissionsecAdapter.MyMissionAppOnClickListener
        , MyMissionAdapter.MyMissionAppOnClickListener
        , View.OnClickListener {
    private ArrayList<mymissionModle> mymissionModelArrayList;
    private ActivityMymissionsBinding fragmentHomePublisharequestBinding;
    private MyMissionAdapter myMissionAdapter;
    private RecyclerView rvhorizontaldata, rvmymissionFrag;
    private ImageView ivnotification;
    private TextView Tvmymissionitemnot;
    private String filterTag;
    private ApiServices apiServices;
    private ProgressBar PbMymission;
    public MyMissionsecAdapter myMissionsecAdapter;
    private List<YourMission> mymissionlist;
    private String position, filtertag;
    private String userId;
    private SwipeRefreshLayout sflMyMission;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomePublisharequestBinding = DataBindingUtil.inflate(inflater, R.layout.activity_mymissions, container, false);
        //  View view = inflater.inflate(R.layout.fragment_home_publisharequest, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        View view = fragmentHomePublisharequestBinding.getRoot();
        mymissionModelArrayList = new ArrayList<>();
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        mymissionModelArrayList.add(new mymissionModle(getResources().getString(R.string.Proposée)));
        mymissionModelArrayList.add(new mymissionModle(getResources().getString(R.string.Encours)));
        mymissionModelArrayList.add(new mymissionModle(getResources().getString(R.string.Livrée)));
        mymissionModelArrayList.add(new mymissionModle(getResources().getString(R.string.Completée)));
        mymissionModelArrayList.add(new mymissionModle(getResources().getString(R.string.Enlitige)));
     /*   position =  AppSession.getStringPreferences(getActivity(),"position");
        filtertag = AppSession.getStringPreferences(getActivity(),"filtertag");*/
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myMission("0");
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

       /* sflMyMission.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    myMission("0");
                } else {
                    Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }

                sflMyMission.setRefreshing(false);
            }
        });*/
        return view;
    }


    private void init(View view) {
        // sflMyMission = view.findViewById(R.id.sflMyMissionId);
        Tvmymissionitemnot = view.findViewById(R.id.TvmymissionitemnotId);
        PbMymission = view.findViewById(R.id.PbMymissionId);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        rvhorizontaldata = view.findViewById(R.id.rvhorizontaldataid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvhorizontaldata.setLayoutManager(layoutManager);
        myMissionAdapter = new MyMissionAdapter(getActivity(), this);
        rvhorizontaldata.setAdapter(myMissionAdapter);
        myMissionAdapter.addmymissionData(mymissionModelArrayList);
        rvmymissionFrag = view.findViewById(R.id.rvmymissionFragId);
        filterTag = "Proposed";
        myMissionSetup();
    }


    private void myMission(String status) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.mymission(status, userId).enqueue(new Callback<MyMissionModel>() {
            @Override
            public void onResponse(Call<MyMissionModel> call, Response<MyMissionModel> response) {
                if (response.isSuccessful()) {

                    CustomProgressbar.hideProgressBar();
                    MyMissionModel missionlist = response.body();
                    if (missionlist.getStatus()) {
                        Tvmymissionitemnot.setVisibility(View.GONE);
                        mymissionlist = missionlist.getYourMissions();
                        myMissionsecAdapter.mymissionlist(mymissionlist);

                    } else
                        Tvmymissionitemnot.setVisibility(View.VISIBLE);

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyMissionModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    public void onClick(View view, int position) {
    }

    @Override
    public void myMissionOnClick(View view, int position, String text, YourMission yourMission) {
        switch (view.getId()) {
            case R.id.rlmymissionid:
                if (text.equals(getResources().getString(R.string.Proposée))) {
                    addFragment(new MyMissionProposeeActivity(), true, Constants.MY_MISSION_PROPOSEE_ACTIVITY, yourMission.getMissionId());
                    AppSession.setStringPreferences(getActivity(), "mission_mission_title", yourMission.getMission_title());
                } else if (text.equals(getResources().getString(R.string.Encours))) {
                    addFragment(new MyMissionOngoingActivity(), true, Constants.MY_MISSION_ONGOING_ACTIVITY, yourMission.getMissionId());
                    AppSession.setStringPreferences(getActivity(), "mission_mission_title", yourMission.getMission_title());
                } else if (text.equals(getResources().getString(R.string.Livrée))) {
                    addFragment(new MyMissionDeliveryActivity(), true, Constants.MY_MISSION_LIVERY_ACTIVITY, yourMission.getMissionId());
                    AppSession.setStringPreferences(getActivity(), "mission_mission_title", yourMission.getMission_title());
                } else if (text.equals(getResources().getString(R.string.Completée))) {
                    addFragment(new MyMissionCompleteActivity(), true, Constants.MY_MISSION_COMPLETE_ACTIVITY, yourMission.getMissionId());
                    AppSession.setStringPreferences(getActivity(), "mission_mission_title", yourMission.getMission_title());
                } else if (text.equals(getResources().getString(R.string.Enlitige))) {
                    addFragment(new MyMissionInDisputeActivity(), true, Constants.MY_MISSION_DISPUTE_ACTIVITY, yourMission.getMissionId());
                    AppSession.setStringPreferences(getActivity(), "mission_mission_title", yourMission.getMission_title());
                    AppSession.setStringPreferences(getActivity(), "msg", "");
                }
                break;

        }
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag, String missionId) {
        Bundle bundle = new Bundle();
        bundle.putString("missionId", missionId);
        fragment.setArguments(bundle);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.flHomeId, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;

        }
    }

    @Override
    public void myMissionTabClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlmissionid:
                filterTag = mymissionModelArrayList.get(position).getName();
                myMissionSetup();
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    myMission("" + position);
                } else {
                    Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void myMissionSetup() {
        LinearLayoutManager layoutManagersec = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvmymissionFrag.setLayoutManager(layoutManagersec);
        myMissionsecAdapter = new MyMissionsecAdapter(getActivity(), this, filterTag);
        rvmymissionFrag.setAdapter(myMissionsecAdapter);
        myMissionsecAdapter.notifyDataSetChanged();

    }
}
