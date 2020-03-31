package com.freelanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.forgetpasswordPkg.ForgetPasswordActivity;
import com.freelanceapp.homePkg.HomeActivity;
import com.freelanceapp.loginInitial.LoginActivity;
import com.freelanceapp.loginInitial.LoginPkgModel.socialLoginPkg.SocialLoginModel;
import com.freelanceapp.signUpInitial.SignupActivity;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.StatusBarManagment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class OptionActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlLogin, rlSignup, rlFacbookLogin, rlgoogleLogin;
    private AppCompatTextView tvUnderLine;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;
    private String  token;
    private ApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
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
        FirebaseApp.initializeApp(OptionActivity.this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                           // Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        token = task.getResult().getToken();
                        Log.d("token",token);

                    }
                });


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
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                break;
            case R.id.rlgoogleLoginId:
              /*  CheckNetwork.goTobackScreen(OptionActivity.this, HomeActivity.class);
                finish();*/
                GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
                if (alreadyloggedAccount != null) {
                    onLoggedIn(alreadyloggedAccount);
                } else {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, 101);
                    // sociallogin1("l","j","Facebook");
                }
                break;
        }

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
                          //  Toast.makeText(OptionActivity.this, ""+first_name, Toast.LENGTH_SHORT).show();
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
                  //  Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
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



    private void sociallogin1(String name, String email, final String status ) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.sociallogin(name, status, email, token).enqueue(new Callback<SocialLoginModel>() {
            @Override
            public void onResponse(Call<SocialLoginModel> call, Response<SocialLoginModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    SocialLoginModel getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        Toast.makeText(OptionActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OptionActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                  /*      AppSession.setStringPreferences(getApplicationContext(), Constants.LOGIN, status);
                        AppSession.setStringPreferences(getApplicationContext(), Constants.USERNAME, getLoginModle.getData().getUserFullname());
                        AppSession.setStringPreferences(getApplicationContext(), Constants.USEREId, getLoginModle.getData().getUserId());
                  */    //  CheckNetwork.goToScreen(LoginActivity.this, HomeActivity.class);
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
