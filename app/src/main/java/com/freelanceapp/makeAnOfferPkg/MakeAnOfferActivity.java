package com.freelanceapp.makeAnOfferPkg;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;
import com.freelanceapp.makeAnOfferPkg.Adapter.MakeanOfferAdapter;
import com.freelanceapp.makeAnOfferPkg.makeAnOfferModlePkg.Datum;
import com.freelanceapp.makeAnOfferPkg.makeAnOfferModlePkg.MakeOfferDetailModle;
import com.freelanceapp.makeAnOfferPkg.makeAnOfferModlePkg.saveOfferModel.SaveOfferModle;
import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.utility.CheckNetwork;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_an_offer);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();
        if (CheckNetwork.isNetAvailable(MakeAnOfferActivity.this)) {
            makeAnOfferDetailApi();
        } else {
            Toast.makeText(MakeAnOfferActivity.this, "Check Network Connection", Toast.LENGTH_LONG).show();
        }

    }

    private void init() {
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
        GridLayoutManager layoutManager = new GridLayoutManager(MakeAnOfferActivity.this, 2);
        rvfileuploadoffer.setLayoutManager(layoutManager);
        MakeanOfferAdapter makeanOfferAdapter = new MakeanOfferAdapter(getApplicationContext(), this);
        rvfileuploadoffer.setAdapter(makeanOfferAdapter);
        radioid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radiogreenid.setChecked(false);
                }
            }
        });
        radiogreenid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioid.setChecked(false);
                }
            }
        });
    }


    private void makeAnOfferDetailApi() {
        pbMakeAnOffer.setVisibility(View.VISIBLE);
        apiServices.getofferid("2").enqueue(new Callback<MakeOfferDetailModle>() {
            @Override
            public void onResponse(Call<MakeOfferDetailModle> call, Response<MakeOfferDetailModle> response) {
                if (response.isSuccessful()) {
                    pbMakeAnOffer.setVisibility(View.GONE);
                    MakeOfferDetailModle makeOfferDetailModle = response.body();
                    findmissionList = makeOfferDetailModle.getData();
                    ettitletext.setText(findmissionList.get(0).getMissionTitle());
                    etdemande.setText(findmissionList.get(0).getMissionDescription());
                    etbudget.setText(findmissionList.get(0).getMissionBudget());
                }
            }

            @Override
            public void onFailure(Call<MakeOfferDetailModle> call, Throwable t) {
                pbMakeAnOffer.setVisibility(View.GONE);
            }
        });
    }

    private void makeAnOfferApi() {
        pbMakeAnOffer.setVisibility(View.VISIBLE);
        apiServices.makeAnOffer("12", "12", "testing", "1", "200").enqueue(new Callback<SaveOfferModle>() {
            @Override
            public void onResponse(Call<SaveOfferModle> call, Response<SaveOfferModle> response) {
                if (response.isSuccessful()) {
                    pbMakeAnOffer.setVisibility(View.GONE);
                    SaveOfferModle body = response.body();
                    if (body.getStatus()) {
                        CheckNetwork.nextScreenWithoutFinish(MakeAnOfferActivity.this, OfferComfirmationActivity.class);
                    }
                }
            }
            @Override
            public void onFailure(Call<SaveOfferModle> call, Throwable t) {
                pbMakeAnOffer.setVisibility(View.GONE);
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
                if (CheckNetwork.isNetAvailable(MakeAnOfferActivity.this)) {
                    makeAnOfferApi();
                } else {
                    Toast.makeText(MakeAnOfferActivity.this, "Check Network Connection", Toast.LENGTH_LONG).show();
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
}

