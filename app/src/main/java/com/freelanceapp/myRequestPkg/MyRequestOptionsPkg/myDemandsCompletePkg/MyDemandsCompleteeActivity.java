package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsCompletePkg;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.detailsPkg.DetailsActivity;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsCompletePkg.Adapter.MyDemandsCompleteAdapter;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsCompletePkg.demandCompleteModlePkg.Datum;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsCompletePkg.demandCompleteModlePkg.DemandCompleteModle;
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

public class MyDemandsCompleteeActivity extends Fragment implements MyDemandsCompleteAdapter.MyRequestCompleteAppOnClickListener, View.OnClickListener {


    private MyDemandsCompleteAdapter myRequestCompleteAdapter;
    private RecyclerView rvmyreqcompletefileupload;
    private ImageView ivdashboardcompleteback, ivnotification;
    private RelativeLayout rlreqcompleteviewdetils;
    private TextView tvviewprofile;
    private ApiServices apiServices;
    private ProgressBar pbDemandComplete;
    private List<Datum> datumList;
    private AppCompatTextView tvUserDemandComp, tvCommentDemandCompl;
    private CircleImageView ivUserDemandComp;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_completee);*/

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_request_completee, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);

        if (CheckNetwork.isNetAvailable(getActivity())) {
            myOnCompleteApi("12");
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;


    }

    private void init(View view) {
        tvUserDemandComp = view.findViewById(R.id.tvUserDemandCompId);
        tvCommentDemandCompl = view.findViewById(R.id.tvCommentDemandComplId);
        ivUserDemandComp = view.findViewById(R.id.ivUserDemandCompId);

        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);

        pbDemandComplete = view.findViewById(R.id.pbDemandCompleteId);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);

        ivdashboardcompleteback = view.findViewById(R.id.ivdashboardcompletebackId);
        ivdashboardcompleteback.setOnClickListener(this);

        rlreqcompleteviewdetils = view.findViewById(R.id.rlreqcompleteviewdetilsid);
        rlreqcompleteviewdetils.setOnClickListener(this);

        rvmyreqcompletefileupload = view.findViewById(R.id.rvmyreqcompletefileuploadid);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rvmyreqcompletefileupload.setLayoutManager(layoutManager);
        MyDemandsCompleteAdapter myRequestCompleteAdapter = new MyDemandsCompleteAdapter(getActivity(), this);
        rvmyreqcompletefileupload.setAdapter(myRequestCompleteAdapter);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivdashboardcompletebackId:
                removeThisFragment();
                // replaceFragementWithoutStack(new MyRequestFragment());
                break;
            case R.id.rlreqcompleteviewdetilsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
        }

    }

    private void replaceFragement(Fragment fragment) {
        AppSession.setStringPreferences(getActivity(), "OnGoing", "MyReqCompletee");
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

    private void myOnCompleteApi(String status) {
        pbDemandComplete.setVisibility(View.VISIBLE);
        apiServices.demandomplete(status).enqueue(new Callback<DemandCompleteModle>() {
            @Override
            public void onResponse(Call<DemandCompleteModle> call, Response<DemandCompleteModle> response) {
                if (response.isSuccessful()) {
                    pbDemandComplete.setVisibility(View.GONE);
                    DemandCompleteModle requestlist = response.body();
                    if (requestlist.getStatus() == true) {
                        datumList = requestlist.getData();
                        tvUserDemandComp.setText(datumList.get(0).getFirstName());
                        tvCommentDemandCompl.setText(datumList.get(0).getYourComments());
                        Picasso.with(getActivity()).load(RetrofitClient.MYMISSIONANDMYDEMANDE_IMAGE_URL + datumList.get(0).getProjectImage()).into(ivUserDemandComp);

                    }

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbDemandComplete.setVisibility(View.GONE);
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
            public void onFailure(Call<DemandCompleteModle> call, Throwable t) {
                pbDemandComplete.setVisibility(View.GONE);
            }
        });
    }
}
