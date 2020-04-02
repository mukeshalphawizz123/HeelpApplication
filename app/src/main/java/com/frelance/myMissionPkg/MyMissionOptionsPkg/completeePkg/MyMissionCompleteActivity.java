package com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg;

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

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg.Adapter.CompleteeFileUploadAdapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg.myMissionCompleteModlePkg.MissionCompleteModle;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMissionCompleteActivity extends Fragment implements CompleteeFileUploadAdapter.CompleteeFileUploadAppOnClickListener, View.OnClickListener {
    private CompleteeFileUploadAdapter completeeFileUploadAdapter;
    private RecyclerView rvfileupload;
    private ImageView ivmissioncompleterdashboardback, ivnotification;
    private RelativeLayout rlmisscompleteviewdetails, rldummyimgid;
    private TextView tvviewprofile;
    private ApiServices apiServices;
    private ProgressBar pbMymissionComplete;
    private AppCompatTextView tvCommentValueMyMisssion, tvUserNameMyMisssion;
    private CircleImageView ivUserImgMyMision;
    private String missionId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mission_complete, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        missionId = this.getArguments().getString("missionId");
        //Toast.makeText(getActivity(), missionId, Toast.LENGTH_LONG).show();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myCompleted(missionId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private void init(View view) {
        pbMymissionComplete = view.findViewById(R.id.pbMymissionCompleteId);
        tvCommentValueMyMisssion = view.findViewById(R.id.tvCommentValueMyMisssionId);
        tvUserNameMyMisssion = view.findViewById(R.id.tvUserNameMyMisssionId);
        ivUserImgMyMision = view.findViewById(R.id.ivUserImgMyMisionId);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);

        ivmissioncompleterdashboardback = view.findViewById(R.id.ivmissioncompleterdashboardbackId);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);
        rldummyimgid = view.findViewById(R.id.rldummyimgid);
        rlmisscompleteviewdetails = view.findViewById(R.id.rlmisscompleteviewdetailsId);

        ivmissioncompleterdashboardback.setOnClickListener(this);
        rlmisscompleteviewdetails.setOnClickListener(this);
        rldummyimgid.setOnClickListener(this);

        rvfileupload = view.findViewById(R.id.rvfileuploadid);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        rvfileupload.setLayoutManager(layoutManager);
        CompleteeFileUploadAdapter completeeFileUploadAdapter = new CompleteeFileUploadAdapter(getActivity(), this);
        rvfileupload.setAdapter(completeeFileUploadAdapter);


        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rldummyimgid:
                break;
            case R.id.ivmissioncompleterdashboardbackId:
                removeThisFragment();
                // replaceFragementWithoutStack(new MyMissionFragment());
                break;
            case R.id.rlmisscompleteviewdetailsId:
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
        AppSession.setStringPreferences(getActivity(), "OnGoing", "Complete");
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


    private void myCompleted(String myMissionId) {
        pbMymissionComplete.setVisibility(View.VISIBLE);
        apiServices.myMissionProjectComplete(myMissionId).enqueue(new Callback<MissionCompleteModle>() {
            @Override
            public void onResponse(Call<MissionCompleteModle> call, Response<MissionCompleteModle> response) {
                if (response.isSuccessful()) {
                    pbMymissionComplete.setVisibility(View.GONE);
                    MissionCompleteModle missionCompleteModle = response.body();
                    if (missionCompleteModle.getStatus() == true) {
                        tvUserNameMyMisssion.setText(missionCompleteModle.getData().get(0).getFirstName());
                        tvCommentValueMyMisssion.setText(missionCompleteModle.getData().get(0).getYourComments());
                        Picasso.with(getActivity()).load(RetrofitClient.MYMISSIONANDMYDEMANDE_IMAGE_URL + missionCompleteModle.getData().get(0).getPictureUrl()).into(ivUserImgMyMision);
                    }
                    else {

                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbMymissionComplete.setVisibility(View.GONE);
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
                pbMymissionComplete.setVisibility(View.GONE);
            }
        });

    }
}
