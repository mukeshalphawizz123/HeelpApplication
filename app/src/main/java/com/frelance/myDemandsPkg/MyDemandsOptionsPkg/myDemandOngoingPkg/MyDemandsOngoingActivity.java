package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.HelpActivity;
import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.Adapter.MyDemandOngoingAdapter;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.demandProgressModlePkg.Datum;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.demandProgressModlePkg.DemandOnProgressModle;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.FileDownloading;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDemandsOngoingActivity extends Fragment
        implements MyDemandOngoingAdapter.MyRequestOngoingAppOnClickListener,
        View.OnClickListener {

    private MyDemandOngoingAdapter myDemandOngoingAdapter;
    private RecyclerView rvmyreqongoingfileupload;
    private ImageView ivongoingreqdashboardback, ivnotification;
    private RelativeLayout rlreqongoingviewdetails, rlproblemstatement;
    private TextView tvviewprofile;
    private ProgressBar pbDemandProgress;
    private ApiServices apiServices;
    private List<Datum> datumList;
    private AppCompatTextView tvCommentDemandProg, tvUserNameDemandProg, tvMyDemandCount, tvDemandTitleRequest;
    private CircleImageView ivUserProgDemain;
    private String projectId, mission_demand_title;
    private ArrayList<String> filesList;
    FileDownloading fileDownloading;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_request_ongoing, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        fileDownloading = new FileDownloading(getActivity());
        filesList = new ArrayList<>();
        try {
            projectId = this.getArguments().getString("projectId");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        projectId = AppSession.getStringPreferences(getActivity(), "mission_id");
        mission_demand_title = AppSession.getStringPreferences(getActivity(), "mission_demand_title");

        //Toast.makeText(getActivity(), projectId, Toast.LENGTH_LONG).show();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myOnProgressApi(projectId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

        tvDemandTitleRequest.setText(mission_demand_title);

        return view;


    }

    private void init(View view) {
        tvMyDemandCount = view.findViewById(R.id.tvMyDemandCountId);
        tvDemandTitleRequest = view.findViewById(R.id.tvDemandTitleRequestId);
        tvUserNameDemandProg = view.findViewById(R.id.tvUserNameDemandProgId);
        ivUserProgDemain = view.findViewById(R.id.ivUserProgDemainId);
        tvCommentDemandProg = view.findViewById(R.id.tvCommentDemandProgId);
        pbDemandProgress = view.findViewById(R.id.pbDemandProgressId);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvmyreqongoingfileupload.setLayoutManager(layoutManager);
        myDemandOngoingAdapter = new MyDemandOngoingAdapter(getActivity(), this);
        rvmyreqongoingfileupload.setAdapter(myDemandOngoingAdapter);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivongoingreqdashboardbackId:
                removeThisFragment();
                //  replaceFragementWithoutStack(new MyDemandFragment());
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
        Bundle bundle = new Bundle();
        bundle.putString("missionId", projectId);
        fragment.setArguments(bundle);
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

    private void myOnProgressApi(String projectId) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.demandPorgress(projectId).enqueue(new Callback<DemandOnProgressModle>() {
            @Override
            public void onResponse(Call<DemandOnProgressModle> call, Response<DemandOnProgressModle> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        DemandOnProgressModle requestlist = response.body();
                        if (requestlist.getStatus()) {
                            tvMyDemandCount.setVisibility(View.VISIBLE);
                            datumList = requestlist.getData();
                            myDemandOngoingAdapter.addOnGoingDemandsFiles(datumList);
                            if (datumList.size() > 1) {
                                tvMyDemandCount.setText(datumList.size() + "Availables");
                            } else {
                                tvMyDemandCount.setText(datumList.size() + "Available");
                            }
                        } else {
                            tvMyDemandCount.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            tvMyDemandCount.setVisibility(View.GONE);
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
            public void onFailure(Call<DemandOnProgressModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
                tvMyDemandCount.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void myDemandOnGoingOnClick(View view, int position) {
      /*  switch (view.getId()) {
            case R.id.rlFileFolderId:
              //  fileDownloading.DownloadImage(RetrofitClient.DOWNLOAD_URL + filesList.get(position));
                break;
        }*/
    }
}
