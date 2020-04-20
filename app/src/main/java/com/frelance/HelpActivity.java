package com.frelance;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.externalModlePkg.ProjectSendDisputeModle;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.DashboardSupportActivity;
import com.frelance.show_dispute_pkg.ShowDisputeActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

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
    private String userid, missionId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  fragmentMessageToutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_tout, container, false);
        View view = inflater.inflate(R.layout.activity_help, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userid = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        missionId = getArguments().getString("missionId");
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
                CheckNetwork.nextScreenWithoutFinish(getActivity(), ShowDisputeActivity.class);
               /* if (CheckNetwork.isNetAvailable(getActivity())) {
                    sendProjectDispute();
                } else {
                    Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }*/

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
        String dispute = etEnterDispute.getText().toString();
        String date = Constants.currentDate();
        apiServices.sendDispute(missionId, dispute, date).enqueue(new Callback<ProjectSendDisputeModle>() {
            @Override
            public void onResponse(Call<ProjectSendDisputeModle> call, Response<ProjectSendDisputeModle> response) {
                if (response.isSuccessful()) {
                    pbHelp.setVisibility(View.GONE);
                    ProjectSendDisputeModle projectSendDisputeModle = response.body();
                    if (projectSendDisputeModle.getStatus()) {
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
