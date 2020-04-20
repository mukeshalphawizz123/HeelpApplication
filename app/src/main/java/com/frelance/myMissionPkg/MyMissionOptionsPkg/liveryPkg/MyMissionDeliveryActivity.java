package com.frelance.myMissionPkg.MyMissionOptionsPkg.liveryPkg;

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
import com.frelance.HelpActivity;
import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg.myMissionCompleteModlePkg.MissionCompleteModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.liveryPkg.Adapter.LiveryAdapter;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.FileDownloading;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMissionDeliveryActivity extends Fragment implements LiveryAdapter.LiveryAppOnClickListener,
        View.OnClickListener {

    private LiveryAdapter liveryAdapter;
    private RecyclerView rvLiveryfileupload;
    private ImageView ivmissionliverydashboardback, ivnotification;
    private RelativeLayout rlmissliveryviewdetails;
    private TextView tvviewprofile, tvmymissionliverytext;
    private String livree, missionId, userId;
    private ApiServices apiServices;
    private ProgressBar pbMymissionDelivery;
    private AppCompatTextView tvUserNameMyMissionDelivery, tvCommentMyMissionDelivery, tvMyMissTitle;
    private CircleImageView ivUserMyMissionDelivery;
    private ArrayList<String> filesList;
    private FileDownloading fileDownloading;
    private String mission_mission_title;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mission_livery, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        fileDownloading = new FileDownloading(getActivity());
        missionId = this.getArguments().getString("missionId");
        mission_mission_title = AppSession.getStringPreferences(getActivity(), "mission_mission_title");
        filesList = new ArrayList<>();
        // Toast.makeText(getActivity(), missionId, Toast.LENGTH_LONG).show();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myDelivery(missionId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

        tvMyMissTitle.setText(mission_mission_title);
        return view;


    }


    private void init(View view) {
        tvMyMissTitle = view.findViewById(R.id.tvMyMissTitleId);
        pbMymissionDelivery = view.findViewById(R.id.pbMymissionDeliveryId);
        tvmymissionliverytext = view.findViewById(R.id.tvmymissionliverytextid);
        tvCommentMyMissionDelivery = view.findViewById(R.id.tvCommentMyMissionDeliveryId);
        ivUserMyMissionDelivery = view.findViewById(R.id.ivUserMyMissionDeliveryId);
        tvUserNameMyMissionDelivery = view.findViewById(R.id.tvUserNameMyMissionDeliveryId);
        tvmymissionliverytext.setOnClickListener(this);

        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);

        ivmissionliverydashboardback = view.findViewById(R.id.ivmissionliverydashboardbackId);
        ivmissionliverydashboardback.setOnClickListener(this);

        rlmissliveryviewdetails = view.findViewById(R.id.rlmissliveryviewdetailsid);
        rlmissliveryviewdetails.setOnClickListener(this);

        rvLiveryfileupload = view.findViewById(R.id.rvLiveryfileuploadid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvLiveryfileupload.setLayoutManager(layoutManager);
        liveryAdapter = new LiveryAdapter(getActivity(), this);
        rvLiveryfileupload.setAdapter(liveryAdapter);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);

        SpannableString content1 = new SpannableString(getResources().getString(R.string.report_a_problem));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvmymissionliverytext.setText(content1);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivmissionliverydashboardbackId:
                removeThisFragment();
                // replaceWithoutAddToStack(new MyMissionFragment());
                break;
            case R.id.rlmissliveryviewdetailsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.tvmymissionliverytextid:
                replaceFragement(new HelpActivity());
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
        AppSession.setStringPreferences(getActivity(), "OnGoing", "Livree");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void replaceWithoutAddToStack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
    }


    public void removeThisFragment() {
        final FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();

        // Toast.makeText(this, ""+manager, Toast.LENGTH_SHORT).show();
    }

    private void myDelivery(String myMissionId) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.myMissionProjectDeliery(myMissionId).enqueue(new Callback<MissionCompleteModle>() {
            @Override
            public void onResponse(Call<MissionCompleteModle> call, Response<MissionCompleteModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    MissionCompleteModle missionCompleteModle = response.body();
                    if (missionCompleteModle.getStatus()) {
                        tvUserNameMyMissionDelivery.setText(missionCompleteModle.getData().get(0).getFirstName());
                        tvCommentMyMissionDelivery.setText(missionCompleteModle.getData().get(0).getYourComments());
                        if (missionCompleteModle.getData().get(0).getPictureUrl().isEmpty()) {
                        } else {
                            Picasso.with(getActivity())
                                    .load(RetrofitClient.MISSION_USER_IMAGE_URL
                                            + missionCompleteModle.getData()
                                            .get(0).getPictureUrl())
                                    .into(ivUserMyMissionDelivery);
                        }

                        if (missionCompleteModle.getData().get(0).getProject_image().isEmpty()) {

                        } else {
                            String[] imgesArray = missionCompleteModle.getData().get(0).getProject_image().split(",");
                            for (int i = 0; i < imgesArray.length; i++) {
                                filesList.add(imgesArray[i]);
                            }
                        }

                        if (missionCompleteModle.getData().get(0).getProjectFiles().isEmpty()) {

                        } else {
                            String[] filesArray = missionCompleteModle.getData().get(0).getProjectFiles().split(",");
                            for (int i = 0; i < filesArray.length; i++) {
                                filesList.add(filesArray[i]);
                            }
                        }

                        liveryAdapter.addDetailFiles(filesList);
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
            public void onFailure(Call<MissionCompleteModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }


    @Override
    public void myMissDeliveryOnClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlFileFolderId:
                fileDownloading.DownloadImage(RetrofitClient.DOWNLOAD_URL + filesList.get(position));
                break;
        }
    }
}
