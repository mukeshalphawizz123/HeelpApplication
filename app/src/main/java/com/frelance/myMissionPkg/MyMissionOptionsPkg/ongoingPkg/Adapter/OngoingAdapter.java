package com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.MyMissionOngoingActivity;

import java.util.ArrayList;


public class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.ViewHolder> {

    private Context context;
    private OngoingAppOnClickListener ongoingAppOnClickListener;
    private ArrayList<String> filesList;


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
        holder.tvfilename.setText(filesList.get(position));
    }

    public void addOnGoingFiles(ArrayList<String> filesList) {
        this.filesList = filesList;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        //return 0;
        return filesList == null ? 0 : filesList.size();
    }


    public interface OngoingAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlFileFolder;
        AppCompatTextView tvfilename;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlFileFolder = itemView.findViewById(R.id.rlFileFolderId);
            tvfilename = itemView.findViewById(R.id.tvfilenameid);
        }
    }
}
