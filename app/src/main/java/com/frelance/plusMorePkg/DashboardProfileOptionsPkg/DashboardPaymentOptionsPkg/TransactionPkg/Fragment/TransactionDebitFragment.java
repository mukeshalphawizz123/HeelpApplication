package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.databinding.ActivityDebitTabBinding;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Adapter.TransactionDebitAdapter;

public class TransactionDebitFragment extends Fragment implements TransactionDebitAdapter.TransactionDebitAppOnClickListener {

    private ActivityDebitTabBinding activityDebitTabBinding;
    private TransactionDebitAdapter transactionDebitAdapter;
    private RecyclerView rvdebitcardproject;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityDebitTabBinding = DataBindingUtil.inflate(inflater, R.layout.activity_debit_tab, container, false);
        // View view = inflater.inflate(R.layout.activity_debit_tab, container, false);
        View view = activityDebitTabBinding.getRoot();
        init(view);
        return view;

    }

    private void init(View view) {
        rvdebitcardproject = view.findViewById(R.id.rvdebitcardprojectid);
        LinearLayoutManager layoutManagersec = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvdebitcardproject.setLayoutManager(layoutManagersec);
        TransactionDebitAdapter transactionDebitAdapter = new TransactionDebitAdapter(getActivity(), this);
        rvdebitcardproject.setAdapter(transactionDebitAdapter);
    }


    public void onClick(View view, int position) {


    }

}
