package com.frelance.homeTablayout.publishPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.myDemandsPkg.myRequestModlePkg.Datum;
import com.frelance.myDemandsPkg.myRequestModlePkg.MyDemandeModel;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPostActivity extends AppCompatActivity implements ShowPostAdapter.MyRequestAppOnClickListener {

    private ApiServices apiServices;
    private String userId;
    private RecyclerView rvmyrequestsFrag;
    private ShowPostAdapter showPostAdapter;
    private List<Datum> myrequestlist;
    private AppCompatTextView Tvmyrequestitemnot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        init();
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            myRequest("0");
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
    }


    private void init() {
        rvmyrequestsFrag = findViewById(R.id.rvmyrequestsFragId);
        Tvmyrequestitemnot = findViewById(R.id.TvmyrequestitemnotId);
        myRequestSetup();
    }


    private void myRequestSetup() {
        LinearLayoutManager layoutManagersec = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvmyrequestsFrag.setLayoutManager(layoutManagersec);
        showPostAdapter = new ShowPostAdapter(getApplicationContext(), this);
        rvmyrequestsFrag.setAdapter(showPostAdapter);
    }


    private void myRequest(String status) {
        CustomProgressbar.showProgressBar(ShowPostActivity.this, false);
        apiServices.myrequest(status, userId).enqueue(new Callback<MyDemandeModel>() {
            @Override
            public void onResponse(Call<MyDemandeModel> call, Response<MyDemandeModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    MyDemandeModel requestlist = response.body();
                    if (requestlist.getStatus() == true) {
                        Tvmyrequestitemnot.setVisibility(View.GONE);
                        myrequestlist = requestlist.getData();
                        showPostAdapter.myrequestlist(myrequestlist);
                    } else
                        Tvmyrequestitemnot.setVisibility(View.VISIBLE);

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
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
            public void onFailure(Call<MyDemandeModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    @Override
    public void myRequestOnClick(View v, int Position, String text, Datum datum) {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(ShowPostActivity.this);
    }
}
