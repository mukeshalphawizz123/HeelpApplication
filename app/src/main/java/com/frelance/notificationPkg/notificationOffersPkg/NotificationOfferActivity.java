package com.frelance.notificationPkg.notificationOffersPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.notificationPkg.NotificationModlePkg.Datum;
import com.frelance.notificationPkg.NotificationModlePkg.NotificationResponseModle;
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

public class NotificationOfferActivity extends AppCompatActivity implements NotificationOfferAdapter.NotificationOfferOnClickListener {

    private RecyclerView rvNotificationOffer;
    private ProgressBar pbNotOffers;
    private SwipeRefreshLayout wrlNotOffer;
    private NotificationOfferAdapter notificationOfferAdapter;
    private AppCompatImageView ivnotificationback;
    private ApiServices apiServices;
    private String userId;
    private List<Datum> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_offer);
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
            notification(userId, "3");
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        ivnotificationback = findViewById(R.id.ivnotificationbackId);
        wrlNotOffer = findViewById(R.id.wrlNotOfferId);
        rvNotificationOffer = findViewById(R.id.rvNotificationOfferid);
        pbNotOffers = findViewById(R.id.pbNotOffersId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvNotificationOffer.setLayoutManager(layoutManager);
        notificationOfferAdapter = new NotificationOfferAdapter(getApplicationContext(), this);
        rvNotificationOffer.setAdapter(notificationOfferAdapter);
    }

    @Override
    public void offerTabClick(View view, int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(NotificationOfferActivity.this);
    }


    private void notification(String userId, String typeId) {
        pbNotOffers.setVisibility(View.VISIBLE);
        apiServices.getNotification(userId, typeId).enqueue(new Callback<NotificationResponseModle>() {
            @Override
            public void onResponse(Call<NotificationResponseModle> call, Response<NotificationResponseModle> response) {
                if (response.isSuccessful()) {
                    pbNotOffers.setVisibility(View.GONE);
                    NotificationResponseModle notificationResponseModle = response.body();
                    if (notificationResponseModle.getStatus()) {
                        notificationList = notificationResponseModle.getData();
                        notificationOfferAdapter.addmymissionData(notificationList);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbNotOffers.setVisibility(View.GONE);
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
                pbNotOffers.setVisibility(View.GONE);
            }
        });

    }
}
