package com.frelance.myMissionPkg.MyMissionOptionsPkg.proposePkg;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.chatPkg.ChatActivity;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.proposePkg.Adapter.ProposeAdapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.proposePkg.myMissionProposedModlePkg.MyMissionProposedModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.proposePkg.myMissionProposedModlePkg.YourMission;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.notificationPkg.NotificationCountResponseModle;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMissionProposeeActivity extends Fragment implements ProposeAdapter.ProposeAppOnClickListener, View.OnClickListener {
    private ProposeAdapter proposeAdapter;
    private RecyclerView rvproposee;
    private ImageView ivmissionproposedashboardback, ivnotification;
    private RelativeLayout rlmissproposeviewdetails, RlMyMissionproposeDiscussbtn;
    private TextView tvviewprofile;
    private ProgressBar PbMymissionProposed;
    private ApiServices apiServices;
    private List<YourMission> yourMissionList;
    String missionId, mission_mission_title;
    private AppCompatTextView tvMyMissTitle, tvHomeNotificationCount;
    private String userId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mission_proposee, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        missionId = this.getArguments().getString("missionId");
        mission_mission_title = AppSession.getStringPreferences(getActivity(), "mission_mission_title");
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
       //  Toast.makeText(getActivity(), missionId, Toast.LENGTH_LONG).show();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myMission(missionId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

        if (CheckNetwork.isNetAvailable(getActivity())) {
            notification(userId);
        } else {

        }
        tvMyMissTitle.setText(mission_mission_title);

        return view;
    }

    private void init(View view) {
        tvHomeNotificationCount = view.findViewById(R.id.tvHomeNotificationCountId);
        tvMyMissTitle = view.findViewById(R.id.tvMyMissTitleId);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);
        ivmissionproposedashboardback = view.findViewById(R.id.ivmissionproposedashboardbackId);
        ivmissionproposedashboardback.setOnClickListener(this);
        rlmissproposeviewdetails = view.findViewById(R.id.rlmissproposeviewdetailsid);
        rlmissproposeviewdetails.setOnClickListener(this);
        rvproposee = view.findViewById(R.id.rvproposeeid);
        PbMymissionProposed = view.findViewById(R.id.PbMymissionProposedId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvproposee.setLayoutManager(layoutManager);
        proposeAdapter = new ProposeAdapter(getActivity(), this);
        rvproposee.setAdapter(proposeAdapter);
        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivmissionproposedashboardbackId:
                removeThisFragment();
                // addFragmentback(new MyMissionFragment(), false, Constants.MY_MISSION_FRAGMENT);
                break;
            case R.id.rlmissproposeviewdetailsid:
                AppSession.setStringPreferences(getActivity(), "OnGoing", "Proposee");
                addFragment(new DetailsActivity(), true, Constants.DETAILS_ACTIVITY);
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
        }

    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "Proposee");
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void removeThisFragment() {
        final FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();
    }

    private void replaceFragementWithoutStack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void mymissionpropose(View view, int position, YourMission yourMission) {
        switch (view.getId()) {
            case R.id.rlMyMissionDiscusseDelId:
                AppSession.setStringPreferences(getActivity(), "chatEntrty", "");
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("client_id", yourMission.getClient_id());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }

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

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
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

    public void addFragmentback(Fragment fragment, boolean addToBackStack,
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


    private void myMission(String myMissionId) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.myMissionbidbyProposed(myMissionId).enqueue(new Callback<MyMissionProposedModle>() {
            @Override
            public void onResponse(Call<MyMissionProposedModle> call, Response<MyMissionProposedModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    MyMissionProposedModle myMissionProposedModle = response.body();
                    if (myMissionProposedModle.getStatus()) {
                        yourMissionList = myMissionProposedModle.getYourMissions();
                        proposeAdapter.addmymissionDataByProposed(yourMissionList);
                    } else
                        PbMymissionProposed.setVisibility(View.GONE);

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
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
            public void onFailure(Call<MyMissionProposedModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }


    private void notification(String userId) {
        //    CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.getnotificationcount(userId).enqueue(new Callback<NotificationCountResponseModle>() {
            @Override
            public void onResponse(Call<NotificationCountResponseModle> call, Response<NotificationCountResponseModle> response) {
                if (response.isSuccessful()) {
                    // CustomProgressbar.hideProgressBar();
                    NotificationCountResponseModle notificationResponseModle = response.body();
                    if (notificationResponseModle.getStatus()) {
                        int messageCount = notificationResponseModle.getCountMessages();
                        int messageDemands = notificationResponseModle.getCountMissionanddemands();
                        int messageOffers = notificationResponseModle.getCountOffers();
                        int messageCountPayment = notificationResponseModle.getCountPayment();
                        int messageCountReveiews = notificationResponseModle.getCountReviews();

                        String totalNotification = String.valueOf(messageCount
                                + messageOffers
                                + messageDemands
                                + messageCountPayment
                                + messageCountReveiews);

                        if (totalNotification == null || totalNotification.isEmpty()) {
                            tvHomeNotificationCount.setVisibility(View.GONE);
                        } else {
                            tvHomeNotificationCount.setText(totalNotification);
                            tvHomeNotificationCount.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvHomeNotificationCount.setVisibility(View.GONE);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!false) {
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
            public void onFailure(Call<NotificationCountResponseModle> call, Throwable t) {
                // CustomProgressbar.hideProgressBar();
                tvHomeNotificationCount.setVisibility(View.GONE);
            }
        });

    }
}
