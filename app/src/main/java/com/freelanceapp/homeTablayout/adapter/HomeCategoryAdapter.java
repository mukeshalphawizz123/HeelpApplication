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
import com.freelanceapp.homeTablayout.homeModel.Project;
import com.freelanceapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    private Context context;
    private List<Project> projectlist;
    private HomePublisherRequest homePublisherRequest;


    public interface HomePublisherRequest {
        void publishOnClick(View view, int position);
    }

    public HomeCategoryAdapter(Context context, HomePublisherRequest homePublisherRequest) {
        this.context = context;
        this.homePublisherRequest = homePublisherRequest;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_home_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvHomeData.setText(projectlist.get(position).getTitle());
        Picasso.with(context).load(RetrofitClient.IMAGE_URL + projectlist.get(position).getPictureUrl()).into(holder.ivphy);

    }

    public void projectlist(List<Project> projectlist) {
        this.projectlist = projectlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // return 6;
        return projectlist == null ? 0 : projectlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatTextView tvHomeData;
        public ImageView ivphy;
        public RelativeLayout rlhomeitems;

        public ViewHolder(View itemView) {
            super(itemView);
            tvHomeData = itemView.findViewById(R.id.tvHomeDataId);
            ivphy = itemView.findViewById(R.id.ivphyid);
            rlhomeitems = itemView.findViewById(R.id.rlhomeitemsRowid);
            rlhomeitems.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            homePublisherRequest.publishOnClick(v, getAdapterPosition());

        }
    }
}

