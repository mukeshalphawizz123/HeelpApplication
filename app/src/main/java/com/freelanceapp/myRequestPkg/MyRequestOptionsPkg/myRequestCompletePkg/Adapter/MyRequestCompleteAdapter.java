package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestCompletePkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestCompletePkg.MyRequestCompleteeActivity;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestLiveryPkg.Adapter.MyRequestLiveryAdapter;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestLiveryPkg.MyRequestLiveryActivity;


public class MyRequestCompleteAdapter extends RecyclerView.Adapter<MyRequestCompleteAdapter.ViewHolder>  {

    private Context context;
    private MyRequestCompleteAdapter.MyRequestCompleteAppOnClickListener myRequestCompleteAppOnClickListener;


    public MyRequestCompleteAdapter(Context context, MyRequestCompleteeActivity myRequestCompleteAdapter) {
        this.context = context;
        this.myRequestCompleteAppOnClickListener = myRequestCompleteAppOnClickListener;
    }

    @NonNull
    @Override
    public MyRequestCompleteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_my_request_complete_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestCompleteAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public interface MyRequestCompleteAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
