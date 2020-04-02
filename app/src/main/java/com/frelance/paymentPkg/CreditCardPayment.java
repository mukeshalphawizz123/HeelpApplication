package com.frelance.paymentPkg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.frelance.CreditCardPayerActivity;
import com.frelance.R;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.CheckNetwork;

public class CreditCardPayment extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivnotificationcreditcard, ivdashboardcreditcardback,Ivwalletpoint,IvCreditcardpoint;
    private RelativeLayout rlpayer, rlcreditcard, rlMaCanotte;
    private String credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_payment);
        init();
        credit="1";
    }

    private void init() {
        rlcreditcard = findViewById(R.id.rlcreditcardid);
        rlMaCanotte = findViewById(R.id.rlMaCanotteId);
        rlpayer = findViewById(R.id.rlpayerId);
        ivnotificationcreditcard = findViewById(R.id.ivnotificationcreditcardId);
        ivdashboardcreditcardback = findViewById(R.id.ivdashboardcreditcardbackId);
        Ivwalletpoint=findViewById(R.id.IvwalletpointId);
        IvCreditcardpoint=findViewById(R.id.IvCreditcardpointId);
        clickListenerSetup();
    }

    private void clickListenerSetup() {
        rlpayer.setOnClickListener(this);
        rlcreditcard.setOnClickListener(this);
        ivdashboardcreditcardback.setOnClickListener(this);
        ivnotificationcreditcard.setOnClickListener(this);
        rlMaCanotte.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlMaCanotteId:
                Ivwalletpoint.setVisibility(View.VISIBLE);
                IvCreditcardpoint.setVisibility(View.GONE);
                credit="2";
                //credit = "prize";
                //CheckNetwork.nextScreenWithoutFinish(CreditCardPayment.this, PrizePoolActivity.class);
                break;
            case R.id.rlcreditcardid:
                IvCreditcardpoint.setVisibility(View.VISIBLE);
                Ivwalletpoint.setVisibility(View.GONE);
                credit="1";
                //credit = "Creditcard";
               // CheckNetwork.nextScreenWithoutFinish(CreditCardPayment.this, CreditCardActivity.class);
                break;
            case R.id.ivnotificationcreditcardId:
                CheckNetwork.nextScreenWithoutFinish(CreditCardPayment.this, NotificationActivity.class);
                break;
            case R.id.ivdashboardcreditcardbackId:
                CheckNetwork.backScreenWithouFinish(CreditCardPayment.this);
                break;
            case R.id.rlpayerId:
                if (credit.equals("1")){
                    CheckNetwork.nextScreenWithoutFinish(CreditCardPayment.this, CreditCardPayerActivity.class);
                }else if (credit.equals("2")){
                    CheckNetwork.nextScreenWithoutFinish(CreditCardPayment.this, PaymentConfirmationPage.class);

                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(CreditCardPayment.this);
    }
}

