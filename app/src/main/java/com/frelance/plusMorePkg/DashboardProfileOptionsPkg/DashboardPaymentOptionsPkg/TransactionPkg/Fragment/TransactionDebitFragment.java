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
import com.frelance.databinding.ActivityDebitTabBinding;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Adapter.TransactionDebitAdapter;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.transactionModlePkg.transactioOutModlePkg.Datum;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.transactionModlePkg.transactioOutModlePkg.TransactionOutResponseModle;
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

public class TransactionDebitFragment extends Fragment implements TransactionDebitAdapter.TransactionDebitAppOnClickListener {

    private ActivityDebitTabBinding activityDebitTabBinding;
    private TransactionDebitAdapter transactionDebitAdapter;
    private RecyclerView rvdebitcardproject;
    private ApiServices apiServices;
    private String userId;
    private List<Datum> datumList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityDebitTabBinding = DataBindingUtil.inflate(inflater, R.layout.activity_debit_tab, container, false);
        // View view = inflater.inflate(R.layout.activity_debit_tab, container, false);
        View view = activityDebitTabBinding.getRoot();
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            getTransactionOut(userId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;

    }

    private void init(View view) {
        rvdebitcardproject = view.findViewById(R.id.rvdebitcardprojectid);
        LinearLayoutManager layoutManagersec = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvdebitcardproject.setLayoutManager(layoutManagersec);
         transactionDebitAdapter = new TransactionDebitAdapter(getActivity(), this);
        rvdebitcardproject.setAdapter(transactionDebitAdapter);
    }


    public void onClick(View view, int position) {


    }


    private void getTransactionOut(String user_id) {
        CustomProgressbar.showProgressBar(getActivity(), true);
        apiServices.payment_out(user_id).enqueue(new Callback<TransactionOutResponseModle>() {
            @Override
            public void onResponse(Call<TransactionOutResponseModle> call, Response<TransactionOutResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    TransactionOutResponseModle transactionInResponseModle = response.body();
                    if (transactionInResponseModle.getStatus()) {
                        datumList = transactionInResponseModle.getData();
                        transactionDebitAdapter.addmymissionData(datumList);
                    }

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<TransactionOutResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }
}
