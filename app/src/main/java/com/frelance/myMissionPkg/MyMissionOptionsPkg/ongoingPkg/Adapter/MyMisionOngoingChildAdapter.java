package com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.utility.FileDownloading;

import java.util.ArrayList;


public class MyMisionOngoingChildAdapter extends RecyclerView.Adapter<MyMisionOngoingChildAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> fileImageList;
    FileDownloading fileDownloading;


    public MyMisionOngoingChildAdapter(Context context) {
        this.context = context;
        fileDownloading = new FileDownloading(context);

    }

    @NonNull
    @Override
    public MyMisionOngoingChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_my_request_ongoing_child_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyMisionOngoingChildAdapter.ViewHolder holder, int position) {
        holder.tvfilename.setText(fileImageList.get(position));
        holder.rlFileFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileDownloading.DownloadImage(RetrofitClient.DOWNLOAD_URL + fileImageList.get(position));
            }
        });
    }

    public void addOnGoingDemandsFiles(ArrayList<String> fileImageList) {
        this.fileImageList = fileImageList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return fileImageList == null ? 0 : fileImageList.size();
    }


    public interface MyRequestOngoingAppOnClickListener {
        void myDemandOnGoingOnClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlFileFolder;
        TextView tvfilename;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlFileFolder = itemView.findViewById(R.id.rlFileFolderId);
            tvfilename = itemView.findViewById(R.id.tvfilenameid);
            // rlFileFolder.setOnClickListener(this);

        }

    }
}
