package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsLiveryPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.HelpActivity;
import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.detailsPkg.DetailsActivity;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsCompletePkg.demandCompleteModlePkg.DemandCompleteModle;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsLiveryPkg.Adapter.MyDemandsLiveryAdapter;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsLiveryPkg.demandDeliveryModlePkg.Datum;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsLiveryPkg.demandDeliveryModlePkg.DemandDeliveredModle;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDemandsDeliveryActivity extends Fragment implements MyDemandsLiveryAdapter.MyRequestLiveryAppOnClickListener, View.OnClickListener {

    private MyDemandsLiveryAdapter myRequestLiveryAdapter;
    private RecyclerView rvmyreqliveryfileupload;
    private ImageView ivlivreedashboardback, ivnotification;
    private RelativeLayout rlreqlivreeviewdetail;
    private TextView tvviewprofile, tvliverycontact;
    private AppCompatCheckBox radioid, radiograyid;
    private ApiServices apiServices;
    private ProgressBar pbDemandDelivery;
    private List<Datum> datumList;
    private AppCompatTextView tvUserDemandDely, tvCommentDemandDely;
    private CircleImageView ivUserDemandDely;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_request_livery, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myOnDeliveryApi("12");
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private void init(View view) {
        ivUserDemandDely = view.findViewById(R.id.ivUserDemandDelyId);
        tvCommentDemandDely = view.findViewById(R.id.tvCommentDemandDelyId);
        tvUserDemandDely = view.findViewById(R.id.tvUserDemandDelyId);
        pbDemandDelivery = view.findViewById(R.id.pbDemandDeliveryId);
        tvliverycontact = view.findViewById(R.id.tvliverycontactid);
        tvliverycontact.setOnClickListener(this);
        radioid = view.findViewById(R.id.radioid);
        radiograyid = view.findViewById(R.id.radiograyid);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);
        ivlivreedashboardback = view.findViewById(R.id.ivlivreedashboardbackId);
        ivlivreedashboardback.setOnClickListener(this);
        rlreqlivreeviewdetail = view.findViewById(R.id.rlreqlivreeviewdetailid);
        rlreqlivreeviewdetail.setOnClickListener(this);
        rvmyreqliveryfileupload = view.findViewById(R.id.rvmyreqliveryfileuploadid);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rvmyreqliveryfileupload.setLayoutManager(layoutManager);
        MyDemandsLiveryAdapter myRequestLiveryAdapter = new MyDemandsLiveryAdapter(getActivity(), this);
        rvmyreqliveryfileupload.setAdapter(myRequestLiveryAdapter);
        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
        SpannableString content1 = new SpannableString(getResources().getString(R.string.report_a_problem));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvliverycontact.setText(content1);
        radioid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radiograyid.setChecked(false);
                }
            }
        });
        radiograyid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioid.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivlivreedashboardbackId:
                removeThisFragment();
                // replaceFragementWithoutStack(new MyRequestFragment());
                break;
            case R.id.rlreqlivreeviewdetailid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.tvliverycontactid:
                replaceFragement(new HelpActivity());
                break;
        }

    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "MyReqLivree");
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

    private void myOnDeliveryApi(String status) {
        pbDemandDelivery.setVisibility(View.VISIBLE);
        apiServices.demandDelivered(status).enqueue(new Callback<DemandDeliveredModle>() {
            @Override
            public void onResponse(Call<DemandDeliveredModle> call, Response<DemandDeliveredModle> response) {
                if (response.isSuccessful()) {
                    pbDemandDelivery.setVisibility(View.GONE);
                    DemandDeliveredModle requestlist = response.body();
                    if (requestlist.getStatus() == true) {
                        datumList = requestlist.getData();
                        tvUserDemandDely.setText(datumList.get(0).getFirstName());
                        tvCommentDemandDely.setText(datumList.get(0).getYourComments());
                        Picasso.with(getActivity())
                                .load(RetrofitClient.MYMISSIONANDMYDEMANDE_IMAGE_URL + datumList.get(0)
                                        .getProjectImage())
                                .into(ivUserDemandDely);

                    }

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbDemandDelivery.setVisibility(View.GONE);
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
            public void onFailure(Call<DemandDeliveredModle> call, Throwable t) {
                pbDemandDelivery.setVisibility(View.GONE);
            }
        });
    }

}
