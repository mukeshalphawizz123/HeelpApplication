package com.frelance.notificationPkg.notificatinPaymentPkg;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.notificationPkg.NotificatinModel;
import com.frelance.notificationPkg.NotificationCountResponseModle;
import com.frelance.notificationPkg.NotificationModlePkg.Datum;
import com.frelance.notificationPkg.NotificationModlePkg.NotificationResponseModle;
import com.frelance.notificationPkg.RemoveNotificationCountModle;
import com.frelance.notificationPkg.notificationCountModlePkg.MsgModel;
import com.frelance.notificationPkg.notificationCountModlePkg.PaymentInterfaceModel;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationPaymentActivity extends AppCompatActivity implements NotificationPaymentAdapter.NotReviewAppOnClickListener {

    private RecyclerView rvNotificationReview;
    private ProgressBar pbNotReview;
    private SwipeRefreshLayout wrlNotReview;
    private NotificationPaymentAdapter notificationReviewAdapter;
    private AppCompatImageView ivnotificationback;
    private ApiServices apiServices;
    private String userId;
    private List<Datum> notificationList;
    private AppCompatTextView tvItemNotFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_payment);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);

        init();
        ivnotificationback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            notification(userId, "1");
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            update_notification_status(userId, "1");
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }


        wrlNotReview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                    notification(userId, "1");
                } else {
                    Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
                wrlNotReview.setRefreshing(false);
            }
        });

        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            notificationCount(userId);
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

    }

    private void init() {
        tvItemNotFound = findViewById(R.id.tvItemNotFoundId);
        ivnotificationback = findViewById(R.id.ivnotificationbackId);
        wrlNotReview = findViewById(R.id.wrlNotReviewId);
        rvNotificationReview = findViewById(R.id.rvNotificationReviewid);
        pbNotReview = findViewById(R.id.pbNotReviewId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvNotificationReview.setLayoutManager(layoutManager);
        notificationReviewAdapter = new NotificationPaymentAdapter(getApplicationContext(), this);
        rvNotificationReview.setAdapter(notificationReviewAdapter);
    }

    @Override
    public void myReviewTabClick(View view, int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(NotificationPaymentActivity.this);
    }


    private void notification(String userId, String typeId) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.getNotification(userId, typeId).enqueue(new Callback<NotificationResponseModle>() {
            @Override
            public void onResponse(Call<NotificationResponseModle> call, Response<NotificationResponseModle> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        NotificationResponseModle notificationResponseModle = response.body();
                        if (notificationResponseModle.getStatus()) {
                            tvItemNotFound.setVisibility(View.GONE);
                            notificationList = notificationResponseModle.getData();
                            notificationReviewAdapter.addmymissionData(notificationList);
                        } else {
                            tvItemNotFound.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
            public void onFailure(Call<NotificationResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    private void update_notification_status(String userId, String typeId) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.update_notification_status(userId, typeId).enqueue(new Callback<RemoveNotificationCountModle>() {
            @Override
            public void onResponse(Call<RemoveNotificationCountModle> call, Response<RemoveNotificationCountModle> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        RemoveNotificationCountModle notificationResponseModle = response.body();
                        if (notificationResponseModle.getStatus()) {
                            ////   notificationList = notificationResponseModle.getData());
                            //  notificationMessageAdapter.addmymissionData(notificationList);
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
                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RemoveNotificationCountModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    private void notificationCount(String userId) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.getnotificationcount(userId).enqueue(new Callback<NotificationCountResponseModle>() {
            @Override
            public void onResponse(Call<NotificationCountResponseModle> call, Response<NotificationCountResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
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

                        // notificatinCount.onTotalCounts(totalNotification);
                        NotificatinModel.getInstance().setNotificationCount("" + totalNotification);
                        PaymentInterfaceModel.getInstance().setNotificationPaymentCount("" + messageCountPayment);
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
                CustomProgressbar.hideProgressBar();
            }
        });

    }
}
