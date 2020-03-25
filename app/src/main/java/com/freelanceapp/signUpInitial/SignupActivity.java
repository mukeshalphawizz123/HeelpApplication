package com.freelanceapp.signUpInitial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.freelanceapp.AcceptConditionActivity;
import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.CustomProgressbar;
import com.freelanceapp.CustomToast;
import com.freelanceapp.loginInitial.LoginActivity;
import com.freelanceapp.OptionActivity;
import com.freelanceapp.R;
import com.freelanceapp.signUpInitial.RegistrationPkgModel.RegistrationModel;
import com.freelanceapp.homePkg.HomeActivity;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;
import com.freelanceapp.utility.StatusBarManagment;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlnextpage, rlFacbookLogin, rlgoogleLogin;
    private AppCompatTextView tvsignuptologin, tvtermsandcondition;
    private TextInputEditText tiewEmailsignupId, tiewUsernameId, tiewPasswordId, tiewcomfirmPasswordId;
    private View ViewIdemailRe, viewUserId, viewPasswordId, viewIdpasswordconfirm;
    private static Animation shakeAnimation;
    private ProgressBar pbLoginId;
    private ApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        Window window = getWindow();
        StatusBarManagment.hideShowStatusBar(getApplicationContext(), window);
        init();
    }

    private void init() {
        rlFacbookLogin = findViewById(R.id.rlFacbookLoginId);
        rlFacbookLogin.setOnClickListener(this);
        tiewEmailsignupId = findViewById(R.id.tiewEmailsignupId);
        tiewUsernameId = findViewById(R.id.tiewUsernameId);
        tiewPasswordId = findViewById(R.id.tiewPasswordId);
        tiewcomfirmPasswordId = findViewById(R.id.tiewcomfirmPasswordId);
        rlgoogleLogin = findViewById(R.id.rlgoogleLoginId);
        ViewIdemailRe = findViewById(R.id.ViewIdemailRe);
        viewUserId = findViewById(R.id.viewUserId);
        viewPasswordId = findViewById(R.id.viewPasswordId);
        viewIdpasswordconfirm = findViewById(R.id.viewIdpasswordconfirm);
        pbLoginId = findViewById(R.id.pbLoginId);
        rlgoogleLogin.setOnClickListener(this);
        tvtermsandcondition = findViewById(R.id.tvtermsandconditionid);
        tvtermsandcondition.setOnClickListener(this);
        rlnextpage = findViewById(R.id.rlnextpageid);
        tvsignuptologin = findViewById(R.id.tvsignuptologinid);
        rlnextpage.setOnClickListener(this);
        tvsignuptologin.setOnClickListener(this);
        SpannableString content = new SpannableString(getResources().getString(R.string.see_more));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvtermsandcondition.setText(content);
        SpannableString content1 = new SpannableString(getResources().getString(R.string.log_in));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvsignuptologin.setText(content1);
        shakeAnimation = AnimationUtils.loadAnimation(this,
                R.anim.shake);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlnextpageid:
                validation(v);
                break;
            case R.id.tvsignuptologinid:
                CheckNetwork.goTobackScreen(SignupActivity.this, LoginActivity.class);
                break;
            case R.id.tvtermsandconditionid:
                CheckNetwork.nextScreenWithoutFinish(SignupActivity.this, AcceptConditionActivity.class);
                break;
            case R.id.rlgoogleLoginId:
                // CheckNetwork.goTobackScreen(SignupActivity.this, HomeActivity.class);
                //  finish();
                break;
            case R.id.rlFacbookLoginId:
                // CheckNetwork.backScreenWithouFinish(SignupActivity.this);
                break;

        }
    }

    private void validation(View v) {
        if (tiewEmailsignupId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Invalid Email");
            ViewIdemailRe.startAnimation(shakeAnimation);
            ViewIdemailRe.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (!Constants.emailValidator(tiewEmailsignupId.getText().toString())) {
            new CustomToast().Show_Toast(getApplicationContext(), v, "Invalid Email");
            ViewIdemailRe.startAnimation(shakeAnimation);
            ViewIdemailRe.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (tiewUsernameId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Invalid UserName");
            viewUserId.startAnimation(shakeAnimation);
            viewUserId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (tiewPasswordId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Invalid Password");
            viewPasswordId.startAnimation(shakeAnimation);
            viewPasswordId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (!tiewPasswordId.getText().toString().matches(tiewcomfirmPasswordId.getText().toString())) {
            new CustomToast().Show_Toast(this, v, "Password Can't Match");
            viewIdpasswordconfirm.startAnimation(shakeAnimation);
            viewIdpasswordconfirm.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (CheckNetwork.isNetAvailable(this)) {
            signup(tiewEmailsignupId.getText().toString().trim(), tiewUsernameId.getText().toString(),
                    tiewPasswordId.getText().toString().trim());
        } else {
            new CustomToast().Show_Toast(this, v, "Check Network Connection");
        }
    }


    private void signup(String email, String userName, String password) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.signup(email, userName, password).enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    RegistrationModel getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        // CheckNetwork.nextScreenWithoutFinish(SignupActivity.this, HomeActivity.class);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(SignupActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.goTobackScreen(SignupActivity.this, OptionActivity.class);
    }
}
