package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.databinding.ActivityCreditTabBinding;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Adapter.TransactionCreditAdapter;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.transactionModlePkg.transactionInModlePkg.Datum;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.transactionModlePkg.transactionInModlePkg.TransactionInResponseModle;
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

public class TransactionCreditFragment extends Fragment implements TransactionCreditAdapter.TransactionCreditAppOnClickListener {

    private ActivityCreditTabBinding activityCreditTabBinding;
    private TransactionCreditAdapter transactionCreditAdapter;
    private RecyclerView rvcreditcardproject;
    private ApiServices apiServices;
    private String userId;
    private List<Datum> datumList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityCreditTabBinding = DataBindingUtil.inflate(inflater, R.layout.activity_credit_tab, container, false);
        View view = activityCreditTabBinding.getRoot();
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);

        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            getTransactionIn(userId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private void init(View view) {
        rvcreditcardproject = view.findViewById(R.id.rvcreditcardprojectid);
        LinearLayoutManager layoutManagersec = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvcreditcardproject.setLayoutManager(layoutManagersec);
         transactionCreditAdapter = new TransactionCreditAdapter(getActivity(), this);
        rvcreditcardproject.setAdapter(transactionCreditAdapter);
    }


    public void onClick(View view, int position) {

    }

    private void getTransactionIn(String user_id) {
        CustomProgressbar.showProgressBar(getActivity(), true);
        apiServices.payment_in(user_id).enqueue(new Callback<TransactionInResponseModle>() {
            @Override
            public void onResponse(Call<TransactionInResponseModle> call, Response<TransactionInResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    TransactionInResponseModle transactionInResponseModle = response.body();
                    if (transactionInResponseModle.getStatus()) {
                        datumList = transactionInResponseModle.getData();
                        transactionCreditAdapter.addmymissionData(datumList);
                    }

                } else {
                    if (response.code() == 400) {
                        if (!false) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionInResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }



    /**/
}
