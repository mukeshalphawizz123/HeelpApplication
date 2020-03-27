package com.freelanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.paymentPkg.PaymentConfirmationPage;
import com.freelanceapp.utility.CheckNetwork;

public class CreditCardPayerActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout RlPayer;
    private ImageView ivdashboardcreditcardback, ivnotificationcreditcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_payer);
        init();
    }

    private void init() {
        ivnotificationcreditcard = findViewById(R.id.ivnotificationcreditcardId);
        ivdashboardcreditcardback = findViewById(R.id.ivdashboardcreditcardbackId);
        RlPayer = findViewById(R.id.RlPayerId);
        RlPayer.setOnClickListener(this);
        ivdashboardcreditcardback.setOnClickListener(this);
        ivnotificationcreditcard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivnotificationcreditcardId:
                CheckNetwork.nextScreenWithoutFinish(CreditCardPayerActivity.this, NotificationActivity.class);
                break;
            case R.id.ivdashboardcreditcardbackId:
                CheckNetwork.backScreenWithouFinish(CreditCardPayerActivity.this);
                break;
            case R.id.RlPayerId:
                CheckNetwork.nextScreenWithoutFinish(CreditCardPayerActivity.this, PaymentConfirmationPage.class);
                break;
        }
    }
}
