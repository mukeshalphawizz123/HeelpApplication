package com.frelance.clientProfilePkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.chatPkg.Adapter.ChatActivityMain;

import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg.GetProfileModle;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg.YourMission;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinetProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlcircleprrogressbar, rldiscuss, rlmessageuserprofile;
    private ImageView ivbackproffilemsg, ivnotificationuserprofile;
    private String clientId;
    private ApiServices apiServices;
    private List<YourMission> clientDetailList;
    private AppCompatImageView ivuserprofileimage;
    private AppCompatTextView tvname, tvdesination, tvpresentation, tvlevelofstudyy, tvfiledofstudyy, tvunivercityy, tvcategoriess, tvcompetencesss, tvRatingCountPlusMore;
    CircularProgressIndicator donutprogress;
    private RatingBar rbhelperprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        clientId = AppSession.getStringPreferences(getApplicationContext(), "clientId");
        init();

        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            getProfileApi(clientId);
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {

        rbhelperprofile = findViewById(R.id.rbhelperprofileId);
        tvRatingCountPlusMore = findViewById(R.id.tvRatingCountPlusMoreId);
        tvname = findViewById(R.id.tvnameid);
        tvdesination = findViewById(R.id.tvdesinationid);
        tvpresentation = findViewById(R.id.tvpresentationidd);
        tvlevelofstudyy = findViewById(R.id.tvlevelofstudyyid);
        tvfiledofstudyy = findViewById(R.id.tvfiledofstudyyid);
        tvunivercityy = findViewById(R.id.tvunivercityyid);
        tvcategoriess = findViewById(R.id.tvcategoriessid);
        tvcompetencesss = findViewById(R.id.tvcompetencesssid);
        ivuserprofileimage = findViewById(R.id.ivuserprofileimageId);


        ivnotificationuserprofile = findViewById(R.id.ivnotificationuserprofileId);
        ivnotificationuserprofile.setOnClickListener(this);

        ivbackproffilemsg = findViewById(R.id.ivbackproffilemsgId);
        ivbackproffilemsg.setOnClickListener(this);

        rlcircleprrogressbar = findViewById(R.id.rlcircleprrogressbarid);
        rldiscuss = findViewById(R.id.rldiscussid);
        rldiscuss.setOnClickListener(this);
        rlcircleprrogressbar.setOnClickListener(this);

        donutprogress = findViewById(R.id.donutprogressid);

// you can set max and current progress values individually
        donutprogress.setMaxProgress(5);
        //  donutprogress.setCurrentProgress(4.3);
// or all at once
        //   donutprogress.setProgress(4.3, 5);

// you can get progress values using following getters
        donutprogress.getProgress(); // returns 4
        donutprogress.getMaxProgress();// returns 5
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlcircleprrogressbarid:
                AppSession.setStringPreferences(getApplicationContext(), "clientEntry", "client");
                Intent intent = new Intent(getApplicationContext(), ProfileRatingDescriptionActivity.class);
                startActivity(intent);
                break;
            case R.id.rldiscussid:
                CheckNetwork.nextScreenWithoutFinish(ClinetProfileActivity.this, ChatActivityMain.class);
                break;
            case R.id.ivbackproffilemsgId:
                CheckNetwork.backScreenWithouFinish(ClinetProfileActivity.this);
                break;
            case R.id.ivnotificationuserprofileId:
                CheckNetwork.nextScreenWithoutFinish(ClinetProfileActivity.this, NotificationActivity.class);
                break;

        }
    }

    private void getProfileApi(String user_id) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.getMyProfile(user_id).enqueue(new Callback<GetProfileModle>() {
            @Override
            public void onResponse(Call<GetProfileModle> call, Response<GetProfileModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GetProfileModle missionlist = response.body();
                    if (missionlist.getStatus() == true) {
                        clientDetailList = missionlist.getYourMissions();
                        tvname.setText(clientDetailList.get(0).getFirstName() + " " + clientDetailList.get(0).getUsername());
                        // tvdesination.setText(clientDetailList.get(0).getSkills());
                        if (clientDetailList.get(0).getPictureUrl().isEmpty()) {

                        } else {
                            Picasso.with(getApplicationContext()).load(RetrofitClient.MISSION_USER_IMAGE_URL + clientDetailList
                                    .get(0).getPictureUrl()).into(ivuserprofileimage);
                        }


                        tvdesination.setText(clientDetailList.get(0).getSkills());
                        tvpresentation.setText(clientDetailList.get(0).getPresentation());
                        tvlevelofstudyy.setText(clientDetailList.get(0).getLevelOfStudy());
                        tvfiledofstudyy.setText(clientDetailList.get(0).getFieldOfStudy());
                        tvunivercityy.setText(clientDetailList.get(0).getUniversity());
                        tvcategoriess.setText(clientDetailList.get(0).getIntrestedCategory());
                        tvcompetencesss.setText(clientDetailList.get(0).getSkills());

                        donutprogress.setCurrentProgress(Double.parseDouble(clientDetailList.get(0).getProfileRate()));
// or all at once
                        donutprogress.setProgress(Double.parseDouble(clientDetailList.get(0).getProfileRate()), 5);
                        rbhelperprofile.setRating(Float.parseFloat(clientDetailList.get(0).getProfileRate()));
                        tvRatingCountPlusMore.setText(clientDetailList.get(0).getProfileRate());

                    }

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
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
            public void onFailure(Call<GetProfileModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }


}
