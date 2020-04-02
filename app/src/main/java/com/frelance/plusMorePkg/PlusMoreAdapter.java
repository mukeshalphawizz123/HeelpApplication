package com.frelance.plusMorePkg;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PlusMoreAdapter extends RecyclerView.Adapter<PlusMoreAdapter.ViewHolder> {
    @NonNull
    @Override
    public PlusMoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PlusMoreAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface PlusMoreAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            //plusMoreAppOnClickListener.moreOnClick(v,getAdapterPosition());

        }
    }
}
