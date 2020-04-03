package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsCompletePkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsCompletePkg.MyDemandsCompleteeActivity;

import java.util.ArrayList;


public class MyDemandsCompleteAdapter extends RecyclerView.Adapter<MyDemandsCompleteAdapter.ViewHolder> {

    private Context context;
    private MyRequestCompleteAppOnClickListener myRequestCompleteAppOnClickListener;
    private ArrayList<String> filesList;


    public MyDemandsCompleteAdapter(Context context, MyDemandsCompleteeActivity myRequestCompleteAppOnClickListener) {
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
        holder.tvfilename.setText(filesList.get(position));
    }


    public void addCompletedDemandsFiles(ArrayList<String> filesList) {
        this.filesList = filesList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        //return 0;
        return filesList == null ? 0 : filesList.size();
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
