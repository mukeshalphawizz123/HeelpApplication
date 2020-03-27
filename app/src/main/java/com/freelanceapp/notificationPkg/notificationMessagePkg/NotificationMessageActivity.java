package com.freelanceapp.notificationPkg.notificationMessagePkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.freelanceapp.R;
import com.freelanceapp.myMissionPkg.MymissionAdapter.MyMissionAdapter;
import com.freelanceapp.utility.CheckNetwork;

public class NotificationMessageActivity extends AppCompatActivity implements NotificationMessageAdapter.NotificationMessageAppOnClickListener {

    private RecyclerView rvNotificationMessage;
    private ProgressBar pbNotMsg;
    private SwipeRefreshLayout sflNotMs;
    private NotificationMessageAdapter notificationMessageAdapter;
    private AppCompatImageView ivnotificationback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_message);
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
        sflNotMs = findViewById(R.id.sflNotMsgId);
        rvNotificationMessage = findViewById(R.id.rvNotificationMessageid);
        pbNotMsg = findViewById(R.id.pbNotMsgId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvNotificationMessage.setLayoutManager(layoutManager);
        notificationMessageAdapter = new NotificationMessageAdapter(getApplicationContext(), this);
        rvNotificationMessage.setAdapter(notificationMessageAdapter);

    }

    @Override
    public void notMsgTabClick(View view, int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(NotificationMessageActivity.this);
    }
}
