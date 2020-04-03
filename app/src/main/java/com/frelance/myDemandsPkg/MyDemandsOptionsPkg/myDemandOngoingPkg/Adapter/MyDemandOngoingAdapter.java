package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.MyDemandsOngoingActivity;

import java.util.ArrayList;


public class MyDemandOngoingAdapter extends RecyclerView.Adapter<MyDemandOngoingAdapter.ViewHolder> {

    private Context context;
    private MyRequestOngoingAppOnClickListener myRequestOngoingAppOnClickListener;
    private ArrayList<String> filesList;


    public MyDemandOngoingAdapter(Context context, MyDemandsOngoingActivity myRequestOngoingAppOnClickListener) {
        this.context = context;
        this.myRequestOngoingAppOnClickListener = myRequestOngoingAppOnClickListener;
    }


    @NonNull
    @Override
    public MyDemandOngoingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_my_request_ongoing_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDemandOngoingAdapter.ViewHolder holder, int position) {
        holder.tvfilename.setText(filesList.get(position));
    }


    public void addOnGoingDemandsFiles(ArrayList<String> filesList) {
        this.filesList = filesList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        //return 0;
        return filesList == null ? 0 : filesList.size();
    }


    public interface MyRequestOngoingAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlFileFolder;
        TextView tvfilename;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlFileFolder=itemView.findViewById(R.id.rlFileFolderId);
            tvfilename=itemView.findViewById(R.id.tvfilenameid);

        }
    }
}
