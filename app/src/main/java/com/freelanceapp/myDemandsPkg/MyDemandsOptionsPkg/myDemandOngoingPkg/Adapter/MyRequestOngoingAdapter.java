package com.freelanceapp.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.MyDemandsOngoingActivity;


public class MyRequestOngoingAdapter extends RecyclerView.Adapter<MyRequestOngoingAdapter.ViewHolder> {

    private Context context;
    private MyRequestOngoingAdapter.MyRequestOngoingAppOnClickListener myRequestOngoingAppOnClickListener;


    public MyRequestOngoingAdapter(Context context, MyDemandsOngoingActivity myRequestOngoingAdapter) {
        this.context = context;
        this.myRequestOngoingAppOnClickListener = myRequestOngoingAppOnClickListener;
    }


    @NonNull
    @Override
    public MyRequestOngoingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_my_request_ongoing_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestOngoingAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
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
