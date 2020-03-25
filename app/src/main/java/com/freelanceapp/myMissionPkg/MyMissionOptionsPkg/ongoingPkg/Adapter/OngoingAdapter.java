package com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.MyMissionOngoingActivity;


public class OngoingAdapter  extends RecyclerView.Adapter<OngoingAdapter.ViewHolder> {

    private Context context;
    private OngoingAdapter.OngoingAppOnClickListener ongoingAppOnClickListener;


    public OngoingAdapter(Context context, MyMissionOngoingActivity ongoingAdapter) {
        this.context = context;
        this.ongoingAppOnClickListener = ongoingAppOnClickListener;
    }

    @NonNull
    @Override
    public OngoingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_ongonig_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public interface OngoingAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
