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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.MyMissionadapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.Datum;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.GetAllDiputeResponseModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.SendDiputeResponseModle;
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
    private SwipeRefreshLayout wrlShowDispute;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_disputer);
        /// missionId = getIntent().getStringExtra("missionId");
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        missionId = AppSession.getStringPreferences(getApplicationContext(), "dispute_mission_id");
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            myMissionDispute(userId);
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        tvMyMissTitle.setText(mission_mission_title);

        wrlShowDispute.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                    myMissionDispute(userId);
                } else {
                    Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
                wrlShowDispute.setRefreshing(false);
            }
        });
    }

    private void init() {
        wrlShowDispute = findViewById(R.id.wrlShowDisputeId);
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

        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
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
                if (CheckNetwork.isNetAvailable(ShowDisputeActivity.this)) {
                    if (TextUtils.isEmpty(ettypemsg.getText().toString())) {
                    } else {
                        sendDispute();
                    }
                } else {
                    Toast.makeText(ShowDisputeActivity.this, "Check network connection", Toast.LENGTH_LONG).show();
                }
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


    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do your function;
                if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                    myMissionDispute(userId);
                } else {
                    Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
                handler.postDelayed(runnable, apiDelayed);
            }
        }, apiDelayed); // so basically after your getHeroes(), from next time it will be 5 sec repeated
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible
    }

    private void myMissionDispute(String userId) {
        apiServices.getprojectdispute(userId, missionId).enqueue(new Callback<GetAllDiputeResponseModle>() {
            @Override
            public void onResponse(Call<GetAllDiputeResponseModle> call, Response<GetAllDiputeResponseModle> response) {
                if (response.isSuccessful()) {
                    GetAllDiputeResponseModle getAllDiputeResponseModle = response.body();
                    if (getAllDiputeResponseModle.getStatus()) {
                        datumList = getAllDiputeResponseModle.getData();
                        myMissionadapter.addDisputeList(datumList);
                        layoutManager.scrollToPosition(myMissionadapter.getItemCount() - 1);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!false) {
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


    private void sendDispute() {
        CustomProgressbar.showProgressBar(ShowDisputeActivity.this, false);
        apiServices.projectsenddispute(missionId, ettypemsg.getText().toString(), userId).enqueue(new Callback<SendDiputeResponseModle>() {
            @Override
            public void onResponse(Call<SendDiputeResponseModle> call, Response<SendDiputeResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    SendDiputeResponseModle missionlist = response.body();
                    if (missionlist.getStatus()) {
                        ettypemsg.setText("");
                    }
                    if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                        myMissionDispute(userId);
                    }

                } else {
                    if (response.code() == 400) {
                        if (!false) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(ShowDisputeActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SendDiputeResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }
}
