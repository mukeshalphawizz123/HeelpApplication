package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.utility.CheckNetwork;

public class CreditCardActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivdashboardcreditcardbackId, ivnotificationcreditcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        //Toast.makeText(CreditCardActivity.this, "dsfdsf", Toast.LENGTH_LONG).show();
        init();
    }

    private void init() {
        ivnotificationcreditcard = findViewById(R.id.ivnotificationcreditcardId);
        ivdashboardcreditcardbackId = findViewById(R.id.ivdashboardcreditcardbackId);
        ivdashboardcreditcardbackId.setOnClickListener(this);
        ivnotificationcreditcard.setOnClickListener(this);
       /* ivdashboardcreditcardbackId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreditCardActivity.this, "dsfdsf", Toast.LENGTH_LONG).show();
            }
        });
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivdashboardcreditcardbackId:
                onBackPressed();
                break;
            case R.id.ivnotificationcreditcardId:
                CheckNetwork.nextScreenWithoutFinish(CreditCardActivity.this, NotificationActivity.class);
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(CreditCardActivity.this);
    }
}
