package com.freelanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.freelanceapp.externalModlePkg.ProjectSendDisputeModle;
import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.DashboardSupportActivity;
import com.freelanceapp.utility.CheckNetwork;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpActivity extends Fragment implements View.OnClickListener {
    private TextView tvmail;
    private ImageView ivnotification, ivdashboardback;
    private RelativeLayout rlcontact, rlsubmitbtnHelp;
    private ApiServices apiServices;
    private ProgressBar pbHelp;
    private AppCompatEditText etEnterDispute;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  fragmentMessageToutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_tout, container, false);
        View view = inflater.inflate(R.layout.activity_help, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);
        return view;
    }

    private void init(View view) {
        etEnterDispute = view.findViewById(R.id.etEnterDisputeId);
        pbHelp = view.findViewById(R.id.pbHelpId);
        ivdashboardback = view.findViewById(R.id.ivdashboardbackId);
        ivdashboardback.setOnClickListener(this);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        rlsubmitbtnHelp = view.findViewById(R.id.rlsubmitbtnHelpid);
        rlcontact = view.findViewById(R.id.rlcontactId);
        rlcontact.setOnClickListener(this);
        rlsubmitbtnHelp.setOnClickListener(this);
        tvmail = view.findViewById(R.id.tvmailidid);
        SpannableString content = new SpannableString("contact@heelp.com");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvmail.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlsubmitbtnHelpid:
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    sendProjectDispute();
                } else {
                    Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.ivnotificationId:
                // replaceFragement(new NotificationActivity());
                CheckNetwork.goTobackScreen(getActivity(), NotificationActivity.class);
                break;
            case R.id.rlcontactId:
                replaceFragement(new DashboardSupportActivity());
                //  CheckNetwork.goTobackScreen(getActivity(), DashboardSupportActivity.class);
                break;
            case R.id.ivdashboardbackId:
                removeThisFragment();
                //replaceFragement(new MyDemandsOngoingActivity());
                break;

        }

    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void removeThisFragment() {
        final FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();

        // Toast.makeText(this, ""+manager, Toast.LENGTH_SHORT).show();
    }


    private void sendProjectDispute() {
        pbHelp.setVisibility(View.VISIBLE);
        apiServices.sendDispute("12", "test", "2020-03-26").enqueue(new Callback<ProjectSendDisputeModle>() {
            @Override
            public void onResponse(Call<ProjectSendDisputeModle> call, Response<ProjectSendDisputeModle> response) {
                if (response.isSuccessful()) {
                    pbHelp.setVisibility(View.GONE);
                    ProjectSendDisputeModle projectSendDisputeModle = response.body();
                    if (projectSendDisputeModle.getStatus() == true) {
                        Toast.makeText(getActivity(), projectSendDisputeModle.getData().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbHelp.setVisibility(View.GONE);
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
            public void onFailure(Call<ProjectSendDisputeModle> call, Throwable t) {
                pbHelp.setVisibility(View.GONE);
            }
        });

    }
}
