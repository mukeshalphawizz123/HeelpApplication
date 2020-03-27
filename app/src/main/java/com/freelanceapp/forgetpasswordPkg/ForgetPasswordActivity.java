package com.freelanceapp.forgetpasswordPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.CustomProgressbar;
import com.freelanceapp.CustomToast;
import com.freelanceapp.R;
import com.freelanceapp.homePkg.HomeActivity;
import com.freelanceapp.loginInitial.LoginActivity;
import com.freelanceapp.loginInitial.LoginPkgModel.Loginmodel;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgetPasswordActivity extends AppCompatActivity {

    private RelativeLayout rlForget;
    private TextInputEditText tiewEmailForget;
    private ProgressBar pbForget;
    private ApiServices apiServices;
    private static Animation shakeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();

        rlForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new CustomToast().Show_Toast(ForgetPasswordActivity.this, v, "Invalid Email");

                if (CheckNetwork.isNetAvailable(ForgetPasswordActivity.this)) {

                    if (tiewEmailForget.getText().toString().isEmpty()) {
                        new CustomToast().Show_Toast(ForgetPasswordActivity.this, v, "Can't Empty");
                        tiewEmailForget.startAnimation(shakeAnimation);
                        tiewEmailForget.getBackground().mutate().setColorFilter(ContextCompat.getColor(ForgetPasswordActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

                    } else if (!Constants.isValidEmail(tiewEmailForget.getText().toString())) {
                        new CustomToast().Show_Toast(ForgetPasswordActivity.this, v, "Invalid Email");
                        tiewEmailForget.startAnimation(shakeAnimation);
                        tiewEmailForget.getBackground().mutate().setColorFilter(ContextCompat.getColor(ForgetPasswordActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    } else {

                        forgetPassword(tiewEmailForget.getText().toString());

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Check Network Conncetion", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        rlForget = findViewById(R.id.rlForgetId);
        tiewEmailForget = findViewById(R.id.tiewEmailForgetId);
        pbForget = findViewById(R.id.pbForgetId);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(ForgetPasswordActivity.this);
    }

    private void forgetPassword(String email) {
        pbForget.setVisibility(View.VISIBLE);
        apiServices.forgot_pass(email).enqueue(new Callback<ForgetPasswordModle>() {
            @Override
            public void onResponse(Call<ForgetPasswordModle> call, Response<ForgetPasswordModle> response) {
                if (response.isSuccessful()) {
                    pbForget.setVisibility(View.GONE);
                    ForgetPasswordModle getLoginModle = response.body();
                    if (getLoginModle.getStatus()) {
                        Toast.makeText(ForgetPasswordActivity.this, getLoginModle.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, getLoginModle.getMessage(), Toast.LENGTH_LONG).show();
                     //   onBackPressed();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(ForgetPasswordActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });


    }

}
