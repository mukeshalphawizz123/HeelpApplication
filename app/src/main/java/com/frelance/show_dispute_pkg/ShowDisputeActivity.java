package com.frelance.show_dispute_pkg;

import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.MyMissionadapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.Datum;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.GetAllDiputeResponseModle;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDisputeActivity extends AppCompatActivity implements View.OnClickListener, MyDisputeadapter.MyMissionDisputeOnClickListener {
    private ImageView ivmissionfeedbackdashboardback, ivnotification;
    private TextView tvviewprofile;
    private RelativeLayout rlmissproposeviewdetails;
    private AppCompatEditText ettypemsg;
    private RecyclerView rvMyMissionDispute;
    private MyDisputeadapter myMissionadapter;
    private AppCompatImageView ivgifbutton;
    private ApiServices apiServices;
    private String missionId, userId, mission_mission_title;
    private List<Datum> datumList;
    private Handler handler = new Handler();
    private int apiDelayed = 5 * 1000; //1 second=1000 milisecond, 5*1000=5seconds
    private Runnable runnable;
    private AppCompatTextView tvMyMissTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_disputer);
        /// missionId = getIntent().getStringExtra("missionId");
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        // mission_mission_title = AppSession.getStringPreferences(getApplicationContext(), "mission_mission_title");
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            myMissionDispute(userId);
        } else {

        }
        tvMyMissTitle.setText(mission_mission_title);
    }

    private void init() {
        tvMyMissTitle = findViewById(R.id.tvMyMissTitleId);
        ivgifbutton = findViewById(R.id.ivgifbuttonid);
        rvMyMissionDispute = findViewById(R.id.rvMyMissionDisputeId);
        ettypemsg = findViewById(R.id.ettypemsgid);
        ivnotification = findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);

        ivmissionfeedbackdashboardback = findViewById(R.id.ivmissionfeedbackdashboardbackId);
        ivmissionfeedbackdashboardback.setOnClickListener(this);

        rlmissproposeviewdetails = findViewById(R.id.rlmissproposeviewdetailsid);
        rlmissproposeviewdetails.setOnClickListener(this);
        ivgifbutton.setOnClickListener(this);

        tvviewprofile = findViewById(R.id.tvviewprofileid);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvMyMissionDispute.setLayoutManager(layoutManager);
        myMissionadapter = new MyDisputeadapter(getApplicationContext(), this);
        rvMyMissionDispute.setAdapter(myMissionadapter);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivgifbuttonid:
                break;
            case R.id.ivmissionfeedbackdashboardbackId:
                onBackPressed();
                break;
            case R.id.rlmissproposeviewdetailsid:
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(ShowDisputeActivity.this, NotificationActivity.class);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }


    private void myMissionDispute(String userId) {
        /// CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.getprojectdispute(userId).enqueue(new Callback<GetAllDiputeResponseModle>() {
            @Override
            public void onResponse(Call<GetAllDiputeResponseModle> call, Response<GetAllDiputeResponseModle> response) {
                if (response.isSuccessful()) {
                    GetAllDiputeResponseModle getAllDiputeResponseModle = response.body();
                    if (getAllDiputeResponseModle.getStatus()) {
                        datumList = getAllDiputeResponseModle.getData();
                        myMissionadapter.addDisputeList(datumList);
                    } else {

                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAllDiputeResponseModle> call, Throwable t) {

            }
        });

    }
}
