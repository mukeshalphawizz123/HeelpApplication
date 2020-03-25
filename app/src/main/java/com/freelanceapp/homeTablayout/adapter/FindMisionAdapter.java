package com.freelanceapp.homeTablayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;
import com.freelanceapp.homeTablayout.homeModel.RepondreUneDemandeModelPkg.YourMission;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FindMisionAdapter extends RecyclerView.Adapter<FindMisionAdapter.ViewHolder> {
    private Context context;
    private HomerespondtoarequestAppOnClickListener homerespondtoarequesttAppOnClickListener;
    private List<YourMission> findmission;


    public FindMisionAdapter(Context context, HomerespondtoarequestAppOnClickListener homerespondtoarequesttAppOnClickListener) {
        this.context = context;
        this.homerespondtoarequesttAppOnClickListener = homerespondtoarequesttAppOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_home_second_tab_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDescription.setText(findmission.get(position).getMissionDescription());
        holder.Tvduration.setText(findmission.get(position).getDuration());
        holder.TvBudget.setText(findmission.get(position).getMissionBudget());
        Picasso.with(context).load(RetrofitClient.MISSION_IMAGE_URL + findmission.get(position).getMissionImage()).into(holder.ivMissionImage);
        Picasso.with(context).load(RetrofitClient.MISSION_USER_IMAGE_URL + findmission.get(position).getUserImage()).into(holder.ivUserImage);
    }

    public void findmission(List<YourMission> findmission) {
        this.findmission = findmission;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // return 10;
        return findmission == null ? 0 : findmission.size();
    }

    public interface HomerespondtoarequestAppOnClickListener {
        void findmissionTabClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivMissionImage, ivUserImage;
        private AppCompatTextView tvDescription, Tvduration, TvBudget;
        private RelativeLayout RlDiscuss, Rlacceptoffer,rldiscuter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMissionImage = itemView.findViewById(R.id.ivMissionImageid);
            ivUserImage = itemView.findViewById(R.id.ivUserImageId);
            tvDescription = itemView.findViewById(R.id.tvDescriptionId);
            Tvduration = itemView.findViewById(R.id.TvdurationId);
            TvBudget = itemView.findViewById(R.id.TvBudgetId);
            RlDiscuss = itemView.findViewById(R.id.RlDiscussId);
            Rlacceptoffer = itemView.findViewById(R.id.RlacceptofferId);
            rldiscuter=itemView.findViewById(R.id.rldiscuterid);
            rldiscuter.setOnClickListener(this);
            RlDiscuss.setOnClickListener(this);
            Rlacceptoffer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            homerespondtoarequesttAppOnClickListener.findmissionTabClick(v, getAdapterPosition());
        }
    }
}
