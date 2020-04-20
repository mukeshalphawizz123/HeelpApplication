package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsCompletePkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsCompletePkg.Adapter.MyDemandsCompleteAdapter;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsCompletePkg.demandCompleteModlePkg.Datum;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsCompletePkg.demandCompleteModlePkg.DemandCompleteModle;
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

public class MyDemandsCompleteeActivity extends Fragment implements
        MyDemandsCompleteAdapter.MyRequestCompleteAppOnClickListener,
        View.OnClickListener {


    private MyDemandsCompleteAdapter myRequestCompleteAdapter;
    private RecyclerView rvmyreqcompletefileupload;
    private ImageView ivdashboardcompleteback, ivnotification;
    private RelativeLayout rlreqcompleteviewdetils;
    private TextView tvviewprofile;
    private ApiServices apiServices;
    private ProgressBar pbDemandComplete;
    private List<Datum> datumList;
    private AppCompatTextView tvUserDemandComp, tvCommentDemandCompl,tvDemandTitleRequest;
    private CircleImageView ivUserDemandComp;
    private String projectId,mission_demand_title;
    private ArrayList<String> filesList;
    FileDownloading fileDownloading;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_request_completee, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        fileDownloading = new FileDownloading(getActivity());
        projectId = this.getArguments().getString("projectId");
        mission_demand_title = AppSession.getStringPreferences(getActivity(), "mission_demand_title");
        filesList = new ArrayList<>();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myOnCompleteApi(projectId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

        tvDemandTitleRequest.setText(mission_demand_title);

        return view;


    }

    private void init(View view) {
        tvDemandTitleRequest = view.findViewById(R.id.tvDemandTitleRequestId);
        tvUserDemandComp = view.findViewById(R.id.tvUserDemandCompId);
        tvCommentDemandCompl = view.findViewById(R.id.tvCommentDemandComplId);
        ivUserDemandComp = view.findViewById(R.id.ivUserDemandCompId);

        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);

        pbDemandComplete = view.findViewById(R.id.pbDemandCompleteId);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);

        ivdashboardcompleteback = view.findViewById(R.id.ivdashboardcompletebackId);
        ivdashboardcompleteback.setOnClickListener(this);

        rlreqcompleteviewdetils = view.findViewById(R.id.rlreqcompleteviewdetilsid);
        rlreqcompleteviewdetils.setOnClickListener(this);

        rvmyreqcompletefileupload = view.findViewById(R.id.rvmyreqcompletefileuploadid);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvmyreqcompletefileupload.setLayoutManager(layoutManager);
        myRequestCompleteAdapter = new MyDemandsCompleteAdapter(getActivity(), this);
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
                // replaceFragementWithoutStack(new MyDemandFragment());
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
        Bundle bundle = new Bundle();
        bundle.putString("missionId", projectId);
        fragment.setArguments(bundle);
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

    private void myOnCompleteApi(String projectId) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.demandomplete(projectId).enqueue(new Callback<DemandCompleteModle>() {
            @Override
            public void onResponse(Call<DemandCompleteModle> call, Response<DemandCompleteModle> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        DemandCompleteModle requestlist = response.body();
                        if (requestlist.getStatus()) {
                            datumList = requestlist.getData();
                            tvUserDemandComp.setText(datumList.get(0).getFirstName());
                            tvCommentDemandCompl.setText(datumList.get(0).getYourComments());

                            if (datumList.get(0).getPictureUrl().isEmpty()) {
                            } else {
                                Picasso.with(getActivity()).load(RetrofitClient.MISSION_USER_IMAGE_URL + datumList.get(0).getPictureUrl()).into(ivUserDemandComp);
                            }

                            if (datumList.get(0).getProjectImage().isEmpty()) {

                            } else {
                                String[] imgesArray = datumList.get(0).getProjectImage().split(",");
                                for (int i = 0; i < imgesArray.length; i++) {
                                    filesList.add(imgesArray[i]);
                                }
                            }

                            if (datumList.get(0).getProjectFiles().isEmpty()) {

                            } else {
                                String[] filesArray = datumList.get(0).getProjectFiles().split(",");
                                for (int i = 0; i < filesArray.length; i++) {
                                    filesList.add(filesArray[i]);
                                }
                            }

                            myRequestCompleteAdapter.addCompletedDemandsFiles(filesList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
            public void onFailure(Call<DemandCompleteModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    @Override
    public void myDemandCompleteOnClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlFileFolderId:
                fileDownloading.DownloadImage(RetrofitClient.DOWNLOAD_URL + filesList.get(position));
                break;
        }
    }
}
