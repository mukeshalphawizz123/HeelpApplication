package com.freelanceapp.userProfileRatingPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.SetLangPkg.SetLangmodel;
import com.freelanceapp.chatPkg.Adapter.ChatAdapter;

import com.freelanceapp.userProfileRatingPkg.AdapterPkg.ProfileRatingAdapter;
import com.freelanceapp.userProfileRatingPkg.getuserreviewsModulePkg.GetUserReviewsModel;
import com.freelanceapp.userProfileRatingPkg.getuserreviewsModulePkg.Review;
import com.freelanceapp.utility.CheckNetwork;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_rating_description);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            getReviews("16");
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
                    GetUserReviewsModel getUserReviewsModel = response.body();
                    getuserreview = getUserReviewsModel.getReviews();
                    profileRatingAdapter.getuserreview(getuserreview);
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
