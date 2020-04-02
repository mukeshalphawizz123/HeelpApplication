package com.frelance.myDemandsPkg.MyRequestAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.myDemandsPkg.myRequestModlePkg.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyRequestsecAdapter extends RecyclerView.Adapter<MyRequestsecAdapter.ViewHolder> {
    private Context context;
    private MyRequestsecAdapter.MyRequestAppOnClickListener myRequestAppOnClickListener;
    private String filterTag;
    private List<Datum> myrequestlist;


    public MyRequestsecAdapter(Context context, MyRequestAppOnClickListener myRequestAppOnClickListener, String filterTag) {
        this.context = context;
        this.myRequestAppOnClickListener = myRequestAppOnClickListener;
        this.filterTag = filterTag;
    }

    @NonNull
    @Override
    public MyRequestsecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_myrequest_publiee_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestsecAdapter.ViewHolder holder, int position) {
        if (filterTag.equalsIgnoreCase(context.getResources().getString(R.string.Publiée))) {
            holder.tvMyRequestFilterStatus.setTextColor(context.getResources().getColor(R.color.lightBlue));
        } else if (filterTag.equalsIgnoreCase(context.getResources().getString(R.string.Encours))) {
            holder.tvMyRequestFilterStatus.setTextColor(context.getResources().getColor(R.color.lightYellow));
        } else if (filterTag.equalsIgnoreCase(context.getResources().getString(R.string.Livrée))) {
            holder.tvMyRequestFilterStatus.setTextColor(context.getResources().getColor(R.color.lightPink));
        } else if (filterTag.equalsIgnoreCase(context.getResources().getString(R.string.Completée))) {
            holder.tvMyRequestFilterStatus.setTextColor(context.getResources().getColor(R.color.lightGreen));
        } else if (filterTag.equalsIgnoreCase(context.getResources().getString(R.string.Enlitige))) {
            holder.tvMyRequestFilterStatus.setTextColor(context.getResources().getColor(R.color.lightRed));
        }
        holder.tvMyRequestFilterStatus.setText(filterTag);

        holder.tvmyrequestData.setText(myrequestlist.get(position).getCategoryTitle());
        holder.tvmyrequestdescription.setText(myrequestlist.get(position).getDescription());
        holder.Tvmyreqby.setText("By:" + myrequestlist.get(position).getFullname());
        holder.tvmyrequesttime.setText(myrequestlist.get(position).getDate());
        Picasso.with(context).load(RetrofitClient.MYMISSIONANDMYDEMANDE_IMAGE_URL + myrequestlist.get(position).getCategoryImage()).into(holder.ivmyrequestimage);
    }


    @Override
    public int getItemCount() {
        //return 7;
        return myrequestlist == null ? 0 : myrequestlist.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void myrequestlist(List<Datum> myrequestlist) {
        this.myrequestlist = myrequestlist;
        notifyDataSetChanged();
    }

    public interface MyRequestAppOnClickListener {
        void myRequestOnClick(View v, int Position, String text, Datum datum);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout rlmyrequest;
        private AppCompatTextView tvMyRequestFilterStatus;
        private ImageView ivmyrequestimage;
        private TextView tvmyrequestData, tvmyrequestdescription, Tvmyreqby, tvmyrequesttime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMyRequestFilterStatus = itemView.findViewById(R.id.tvMyRequestFilterStatusId);
            rlmyrequest = itemView.findViewById(R.id.rlmyrequestid);
            rlmyrequest.setOnClickListener(this);

            ivmyrequestimage = itemView.findViewById(R.id.ivmyrequestimageid);
            tvmyrequestData = itemView.findViewById(R.id.tvmyrequestDataId);
            tvmyrequestdescription = itemView.findViewById(R.id.tvmyrequestdescriptionid);
            Tvmyreqby = itemView.findViewById(R.id.TvmyreqbyId);
            tvmyrequesttime = itemView.findViewById(R.id.tvmyrequesttimeId);
        }

        @Override
        public void onClick(View v) {
            myRequestAppOnClickListener.myRequestOnClick(v,
                    getAdapterPosition(),
                    tvMyRequestFilterStatus.getText().toString(),
                    myrequestlist.get(getAdapterPosition()));

        }
    }
}
