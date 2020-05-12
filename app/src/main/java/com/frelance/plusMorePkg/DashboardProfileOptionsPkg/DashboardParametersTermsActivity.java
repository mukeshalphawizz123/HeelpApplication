package com.frelance.plusMorePkg.DashboardProfileOptionsPkg;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.notificationPkg.NotificationCountResponseModle;
import com.frelance.plusMorePkg.PlusMoreFragment;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardParametersTermsActivity extends Fragment implements View.OnClickListener {
    private ImageView ivdashboardtermsback, ivnotificationterms;
    private TextView tvtext, tvtextthree, tvtextfive, tvtextseven, tvtexteghit;
    private AppCompatTextView tvHomeNotificationCount;

    private ApiServices apiServices;
    private String userid;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard_parameters_terms, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userid = AppSession.getStringPreferences(getActivity(), Constants.USERID);

        init(view);

        if (CheckNetwork.isNetAvailable(getActivity())) {
            notification(userid);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

        return view;

    }

    private void init(View view) {
        tvHomeNotificationCount = view.findViewById(R.id.tvHomeNotificationCountId);
        tvtexteghit = view.findViewById(R.id.tvtexteghitid);
        tvtext = view.findViewById(R.id.tvtextid);
        tvtextthree = view.findViewById(R.id.tvtextthreeid);
        tvtextfive = view.findViewById(R.id.tvtextfiveid);
        tvtextseven = view.findViewById(R.id.tvtextsevenid);


        ivnotificationterms = view.findViewById(R.id.ivnotificationtermsId);
        ivnotificationterms.setOnClickListener(this);
        ivdashboardtermsback = view.findViewById(R.id.ivdashboardtermsbackId);
        ivdashboardtermsback.setOnClickListener(this);

        SpannableString content = new SpannableString("Vos engagements :");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvtext.setText(content);

        SpannableString content1 = new SpannableString("Données personnelles :");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvtextthree.setText(content1);

        SpannableString content2 = new SpannableString("Terminologie :");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        tvtextthree.setText(content2);

        SpannableString content3 = new SpannableString("Statuts des missions et demandes :");
        content3.setSpan(new UnderlineSpan(), 0, content3.length(), 0);
        tvtextseven.setText(content3);

        SpannableString content4 = new SpannableString("Parrainage:");
        content4.setSpan(new UnderlineSpan(), 0, content4.length(), 0);
        tvtexteghit.setText(content4);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivdashboardtermsbackId:
                // CheckNetwork.goTobackScreen(getActivity(), PlusMoreFragment.class);
                removeAllFragment(new PlusMoreFragment(), false, Constants.PLUS_MORE_FRAGMENT);
                break;
            case R.id.ivnotificationtermsId:
                // replaceFragement(new NotificationActivity());
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;

        }
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void removeAllFragment(Fragment replaceFragment,
                                  boolean addToBackStack, String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        manager.popBackStackImmediate(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.flHomeId, replaceFragment);
        ft.commit();
    }

    private void notification(String userId) {
        //    CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.getnotificationcount(userId).enqueue(new Callback<NotificationCountResponseModle>() {
            @Override
            public void onResponse(Call<NotificationCountResponseModle> call, Response<NotificationCountResponseModle> response) {
                if (response.isSuccessful()) {
                    // CustomProgressbar.hideProgressBar();
                    NotificationCountResponseModle notificationResponseModle = response.body();
                    if (notificationResponseModle.getStatus()) {
                        int messageCount = notificationResponseModle.getCountMessages();
                        int messageDemands = notificationResponseModle.getCountMissionanddemands();
                        int messageOffers = notificationResponseModle.getCountOffers();
                        int messageCountPayment = notificationResponseModle.getCountPayment();
                        int messageCountReveiews = notificationResponseModle.getCountReviews();

                        String totalNotification = String.valueOf(messageCount
                                + messageOffers
                                + messageDemands
                                + messageCountPayment
                                + messageCountReveiews);

                        if (totalNotification == null || totalNotification.isEmpty()) {
                            tvHomeNotificationCount.setVisibility(View.GONE);
                        } else {
                            tvHomeNotificationCount.setText(totalNotification);
                            tvHomeNotificationCount.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvHomeNotificationCount.setVisibility(View.GONE);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!false) {
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
            public void onFailure(Call<NotificationCountResponseModle> call, Throwable t) {
                // CustomProgressbar.hideProgressBar();
                tvHomeNotificationCount.setVisibility(View.GONE);
            }
        });

    }
}
