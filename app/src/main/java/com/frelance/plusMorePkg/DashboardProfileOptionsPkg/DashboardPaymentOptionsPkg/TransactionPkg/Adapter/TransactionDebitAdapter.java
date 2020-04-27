package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.TransactionDebitFragment;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.transactionModlePkg.transactioOutModlePkg.Datum;
import com.frelance.utility.Constants;


import java.util.List;

public class TransactionDebitAdapter extends RecyclerView.Adapter<TransactionDebitAdapter.ViewHolder> {

    private Context context;
    private TransactionDebitAppOnClickListener transactionDebitAppOnClickListener;
    private List<Datum> transactionList;


    public TransactionDebitAdapter(Context context, TransactionDebitFragment transactionCreditAdapter) {
        this.context = context;
        this.transactionDebitAppOnClickListener = transactionDebitAppOnClickListener;
    }

    @NonNull
    @Override
    public TransactionDebitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_debit_tab_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvdebitdatetime.setText(Constants.transactionDate(transactionList.get(position).getCreatedDate()));
        holder.tvdebitprojecttitle.setText(transactionList.get(position).getProjectTitle());
        holder.tvdebitprojetcost.setText(transactionList.get(position).getAmount() + "€");
        holder.tvdebitprojectdescription.setText("Project of : " + transactionList.get(position).getUsername());

    }

    public void addmymissionData(List<Datum> transactionList) {
        this.transactionList = transactionList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return transactionList == null ? 0 : transactionList.size();
    }


    public interface TransactionDebitAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView tvdebitdatetime, tvdebitprojecttitle, tvdebitprojetcost, tvdebitprojectdescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvdebitdatetime = itemView.findViewById(R.id.tvdebitdatetimeid);
            tvdebitprojecttitle = itemView.findViewById(R.id.tvdebitprojecttitleid);
            tvdebitprojetcost = itemView.findViewById(R.id.tvdebitprojetcostid);
            tvdebitprojectdescription = itemView.findViewById(R.id.tvdebitprojectdescriptionid);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
