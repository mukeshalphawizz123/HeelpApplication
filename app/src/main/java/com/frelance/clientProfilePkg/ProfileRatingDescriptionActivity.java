package com.frelance.clientProfilePkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;

import com.frelance.clientProfilePkg.getuserreviewsModulePkg.GetUserReviewsModel;
import com.frelance.clientProfilePkg.getuserreviewsModulePkg.Review;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.clientProfilePkg.AdapterPkg.ProfileRatingAdapter;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRatingDescriptionActivity extends AppCompatActivity implements ProfileRatingAdapter.ProfileRatingAppOnClickListener, View.OnClickListener {
    private ProfileRatingAdapter profileRatingAdapter;
    private RecyclerView rvratinglist;
    private ImageView ivbackmsgfeedback, ivnotificationuserprofile;
    private ProgressBar Pbgetuserreviews;
    private ApiServices apiServices;
    private List<Review> getuserreview;
    private String userImg, userName, userId, clientId, flag;
    private AppCompatTextView tvname;
    private AppCompatImageView ivuserprofileimage;
    private RatingBar rbhelperprofile;
    private AppCompatTextView tvReveiw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_rating_description);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userImg = getIntent().getStringExtra("userImg");
        userName = getIntent().getStringExtra("userName");
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        clientId = AppSession.getStringPreferences(getApplicationContext(), "clientId");
        flag = AppSession.getStringPreferences(getApplicationContext(), "clientEntry");
        init();
        Toast.makeText(getApplicationContext(), clientId, Toast.LENGTH_LONG).show();


        if (flag.equalsIgnoreCase("client")) {
            if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), clientId, Toast.LENGTH_LONG).show();
                getReviews(clientId);
            } else {
                Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
            }
        } else {
            if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_LONG).show();
                getReviews(userId);
            } else {
                Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void getReviews(String user_id) {
        Pbgetuserreviews.setVisibility(View.VISIBLE);
        apiServices.GetUserReviewsModel(user_id).enqueue(new Callback<GetUserReviewsModel>() {
            @Override
            public void onResponse(Call<GetUserReviewsModel> call, Response<GetUserReviewsModel> response) {
                if (response.isSuccessful()) {
                    Pbgetuserreviews.setVisibility(View.GONE);
                    try {
                        GetUserReviewsModel getUserReviewsModel = response.body();
                        if (getUserReviewsModel.getStatus()) {
                            tvReveiw.setVisibility(View.GONE);
                            tvname.setText(getUserReviewsModel.getUserDetail().getFullName());
                            rbhelperprofile.setRating(Float.parseFloat(getUserReviewsModel.getUserDetail().getRatingAvg()));
                            if (getUserReviewsModel.getUserDetail().getPictureUrl().isEmpty()) {
                            } else {
                                Picasso.with(getApplicationContext())
                                        .load(RetrofitClient.MISSION_USER_IMAGE_URL + getUserReviewsModel.getUserDetail().getPictureUrl())
                                        .into(ivuserprofileimage);
                            }
                            getuserreview = getUserReviewsModel.getReviews();
                            profileRatingAdapter.getuserreview(getuserreview);

                        } else {
                            tvReveiw.setVisibility(View.VISIBLE);
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
                                Pbgetuserreviews.setVisibility(View.GONE);
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
            public void onFailure(Call<GetUserReviewsModel> call, Throwable t) {
                Pbgetuserreviews.setVisibility(View.GONE);
            }
        });

    }

    private void init() {
        tvReveiw = findViewById(R.id.tvReveiwId);
        rbhelperprofile = findViewById(R.id.rbhelperprofileId);
        tvname = findViewById(R.id.tvnameid);
        ivuserprofileimage = findViewById(R.id.ivuserprofileimageId);
        Pbgetuserreviews = findViewById(R.id.PbgetuserreviewsId);
        ivnotificationuserprofile = findViewById(R.id.ivnotificationuserprofileId);
        ivnotificationuserprofile.setOnClickListener(this);
        ivbackmsgfeedback = findViewById(R.id.ivbackmsgfeedbackId);
        ivbackmsgfeedback.setOnClickListener(this);
        rvratinglist = findViewById(R.id.rvratinglistid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvratinglist.setLayoutManager(layoutManager);
        profileRatingAdapter = new ProfileRatingAdapter(getApplicationContext(), this);
        rvratinglist.setAdapter(profileRatingAdapter);

        tvname.setText(userName);
        Picasso.with(getApplicationContext())
                .load(RetrofitClient.MISSION_USER_IMAGE_URL + userImg)
                .into(ivuserprofileimage);

        //   userImg
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivbackmsgfeedbackId:
                CheckNetwork.backScreenWithouFinish(ProfileRatingDescriptionActivity.this);
                break;
            case R.id.ivnotificationuserprofileId:
                CheckNetwork.nextScreenWithoutFinish(ProfileRatingDescriptionActivity.this, NotificationActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(ProfileRatingDescriptionActivity.this);

    }
}
