package com.frelance.myMissionPkg.MyMissionOptionsPkg.proposePkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.proposePkg.MyMissionProposeeActivity;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.proposePkg.myMissionProposedModlePkg.YourMission;
import com.frelance.utility.Constants;
import com.squareup.picasso.Picasso;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_proposee_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMyMissionDelTitle.setText(yourMissionList.get(position).getCategory_title());
        holder.tvMyMissionDesDel.setText(yourMissionList.get(position).getMissionTitle());
        holder.tvOfferValueDel.setText(yourMissionList.get(position).getMissionBudget() + "€");
        holder.tvMyMissionDaysDel.setText(yourMissionList.get(position).getDuration());
        Picasso.with(context).load(RetrofitClient.IMAGE_URL + yourMissionList.get(position).getCategory_image()).into(holder.ivMymissionDel);
    }


    public interface ProposeAppOnClickListener {
        void mymissionpropose(View view, int position, YourMission yourMission);
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
            proposeAppOnClickListener.mymissionpropose(v, getAdapterPosition(), yourMissionList.get(getAdapterPosition()));

        }
    }
}
