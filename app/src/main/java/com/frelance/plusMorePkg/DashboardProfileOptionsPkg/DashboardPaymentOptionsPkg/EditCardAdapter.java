package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;
import com.frelance.R;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.getCardDetailModle.Datum;
import java.util.List;


public class EditCardAdapter extends RecyclerView.Adapter<EditCardAdapter.ViewHolder> {

    private Context context;
    private CardEditAppOnClickListener cardEditAppOnClickListener;
    private List<Datum> filesList;


    public EditCardAdapter(Context context, CreditCardListActivity cardEditAppOnClickListener) {
        this.context = context;
        this.cardEditAppOnClickListener = cardEditAppOnClickListener;
    }


    @NonNull
    @Override
    public EditCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.edit_card_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditCardAdapter.ViewHolder holder, int position) {
       holder.etCardHolderName.setText(filesList.get(position).getName());
       holder.etCardNumber.setText(filesList.get(position).getCardNo());
       holder.etExpiry.setText(filesList.get(position).getExpiry());
    }


    public void addCardFiles(List<Datum> filesList) {
        this.filesList = filesList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return filesList == null ? 0 : filesList.size();
    }


    public interface CardEditAppOnClickListener {
        void cardOnGoingOnClick(View view, int position,Datum datum);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatEditText etCardHolderName, etCardNumber, etExpiry;
        RelativeLayout rlediterRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etCardHolderName = itemView.findViewById(R.id.etCardHolderNameRowId);
            etCardNumber = itemView.findViewById(R.id.etCardNumberRowId);
            etExpiry = itemView.findViewById(R.id.etExpiryRowId);
            rlediterRow = itemView.findViewById(R.id.rlediterRowid);
            rlediterRow.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            cardEditAppOnClickListener.cardOnGoingOnClick(v, getAdapterPosition(),filesList.get(getAdapterPosition()));
        }
    }
}
