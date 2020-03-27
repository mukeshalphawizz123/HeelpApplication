package com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.liveryPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.HelpActivity;
import com.freelanceapp.R;
import com.freelanceapp.detailsPkg.DetailsActivity;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.completeePkg.myMissionCompleteModlePkg.MissionCompleteModle;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.liveryPkg.Adapter.LiveryAdapter;
import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMissionDeliveryActivity extends Fragment implements LiveryAdapter.LiveryAppOnClickListener, View.OnClickListener {

    private LiveryAdapter liveryAdapter;
    private RecyclerView rvLiveryfileupload;
    private ImageView ivmissionliverydashboardback, ivnotification;
    private RelativeLayout rlmissliveryviewdetails;
    private TextView tvviewprofile, tvmymissionliverytext;
    private String livree;
    private ApiServices apiServices;
    private ProgressBar pbMymissionDelivery;
    private AppCompatTextView tvUserNameMyMissionDelivery, tvCommentMyMissionDelivery;
    private CircleImageView ivUserMyMissionDelivery;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mission_livery, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myDelivery("12");
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }


    private void init(View view) {
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
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        rvLiveryfileupload.setLayoutManager(layoutManager);
        LiveryAdapter liveryAdapter = new LiveryAdapter(getActivity(), this);
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
        pbMymissionDelivery.setVisibility(View.VISIBLE);
        apiServices.myMissionProjectDeliery(myMissionId).enqueue(new Callback<MissionCompleteModle>() {
            @Override
            public void onResponse(Call<MissionCompleteModle> call, Response<MissionCompleteModle> response) {
                if (response.isSuccessful()) {
                    pbMymissionDelivery.setVisibility(View.GONE);
                    MissionCompleteModle missionCompleteModle = response.body();
                    if (missionCompleteModle.getStatus() == true) {
                        tvUserNameMyMissionDelivery.setText(missionCompleteModle.getData().get(0).getFirstName());
                        tvCommentMyMissionDelivery.setText(missionCompleteModle.getData().get(0).getYourComments());
                        Picasso.with(getActivity())
                                .load(RetrofitClient.MYMISSIONANDMYDEMANDE_IMAGE_URL + missionCompleteModle.getData()
                                        .get(0).getPictureUrl())
                                .into(ivUserMyMissionDelivery);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbMymissionDelivery.setVisibility(View.GONE);
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
                pbMymissionDelivery.setVisibility(View.GONE);
            }
        });

    }


}
