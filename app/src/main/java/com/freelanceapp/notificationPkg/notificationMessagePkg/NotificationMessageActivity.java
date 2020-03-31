package com.freelanceapp.notificationPkg.notificationMessagePkg;

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
import com.freelanceapp.externalModlePkg.Data;
import com.freelanceapp.myMissionPkg.MymissionAdapter.MyMissionAdapter;
import com.freelanceapp.myMissionPkg.myMissionModlePkg.MyMissionModel;
import com.freelanceapp.notificationPkg.NotificationModlePkg.Datum;
import com.freelanceapp.notificationPkg.NotificationModlePkg.NotificationResponseModle;
import com.freelanceapp.notificationPkg.notificationReviewPkg.NotificationReviewActivity;
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

public class NotificationMessageActivity extends AppCompatActivity implements NotificationMessageAdapter.NotificationMessageAppOnClickListener {

    private RecyclerView rvNotificationMessage;
    private ProgressBar pbNotMsg;
    private SwipeRefreshLayout sflNotMs;
    private NotificationMessageAdapter notificationMessageAdapter;
    private AppCompatImageView ivnotificationback;
    private ApiServices apiServices;
    private String userId;
    private List<Datum> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_message);
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
            notification("1", "4");
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        ivnotificationback = findViewById(R.id.ivnotificationbackId);
        sflNotMs = findViewById(R.id.sflNotMsgId);
        rvNotificationMessage = findViewById(R.id.rvNotificationMessageid);
        pbNotMsg = findViewById(R.id.pbNotMsgId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvNotificationMessage.setLayoutManager(layoutManager);
        notificationMessageAdapter = new NotificationMessageAdapter(getApplicationContext(), this);
        rvNotificationMessage.setAdapter(notificationMessageAdapter);

    }

    @Override
    public void notMsgTabClick(View view, int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(NotificationMessageActivity.this);
    }

    private void notification(String userId, String typeId) {
        pbNotMsg.setVisibility(View.VISIBLE);
        apiServices.getNotification(userId, typeId).enqueue(new Callback<NotificationResponseModle>() {
            @Override
            public void onResponse(Call<NotificationResponseModle> call, Response<NotificationResponseModle> response) {
                if (response.isSuccessful()) {
                    pbNotMsg.setVisibility(View.GONE);
                    NotificationResponseModle notificationResponseModle = response.body();
                    if (notificationResponseModle.getStatus()) {
                        notificationList = notificationResponseModle.getData();
                        notificationMessageAdapter.addmymissionData(notificationList);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbNotMsg.setVisibility(View.GONE);
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
                pbNotMsg.setVisibility(View.GONE);
            }
        });

    }
}
