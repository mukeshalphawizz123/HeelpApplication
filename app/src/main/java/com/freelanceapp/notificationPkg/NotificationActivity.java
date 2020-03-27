package com.freelanceapp.notificationPkg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.freelanceapp.R;
import com.freelanceapp.notificationPkg.notificationMessagePkg.NotificationMessageActivity;
import com.freelanceapp.notificationPkg.notificationMission_demandsPkg.NotificationMissionDemandActivity;
import com.freelanceapp.notificationPkg.notificationOffersPkg.NotificationOfferActivity;
import com.freelanceapp.notificationPkg.notificationReviewPkg.NotificationReviewActivity;
import com.freelanceapp.paymentPkg.CreditCardPayment;
import com.freelanceapp.utility.CheckNetwork;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivnotificationback;
    private RelativeLayout rlPaymentNotification, rlnotification, rlStatusNotification, rlOffersNotification, rlMsgsNotification, rlAvisNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        init();
    }

    private void init() {
        rlPaymentNotification = findViewById(R.id.rlPaymentNotificationId);
        ivnotificationback = findViewById(R.id.ivnotificationbackId);
        rlnotification = findViewById(R.id.rlnotificationId);
        rlStatusNotification = findViewById(R.id.rlStatusNotificationId);
        rlOffersNotification = findViewById(R.id.rlOffersNotificationId);
        rlMsgsNotification = findViewById(R.id.rlMsgNotificationId);
        rlAvisNotification = findViewById(R.id.rlAvisNotificationId);
        clickListenerSetup();
    }

    private void clickListenerSetup() {
        rlPaymentNotification.setOnClickListener(this);
        ivnotificationback.setOnClickListener(this);
        rlnotification.setOnClickListener(this);
        rlStatusNotification.setOnClickListener(this);
        rlOffersNotification.setOnClickListener(this);
        rlMsgsNotification.setOnClickListener(this);
        rlAvisNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlOffersNotificationId:
                CheckNetwork.nextScreenWithoutFinish(NotificationActivity.this, NotificationOfferActivity.class);
                break;
            case R.id.rlAvisNotificationId:
                CheckNetwork.nextScreenWithoutFinish(NotificationActivity.this, NotificationReviewActivity.class);
                break;
            case R.id.rlMsgNotificationId:
                CheckNetwork.nextScreenWithoutFinish(NotificationActivity.this, NotificationMessageActivity.class);
                break;
            case R.id.rlStatusNotificationId:
                CheckNetwork.nextScreenWithoutFinish(NotificationActivity.this, NotificationMissionDemandActivity.class);
                break;
            case R.id.rlnotificationId:
              //  CheckNetwork.nextScreenWithoutFinish(NotificationActivity.this, );
                break;
            case R.id.ivnotificationbackId:
                onBackPressed();
                break;
            case R.id.rlPaymentNotificationId:
                CheckNetwork.nextScreenWithoutFinish(NotificationActivity.this, CreditCardPayment.class);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Intent intent = new Intent(NotificationActivity.this, HomeActivity.class);
        startActivity(intent)*/
        ;
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }
}
