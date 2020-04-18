package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg.GetProfileModle;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditCardActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivdashboardcreditcardbackId, ivnotificationcreditcard;
    private RelativeLayout bottomRel, rlAddANewCard;
    private ApiServices apiServices;
    private AppCompatEditText etCardHolderName, etCardNumber, etExpiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();
    }

    private void init() {
        etCardHolderName = findViewById(R.id.etCardHolderNameId);
        etCardNumber = findViewById(R.id.etCardNumberId);
        etExpiry = findViewById(R.id.etExpiryId);
        rlAddANewCard = findViewById(R.id.rlAddANewCardId);
        bottomRel = findViewById(R.id.bottomRel);
        ivnotificationcreditcard = findViewById(R.id.ivnotificationcreditcardId);
        ivdashboardcreditcardbackId = findViewById(R.id.ivdashboardcreditcardbackId);
        ivdashboardcreditcardbackId.setOnClickListener(this);
        ivnotificationcreditcard.setOnClickListener(this);
        bottomRel.setOnClickListener(this);
        // bottomRel.setVisibility(View.GONE);
        rlAddANewCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlAddANewCardId:
                etCardHolderName.requestFocus();
                //  showDialog();
                break;
            case R.id.bottomRel:
                break;
            case R.id.ivdashboardcreditcardbackId:
                onBackPressed();
                break;
            case R.id.ivnotificationcreditcardId:
                CheckNetwork.nextScreenWithoutFinish(CreditCardActivity.this, NotificationActivity.class);
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(CreditCardActivity.this);
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(CreditCardActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_card_dialoge);
        RelativeLayout rlSaveCardId = (RelativeLayout) dialog.findViewById(R.id.rlSaveCardId);
        AppCompatEditText etFirstBoxId = (AppCompatEditText) dialog.findViewById(R.id.etFirstBoxId);
        AppCompatEditText etSecBoxId = (AppCompatEditText) dialog.findViewById(R.id.etSecBoxId);
        AppCompatEditText etThirdBoxId = (AppCompatEditText) dialog.findViewById(R.id.etThirdBoxId);
        AppCompatEditText etFourthBoxId = (AppCompatEditText) dialog.findViewById(R.id.etFourthBoxId);
        AppCompatEditText etMyId = (AppCompatEditText) dialog.findViewById(R.id.etMyId);
        AppCompatEditText etCvcId = (AppCompatEditText) dialog.findViewById(R.id.etCvcId);
        rlSaveCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void addCard(String user_id) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.getMyProfile(user_id).enqueue(new Callback<GetProfileModle>() {
            @Override
            public void onResponse(Call<GetProfileModle> call, Response<GetProfileModle> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        GetProfileModle missionlist = response.body();
                        if (missionlist.getStatus() == true) {

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
