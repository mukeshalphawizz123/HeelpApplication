package com.frelance.makeAnOfferPkg;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.CustomToast;
import com.frelance.R;
import com.frelance.makeAnOfferPkg.Adapter.MakeanOfferAdapter;
import com.frelance.makeAnOfferPkg.makeAnOfferModlePkg.Datum;
import com.frelance.makeAnOfferPkg.makeAnOfferModlePkg.MakeOfferDetailModle;
import com.frelance.makeAnOfferPkg.makeAnOfferModlePkg.saveOfferModel.SaveOfferModle;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.notificationPkg.NotificationCountResponseModle;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.frelance.utility.FileDownloading;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MakeAnOfferActivity extends AppCompatActivity implements MakeanOfferAdapter.MakeanOfferAppOnClickListener, View.OnClickListener {
    private ImageView ivofferback, ivnotification;
    private RecyclerView rvfileuploadoffer;
    private RelativeLayout rlpublishapplicationns;
    private MakeanOfferAdapter makeanOfferAdapter;
    private AppCompatCheckBox radioid, radiogreenid;
    private ProgressBar pbMakeAnOffer;
    private ApiServices apiServices;
    private List<Datum> findmissionList;
    private TextView ettitletext, etdemande, etbudget;
    private String missionId, userid;
    private AppCompatEditText etAcceptOffer, etMakeOfferAmount;
    private String status;
    private String offerPrice;
    private static Animation shakeAnimation;
    private ArrayList<String> filesList;
    FileDownloading fileDownloading;
    private AppCompatTextView tvHomeNotificationCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_an_offer);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        fileDownloading = new FileDownloading(getApplicationContext());
        missionId = AppSession.getStringPreferences(getApplicationContext(), "mission_Id");
        //Toast.makeText(getApplicationContext(), missionId, Toast.LENGTH_LONG).show();
        userid = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        filesList = new ArrayList<>();
        init();
        if (CheckNetwork.isNetAvailable(MakeAnOfferActivity.this)) {
            makeAnOfferDetailApi();
        } else {
            Toast.makeText(MakeAnOfferActivity.this, "Check Network Connection", Toast.LENGTH_LONG).show();
        }

        if (CheckNetwork.isNetAvailable(MakeAnOfferActivity.this)) {
            notification(userid);
        } else {
            Toast.makeText(MakeAnOfferActivity.this, "Check Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        tvHomeNotificationCount = findViewById(R.id.tvHomeNotificationCountId);
        etMakeOfferAmount = findViewById(R.id.etMakeOfferAmountId);
        etAcceptOffer = findViewById(R.id.etAcceptOfferId);
        etbudget = findViewById(R.id.etbudgetid);
        etdemande = findViewById(R.id.etdemandeidd);
        ettitletext = findViewById(R.id.ettitletextid);
        pbMakeAnOffer = findViewById(R.id.pbMakeAnOfferId);
        ivnotification = findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        rlpublishapplicationns = findViewById(R.id.rlpublishapplicationnsid);
        rlpublishapplicationns.setOnClickListener(this);
        ivofferback = findViewById(R.id.ivofferbackId);
        radioid = findViewById(R.id.radioid);
        radiogreenid = findViewById(R.id.radiogreenid);
        ivofferback.setOnClickListener(this);
        rvfileuploadoffer = findViewById(R.id.rvfileuploadofferId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MakeAnOfferActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvfileuploadoffer.setLayoutManager(layoutManager);
        makeanOfferAdapter = new MakeanOfferAdapter(getApplicationContext(), this);
        rvfileuploadoffer.setAdapter(makeanOfferAdapter);
        radioid.setChecked(true);


        radioid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radiogreenid.setChecked(false);
                    status = "1";
                    offerPrice = etbudget.getText().toString();
                    //  Toast.makeText(getApplicationContext(), offerPrice, Toast.LENGTH_LONG).show();
                }
            }
        });
        radiogreenid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioid.setChecked(false);
                    status = "2";
                    offerPrice = etMakeOfferAmount.getText().toString();
                    // Toast.makeText(getApplicationContext(), offerPrice, Toast.LENGTH_LONG).show();
                }
            }
        });

        etbudget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                offerPrice = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etMakeOfferAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                offerPrice = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void makeAnOfferDetailApi() {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.getofferid(missionId).enqueue(new Callback<MakeOfferDetailModle>() {
            @Override
            public void onResponse(Call<MakeOfferDetailModle> call, Response<MakeOfferDetailModle> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        MakeOfferDetailModle makeOfferDetailModle = response.body();
                        findmissionList = makeOfferDetailModle.getData();
                        ettitletext.setText(findmissionList.get(0).getMissionTitle());
                        etdemande.setText(findmissionList.get(0).getMissionDescription());
                        etbudget.setText(findmissionList.get(0).getMissionBudget());

                        if (findmissionList.get(0).getMissionImage().isEmpty()) {

                        } else {
                            String[] imgesArray = findmissionList.get(0).getMissionImage().split(",");
                            for (int i = 0; i < imgesArray.length; i++) {
                                filesList.add(imgesArray[i]);
                            }
                        }

                        if (findmissionList.get(0).getMissionDoc().isEmpty()) {

                        } else {
                            String[] filesArray = findmissionList.get(0).getMissionDoc().split(",");
                            for (int i = 0; i < filesArray.length; i++) {
                                filesList.add(filesArray[i]);
                            }

                        }

                        makeanOfferAdapter.addDetailFiles(filesList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // makeanOfferAdapter.add();
                }
            }

            @Override
            public void onFailure(Call<MakeOfferDetailModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    private void makeAnOfferApi() {
        CustomProgressbar.showProgressBar(this, false);
       // Toast.makeText(getApplicationContext(), offerPrice, Toast.LENGTH_LONG).show();
        apiServices.makeAnOffer(missionId, userid, etAcceptOffer.getText().toString(), status, offerPrice).enqueue(new Callback<SaveOfferModle>() {
            @Override
            public void onResponse(Call<SaveOfferModle> call, Response<SaveOfferModle> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        SaveOfferModle body = response.body();
                        if (body.getStatus()) {
                            CheckNetwork.nextScreenWithoutFinish(MakeAnOfferActivity.this, OfferComfirmationActivity.class);
                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveOfferModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivofferbackId:
                onBackPressed();
                //onBackPressed();
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(MakeAnOfferActivity.this, NotificationActivity.class);
                break;
            case R.id.rlpublishapplicationnsid:
                if (etAcceptOffer.getText().toString().isEmpty()) {
                    new CustomToast().Show_Toast(this, v, "Can't Empty");
                    etAcceptOffer.startAnimation(shakeAnimation);
                    etAcceptOffer.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                } else {
                    if (CheckNetwork.isNetAvailable(MakeAnOfferActivity.this)) {
                        makeAnOfferApi();
                    } else {
                        Toast.makeText(MakeAnOfferActivity.this, "Check Network Connection", Toast.LENGTH_LONG).show();
                    }
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void myMakeAnOfferDetailTabClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlFileFolderId:
                fileDownloading.DownloadImage(RetrofitClient.DOWNLOAD_URL + filesList.get(position));
                break;
        }
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

