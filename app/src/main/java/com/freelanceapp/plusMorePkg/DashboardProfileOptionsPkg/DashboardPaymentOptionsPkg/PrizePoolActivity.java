package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.freelanceapp.R;
import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.utility.CheckNetwork;

public class PrizePoolActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivdashboardprizepooloptionback, ivnotificationprizepool;
    private RelativeLayout rlsubmitbtnid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_pool);
        init();
    }

  /* @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.activity_prize_pool, container, false);
       init(view);
       return view;
    }
    */


    private void init() {
        ivnotificationprizepool = findViewById(R.id.ivnotificationprizepoolId);
        ivdashboardprizepooloptionback = findViewById(R.id.ivdashboardprizepooloptionbackId);
        rlsubmitbtnid = findViewById(R.id.rlsubmitbtnid);

        clickListenerSetup();
    }

    private void clickListenerSetup() {
        ivnotificationprizepool.setOnClickListener(this);
        ivdashboardprizepooloptionback.setOnClickListener(this);
        rlsubmitbtnid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivdashboardprizepooloptionbackId:
                onBackPressed();
                // CheckNetwork.goTobackScreen(PrizePoolActivity.this, DashboardPaymentActivity.class);
                // replaceFragement(new DashboardPaymentActivity());
                break;
            case R.id.ivnotificationprizepoolId:
                // replaceFragement(new NotificationActivity());
                //CheckNetwork.goTobackScreen(PrizePoolActivity.this, NotificationActivity.class);
                CheckNetwork.nextScreenWithoutFinish(PrizePoolActivity.this, NotificationActivity.class);
                break;
            case R.id.rlsubmitbtnid:
                Toast.makeText(this, "under development", Toast.LENGTH_SHORT).show();
                break;

        }
    }

  /*  private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(PrizePoolActivity.this);
    }
}
