package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.MyDemandsDeliveryActivity;

import java.util.ArrayList;


public class MyDemandsLiveryAdapter extends RecyclerView.Adapter<MyDemandsLiveryAdapter.ViewHolder> {

    private Context context;
    private MyRequestLiveryAppOnClickListener myRequestLiveryAppOnClickListener;
    private ArrayList<String> filesList;


    public MyDemandsLiveryAdapter(Context context, MyDemandsDeliveryActivity myRequestLiveryAppOnClickListener) {
        this.context = context;
        this.myRequestLiveryAppOnClickListener = myRequestLiveryAppOnClickListener;
    }

    @NonNull
    @Override
    public MyDemandsLiveryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_my_request_livery_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDemandsLiveryAdapter.ViewHolder holder, int position) {
        holder.tvfilename.setText(filesList.get(position));
    }


    public void addDeliveryDemandsFiles(ArrayList<String> filesList) {
        this.filesList = filesList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        //return 0;
        return filesList == null ? 0 : filesList.size();
    }

    public interface MyRequestLiveryAppOnClickListener {
        void myDemandDeliveryOnClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout rlFileFolder;
        private AppCompatTextView tvfilename;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlFileFolder = itemView.findViewById(R.id.rlFileFolderId);
            tvfilename = itemView.findViewById(R.id.tvfilenameid);
            rlFileFolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myRequestLiveryAppOnClickListener.myDemandDeliveryOnClick(v, getAdapterPosition());
        }
    }
}
