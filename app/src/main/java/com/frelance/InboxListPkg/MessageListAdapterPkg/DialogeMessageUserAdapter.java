package com.frelance.InboxListPkg.MessageListAdapterPkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.InboxListPkg.msgModlePkg.Datum;
import com.frelance.R;
import com.frelance.homeTablayout.homeModel.Project;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogeMessageUserAdapter extends RecyclerView.Adapter<DialogeMessageUserAdapter.ViewHolder> implements Filterable {
    private Context context;
    private DialogeMessageUserAdapter.MessageToutAppOnClickListener messageToutAppOnClickListener;
    private List<Datum> datumList;
    private List<Datum> datumList1;


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    datumList = datumList1;
                } else {
                    List<Datum> filteredList = new ArrayList<>();
                    for (Datum row : datumList1) {

                        if (row.getFirstName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    datumList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = datumList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                datumList = (ArrayList<Datum>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public DialogeMessageUserAdapter(Context context, MessageToutAppOnClickListener messageToutAppOnClickListener) {
        this.context = context;
        this.messageToutAppOnClickListener = messageToutAppOnClickListener;
    }

    @NonNull
    @Override
    public DialogeMessageUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_messagelist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvUserNameMsg.setText(datumList.get(position).getFirstName() + " " + datumList.get(position).getLastName());
        Picasso.with(context).load(RetrofitClient.MISSION_USER_IMAGE_URL + datumList.get(position).getPictureUrl()).into(holder.ivUserMsg);

    }


    public void addmymissionData(List<Datum> datumList) {
        this.datumList = datumList;
        this.datumList1 = datumList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        //  return 15;
        return datumList == null ? 0 : datumList.size();
    }


    public interface MessageToutAppOnClickListener {
        void msgOnClick(View view, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RelativeLayout rlmsguser;
        CircleImageView ivUserMsg;
        AppCompatTextView tvUserNameMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlmsguser = itemView.findViewById(R.id.rlmsguserid);
            tvUserNameMsg = itemView.findViewById(R.id.tvUserNameMsgId);
            ivUserMsg = itemView.findViewById(R.id.ivUserMsgId);
            rlmsguser.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            messageToutAppOnClickListener.msgOnClick(v, getAdapterPosition());

        }
    }
}
