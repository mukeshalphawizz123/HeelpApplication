package com.freelanceapp.userProfileRatingPkg;

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

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;

import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.userProfileRatingPkg.AdapterPkg.ProfileRatingAdapter;
import com.freelanceapp.userProfileRatingPkg.getuserreviewsModulePkg.GetUserReviewsModel;
import com.freelanceapp.userProfileRatingPkg.getuserreviewsModulePkg.Review;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;
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
    private String userImg, userName, userId;
    private AppCompatTextView tvname;
    private AppCompatImageView ivuserprofileimage;
    private RatingBar rbhelperprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_rating_description);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userImg = getIntent().getStringExtra("userImg");
        userName = getIntent().getStringExtra("userName");
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        init();
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            getReviews(userId);
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
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
                        tvname.setText(getUserReviewsModel.getUserDetail().getFullName());
                        rbhelperprofile.setNumStars(getUserReviewsModel.getUserDetail().getRatingAvg());
                        Picasso.with(getApplicationContext())
                                .load(RetrofitClient.MISSION_USER_IMAGE_URL + getUserReviewsModel.getUserDetail().getPictureUrl())
                                .into(ivuserprofileimage);
                        getuserreview = getUserReviewsModel.getReviews();
                        profileRatingAdapter.getuserreview(getuserreview);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                Pbgetuserreviews.setVisibility(View.GONE);
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
            public void onFailure(Call<GetUserReviewsModel> call, Throwable t) {
                Pbgetuserreviews.setVisibility(View.GONE);
            }
        });

    }

    private void init() {
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
