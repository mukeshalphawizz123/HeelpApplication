package com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg.MyMissionCompleteActivity;

import java.util.ArrayList;


public class CompleteeFileUploadAdapter extends RecyclerView.Adapter<CompleteeFileUploadAdapter.ViewHolder> {

    private Context context;
    private CompleteeFileUploadAppOnClickListener completeeFileUploadAppOnClickListener;
    private ArrayList<String> filesList;


    public CompleteeFileUploadAdapter(Context context, MyMissionCompleteActivity completeeFileUploadAppOnClickListener) {
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
        holder.tvfilename.setText(filesList.get(position));
    }

    public void addOnGoingFiles(ArrayList<String> filesList) {
        this.filesList = filesList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return filesList == null ? 0 : filesList.size();
    }


    public interface CompleteeFileUploadAppOnClickListener {
        void myMissCompleteOnClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout rlFileFolder;
        AppCompatTextView tvfilename;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlFileFolder = itemView.findViewById(R.id.rlFileFolderId);
            tvfilename = itemView.findViewById(R.id.tvfilenameid);
            rlFileFolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            completeeFileUploadAppOnClickListener.myMissCompleteOnClick(v, getAdapterPosition());
        }
    }
}
