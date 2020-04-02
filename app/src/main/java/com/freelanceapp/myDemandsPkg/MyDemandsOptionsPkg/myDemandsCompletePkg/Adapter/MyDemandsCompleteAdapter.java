package com.freelanceapp.myDemandsPkg.MyDemandsOptionsPkg.myDemandsCompletePkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myDemandsPkg.MyDemandsOptionsPkg.myDemandsCompletePkg.MyDemandsCompleteeActivity;


public class MyDemandsCompleteAdapter extends RecyclerView.Adapter<MyDemandsCompleteAdapter.ViewHolder> {

    private Context context;
    private MyDemandsCompleteAdapter.MyRequestCompleteAppOnClickListener myRequestCompleteAppOnClickListener;


    public MyDemandsCompleteAdapter(Context context, MyDemandsCompleteeActivity myRequestCompleteAdapter) {
        this.context = context;
        this.myRequestCompleteAppOnClickListener = myRequestCompleteAppOnClickListener;
    }

    @NonNull
    @Override
    public MyDemandsCompleteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_my_request_complete_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDemandsCompleteAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public interface MyRequestCompleteAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlFileFolder;
        private TextView tvfilename;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlFileFolder = itemView.findViewById(R.id.rlFileFolderId);
            tvfilename = itemView.findViewById(R.id.tvfilenameid);
        }
    }
}
