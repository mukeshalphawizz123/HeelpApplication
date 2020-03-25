package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.myMissionPkg.myMissionModlePkg.MyMissionModel;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.dashboardsupportModlePkg.Dashboardsupportmodel;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DeshboardSponsorshipActivity;
import com.freelanceapp.plusMorePkg.PlusMoreFragment;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DashboardSupportActivity extends Fragment implements View.OnClickListener {
    private ImageView ivdashboardsupportback, ivnotificationsupport;
    private RelativeLayout rlsubmitbtn;
    private EditText EtcontactDescription, Etcontacttitle;
    private ProgressBar PbContact;
    private ApiServices apiServices;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard_support, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }


    private void init(View view) {
        EtcontactDescription = view.findViewById(R.id.EtcontactDescriptionId);
        Etcontacttitle = view.findViewById(R.id.EtcontacttitleId);
        PbContact = view.findViewById(R.id.PbContactId);
        rlsubmitbtn = view.findViewById(R.id.rlsubmitbtnid);
        rlsubmitbtn.setOnClickListener(this);
        ivnotificationsupport = view.findViewById(R.id.ivnotificationsupportId);
        ivnotificationsupport.setOnClickListener(this);
        ivdashboardsupportback = view.findViewById(R.id.ivdashboardsupportbackId);
        ivdashboardsupportback.setOnClickListener(this);
    }

    private void dashboardsupport(String title,String description) {
        PbContact.setVisibility(View.VISIBLE);
        apiServices.enquiry(title,description).enqueue(new Callback<Dashboardsupportmodel>() {
            @Override
            public void onResponse(Call<Dashboardsupportmodel> call, Response<Dashboardsupportmodel> response) {
                if (response.isSuccessful()) {
                    PbContact.setVisibility(View.GONE);
                    Dashboardsupportmodel getDashboardsupportmodel = response.body();
                    if (getDashboardsupportmodel.getStatus() == true) {

                        Toast.makeText(getActivity(), "Your Email has successfully been sent..", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
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
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "under Development...", Toast.LENGTH_SHORT).show();
                   // dashboardsupport(Etcontacttitle.getText().toString().trim(), EtcontactDescription.getText().toString().trim());
                } else {
                    Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
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
        ft.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }else {

        }
        ft.replace(R.id.flHomeId, fragment, tag);
        ft.commitAllowingStateLoss();
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
}
