package com.frelance.myMissionPkg.MyMissionOptionsPkg.liveryPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.liveryPkg.MyMissionDeliveryActivity;


public class LiveryAdapter extends RecyclerView.Adapter<LiveryAdapter.ViewHolder> {


    private Context context;
    private LiveryAdapter.LiveryAppOnClickListener liveryAppOnClickListener;


    public LiveryAdapter(Context context, MyMissionDeliveryActivity liveryAdapter) {
        this.context = context;
        this.liveryAppOnClickListener = liveryAppOnClickListener;
    }

    @NonNull
    @Override
    public LiveryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_livery_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public interface LiveryAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlFileFolder;
        private AppCompatTextView tvfilename;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlFileFolder = itemView.findViewById(R.id.rlFileFolderId);
            tvfilename = itemView.findViewById(R.id.tvfilenameid);
        }

    }
}
