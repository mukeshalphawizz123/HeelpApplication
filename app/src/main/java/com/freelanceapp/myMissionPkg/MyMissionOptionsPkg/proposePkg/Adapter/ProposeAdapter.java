package com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.proposePkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.completeePkg.Adapter.CompleteeFileUploadAdapter;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.proposePkg.MyMissionProposeeActivity;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.proposePkg.myMissionProposedModlePkg.YourMission;
import com.freelanceapp.myMissionPkg.Mymissionmodle.mymissionModle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProposeAdapter extends RecyclerView.Adapter<ProposeAdapter.ViewHolder> {
    private Context context;
    private ProposeAdapter.ProposeAppOnClickListener proposeAppOnClickListener;
    private List<YourMission> yourMissionList;


    public ProposeAdapter(Context context, MyMissionProposeeActivity proposeAppOnClickListener) {
        this.context = context;
        this.proposeAppOnClickListener = proposeAppOnClickListener;
    }


    @NonNull
    @Override
    public ProposeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_proposee_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProposeAdapter.ViewHolder holder, int position) {
        holder.tvMyMissionDelTitle.setText(yourMissionList.get(position).getMissionTitle());
        holder.tvMyMissionDesDel.setText(yourMissionList.get(position).getMessage());
        holder.tvOfferValueDel.setText(yourMissionList.get(position).getMissionBudget());
        holder.tvMyMissionDaysDel.setText(yourMissionList.get(position).getCreatedDate());
        Picasso.with(context).load(RetrofitClient.MYMISSIONANDMYDEMANDE_IMAGE_URL + yourMissionList.get(position).getMissionImage()).into(holder.ivMymissionDel);

    }


    public interface ProposeAppOnClickListener {
        void mymissionpropose(View view, int position);
    }


    public void addmymissionDataByProposed(List<YourMission> yourMissionList) {
        this.yourMissionList = yourMissionList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return yourMissionList == null ? 0 : yourMissionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout rlMyMissionDiscusseDel;
        private AppCompatImageView ivMymissionDel;
        private AppCompatTextView tvMyMissionDelTitle, tvMyMissionDesDel, tvOfferValueDel, tvMyMissionDaysDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMymissionDel = itemView.findViewById(R.id.ivMymissionDelid);
            rlMyMissionDiscusseDel = itemView.findViewById(R.id.rlMyMissionDiscusseDelId);
            tvMyMissionDelTitle = itemView.findViewById(R.id.tvMyMissionDelTitleId);
            tvMyMissionDesDel = itemView.findViewById(R.id.tvMyMissionDesDelId);
            tvOfferValueDel = itemView.findViewById(R.id.tvOfferValueDelId);
            tvMyMissionDaysDel = itemView.findViewById(R.id.tvMyMissionDaysDelId);
            rlMyMissionDiscusseDel.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            proposeAppOnClickListener.mymissionpropose(v, getAdapterPosition());

        }
    }
}
