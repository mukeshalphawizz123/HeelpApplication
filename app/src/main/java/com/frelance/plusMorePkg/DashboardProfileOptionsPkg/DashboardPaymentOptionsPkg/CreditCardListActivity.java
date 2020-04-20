package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.AddCardModel;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.getCardDetailModle.Datum;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.getCardDetailModle.GetCarListModel;
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

public class CreditCardListActivity extends AppCompatActivity implements EditCardAdapter.CardEditAppOnClickListener {
    private ImageView ivdashboardcreditcardbackId, ivnotificationcreditcard;
    private RelativeLayout bottomRel, rlAddANewCard;
    private ApiServices apiServices;
    private AppCompatEditText etCardHolderName, etCardNumber, etExpiry;
    private RecyclerView rvEditCreditCard;
    private String userId;
    private EditCardAdapter editCardAdapter;
    private List<Datum> datumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_credit_card);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        init();

        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            getCardList();
        } else {
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        ivdashboardcreditcardbackId = findViewById(R.id.ivdashboardcreditcardbackId);
        rvEditCreditCard = findViewById(R.id.rvEditCreditCardId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvEditCreditCard.setLayoutManager(layoutManager);
        editCardAdapter = new EditCardAdapter(getApplicationContext(), this);
        rvEditCreditCard.setAdapter(editCardAdapter);

        ivdashboardcreditcardbackId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(CreditCardListActivity.this);
    }

    private void getCardList() {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.getCardList(userId).enqueue(new Callback<GetCarListModel>() {
            @Override
            public void onResponse(Call<GetCarListModel> call, Response<GetCarListModel> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        GetCarListModel body = response.body();
                        if (body.getStatus()) {
                            datumList = body.getData();
                            editCardAdapter.addCardFiles(datumList);
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
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCarListModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }


    @Override
    public void cardOnGoingOnClick(View view, int position, Datum datum) {
        switch (view.getId()) {
            case R.id.rlediterRowid:
               // Toast.makeText(getApplicationContext(), "sdfsd", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CreditCardListActivity.this, EditCreditCardActivity.class);
                intent.putExtra("holdername", datum.getName());
                intent.putExtra("cardNo", datum.getCardNo());
                intent.putExtra("exydate", datum.getExpiry());
                intent.putExtra("cardid", datum.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
        }
    }
}
