package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.TransactionCreditFragment;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.transactionModlePkg.transactionInModlePkg.Datum;
import com.frelance.utility.Constants;

import java.util.List;


public class TransactionCreditAdapter extends RecyclerView.Adapter<TransactionCreditAdapter.ViewHolder> {

    private Context context;
    private TransactionCreditAppOnClickListener transactionCreditAppOnClickListener;
    private List<Datum> transactionList;


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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvdatetime.setText(Constants.transactionDate(transactionList.get(0).getCreatedDate()));
        holder.tvprojecttitle.setText(transactionList.get(0).getProjectTitle());
        holder.tvprojetcost.setText(transactionList.get(0).getAmount() + "€");
        holder.tvprojectdescription.setText("Project of : " + transactionList.get(0).getUsername());

    }

    public void addmymissionData(List<Datum> transactionList) {
        this.transactionList = transactionList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return transactionList == null ? 0 : transactionList.size();
    }

    public interface TransactionCreditAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatTextView tvdatetime, tvprojecttitle, tvprojetcost,
                tvprojectdescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvdatetime = itemView.findViewById(R.id.tvdatetimeid);
            tvprojecttitle = itemView.findViewById(R.id.tvprojecttitleid);
            tvprojetcost = itemView.findViewById(R.id.tvprojetcostid);
            tvprojectdescription = itemView.findViewById(R.id.tvprojectdescriptionid);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
