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
    private AppCompatTextView tvCommentDemandProg, tvUserNameDemandProg;
    private CircleImageView ivUserProgDemain;
    private String projectId;
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


        //   Toast.makeText(getActivity(), projectId, Toast.LENGTH_LONG).show();

        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myOnProgressApi(projectId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;


    }

    private void init(View view) {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
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
        pbDemandProgress.setVisibility(View.VISIBLE);
        apiServices.demandPorgress(projectId).enqueue(new Callback<DemandOnProgressModle>() {
            @Override
            public void onResponse(Call<DemandOnProgressModle> call, Response<DemandOnProgressModle> response) {
                if (response.isSuccessful()) {
                    pbDemandProgress.setVisibility(View.GONE);
                    DemandOnProgressModle requestlist = response.body();
                    if (requestlist.getStatus()) {
                        datumList = requestlist.getData();
                        tvUserNameDemandProg.setText(datumList.get(0).getFirstName());
                        tvCommentDemandProg.setText(datumList.get(0).getYourComments());
                        if (datumList.get(0).getPictureUrl().isEmpty()) {

                        } else {
                            Picasso.with(getActivity()).load(RetrofitClient.MISSION_USER_IMAGE_URL + datumList.get(0).getPictureUrl()).into(ivUserProgDemain);
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
                        myDemandOngoingAdapter.addOnGoingDemandsFiles(filesList);

                    }

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbDemandProgress.setVisibility(View.GONE);
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
                pbDemandProgress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void myDemandOnGoingOnClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlFileFolderId:
                fileDownloading.DownloadImage(RetrofitClient.DOWNLOAD_URL + filesList.get(position));
                break;
        }
    }
}
