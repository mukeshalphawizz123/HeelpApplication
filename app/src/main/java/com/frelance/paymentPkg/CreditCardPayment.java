package com.frelance.paymentPkg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.notificationPkg.NotificationCountResponseModle;
import com.frelance.paymentPkg.paymentModlePkg.ProjectAmountResponseModle;
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

public class CreditCardPayment extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivnotificationcreditcard, ivdashboardcreditcardback, Ivwalletpoint, IvCreditcardpoint;
    private RelativeLayout rlpayer, rlcreditcard, rlMaCanotte;
    private String credit, missionId;
    private ApiServices apiServices;
    private float projectBudget, bankFees, totalAmount;

    private AppCompatTextView tvcreditcardprice, tvcreditcardpricetwo, tvTotalProjecdPrice, tvHomeNotificationCount;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_payment);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        missionId = AppSession.getStringPreferences(getApplicationContext(), "pay_mission_id");
        userid = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        init();
        credit = "1";
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            getAmount(missionId);
        } else {
            Toast.makeText(getApplicationContext(), "Check Netork Connection", Toast.LENGTH_LONG).show();
        }

        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            notification(userid);
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

    }

    private void init() {
        tvHomeNotificationCount = findViewById(R.id.tvHomeNotificationCountId);
        tvTotalProjecdPrice = findViewById(R.id.tvTotalProjecdPriceId);
        tvcreditcardpricetwo = findViewById(R.id.tvcreditcardpricetwoidd);
        tvcreditcardprice = findViewById(R.id.tvcreditcardpriceidd);
        rlcreditcard = findViewById(R.id.rlcreditcardid);
        rlMaCanotte = findViewById(R.id.rlMaCanotteId);
        rlpayer = findViewById(R.id.rlpayerId);
        ivnotificationcreditcard = findViewById(R.id.ivnotificationcreditcardId);
        ivdashboardcreditcardback = findViewById(R.id.ivdashboardcreditcardbackId);
        Ivwalletpoint = findViewById(R.id.IvwalletpointId);
        IvCreditcardpoint = findViewById(R.id.IvCreditcardpointId);
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
                credit = "2";
                break;
            case R.id.rlcreditcardid:
                IvCreditcardpoint.setVisibility(View.VISIBLE);
                Ivwalletpoint.setVisibility(View.GONE);
                credit = "1";
                break;
            case R.id.ivnotificationcreditcardId:
                CheckNetwork.nextScreenWithoutFinish(CreditCardPayment.this, NotificationActivity.class);
                break;
            case R.id.ivdashboardcreditcardbackId:
                CheckNetwork.backScreenWithouFinish(CreditCardPayment.this);
                break;
            case R.id.rlpayerId:
                if (credit.equals("1")) {
                    CheckNetwork.nextScreenWithoutFinish(CreditCardPayment.this, CheckoutActivityJava.class);
                } else if (credit.equals("2")) {
                    Toast.makeText(getApplicationContext(), "There is insufficient balance on the wallet", Toast.LENGTH_LONG).show();
                    // CheckNetwork.nextScreenWithoutFinish(CreditCardPayment.this, PrizePoolActivity.class);

                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(CreditCardPayment.this);
    }


    private void getAmount(String missionId) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.getProjectPrize(missionId).enqueue(new Callback<ProjectAmountResponseModle>() {
            @Override
            public void onResponse(Call<ProjectAmountResponseModle> call, Response<ProjectAmountResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    try {
                        ProjectAmountResponseModle projectAmountResponseModle = response.body();
                        if (projectAmountResponseModle.getStatus()) {
                            // tvcreditcardpricetwo.setText(projectAmountResponseModle.getData().get(0).getBankFee() + "€");
                             tvcreditcardprice.setText(projectAmountResponseModle.getData().get(0).getMissionBudget() + "€");

                            if (projectAmountResponseModle.getData().get(0).getMissionBudget().isEmpty()) {
                                projectBudget = 0.0f;
                            } else {
                                projectBudget = Float.parseFloat(projectAmountResponseModle.getData().get(0).getMissionBudget());
                            }

                           /* if (projectAmountResponseModle.getData().get(0).getBankFee().isEmpty()) {
                                bankFees = 0.0f;
                            } else {
                                bankFees = Float.parseFloat(projectAmountResponseModle.getData().get(0).getBankFee());
                            }
*/
                            bankFees = (12 * projectBudget) / 100;
                            totalAmount = (float) (projectBudget + bankFees + 0.25);
                            int total = (int) projectBudget;
                            tvTotalProjecdPrice.setText((totalAmount) + "€");
                            AppSession.setStringPreferences(getApplicationContext(), "totalamount", "" + total);

                        } else {
                            tvcreditcardpricetwo.setText("0€");
                            tvcreditcardprice.setText("0€");
                            tvTotalProjecdPrice.setText("0€");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!false) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProjectAmountResponseModle> call, Throwable t) {
                //  Log.d("test", String.valueOf(t));
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    private void notification(String userId) {
        //    CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.getnotificationcount(userId).enqueue(new Callback<NotificationCountResponseModle>() {
            @Override
            public void onResponse(Call<NotificationCountResponseModle> call, Response<NotificationCountResponseModle> response) {
                if (response.isSuccessful()) {
                    // CustomProgressbar.hideProgressBar();
                    NotificationCountResponseModle notificationResponseModle = response.body();
                    if (notificationResponseModle.getStatus()) {
                        int messageCount = notificationResponseModle.getCountMessages();
                        int messageDemands = notificationResponseModle.getCountMissionanddemands();
                        int messageOffers = notificationResponseModle.getCountOffers();
                        int messageCountPayment = notificationResponseModle.getCountPayment();
                        int messageCountReveiews = notificationResponseModle.getCountReviews();

                        String totalNotification = String.valueOf(messageCount
                                + messageOffers
                                + messageDemands
                                + messageCountPayment
                                + messageCountReveiews);

                        if (totalNotification == null || totalNotification.isEmpty()) {
                            tvHomeNotificationCount.setVisibility(View.GONE);
                        } else {
                            tvHomeNotificationCount.setText(totalNotification);
                            tvHomeNotificationCount.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvHomeNotificationCount.setVisibility(View.GONE);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!false) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationCountResponseModle> call, Throwable t) {
                // CustomProgressbar.hideProgressBar();
                tvHomeNotificationCount.setVisibility(View.GONE);
            }
        });

    }
}

