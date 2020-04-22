package com.frelance.stripePaymentPkg;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.CustomToast;
import com.frelance.R;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.CreditCardActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.addCardDetailPkg.AddCardModel;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("Registered")
public class AddCardActivityJava extends AppCompatActivity {
    private Stripe stripe;
    private AppCompatImageView ivBackCheckout;
    private AppCompatTextView payButton;
    private ApiServices apiServices;
    private String userId;
    private TextInputEditText etHolderName;
    private static Animation shakeAnimation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card_);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        PaymentConfiguration.init(getApplicationContext(), "pk_test_IKgHpz7lpleTM3rcFSnyoxC700UDOoixI7"); // Get your key here: https://stripe.com/docs/keys#obtain-api-keys
        payButton = findViewById(R.id.payButton);
        ivBackCheckout = findViewById(R.id.ivBackCheckoutId);
        etHolderName = findViewById(R.id.etHolderNameid);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        ivBackCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        payButton.setOnClickListener((View view) ->
        {
            if (etHolderName.getText().toString().isEmpty()) {
                if (etHolderName.getText().toString().isEmpty()) {
                    new CustomToast().Show_Toast(this, view, "Holder Name Can't Empty");
                }
            } else {
                CardMultilineWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                cardInputWidget.setPostalCodeRequired(false);
                Card card = cardInputWidget.getCard();
                if (card != null) {
                    stripe = new Stripe(getApplicationContext(), PaymentConfiguration.getInstance(getApplicationContext()).getPublishableKey());
                    stripe.createToken(card, new ApiResultCallback<Token>() {
                        @Override
                        public void onSuccess(@NonNull Token result) {
                            MediaType mediaType = MediaType.get("application/json; charset=utf-8");
                            String json = "{" + "\"currency\":\"usd\","
                                    + "\"items\":[" + "{\"id\":\"photo_subscription\"}" + "],"
                                    + "\"token\":\"" + result.getId() + "\"" + "}";
                            Log.v("token", result.getId());
                            addStripCard(userId, result.getId());
                        }

                        @Override
                        public void onError(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            // weakActivity.get().displayAlert("Failed to decode response from server", e.getLocalizedMessage(), false);
                        }
                    });
                }
            }
        });


    }

    private void addStripCard(String userId, String token) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.addStripCard(userId, token, etHolderName.getText().toString())
                .enqueue(new Callback<AddCardModel>() {
                    @Override
                    public void onResponse(Call<AddCardModel> call, Response<AddCardModel> response) {
                        if (response.isSuccessful()) {
                            CustomProgressbar.hideProgressBar();
                            AddCardModel getLoginModle = response.body();
                            if (getLoginModle.getStatus()) {
                                Toast.makeText(AddCardActivityJava.this, "Card added", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        } else {
                            if (response.code() == 400) {
                                if (!response.isSuccessful()) {
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(response.errorBody().string());
                                        CustomProgressbar.hideProgressBar();

                                        String message = jsonObject.getString("message");
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddCardModel> call, Throwable t) {
                        // Log.d("test", String.valueOf(t));
                        CustomProgressbar.hideProgressBar();
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.nextScreen(AddCardActivityJava.this, CreditCardActivity.class);
    }
}
