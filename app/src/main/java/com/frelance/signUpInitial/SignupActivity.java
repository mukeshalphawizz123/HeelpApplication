package com.frelance.signUpInitial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
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
import android.widget.CompoundButton;
import android.widget.ProgressBar;
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
import com.frelance.AcceptConditionActivity;
import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.CustomToast;
import com.frelance.loginInitial.LoginActivity;
import com.frelance.OptionActivity;
import com.frelance.R;
import com.frelance.loginInitial.LoginPkgModel.socialLoginPkg.SocialLoginModel;
import com.frelance.signUpInitial.RegistrationPkgModel.RegistrationModel;
import com.frelance.homePkg.HomeActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.frelance.utility.StatusBarManagment;
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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlnextpage, rlFacbookLogin, rlgoogleLogin;
    private AppCompatTextView tvsignuptologin, tvtermsandcondition;
    private TextInputEditText tiewEmailsignupId, tiewUsernameId, tiewPasswordId, tiewcomfirmPasswordId;
    private View ViewIdemailRe, viewUserId, viewPasswordId, viewIdpasswordconfirm;
    private static Animation shakeAnimation;
    private ProgressBar pbLoginId;
    private ApiServices apiServices;
    private CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;
    private String token;
    private AppCompatCheckBox radio;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        Window window = getWindow();
        StatusBarManagment.hideShowStatusBar(getApplicationContext(), window);
        init();


        radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flag = "true";
                } else {
                    flag = "false";
                }
            }
        });

        if (!radio.isChecked()) {
            flag = "false";
        }

    }

    private void init() {
        radio = findViewById(R.id.radioid);
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
        FirebaseApp.initializeApp(SignupActivity.this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            // Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        token = task.getResult().getToken();
                        Log.d("token", token);

                    }
                });

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

                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                googleSignInClient.signOut();

                GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);

                if (alreadyloggedAccount != null) {
                    onLoggedIn(alreadyloggedAccount);
                } else {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, 101);
                    // sociallogin1("l","j","Facebook");
                }
                break;
            case R.id.rlFacbookLoginId:
                LoginManager.getInstance().logOut();
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                // CheckNetwork.backScreenWithouFinish(SignupActivity.this);
                break;

        }
    }

    private void validation(View v) {
        if (tiewUsernameId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "UserName Can't Empty");
            viewUserId.startAnimation(shakeAnimation);
            viewUserId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (tiewEmailsignupId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Email Can't Empty");
            ViewIdemailRe.startAnimation(shakeAnimation);
            ViewIdemailRe.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (!Constants.emailValidator(tiewEmailsignupId.getText().toString())) {
            new CustomToast().Show_Toast(getApplicationContext(), v, "Invalid Email");
            ViewIdemailRe.startAnimation(shakeAnimation);
            ViewIdemailRe.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (tiewPasswordId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Password Can't Empty");
            viewPasswordId.startAnimation(shakeAnimation);
            viewPasswordId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (tiewPasswordId.getText().toString().length() < 6) {
            new CustomToast().Show_Toast(this, v, "Password must contains at least 6 characters");
            viewPasswordId.startAnimation(shakeAnimation);
            viewPasswordId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (tiewcomfirmPasswordId.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Confirm Password Can't Empty");
            viewIdpasswordconfirm.startAnimation(shakeAnimation);
            viewIdpasswordconfirm.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (!tiewPasswordId.getText().toString().matches(tiewcomfirmPasswordId.getText().toString())) {
            new CustomToast().Show_Toast(this, v, "Password Not Matched");
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
        apiServices.signup(email, userName, password, token).enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    RegistrationModel getLoginModle = response.body();
                    if (getLoginModle.getStatus()) {
                        if (flag.equalsIgnoreCase("false")) {
                            AppSession.setStringPreferences(SignupActivity.this, "status", "");
                        } else {
                            AppSession.setStringPreferences(SignupActivity.this, "status", "auth");
                        }
                        AppSession.setStringPreferences(SignupActivity.this, Constants.USERID, getLoginModle.getData().get(0).getId());
                        AppSession.setStringPreferences(SignupActivity.this, Constants.USERNAME, getLoginModle.getData().get(0).getUsername());
                        AppSession.setStringPreferences(SignupActivity.this, Constants.EMAIL, getLoginModle.getData().get(0).getEmail());
                        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        // CheckNetwork.nextScreenWithoutFinish(SignupActivity.this, HomeActivity.class);
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
                                // Toast.makeText(SignupActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
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
                            sociallogin1(first_name + " " + last_name, id, "2");
                            //  Toast.makeText(SignupActivity.this, ""+first_name, Toast.LENGTH_SHORT).show();
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
        if (resultCode == Activity.RESULT_OK) {
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
        // Toast.makeText(this, "" + email, Toast.LENGTH_SHORT).show();
        sociallogin1(name, email, "1");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.goTobackScreen(SignupActivity.this, OptionActivity.class);
    }


    private void sociallogin1(String name, String email, final String status) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.sociallogin(name, email, status, token).enqueue(new Callback<SocialLoginModel>() {
            @Override
            public void onResponse(Call<SocialLoginModel> call, Response<SocialLoginModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    SocialLoginModel getLoginModle = response.body();
                    if (getLoginModle.getStatus()) {
                        AppSession.setStringPreferences(SignupActivity.this, "status", "auth");
                        Toast.makeText(SignupActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                        AppSession.setStringPreferences(SignupActivity.this, Constants.USERID, getLoginModle.getData().get(0).getId());
                        AppSession.setStringPreferences(SignupActivity.this, Constants.USERNAME, getLoginModle.getData().get(0).getUsername());
                        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!false) {
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
            public void onFailure(Call<SocialLoginModel> call, Throwable t) {
                Log.d("test", String.valueOf(t));
                CustomProgressbar.hideProgressBar();
            }
        });
    }
}
