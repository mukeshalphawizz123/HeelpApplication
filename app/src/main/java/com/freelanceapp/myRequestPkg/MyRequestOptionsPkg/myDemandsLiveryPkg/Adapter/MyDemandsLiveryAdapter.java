package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsLiveryPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myDemandsLiveryPkg.MyDemandsDeliveryActivity;


public class MyDemandsLiveryAdapter extends RecyclerView.Adapter<MyDemandsLiveryAdapter.ViewHolder> {

    private Context context;
    private MyDemandsLiveryAdapter.MyRequestLiveryAppOnClickListener myRequestLiveryAppOnClickListener;


    public MyDemandsLiveryAdapter(Context context, MyDemandsDeliveryActivity myRequestLiveryAdapter) {
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

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public interface MyRequestLiveryAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
