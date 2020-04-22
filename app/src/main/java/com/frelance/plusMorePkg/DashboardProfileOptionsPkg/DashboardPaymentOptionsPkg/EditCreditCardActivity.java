package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.CustomToast;
import com.frelance.R;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditCreditCardActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivdashboardcreditcardbackId;
    private ApiServices apiServices;
    private String userId;
    AppCompatEditText etCardHolderName, etCardNumber, etExpiry;
    private RelativeLayout bottomRel;
    private String holdername, cardNo, exydate, cardid;
    String a;
    int keyDel;
    private static Animation shakeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credit_card);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        holdername = getIntent().getStringExtra("holdername");
        cardNo = getIntent().getStringExtra("cardNo");
        exydate = getIntent().getStringExtra("exydate");
        cardid = getIntent().getStringExtra("cardid");
        init();
    }

    private void init() {
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        etExpiry = findViewById(R.id.etExpiryRowId);
        etCardNumber = findViewById(R.id.etCardNumberRowId);
        etCardHolderName = findViewById(R.id.etCardHolderNameRowId);
        bottomRel = findViewById(R.id.bottomRel);
        ivdashboardcreditcardbackId = findViewById(R.id.ivdashboardcreditcardbackId);
        bottomRel.setOnClickListener(this);
        ivdashboardcreditcardbackId.setOnClickListener(this);
        etCardNumber.setText(cardNo);
        etCardHolderName.setText(holdername);
        etExpiry.setText(exydate);


        etExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && (editable.length() % 3) == 0) {
                    final char c = editable.charAt(editable.length() - 1);
                    if ('/' == c) {
                        editable.delete(editable.length() - 1, editable.length());
                    }
                }
                if (editable.length() > 0 && (editable.length() % 3) == 0) {
                    char c = editable.charAt(editable.length() - 1);
                    if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf("/")).length <= 2) {
                        editable.insert(editable.length() - 1, String.valueOf("/"));
                    }
                }
            }
        });


        etCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean flag = true;
                String eachBlock[] = etCardNumber.getText().toString().split("-");
                for (int i = 0; i < eachBlock.length; i++) {
                    if (eachBlock[i].length() > 4) {
                        flag = false;
                    }
                }
                if (flag) {

                    etCardNumber.setOnKeyListener(new View.OnKeyListener() {

                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                keyDel = 1;
                            return false;
                        }
                    });

                    if (keyDel == 0) {

                        if (((etCardNumber.getText().length() + 1) % 5) == 0) {

                            if (etCardNumber.getText().toString().split("-").length <= 3) {
                                etCardNumber.setText(etCardNumber.getText() + "-");
                                etCardNumber.setSelection(etCardNumber.getText().length());
                            }
                        }
                        a = etCardNumber.getText().toString();
                    } else {
                        a = etCardNumber.getText().toString();
                        keyDel = 0;
                    }

                } else {
                    etCardNumber.setText(a);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditCreditCardActivity.this, CreditCardListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottomRel:
                if (etCardHolderName.getText().toString().isEmpty()) {
                    new CustomToast().Show_Toast(this, v, "Can't Empty");
                    etCardHolderName.startAnimation(shakeAnimation);
                    etCardHolderName.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                } else if (etCardNumber.getText().toString().isEmpty()) {
                    new CustomToast().Show_Toast(this, v, "Can't Empty");
                    etCardNumber.startAnimation(shakeAnimation);
                    etCardNumber.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                } else if (etCardNumber.getText().toString().length() < 16) {
                    new CustomToast().Show_Toast(this, v, "Invalid Card Number");
                    etCardNumber.startAnimation(shakeAnimation);
                    etCardNumber.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                } else if (etExpiry.getText().toString().isEmpty()) {
                    new CustomToast().Show_Toast(this, v, "Can't Empty");
                    etExpiry.startAnimation(shakeAnimation);
                    etExpiry.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                } else if (etExpiry.getText().toString().length() < 5) {
                    new CustomToast().Show_Toast(this, v, "Wrong Expiry Date");
                    etExpiry.startAnimation(shakeAnimation);
                    etExpiry.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                } else {
                    if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                       // updateaddCard();
                    } else {
                        Toast.makeText(getApplicationContext(), "Check Network Conncetion", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.ivdashboardcreditcardbackId:
                onBackPressed();
                break;
        }
    }


   /* private void updateaddCard() {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.update_credit_card(etCardNumber.getText().toString(), etExpiry.getText().toString(),
                userId, etCardHolderName.getText().toString(), cardid).enqueue(new Callback<UpdateCardModel>() {
            @Override
            public void onResponse(Call<UpdateCardModel> call, Response<UpdateCardModel> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        UpdateCardModel body = response.body();
                        if (body.getStatus() == true) {
                            Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<UpdateCardModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }*/


}
