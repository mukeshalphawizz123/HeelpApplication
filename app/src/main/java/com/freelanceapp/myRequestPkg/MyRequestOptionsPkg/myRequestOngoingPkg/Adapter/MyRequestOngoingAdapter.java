package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestOngoingPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestOngoingPkg.MyRequestOngoingActivity;


public class MyRequestOngoingAdapter extends RecyclerView.Adapter<MyRequestOngoingAdapter.ViewHolder> {

    private Context context;
    private MyRequestOngoingAdapter.MyRequestOngoingAppOnClickListener myRequestOngoingAppOnClickListener;


    public MyRequestOngoingAdapter(Context context, MyRequestOngoingActivity myRequestOngoingAdapter) {
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
