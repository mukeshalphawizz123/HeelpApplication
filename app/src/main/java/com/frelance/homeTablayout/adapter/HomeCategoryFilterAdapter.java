package com.frelance.homeTablayout.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.homeTablayout.homeModel.Project;
import com.frelance.homeTablayout.publishPkg.PostADemandActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HomeCategoryFilterAdapter extends RecyclerView.Adapter<HomeCategoryFilterAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Project> projectlist;
    private List<Project> projectlist1;
    private HomePublisherRequest homePublisherRequest;



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    projectlist = projectlist1;
                } else {
                    List<Project> filteredList = new ArrayList<>();
                    for (Project row : projectlist1) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    projectlist = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = projectlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                projectlist = (ArrayList<Project>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public interface HomePublisherRequest {
        void publishOnClick(View view, int position, Project project);
    }

    public HomeCategoryFilterAdapter(Context context, HomePublisherRequest homePublisherRequest) {
        this.context = context;
        this.homePublisherRequest = homePublisherRequest;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.home_filter_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvHomeData.setText(projectlist.get(position).getTitle());
        Picasso.with(context).load(RetrofitClient.IMAGE_URL + projectlist.get(position).getPictureUrl()).into(holder.ivphy);
        holder.rlhomeitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostADemandActivity.class);
                intent.putExtra("imagetitle",projectlist.get(position).getTitle());
                intent.putExtra("title", projectlist.get(position).getTitle());
                intent.putExtra("description", projectlist.get(position).getDescription());
                intent.putExtra("budget", projectlist.get(position).getBudget());
                intent.putExtra("imageUrl", projectlist.get(position).getPictureUrl());
             //  intent.putExtra("cat_id", projectlist.get(position).getPictureUrl());
                context.startActivity(intent);
            }
        });

    }


    public void projectlist(List<Project> projectlist1) {
        this.projectlist = projectlist1;
        this.projectlist1 = projectlist1;
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
            homePublisherRequest.publishOnClick(v, getAdapterPosition(),projectlist.get(getAdapterPosition()));

        }
    }


}

