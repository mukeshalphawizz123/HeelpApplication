package com.frelance.notificationPkg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.notificationPkg.notificatinPaymentPkg.NotificationPaymentActivity;
import com.frelance.notificationPkg.notificationMessagePkg.NotificationMessageActivity;
import com.frelance.notificationPkg.notificationMission_demandsPkg.NotificationMissionDemandActivity;
import com.frelance.notificationPkg.notificationOffersPkg.NotificationOfferActivity;
import com.frelance.notificationPkg.notificationReviewPkg.NotificationReviewActivity;
import com.frelance.paymentPkg.CreditCardPayment;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivnotificationback;
    private RelativeLayout rlPaymentNotification, rlnotification, rlStatusNotification, rlOffersNotification, rlMsgsNotification, rlAvisNotification;
    private ProgressBar pbNotification;
    private ApiServices apiServices;
    private String userId;
    private AppCompatTextView tvPaymentCount, tvStatusCount, tvOfferCount, tvMsgCount, tvReviewCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        init();
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            notification(userId);
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

    }

    private void init() {
        pbNotification = findViewById(R.id.pbNotificationId);
        rlPaymentNotification = findViewById(R.id.rlPaymentNotificationId);
        ivnotificationback = findViewById(R.id.ivnotificationbackId);
        rlnotification = findViewById(R.id.rlnotificationId);
        rlStatusNotification = findViewById(R.id.rlStatusNotificationId);
        rlOffersNotification = findViewById(R.id.rlOffersNotificationId);
        rlMsgsNotification = findViewById(R.id.rlMsgNotificationId);
        rlAvisNotification = findViewById(R.id.rlAvisNotificationId);
        tvPaymentCount = findViewById(R.id.tvPaymentCountId);
        tvStatusCount = findViewById(R.id.tvStatusCountId);
        tvOfferCount = findViewById(R.id.tvOfferCountId);
        tvMsgCount = findViewById(R.id.tvMsgCountId);
        tvReviewCount = findViewById(R.id.tvReviewCountId);
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
                CheckNetwork.nextScreenWithoutFinish(NotificationActivity.this, NotificationPaymentActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }


    private void notification(String userId) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.getnotificationcount(userId).enqueue(new Callback<NotificationCountResponseModle>() {
            @Override
            public void onResponse(Call<NotificationCountResponseModle> call, Response<NotificationCountResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    NotificationCountResponseModle notificationResponseModle = response.body();
                    if (notificationResponseModle.getStatus()) {

                        if (notificationResponseModle.getCountPayment() > 10) {
                            tvPaymentCount.setText("10" + "+");
                        } else {
                            tvPaymentCount.setText("" + notificationResponseModle.getCountPayment());
                        }

                        if (notificationResponseModle.getCountMissionanddemands() > 10) {
                            tvStatusCount.setText("10" + "+");
                        } else {
                            tvStatusCount.setText("" + notificationResponseModle.getCountMissionanddemands());
                        }


                        if (notificationResponseModle.getCountOffers() > 10) {
                            tvOfferCount.setText("10" + "+");
                        } else {
                            tvOfferCount.setText("" + notificationResponseModle.getCountOffers());
                        }


                        if (notificationResponseModle.getCountMessages() > 10) {
                            tvMsgCount.setText("10" + "+");
                        } else {
                            tvMsgCount.setText("" + notificationResponseModle.getCountMessages());
                        }

                        if (notificationResponseModle.getCountReviews() > 10) {
                            tvReviewCount.setText("10" + "+");
                        } else {
                            tvReviewCount.setText("" + notificationResponseModle.getCountReviews());
                        }


                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationCountResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }
}
