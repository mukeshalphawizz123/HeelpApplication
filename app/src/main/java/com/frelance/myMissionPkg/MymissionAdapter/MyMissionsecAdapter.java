package com.frelance.myMissionPkg.MymissionAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.myMissionPkg.myMissionModlePkg.YourMission;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyMissionsecAdapter extends RecyclerView.Adapter<MyMissionsecAdapter.ViewHolder> {

    private Context context;
    private String filterTag;
    private MyMissionAppOnClickListener myMissionAppOnClickListener;
    private List<YourMission> mymissionlist;


    public interface MyMissionAppOnClickListener {
        void myMissionOnClick(View view, int position, String text,YourMission yourMission);

    }

    public void mymissionlist(List<YourMission> mymissionlist) {
        this.mymissionlist = mymissionlist;
        notifyDataSetChanged();
    }

    public MyMissionsecAdapter(Context context, MyMissionAppOnClickListener myMissionAppOnClickListener, String filterTag) {
        this.context = context;
        this.myMissionAppOnClickListener = myMissionAppOnClickListener;
        this.filterTag = filterTag;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyMissionsecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_mymission_proposee_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyMissionsecAdapter.ViewHolder holder, int position) {
        if (filterTag.equalsIgnoreCase(context.getResources().getString(R.string.Proposée))) {
            holder.tvMyMissionRowFilterStatus.setTextColor(context.getResources().getColor(R.color.lightBlue));
        } else if (filterTag.equalsIgnoreCase(context.getResources().getString(R.string.Encours))) {
            holder.tvMyMissionRowFilterStatus.setTextColor(context.getResources().getColor(R.color.lightYellow));
        } else if (filterTag.equalsIgnoreCase(context.getResources().getString(R.string.Livrée))) {
            holder.tvMyMissionRowFilterStatus.setTextColor(context.getResources().getColor(R.color.lightPink));
        } else if (filterTag.equalsIgnoreCase(context.getResources().getString(R.string.Completée))) {
            holder.tvMyMissionRowFilterStatus.setTextColor(context.getResources().getColor(R.color.lightGreen));
        } else if (filterTag.equalsIgnoreCase(context.getResources().getString(R.string.Enlitige))) {
            holder.tvMyMissionRowFilterStatus.setTextColor(context.getResources().getColor(R.color.lightRed));
        }
        holder.tvMyMissionRowFilterStatus.setText(filterTag);
        holder.TvMymissionproposeDes.setText(mymissionlist.get(position).getMissionDescription());
        holder.TvMyMissionproposeTime.setText(mymissionlist.get(position).getDate());
        holder.TvBy.setText("By:" + mymissionlist.get(position).getBy());
        holder.tvMyMissionTitle.setText(mymissionlist.get(position).getCategoryTitle());
        Picasso.with(context).load(RetrofitClient.MYMISSIONANDMYDEMANDE_IMAGE_URL + mymissionlist.get(position).getCategoryImage()).into(holder.ivmymissionimage);

    }

    @Override
    public int getItemCount() {
         return mymissionlist == null ? 0 : mymissionlist.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout rlmymission;
        private AppCompatTextView tvMyMissionRowFilterStatus, TvMymissionproposeDes, TvMyMissionproposeTime,
                TvBy, tvMyMissionTitle;
        private ImageView ivmymissionimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMyMissionRowFilterStatus = itemView.findViewById(R.id.tvMyMissionRowFilterStatusId);
            rlmymission = itemView.findViewById(R.id.rlmymissionid);
            rlmymission.setOnClickListener(this);
            TvMymissionproposeDes = itemView.findViewById(R.id.TvMymissionproposeDesid);
            TvMyMissionproposeTime = itemView.findViewById(R.id.TvMyMissionproposeTimeId);
            TvBy = itemView.findViewById(R.id.TvById);
            tvMyMissionTitle = itemView.findViewById(R.id.tvMyMissionTitleId);
            ivmymissionimage = itemView.findViewById(R.id.ivmymissionimageid);
            tvMyMissionRowFilterStatus.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            myMissionAppOnClickListener.myMissionOnClick(v, getAdapterPosition(), tvMyMissionRowFilterStatus.getText().toString(),mymissionlist.get(getAdapterPosition()));
        }
    }
}
