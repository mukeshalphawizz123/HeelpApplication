package com.freelanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.freelanceapp.forgetpasswordPkg.ForgetPasswordActivity;
import com.freelanceapp.loginInitial.LoginActivity;
import com.freelanceapp.signUpInitial.SignupActivity;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.StatusBarManagment;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class OptionActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlLogin, rlSignup, rlFacbookLogin, rlgoogleLogin;
    private AppCompatTextView tvUnderLine;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        Window window = getWindow();
        StatusBarManagment.hideShowStatusBar(getApplicationContext(), window);
        init();
    }

    private void init() {
        rlFacbookLogin = findViewById(R.id.rlFacbookLoginId);
        rlgoogleLogin = findViewById(R.id.rlgoogleLoginId);
        rlFacbookLogin.setOnClickListener(this);
        rlgoogleLogin.setOnClickListener(this);
        tvUnderLine = findViewById(R.id.tvUnderLineId);
        rlLogin = findViewById(R.id.rlLoginId);
        rlSignup = findViewById(R.id.rlSignupId);
        rlLogin.setOnClickListener(this);
        rlSignup.setOnClickListener(this);
        SpannableString content = new SpannableString(getResources().getString(R.string.forgot_password));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvUnderLine.setText(content);

        tvUnderLine.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvUnderLineId:
                CheckNetwork.nextScreenWithoutFinish(OptionActivity.this, ForgetPasswordActivity.class);
                break;
            case R.id.rlLoginId:
                CheckNetwork.nextScreen(OptionActivity.this, LoginActivity.class);
                break;
            case R.id.rlSignupId:
                CheckNetwork.nextScreen(OptionActivity.this, SignupActivity.class);
                break;
            case R.id.rlFacbookLoginId:
                /*CheckNetwork.goTobackScreen(OptionActivity.this, HomeActivity.class);*/
                /*finish();*/
                Toast.makeText(this, "Working....", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rlgoogleLoginId:
              /*  CheckNetwork.goTobackScreen(OptionActivity.this, HomeActivity.class);
                finish();*/
                Toast.makeText(this, "Working....", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
