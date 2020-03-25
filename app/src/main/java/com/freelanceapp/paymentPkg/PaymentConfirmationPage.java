package com.freelanceapp.paymentPkg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.homePkg.HomeActivity;
import com.freelanceapp.utility.CheckNetwork;

public class PaymentConfirmationPage extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivnotification, ivdashboardback;
    private RelativeLayout RlPaymentConfrm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation_page);
        init();
    }

    private void init() {
        //ivdashboardback = findViewById(R.id.ivdashboardbackId);
        RlPaymentConfrm = findViewById(R.id.RlPaymentConfrmId);
        ivnotification = findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
//        ivdashboardback.setOnClickListener(this);
        RlPaymentConfrm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.ivdashboardbackId:
                CheckNetwork.backScreenWithouFinish(PaymentConfirmationPage.this);
                break;*/
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(PaymentConfirmationPage.this, NotificationActivity.class);
                break;
            case R.id.RlPaymentConfrmId:
                CheckNetwork.nextScreen(PaymentConfirmationPage.this, HomeActivity.class);
                break;

        }
    }
}
