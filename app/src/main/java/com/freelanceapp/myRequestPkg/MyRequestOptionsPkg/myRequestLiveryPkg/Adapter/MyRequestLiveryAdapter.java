package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestLiveryPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestLiveryPkg.MyRequestLiveryActivity;


public class MyRequestLiveryAdapter extends RecyclerView.Adapter<MyRequestLiveryAdapter.ViewHolder> {

    private Context context;
    private MyRequestLiveryAdapter.MyRequestLiveryAppOnClickListener myRequestLiveryAppOnClickListener;


    public MyRequestLiveryAdapter(Context context, MyRequestLiveryActivity myRequestLiveryAdapter) {
        this.context = context;
        this.myRequestLiveryAppOnClickListener = myRequestLiveryAppOnClickListener;
    }

    @NonNull
    @Override
    public MyRequestLiveryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_my_request_livery_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestLiveryAdapter.ViewHolder holder, int position) {

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
