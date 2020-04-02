package com.frelance.notificationPkg.notificationMission_demandsPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.notificationPkg.NotificationModlePkg.Datum;
import com.frelance.notificationPkg.NotificationModlePkg.NotificationResponseModle;
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

public class NotificationMissionDemandActivity extends AppCompatActivity implements MissionAndDemandsAdapter.MissionAdapterAppOnClickListener {

    private RecyclerView rvNotificationMissionAndDemands;
    private ProgressBar pbNotMissionDemands;
    private SwipeRefreshLayout sflNotMissAndDemand;
    private MissionAndDemandsAdapter missionAndDemandsAdapter;
    private AppCompatImageView ivnotificationback;
    private ApiServices apiServices;
    private String userId;
    private List<Datum> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_mission_demand);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        init();
        ivnotificationback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            notification(userId, "2");
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        ivnotificationback = findViewById(R.id.ivnotificationbackId);
        rvNotificationMissionAndDemands = findViewById(R.id.rvNotificationMissionAndDemandsid);
        sflNotMissAndDemand = findViewById(R.id.sflNotMissAndDemandId);
        pbNotMissionDemands = findViewById(R.id.pbNotMissionDemandsId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvNotificationMissionAndDemands.setLayoutManager(layoutManager);
        missionAndDemandsAdapter = new MissionAndDemandsAdapter(getApplicationContext(), this);
        rvNotificationMissionAndDemands.setAdapter(missionAndDemandsAdapter);
    }

    @Override
    public void missionAdapterTabClick(View view, int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(NotificationMissionDemandActivity.this);
    }


    private void notification(String userId, String typeId) {
        pbNotMissionDemands.setVisibility(View.VISIBLE);
        apiServices.getNotification(userId, typeId).enqueue(new Callback<NotificationResponseModle>() {
            @Override
            public void onResponse(Call<NotificationResponseModle> call, Response<NotificationResponseModle> response) {
                if (response.isSuccessful()) {
                    pbNotMissionDemands.setVisibility(View.GONE);
                    NotificationResponseModle notificationResponseModle = response.body();
                    if (notificationResponseModle.getStatus()) {
                        notificationList = notificationResponseModle.getData();
                        missionAndDemandsAdapter.addmymissionData(notificationList);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbNotMissionDemands.setVisibility(View.GONE);
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<NotificationResponseModle> call, Throwable t) {
                pbNotMissionDemands.setVisibility(View.GONE);
            }
        });

    }
}
