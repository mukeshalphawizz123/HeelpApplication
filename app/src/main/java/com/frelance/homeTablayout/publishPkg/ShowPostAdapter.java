package com.frelance.homeTablayout.publishPkg;

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
import com.frelance.utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ShowPostAdapter extends RecyclerView.Adapter<ShowPostAdapter.ViewHolder> {
    private Context context;
    private ShowPostAdapter.MyRequestAppOnClickListener myRequestAppOnClickListener;
    private String filterTag;
    private List<Datum> myrequestlist;


    public ShowPostAdapter(Context context, MyRequestAppOnClickListener myRequestAppOnClickListener) {
        this.context = context;
        this.myRequestAppOnClickListener = myRequestAppOnClickListener;
    }

    @NonNull
    @Override
    public ShowPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.show_post_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowPostAdapter.ViewHolder holder, int position) {
        holder.tvMyRequestFilterStatus.setText(filterTag);
        holder.tvmyrequestData.setText(myrequestlist.get(position).getCategoryTitle());
        holder.tvmyrequestdescription.setText(myrequestlist.get(position).getMission_title());
        holder.Tvmyreqby.setText("By:" + myrequestlist.get(position).getFullname());
        holder.tvmyrequesttime.setText(Constants.missionDemandDate(myrequestlist.get(position).getDate()));
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
