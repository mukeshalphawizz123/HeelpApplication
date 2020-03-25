package com.freelanceapp.paymentPkg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.freelanceapp.R;
import com.freelanceapp.homePkg.HomeActivity;
import com.freelanceapp.utility.CheckNetwork;

public class ComfirmationActivity extends AppCompatActivity implements View.OnClickListener {
   private RelativeLayout Rlcomfirmationsendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirmation);
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
               CheckNetwork.goTobackScreen(ComfirmationActivity.this, HomeActivity.class);
                break;
        }
    }
}
