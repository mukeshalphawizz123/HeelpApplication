package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.TransactionDebitFragment;

public class TransactionDebitAdapter  extends RecyclerView.Adapter<TransactionDebitAdapter.ViewHolder> {

    private Context context;
    private TransactionDebitAdapter.TransactionDebitAppOnClickListener transactionDebitAppOnClickListener;


    public TransactionDebitAdapter(Context context, TransactionDebitFragment transactionCreditAdapter) {
        this.context = context;
        this.transactionDebitAppOnClickListener = transactionDebitAppOnClickListener;
    }
    @NonNull
    @Override
    public TransactionDebitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_debit_tab_row, parent, false);
        ViewHolder viewHolder =new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionDebitAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public interface TransactionDebitAppOnClickListener {
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
