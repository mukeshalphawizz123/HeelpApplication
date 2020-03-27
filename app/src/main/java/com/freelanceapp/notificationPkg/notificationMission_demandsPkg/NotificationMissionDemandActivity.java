package com.freelanceapp.notificationPkg.notificationMission_demandsPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.freelanceapp.R;
import com.freelanceapp.notificationPkg.notificationMessagePkg.NotificationMessageActivity;
import com.freelanceapp.notificationPkg.notificationMessagePkg.NotificationMessageAdapter;
import com.freelanceapp.utility.CheckNetwork;

public class NotificationMissionDemandActivity extends AppCompatActivity implements MissionAndDemandsAdapter.MissionAdapterAppOnClickListener {

    private RecyclerView rvNotificationMissionAndDemands;
    private ProgressBar pbNotMissionDemands;
    private SwipeRefreshLayout sflNotMissAndDemand;
    private MissionAndDemandsAdapter missionAndDemandsAdapter;
    private AppCompatImageView ivnotificationback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_mission_demand);
        init();
        ivnotificationback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
}
