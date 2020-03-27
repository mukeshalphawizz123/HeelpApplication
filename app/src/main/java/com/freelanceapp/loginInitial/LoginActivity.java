package com.freelanceapp.loginInitial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.freelanceapp.AcceptConditionActivity;
import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.CustomProgressbar;
import com.freelanceapp.CustomToast;
import com.freelanceapp.forgetpasswordPkg.ForgetPasswordActivity;
import com.freelanceapp.loginInitial.LoginPkgModel.Loginmodel;
import com.freelanceapp.OptionActivity;
import com.freelanceapp.R;
import com.freelanceapp.signUpInitial.SignupActivity;
import com.freelanceapp.homePkg.HomeActivity;
import com.freelanceapp.utility.AppSession;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlnextpager, rlFacbookLogin, rlgoogleLogin;
    private AppCompatTextView tvsignuppage, tvtermsandcondition, tvtextunderline;
    private TextInputEditText tiewEmailsignupId, tiewPasswordId;
    private View viewIdemail, viewIdpassword;
    private static Animation shakeAnimation;
    private ApiServices apiServices;
    private CheckBox rememberradio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        Window window = getWindow();
        StatusBarManagment.hideShowStatusBar(getApplicationContext(), window);
        init();

        rememberradio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    private void init() {
        rememberradio = findViewById(R.id.radioid);
        rlFacbookLogin = findViewById(R.id.rlFacbookLoginId);
        rlgoogleLogin = findViewById(R.id.rlgoogleLoginId);
        tiewEmailsignupId = findViewById(R.id.tiewEmailsignupId);
        tiewPasswordId = findViewById(R.id.tiewPasswordId);
        viewIdemail = findViewById(R.id.viewIdemail);
        viewIdpassword = findViewById(R.id.viewIdpassword);
        rlFacbookLogin.setOnClickListener(this);
        rlgoogleLogin.setOnClickListener(this);
        tvtermsandcondition = findViewById(R.id.tvtermsandconditionid);
        tvtermsandcondition.setOnClickListener(this);
        tvsignuppage = findViewById(R.id.tvsignuppageid);
        tvtextunderline = findViewById(R.id.tvtextunderlineid);
        rlnextpager = findViewById(R.id.rlnextpagerid);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        tvsignuppage.setOnClickListener(this);
        rlnextpager.setOnClickListener(this);
        SpannableString content = new SpannableString(getResources().getString(R.string.see_more));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvtermsandcondition.setText(content);
        SpannableString content1 = new SpannableString(getResources().getString(R.string.register));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvsignuppage.setText(content1);
        SpannableString content2 = new SpannableString(getResources().getString(R.string.forgot_password));
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        tvtextunderline.setText(content2);
        tvtextunderline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvtextunderlineid:
                CheckNetwork.nextScreenWithoutFinish(LoginActivity.this, ForgetPasswordActivity.class);
                break;
            case R.id.rlnextpagerid:
                CheckNetwork.nextScreen(LoginActivity.this, HomeActivity.class);
                //validation(v);
                break;
            case R.id.tvsignuppageid:
                CheckNetwork.nextScreen(LoginActivity.this, SignupActivity.class);
                break;
            case R.id.rlFacbookLoginId:
                Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rlgoogleLoginId:
                Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvtermsandconditionid:
                CheckNetwork.nextScreenWithoutFinish(LoginActivity.this, AcceptConditionActivity.class);
                break;

        }
    }

    private void validation(View v) {
        if (tiewEmailsignupId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Invalid Email");
            viewIdemail.startAnimation(shakeAnimation);
            viewIdemail.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (!Constants.emailValidator(tiewEmailsignupId.getText().toString())) {
            new CustomToast().Show_Toast(getApplicationContext(), v, "Invalid Email");
            viewIdemail.startAnimation(shakeAnimation);
            viewIdemail.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (tiewPasswordId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Invalid Password");
            viewIdpassword.startAnimation(shakeAnimation);
            viewIdpassword.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (CheckNetwork.isNetAvailable(this)) {
            login(tiewEmailsignupId.getText().toString().trim(), tiewPasswordId.getText().toString().trim());
            //   Toast.makeText(this, "ada", Toast.LENGTH_SHORT).show();

        } else {
            new CustomToast().Show_Toast(this, v, "Check Network Connection");
        }

    }

    private void login(String email, String password) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.login(email, password).enqueue(new Callback<Loginmodel>() {
            @Override
            public void onResponse(Call<Loginmodel> call, Response<Loginmodel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    Loginmodel getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        AppSession.setStringPreferences(LoginActivity.this, Constants.USERID, getLoginModle.getData().getId());
                        AppSession.setStringPreferences(LoginActivity.this, Constants.USERNAME, getLoginModle.getData().getUsername());
                        AppSession.setStringPreferences(LoginActivity.this, Constants.EMAIL, getLoginModle.getData().getEmail());
                        AppSession.setStringPreferences(LoginActivity.this, Constants.PICTURE_URL, getLoginModle.getData().getProfileUrl());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
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
                                Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<Loginmodel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.goTobackScreen(LoginActivity.this, OptionActivity.class);
    }
}