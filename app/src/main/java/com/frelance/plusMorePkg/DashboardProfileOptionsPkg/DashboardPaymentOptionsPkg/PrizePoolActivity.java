package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getWalletModlePkg.GetWattetAmountModle;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.transactionModlePkg.transactionInModlePkg.TransactionInResponseModle;
import com.frelance.stripePaymentPkg.CheckoutActivityJava;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrizePoolActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivdashboardprizepooloptionback, ivnotificationprizepool;
    private RelativeLayout rlsubmitbtnid;
    private ApiServices apiServices;
    private String userId;
    private AppCompatTextView tvpriceamt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_pool);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        init();
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            getWalletAmount(userId);
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

    }


    private void init() {
        tvpriceamt = findViewById(R.id.tvpriceamtid);
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
                break;
            case R.id.ivnotificationprizepoolId:
                CheckNetwork.nextScreenWithoutFinish(PrizePoolActivity.this, NotificationActivity.class);
                break;
            case R.id.rlsubmitbtnid:
                Toast.makeText(getApplicationContext(), "Upcoming Service", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(PrizePoolActivity.this, CheckoutActivityJava.class);
                //startActivity(intent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(PrizePoolActivity.this);
    }

    private void getWalletAmount(String user_id) {
        CustomProgressbar.showProgressBar(PrizePoolActivity.this, true);
        apiServices.get_wallet_balance(user_id).enqueue(new Callback<GetWattetAmountModle>() {
            @Override
            public void onResponse(Call<GetWattetAmountModle> call, Response<GetWattetAmountModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GetWattetAmountModle getWattetAmountModle = response.body();
                    if (getWattetAmountModle.getStatus()) {
                        tvpriceamt.setText(getWattetAmountModle.getData() + " €");
                    }
                } else {
                    if (response.code() == 400) {
                        if (!false) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                //   Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetWattetAmountModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }
}
