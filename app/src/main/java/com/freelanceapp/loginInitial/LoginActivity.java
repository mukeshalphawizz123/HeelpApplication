package com.freelanceapp.loginInitial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.freelanceapp.AcceptConditionActivity;
import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.CustomProgressbar;
import com.freelanceapp.CustomToast;
import com.freelanceapp.forgetpasswordPkg.ForgetPasswordActivity;
import com.freelanceapp.loginInitial.LoginPkgModel.Loginmodel;
import com.freelanceapp.OptionActivity;
import com.freelanceapp.R;
import com.freelanceapp.loginInitial.LoginPkgModel.socialLoginPkg.SocialLoginModel;
import com.freelanceapp.signUpInitial.SignupActivity;
import com.freelanceapp.homePkg.HomeActivity;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;
import com.freelanceapp.utility.StatusBarManagment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

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
    private String flag;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;
    private String token;

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
                if (isChecked) {
                    flag = "true";
                } else {
                    flag = "false";
                }
            }
        });

        if (!rememberradio.isChecked()) {
            flag = "false";
        }
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


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // .requestIdToken(getResources().getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        callbackManager = CallbackManager.Factory.create();
        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;
        if (!loggedOut) {
            LoginManager.getInstance().logOut();
        }
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        getUserProfile(accessToken);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        FirebaseApp.initializeApp(LoginActivity.this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                           // Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        token = task.getResult().getToken();
                      //  Log.d("token",token);

                    }
                });
    }



    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String id = object.getString("id");
                            sociallogin1(first_name + last_name, id, "2");
                            Toast.makeText(LoginActivity.this, ""+first_name, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 101) {
                try {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    onLoggedIn(account);
                } catch (ApiException e) {
                   // Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                }
            } else {
                callbackManager.onActivityResult(requestCode, resultCode, data);

            }
        }
    }
    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        String name = googleSignInAccount.getDisplayName();
        String email = googleSignInAccount.getEmail();
        Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();
        sociallogin1(name, email, "1");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvtextunderlineid:
                CheckNetwork.nextScreenWithoutFinish(LoginActivity.this, ForgetPasswordActivity.class);
                break;
            case R.id.rlnextpagerid:
                validation(v);
                break;
            case R.id.tvsignuppageid:
                CheckNetwork.nextScreen(LoginActivity.this, SignupActivity.class);
                break;
            case R.id.rlFacbookLoginId:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                break;
            case R.id.rlgoogleLoginId:
                GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
                if (alreadyloggedAccount != null) {
                    onLoggedIn(alreadyloggedAccount);
                } else {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, 101);
                    // sociallogin1("l","j","Facebook");
                }
                break;
            case R.id.tvtermsandconditionid:
                CheckNetwork.nextScreenWithoutFinish(LoginActivity.this, AcceptConditionActivity.class);
                break;

        }
    }

    private void validation(View v) {
        if (tiewEmailsignupId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Email Can't Empty");
            viewIdemail.startAnimation(shakeAnimation);
            viewIdemail.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (!Constants.emailValidator(tiewEmailsignupId.getText().toString())) {
            new CustomToast().Show_Toast(getApplicationContext(), v, "Invalid Email");
            viewIdemail.startAnimation(shakeAnimation);
            viewIdemail.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (tiewPasswordId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Password Can't Empty");
            viewIdpassword.startAnimation(shakeAnimation);
            viewIdpassword.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (CheckNetwork.isNetAvailable(this)) {
            login(tiewEmailsignupId.getText().toString().trim(), tiewPasswordId.getText().toString().trim());
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
                    if (getLoginModle.getStatus()) {
                        if (flag.equalsIgnoreCase("false")) {
                            AppSession.setStringPreferences(LoginActivity.this, "status", "");
                        } else {
                            AppSession.setStringPreferences(LoginActivity.this, "status", "auth");
                        }
                        Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_LONG).show();
                        AppSession.setStringPreferences(LoginActivity.this, Constants.USERID, getLoginModle.getData().getId());
                        AppSession.setStringPreferences(LoginActivity.this, Constants.USERNAME, getLoginModle.getData().getUsername());
                        AppSession.setStringPreferences(LoginActivity.this, Constants.EMAIL, getLoginModle.getData().getEmail());
                        AppSession.setStringPreferences(LoginActivity.this, Constants.PICTURE_URL, getLoginModle.getData().getProfileUrl());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), getLoginModle.getMessage(), Toast.LENGTH_LONG).show();

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
    private void sociallogin1(String name, String email, final String status) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.sociallogin(name,status, email, token).enqueue(new Callback<SocialLoginModel>() {
            @Override
            public void onResponse(Call<SocialLoginModel> call, Response<SocialLoginModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    SocialLoginModel getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
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
            public void onFailure(Call<SocialLoginModel> call, Throwable t) {
                Log.d("test", String.valueOf(t));
                CustomProgressbar.hideProgressBar();
            }
        });
    }

}