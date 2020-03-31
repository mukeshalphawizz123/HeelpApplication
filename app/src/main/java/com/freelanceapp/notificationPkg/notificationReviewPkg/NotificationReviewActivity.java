package com.freelanceapp.notificationPkg.notificationReviewPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;
import com.freelanceapp.notificationPkg.NotificationModlePkg.Datum;
import com.freelanceapp.notificationPkg.NotificationModlePkg.NotificationResponseModle;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationReviewActivity extends AppCompatActivity implements NotificationReviewAdapter.NotReviewAppOnClickListener {

    private RecyclerView rvNotificationReview;
    private ProgressBar pbNotReview;
    private SwipeRefreshLayout wrlNotReview;
    private NotificationReviewAdapter notificationReviewAdapter;
    private AppCompatImageView ivnotificationback;
    private ApiServices apiServices;
    private String userId;
    private List<Datum> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_review);
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
            notification(userId, "5");
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        ivnotificationback = findViewById(R.id.ivnotificationbackId);
        wrlNotReview = findViewById(R.id.wrlNotReviewId);
        rvNotificationReview = findViewById(R.id.rvNotificationReviewid);
        pbNotReview = findViewById(R.id.pbNotReviewId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvNotificationReview.setLayoutManager(layoutManager);
        notificationReviewAdapter = new NotificationReviewAdapter(getApplicationContext(), this);
        rvNotificationReview.setAdapter(notificationReviewAdapter);
    }

    @Override
    public void myReviewTabClick(View view, int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(NotificationReviewActivity.this);
    }


    private void notification(String userId, String typeId) {
        pbNotReview.setVisibility(View.VISIBLE);
        apiServices.getNotification(userId, typeId).enqueue(new Callback<NotificationResponseModle>() {
            @Override
            public void onResponse(Call<NotificationResponseModle> call, Response<NotificationResponseModle> response) {
                if (response.isSuccessful()) {
                    pbNotReview.setVisibility(View.GONE);
                    NotificationResponseModle notificationResponseModle = response.body();
                    if (notificationResponseModle.getStatus()) {
                        notificationList = notificationResponseModle.getData();
                        notificationReviewAdapter.addmymissionData(notificationList);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbNotReview.setVisibility(View.GONE);
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
                pbNotReview.setVisibility(View.GONE);
            }
        });

    }
}
