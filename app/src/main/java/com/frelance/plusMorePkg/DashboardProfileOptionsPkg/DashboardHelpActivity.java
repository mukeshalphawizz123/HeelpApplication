package com.frelance.plusMorePkg.DashboardProfileOptionsPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
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
import android.widget.Toast;

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

public class DashboardHelpActivity extends Fragment implements View.OnClickListener {
    private ImageView ivdashboardbackhelp,ivnotification;
    private AppCompatTextView tvmail;
    private AppCompatTextView tvHomeNotificationCount;
    private ApiServices apiServices;
    private String userid;


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_help);*/

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard_help, container, false);
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
        tvHomeNotificationCount=view.findViewById(R.id.tvHomeNotificationCountId);
        ivnotification=view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);

        tvmail = view.findViewById(R.id.tvmailidid);
        tvmail.setOnClickListener(this);

        ivdashboardbackhelp = view.findViewById(R.id.ivdashboardbackhelpId);
        ivdashboardbackhelp.setOnClickListener(this);

        SpannableString content = new SpannableString("contact@heelp.com");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvmail.setText(content);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivdashboardbackhelpId:
                // CheckNetwork.goTobackScreen(getActivity(), PlusMoreFragment.class);
                removeAllFragment(new PlusMoreFragment(),false, Constants.PLUS_MORE_FRAGMENT);
                break;
            case R.id.ivnotificationId:
                // replaceFragement(new NotificationActivity());
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;


        }
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
    }

    public void removeAllFragment(Fragment replaceFragment,
                                  boolean addToBackStack, String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);
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
