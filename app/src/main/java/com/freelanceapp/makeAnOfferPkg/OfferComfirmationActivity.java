package com.freelanceapp.makeAnOfferPkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.freelanceapp.R;
import com.freelanceapp.homePkg.HomeActivity;
import com.freelanceapp.utility.CheckNetwork;

public class OfferComfirmationActivity extends AppCompatActivity implements View.OnClickListener {
   private RelativeLayout Rlcomfirmationsendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_comfirmation);
        init();
    }

    private void init() {
        Rlcomfirmationsendbtn=findViewById(R.id.RlcomfirmationsendbtnId);
        Rlcomfirmationsendbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RlcomfirmationsendbtnId:
                Intent intent = new Intent(OfferComfirmationActivity.this,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                finishAffinity();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }
}
