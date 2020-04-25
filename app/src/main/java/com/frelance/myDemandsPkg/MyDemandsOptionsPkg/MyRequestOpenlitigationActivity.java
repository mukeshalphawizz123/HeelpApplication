package com.frelance.myDemandsPkg.MyDemandsOptionsPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.MyMissionadapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.Datum;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.GetAllDiputeResponseModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.SendDiputeResponseModle;
import com.frelance.notificationPkg.NotificationActivity;
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

public class MyRequestOpenlitigationActivity extends Fragment implements View.OnClickListener, MyMissionadapter.MyMissionDisputeOnClickListener {
    private RelativeLayout rlreqfeedbackviewdetails;
    private TextView tvviewprofile;
    private ImageView ivtextfeedbackreqdashboardback, ivnotification;
    private ApiServices apiServices;
    private AppCompatImageView ivgifbutton;
    private String missionId, userId, mission_demand_title;
    private AppCompatEditText ettypemsg;
    private RecyclerView rvMyMissionDispute;
    private List<Datum> datumList;
    private MyMissionadapter myMissionadapter;
    private Handler handler = new Handler();
    private int apiDelayed = 5 * 1000; //1 second=1000 milisecond, 5*1000=5seconds
    private Runnable runnable;
    private AppCompatTextView tvDemandTitleRequest;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_request_openlitigation, container, false);
        missionId = this.getArguments().getString("projectId");
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        mission_demand_title = AppSession.getStringPreferences(getActivity(), "mission_demand_title");
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        // Toast.makeText(getActivity(),missionId,Toast.LENGTH_LONG).show();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myDemandDispute(userId);
        } else {

        }
        tvDemandTitleRequest.setText(mission_demand_title);
        return view;
    }

    private void init(View view) {

        tvDemandTitleRequest = view.findViewById(R.id.tvDemandTitleRequestId);
        ivgifbutton = view.findViewById(R.id.ivgifbuttonid);
        rvMyMissionDispute = view.findViewById(R.id.rvMyMissionDisputeId);
        ettypemsg = view.findViewById(R.id.ettypemsgid);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        rlreqfeedbackviewdetails = view.findViewById(R.id.rlreqfeedbackviewdetailsid);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);
        ivtextfeedbackreqdashboardback = view.findViewById(R.id.ivtextfeedbackreqdashboardbackId);
        ivtextfeedbackreqdashboardback.setOnClickListener(this);
        rlreqfeedbackviewdetails.setOnClickListener(this);
        ivgifbutton.setOnClickListener(this);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvMyMissionDispute.setLayoutManager(layoutManager);

        myMissionadapter = new MyMissionadapter(getActivity(), this);
        rvMyMissionDispute.setAdapter(myMissionadapter);


    }

    @Override
    public void onResume() {
        super.onResume();

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do your function;
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    myDemandDispute(userId);
                } else {

                }
                handler.postDelayed(runnable, apiDelayed);
            }
        }, apiDelayed); // so basically after your getHeroes(), from next time it will be 5 sec repeated
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivgifbuttonid:
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    if (TextUtils.isEmpty(ettypemsg.getText().toString())) {
                    } else {
                        sendDispute();
                    }
                } else {
                    Toast.makeText(getActivity(), "Check network connection", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.ivtextfeedbackreqdashboardbackId:
                removeThisFragment();
                //replaceFragementWithoutStack(new MyDemandFragment());
                break;
            case R.id.rlreqfeedbackviewdetailsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;

        }
    }

    private void replaceFragement(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("missionId", missionId);
        fragment.setArguments(bundle);
        AppSession.setStringPreferences(getActivity(), "OnGoing", "MyReqEnlitige");
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

    private void myDemandDispute(String userId) {
        //  PbMymission.setVisibility(View.VISIBLE);
        apiServices.getprojectdispute(userId).enqueue(new Callback<GetAllDiputeResponseModle>() {
            @Override
            public void onResponse(Call<GetAllDiputeResponseModle> call, Response<GetAllDiputeResponseModle> response) {
                if (response.isSuccessful()) {
                    try {
                        GetAllDiputeResponseModle getAllDiputeResponseModle = response.body();
                        if (getAllDiputeResponseModle.getStatus()) {
                            datumList = getAllDiputeResponseModle.getData();
                            myMissionadapter.addDisputeList(datumList);
                            rvMyMissionDispute.smoothScrollToPosition(myMissionadapter.getItemCount() - 1);

                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //  Tvmymissionitemnot.setVisibility(View.VISIBLE);

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
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
            public void onFailure(Call<GetAllDiputeResponseModle> call, Throwable t) {

            }
        });

    }

    private void sendDispute() {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.projectsenddispute(missionId, ettypemsg.getText().toString(), userId).enqueue(new Callback<SendDiputeResponseModle>() {
            @Override
            public void onResponse(Call<SendDiputeResponseModle> call, Response<SendDiputeResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    SendDiputeResponseModle missionlist = response.body();
                    if (missionlist.getStatus()) {
                        ettypemsg.setText("");
                    } else {

                    }

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
            public void onFailure(Call<SendDiputeResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }
}
