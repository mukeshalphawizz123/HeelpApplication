package com.freelanceapp.notificationPkg.notificationReviewPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.freelanceapp.R;
import com.freelanceapp.utility.CheckNetwork;


public class NotificationReviewActivity extends AppCompatActivity implements NotificationReviewAdapter.NotReviewAppOnClickListener {

    private RecyclerView rvNotificationReview;
    private ProgressBar pbNotReview;
    private SwipeRefreshLayout wrlNotReview;
    private NotificationReviewAdapter notificationReviewAdapter;
    private AppCompatImageView ivnotificationback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_review);
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
        wrlNotReview = findViewById(R.id.wrlNotReviewId);
        rvNotificationReview = findViewById(R.id.rvNotificationReviewid);
        pbNotReview = findViewById(R.id.pbNotReviewId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvNotificationReview.setLayoutManager(layoutManager);
        notificationReviewAdapter = new NotificationReviewAdapter(getApplicationContext(), this);
        rvNotificationReview.setAdapter(notificationReviewAdapter);
    }

    @Override
    public void myReviewTabClick(View view, int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(NotificationReviewActivity.this);
    }
}
