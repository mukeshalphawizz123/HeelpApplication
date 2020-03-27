package com.freelanceapp.notificationPkg.notificationOffersPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.freelanceapp.R;
import com.freelanceapp.notificationPkg.notificationMission_demandsPkg.MissionAndDemandsAdapter;
import com.freelanceapp.notificationPkg.notificationMission_demandsPkg.NotificationMissionDemandActivity;
import com.freelanceapp.utility.CheckNetwork;

public class NotificationOfferActivity extends AppCompatActivity implements NotificationOfferAdapter.NotificationOfferOnClickListener {

    private RecyclerView rvNotificationOffer;
    private ProgressBar pbNotOffers;
    private SwipeRefreshLayout wrlNotOffer;
    private NotificationOfferAdapter notificationOfferAdapter;
    private AppCompatImageView ivnotificationback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_offer);
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
        wrlNotOffer = findViewById(R.id.wrlNotOfferId);
        rvNotificationOffer = findViewById(R.id.rvNotificationOfferid);
        pbNotOffers = findViewById(R.id.pbNotOffersId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvNotificationOffer.setLayoutManager(layoutManager);
        notificationOfferAdapter = new NotificationOfferAdapter(getApplicationContext(), this);
        rvNotificationOffer.setAdapter(notificationOfferAdapter);
    }

    @Override
    public void offerTabClick(View view, int position) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(NotificationOfferActivity.this);
    }
}
