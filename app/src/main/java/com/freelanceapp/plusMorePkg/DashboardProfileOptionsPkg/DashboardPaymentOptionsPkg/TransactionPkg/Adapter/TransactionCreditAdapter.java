package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.TransactionCreditFragment;


public class TransactionCreditAdapter extends RecyclerView.Adapter<TransactionCreditAdapter.ViewHolder> {

    private Context context;
    private TransactionCreditAdapter.TransactionCreditAppOnClickListener transactionCreditAppOnClickListener;


    public TransactionCreditAdapter(Context context, TransactionCreditFragment transactionCreditAdapter) {
        this.context = context;
        this.transactionCreditAppOnClickListener = transactionCreditAppOnClickListener;
    }

    @NonNull
    @Override
    public TransactionCreditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_credit_tab_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionCreditAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public interface TransactionCreditAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
