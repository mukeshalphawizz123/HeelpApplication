package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.CustomToast;
import com.frelance.R;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.AddCardModel;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.getCardDetailModle.Datum;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.getCardDetailModle.GetCarListModel;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.retrivecardPkg.RetrveCardModel;
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

public class CreditCardActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivdashboardcreditcardbackId, ivnotificationcreditcard;
    private RelativeLayout bottomRel, rlediter, rlAddANewCard;
    private ApiServices apiServices;
    private AppCompatEditText etCardHolderName, etCardNumber, etExpiry;
    String a;
    int keyDel;
    private String userId, email;
    private static Animation shakeAnimation;
    private List<Datum> datumList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        email = AppSession.getStringPreferences(getApplicationContext(), Constants.EMAIL);
        init();

        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            getCardList();
        } else {
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        rlediter = findViewById(R.id.rlediterid);
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
        rlediter.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlediterid:
                etCardHolderName.requestFocus();
                // CheckNetwork.nextScreenWithoutFinish(CreditCardActivity.this, CreditCardListActivity.class);
                break;
            case R.id.rlAddANewCardId:
                etCardHolderName.requestFocus();
                //  showDialog();
                break;
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
                        addCard();
                    } else {
                        Toast.makeText(getApplicationContext(), "Check Network Conncetion", Toast.LENGTH_LONG).show();
                    }
                }
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

    private void addCard() {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.add_credit_card(etCardNumber.getText().toString(),
                etExpiry.getText().toString(),
                userId,
                etCardHolderName.getText().toString()
                , email).enqueue(new Callback<AddCardModel>() {
            @Override
            public void onResponse(Call<AddCardModel> call, Response<AddCardModel> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        AddCardModel body = response.body();
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
            public void onFailure(Call<AddCardModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }


    private void getCardList() {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.retrieve_a_card("12").enqueue(new Callback<RetrveCardModel>() {
            @Override
            public void onResponse(Call<RetrveCardModel> call, Response<RetrveCardModel> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        RetrveCardModel body = response.body();
                        if (body.getStatus()) {
                            etCardHolderName.setText(body.getData().getData().get(0).getCustomer());
                            etCardNumber.setText(body.getData().getData().get(0).getLast4());
                            etExpiry.setText(body.getData().getData().get(0).getExpMonth() + body.getData().getData().get(0).getExpYear());
                            //  editCardAdapter.addCardFiles(datumList);
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
            public void onFailure(Call<RetrveCardModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }

}
