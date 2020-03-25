package com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.completeePkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.completeePkg.MyMissionCompleteActivity;


public class CompleteeFileUploadAdapter extends RecyclerView.Adapter<CompleteeFileUploadAdapter.ViewHolder> {

    private Context context;
    private CompleteeFileUploadAdapter.CompleteeFileUploadAppOnClickListener completeeFileUploadAppOnClickListener;


    public CompleteeFileUploadAdapter(Context context, MyMissionCompleteActivity completeeFileUploadAdapter) {
        this.context = context;
        this.completeeFileUploadAppOnClickListener = completeeFileUploadAppOnClickListener;
    }

    @NonNull
    @Override
    public CompleteeFileUploadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_completee_file_upload_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompleteeFileUploadAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public interface CompleteeFileUploadAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
