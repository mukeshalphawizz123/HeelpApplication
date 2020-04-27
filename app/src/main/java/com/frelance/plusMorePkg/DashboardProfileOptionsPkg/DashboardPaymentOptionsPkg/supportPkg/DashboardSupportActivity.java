package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomToast;
import com.frelance.R;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.dashboardsupportModlePkg.Dashboardsupportmodel;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardSupportActivity extends Fragment implements View.OnClickListener {
    private ImageView ivdashboardsupportback, ivnotificationsupport;
    private RelativeLayout rlsubmitbtn;
    private EditText etDescSupport, etTitleSupport;
    private ProgressBar PbContact;
    private ApiServices apiServices;
    private static Animation shakeAnimation;
    private String userId;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard_support, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        init(view);
        return view;
    }


    private void init(View view) {
        etDescSupport = view.findViewById(R.id.etDescSupportId);
        etTitleSupport = view.findViewById(R.id.etTitleSupportId);
        PbContact = view.findViewById(R.id.PbContactId);
        rlsubmitbtn = view.findViewById(R.id.rlsubmitbtnid);
        rlsubmitbtn.setOnClickListener(this);
        ivnotificationsupport = view.findViewById(R.id.ivnotificationsupportId);
        ivnotificationsupport.setOnClickListener(this);
        ivdashboardsupportback = view.findViewById(R.id.ivdashboardsupportbackId);
        ivdashboardsupportback.setOnClickListener(this);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
    }

    private void dashboardsupport(String title, String description) {
        PbContact.setVisibility(View.VISIBLE);
        apiServices.enquiry(title, userId, description).enqueue(new Callback<Dashboardsupportmodel>() {
            @Override
            public void onResponse(Call<Dashboardsupportmodel> call, Response<Dashboardsupportmodel> response) {
                if (response.isSuccessful()) {
                    PbContact.setVisibility(View.GONE);
                    Dashboardsupportmodel getDashboardsupportmodel = response.body();
                    if (getDashboardsupportmodel.getStatus() == true) {
                        Toast.makeText(getActivity(), getDashboardsupportmodel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                PbContact.setVisibility(View.GONE);
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
            public void onFailure(Call<Dashboardsupportmodel> call, Throwable t) {
                PbContact.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivdashboardsupportbackId:
                // CheckNetwork.goTobackScreen(getActivity(), PlusMoreFragment.class);
                // replaceFragement(new PlusMoreFragment());
               /* FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flHomeId, new DeshboardSponsorshipActivity());
                fragmentTransaction.commit();*/
                removeThisFragment();
                //addFragment(new PlusMoreFragment(), false, Constants.PLUS_MORE_FRAGMENT);

                break;
            case R.id.ivnotificationsupportId:
                // replaceFragement(new NotificationActivity());
                CheckNetwork.goTobackScreen(getActivity(), NotificationActivity.class);
                break;
            case R.id.rlsubmitbtnid:
                if (etTitleSupport.getText().toString().isEmpty()) {
                    new CustomToast().Show_Toast(getActivity(), v, "Title Can't Empty");
                    etTitleSupport.startAnimation(shakeAnimation);
                    etTitleSupport.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                } else {
                    if (CheckNetwork.isNetAvailable(getActivity())) {
                        dashboardsupport(etTitleSupport.getText().toString().trim(), etDescSupport.getText().toString().trim());
                    } else {
                        Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                    }
                }
        }
    }


    public void removeThisFragment() {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStackImmediate();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        } else {

        }
        ft.replace(R.id.flHomeId, fragment, tag);
        ft.commitAllowingStateLoss();
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
}
