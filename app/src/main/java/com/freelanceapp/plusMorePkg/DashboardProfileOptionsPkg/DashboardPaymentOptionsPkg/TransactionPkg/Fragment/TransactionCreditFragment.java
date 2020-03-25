package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.databinding.ActivityCreditTabBinding;
import com.freelanceapp.myMissionPkg.MymissionAdapter.MyMissionsecAdapter;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Adapter.TransactionCreditAdapter;

import java.util.ArrayList;

public class TransactionCreditFragment extends Fragment implements TransactionCreditAdapter.TransactionCreditAppOnClickListener {

    private ActivityCreditTabBinding activityCreditTabBinding;
    private TransactionCreditAdapter transactionCreditAdapter;
    private RecyclerView rvcreditcardproject;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityCreditTabBinding = DataBindingUtil.inflate(inflater, R.layout.activity_credit_tab, container, false);
        //View view = inflater.inflate(R.layout.activity_credit_tab, container, false);
        View view = activityCreditTabBinding.getRoot();
        init(view);
        return view;
    }

    private void init(View view) {
        rvcreditcardproject = view.findViewById(R.id.rvcreditcardprojectid);
        LinearLayoutManager layoutManagersec = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rvcreditcardproject.setLayoutManager(layoutManagersec);
        TransactionCreditAdapter transactionCreditAdapter = new TransactionCreditAdapter(getActivity(), this);
        rvcreditcardproject.setAdapter(transactionCreditAdapter);
    }


    public void onClick(View view, int position) {

    }
}
