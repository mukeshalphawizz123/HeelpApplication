package com.frelance.stripePaymentPkg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.OptionActivity;
import com.frelance.R;
import com.frelance.homePkg.HomeActivity;
import com.frelance.loginInitial.LoginPkgModel.socialLoginPkg.SocialLoginModel;
import com.frelance.paymentPkg.PaymentConfirmationPage;
import com.frelance.stripePaymentPkg.stripModlePkg.PaymentResponseModle;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.google.android.gms.common.api.Api;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.CardMultilineWidget;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressLint("Registered")
public class CheckoutActivityJava extends AppCompatActivity {
    /**
     * This example collects card payments, implementing the guide here: https://stripe.com/docs/payments/accept-a-payment-charges#android
     * <p>
     * To run this app, follow the steps here: https://github.com/stripe-samples/card-payment-charges-api#how-to-run-locally
     */
    // 10.0.2.2 is the Android emulator's alias to localhost
    private static final String BACKEND_URL = "http://10.0.2.2:4242/";

    private OkHttpClient httpClient = new OkHttpClient();
    private Stripe stripe;

    private AppCompatImageView ivBackCheckout;
    private AppCompatTextView payButton;
    private ApiServices apiServices;
    private String userId, clientId;
    private String userName, categoryTitle, totalamount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        userName = AppSession.getStringPreferences(getApplicationContext(), Constants.USERNAME);
        clientId = AppSession.getStringPreferences(getApplicationContext(), "clientId");
        categoryTitle = AppSession.getStringPreferences(getApplicationContext(), "categoryTitle");
        totalamount = AppSession.getStringPreferences(getApplicationContext(), "totalamount");
        // PaymentConfiguration.init(getApplicationContext(), "pk_live_uCA4uxOsl9sM5e534oDNRbJK00mGBuYjsW"); // Get your key here: https://stripe.com/docs/keys#obtain-api-keys
        PaymentConfiguration.init(getApplicationContext(), "pk_test_IKgHpz7lpleTM3rcFSnyoxC700UDOoixI7"); // Get your key here: https://stripe.com/docs/keys#obtain-api-keys
        payButton = findViewById(R.id.payButton);
        ivBackCheckout = findViewById(R.id.ivBackCheckoutId);
        WeakReference<CheckoutActivityJava> weakActivity = new WeakReference<>(this);

        ivBackCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckNetwork.backScreenWithouFinish(getApplicationContext());
            }
        });

        payButton.setOnClickListener((View view) -> {
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
                        paymentStrip(clientId, userId, userName, "100", categoryTitle, result.getId());

                    }

                    @Override
                    public void onError(@NonNull Exception e) {
                        // weakActivity.get().displayAlert("Failed to decode response from server", e.getLocalizedMessage(), false);
                    }
                });
            }
        });

       /* payButton.setOnClickListener((View view) -> {
            CardMultilineWidget cardInputWidget = findViewById(R.id.cardInputWidget);
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

                        RequestBody body = RequestBody.create(json, mediaType);
                        Request request = new Request.Builder()
                                .url(BACKEND_URL + "pay")
                                .post(body)
                                .build();
                        httpClient.newCall(request)
                                .enqueue(new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                        // weakActivity.get().displayAlert("Failed to decode response from server", e.getLocalizedMessage(), false);
                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        if (!response.isSuccessful()) {
                                            //  weakActivity.get().displayAlert("Failed to decode response from server", "Error: " + response, false);
                                            return;
                                        }
                                        String responseData = response.body().string();
                                        try {
                                            JSONObject responseMap = new JSONObject(responseData);
                                            String error = responseMap.optString("error", null);
                                            if (error != null) {
                                                // weakActivity.get().displayAlert("Payment failed", error, false);
                                            } else {
                                                //weakActivity.get().displayAlert("Success", "Payment succeeded!", true);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                    }

                    @Override
                    public void onError(@NonNull Exception e) {
                        // weakActivity.get().displayAlert("Failed to decode response from server", e.getLocalizedMessage(), false);
                    }
                });
            }
        });*/
    }


    private void paymentStrip(String clientid, String userId, String username, String amount, String projectName, String token) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.paymentStrip(clientid, userId, username, amount, projectName, token)
                .enqueue(new retrofit2.Callback<PaymentResponseModle>() {
                    @Override
                    public void onResponse(retrofit2.Call<PaymentResponseModle> call, retrofit2.Response<PaymentResponseModle> response) {
                        if (response.isSuccessful()) {
                            CustomProgressbar.hideProgressBar();
                            PaymentResponseModle getLoginModle = response.body();
                            if (getLoginModle.getStatus()) {
                                Toast.makeText(CheckoutActivityJava.this, "Transaction Completed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CheckoutActivityJava.this, PaymentConfirmationPage.class);
                                startActivity(intent);
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
                    public void onFailure(retrofit2.Call<PaymentResponseModle> call, Throwable t) {
                        Log.d("test", String.valueOf(t));
                        CustomProgressbar.hideProgressBar();
                    }
                });
    }


    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean restartDemo) {
        Activity activity = this;
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(message);
            if (restartDemo) {
                builder.setPositiveButton("Restart demo",
                        (DialogInterface dialog, int index) -> {
                            CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                            cardInputWidget.clear();
                        });
            } else {
                builder.setPositiveButton("Ok", null);
            }
            builder.create().show();
        });
    }

   /* private void sociallogin1(String name, String email, final String status ) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.sociallogin(name, status, email, token).enqueue(new retrofit2.Callback<SocialLoginModel>() {
            @Override
            public void onResponse(retrofit2.Call<SocialLoginModel> call, retrofit2.Response<SocialLoginModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    SocialLoginModel getLoginModle = response.body();
                    if (getLoginModle.getStatus()) {
                        Toast.makeText(OptionActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                        AppSession.setStringPreferences(OptionActivity.this, Constants.USERID, getLoginModle.getData().get(0).getId());
                        AppSession.setStringPreferences(OptionActivity.this, Constants.USERNAME, getLoginModle.getData().get(0).getUsername());
                        Intent intent = new Intent(OptionActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
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
            public void onFailure(retrofit2.Call<SocialLoginModel> call, Throwable t) {
                Log.d("test", String.valueOf(t));
                CustomProgressbar.hideProgressBar();
            }
        });
    }*/
}
