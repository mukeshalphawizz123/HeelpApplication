package com.frelance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ProgressBar;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.homePkg.HomeActivity;
import com.frelance.splashBanner.BannerFragment;
import com.frelance.stripePaymentPkg.CheckoutActivityJava;
import com.frelance.utility.AppSession;
import com.frelance.utility.PrefData;
import com.frelance.utility.StatusBarManagment;

import java.util.Locale;


public class SplashActivity extends AppCompatActivity {
    private ApiServices apiServices;
    private ProgressBar Pbsetlang;
    private Context con;
    private PrefData prefData;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        flag = AppSession.getStringPreferences(SplashActivity.this, "status");
        init();

        Window window = getWindow();
        StatusBarManagment.hideShowStatusBar(getApplicationContext(), window);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (flag == null) {
                    Intent start = new Intent(SplashActivity.this, BannerFragment.class);
                    startActivity(start);
                    finish();
                } else if (flag.isEmpty()) {
                    Intent start = new Intent(SplashActivity.this, BannerFragment.class);
                    startActivity(start);
                    finish();
                } else {
                    Intent start = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(start);
                    finish();
                }

            }
        }, 2000);
        return;
    }

    private void init() {
        //Pbsetlang=findViewById(R.id.Pbsetlangid);
        con = getApplicationContext();
        prefData = new PrefData(con);
        Locale myLocale = new Locale(prefData.getCurrentLanguage());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        prefData.setCurrentLanguage(prefData.getCurrentLanguage());
    }

  /*  private void SetLang(String language_key) {
        Pbsetlang.setVisibility(View.VISIBLE);
        apiServices.setlang(language_key).enqueue(new Callback<SetLangmodel>() {
            @Override
            public void onResponse(Call<SetLangmodel> call, Response<SetLangmodel> response) {
                if (response.isSuccessful()) {
                    Pbsetlang.setVisibility(View.GONE);
                    SetLangmodel setlangmodel = response.body();
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                Pbsetlang.setVisibility(View.GONE);
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
            public void onFailure(Call<SetLangmodel> call, Throwable t) {
                Pbsetlang.setVisibility(View.GONE);
            }
        });
    }*/
}