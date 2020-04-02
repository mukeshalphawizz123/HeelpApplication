package com.frelance.myDemandsPkg.FragmentPkg;


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

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.databinding.ActivityMyrequestBinding;
import com.frelance.myDemandsPkg.MyRequestAdapter.MyRequestAdapter;
import com.frelance.myDemandsPkg.MyRequestAdapter.MyRequestsecAdapter;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.MyRequestOpenlitigationActivity;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsCompletePkg.MyDemandsCompleteeActivity;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.MyDemandsDeliveryActivity;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.MyDemandsOngoingActivity;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.MyDemandsPublishedTablayoutFragment;
import com.frelance.myDemandsPkg.Myrequestmodle.myrequestModle;
import com.frelance.myDemandsPkg.myRequestModlePkg.Datum;
import com.frelance.myDemandsPkg.myRequestModlePkg.MyDemandeModel;
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

public class MyDemandFragment extends Fragment implements MyRequestsecAdapter.MyRequestAppOnClickListener
        , MyRequestAdapter.MyRequestAppOnClickListener,
        View.OnClickListener {

    private ArrayList<myrequestModle> myrequestModleArrayList;

    private ActivityMyrequestBinding activityMyrequestBinding;
    private MyRequestAdapter myRequestAdapter;
    private RecyclerView rvrequesthorizontaldata, rvmyrequestsFrag;
    private ImageView ivnotification;
    private TextView Tvmyrequestitemnot;
    private String filterTag;
    private ProgressBar PbMyrequest;
    private ApiServices apiServices;
    public MyRequestsecAdapter myRequestsecAdapter;
    private List<Datum> myrequestlist;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityMyrequestBinding = DataBindingUtil.inflate(inflater, R.layout.activity_myrequest, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        View view = activityMyrequestBinding.getRoot();
        myrequestModleArrayList = new ArrayList<>();
        myrequestModleArrayList.add(new myrequestModle(getResources().getString(R.string.Publiée)));
        myrequestModleArrayList.add(new myrequestModle(getResources().getString(R.string.Encours)));
        myrequestModleArrayList.add(new myrequestModle(getResources().getString(R.string.Livrée)));
        myrequestModleArrayList.add(new myrequestModle(getResources().getString(R.string.Completée)));
        myrequestModleArrayList.add(new myrequestModle(getResources().getString(R.string.Enlitige)));
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            MyRequest("0");
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }


    private void init(View view) {
        Tvmyrequestitemnot = view.findViewById(R.id.TvmyrequestitemnotId);
        PbMyrequest = view.findViewById(R.id.PbMyrequestId);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        rvmyrequestsFrag = view.findViewById(R.id.rvmyrequestsFragId);
        ivnotification.setOnClickListener(this);
        rvrequesthorizontaldata = view.findViewById(R.id.rvrequesthorizontaldataid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvrequesthorizontaldata.setLayoutManager(layoutManager);
        MyRequestAdapter myRequestAdapter = new MyRequestAdapter(getActivity(), this);
        rvrequesthorizontaldata.setAdapter(myRequestAdapter);
        myRequestAdapter.addmyrequestData(myrequestModleArrayList);
        filterTag = "Posted";
        myRequestSetup();
        //myMissionAdapter.addmymissionData(mymissionModelArrayList);

    }

    private void MyRequest(String status) {
        PbMyrequest.setVisibility(View.VISIBLE);
        apiServices.myrequest(status).enqueue(new Callback<MyDemandeModel>() {
            @Override
            public void onResponse(Call<MyDemandeModel> call, Response<MyDemandeModel> response) {
                if (response.isSuccessful()) {
                    PbMyrequest.setVisibility(View.GONE);
                    MyDemandeModel requestlist = response.body();
                    if (requestlist.getStatus() == true) {
                        Tvmyrequestitemnot.setVisibility(View.GONE);
                        myrequestlist = requestlist.getData();
                        myRequestsecAdapter.myrequestlist(myrequestlist);
                    } else
                        Tvmyrequestitemnot.setVisibility(View.VISIBLE);

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                PbMyrequest.setVisibility(View.GONE);
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
            public void onFailure(Call<MyDemandeModel> call, Throwable t) {
                PbMyrequest.setVisibility(View.GONE);
            }
        });
    }

    public void onClick(View view, int position) {
    }

    @Override
    public void myRequestOnClick(View view, int position, String text, Datum datum) {
        switch (view.getId()) {
            case R.id.rlmyrequestid:
                if (text.equals(getResources().getString(R.string.Publiée))) {
                    addFragment(new MyDemandsPublishedTablayoutFragment(), true, Constants.MY_REQUEST_PUBLISHED_TABLAYOUT_FRAGMENT, datum.getId());
                } else if (text.equals(getResources().getString(R.string.Encours))) {
                    addFragment(new MyDemandsOngoingActivity(), true, Constants.MY_REQUEST_ONGOING_FRAGMENT, datum.getId());
                    AppSession.setStringPreferences(getActivity(), "mission_id", datum.getId());
                } else if (text.equals(getResources().getString(R.string.Livrée))) {
                    addFragment(new MyDemandsDeliveryActivity(), true, Constants.MY_REQUEST_LIVERY_FRAGMENT, datum.getId());
                    AppSession.setStringPreferences(getActivity(), "clientId", datum.getClient_id());
                } else if (text.equals(getResources().getString(R.string.Completée))) {
                    addFragment(new MyDemandsCompleteeActivity(), true, Constants.MY_REQUEST_COMPLETE_FRAGMENT, datum.getId());
                } else if (text.equals(getResources().getString(R.string.Enlitige))) {
                    addFragment(new MyRequestOpenlitigationActivity(), true, Constants.MY_REQUEST_OPENLITIGATION_FRAGMENT, datum.getId());
                }
                break;
        }
    }


    public void addFragment(Fragment fragment, boolean addToBackStack, String tag, String projectId) {
        Bundle bundle = new Bundle();
        bundle.putString("projectId", projectId);
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

    private void myRequestSetup() {
        LinearLayoutManager layoutManagersec = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvmyrequestsFrag.setLayoutManager(layoutManagersec);
        myRequestsecAdapter = new MyRequestsecAdapter(getActivity(), this, filterTag);
        rvmyrequestsFrag.setAdapter(myRequestsecAdapter);

    }

    @Override
    public void myRequestTabClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlMyRequestRowId:
                filterTag = myrequestModleArrayList.get(position).getName();
                myRequestSetup();
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    MyRequest("" + position);
                } else {
                    Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }
}
