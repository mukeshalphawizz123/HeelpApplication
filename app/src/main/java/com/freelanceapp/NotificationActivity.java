package com.freelanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.freelanceapp.homePkg.HomeActivity;
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
            case R.id.rlAvisNotificationId:
                CheckNetwork.callToast(NotificationActivity.this);
                break;
            case R.id.rlMsgNotificationId:
                CheckNetwork.callToast(NotificationActivity.this);
                break;
            case R.id.rlStatusNotificationId:
                CheckNetwork.callToast(NotificationActivity.this);
                break;
            case R.id.rlnotificationId:
                CheckNetwork.callToast(NotificationActivity.this);
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
        startActivity(intent)*/;
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }
}
