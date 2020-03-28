package com.freelanceapp.myDemandsPkg.MyRequestAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myDemandsPkg.Myrequestmodle.myrequestModle;

import java.util.ArrayList;

public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.ViewHolder> {

    private Context context;
    private ArrayList<myrequestModle> myrequestModleArrayList;
    private MyRequestAppOnClickListener myRequestAppOnClickListener;
    private int selectedPos = 0;


    public MyRequestAdapter(Context context, MyRequestAppOnClickListener myRequestAppOnClickListener) {
        this.context = context;
        this.myRequestAppOnClickListener = myRequestAppOnClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_myrequest_tab_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvtextmyrequest.setText(myrequestModleArrayList.get(position).getName());
        holder.changeToSelect(selectedPos == position ? Color.parseColor("#000000") : Color.GRAY);


    }

    public void addmyrequestData(ArrayList<myrequestModle> myrequestModleArrayList) {
        this.myrequestModleArrayList = myrequestModleArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        //return 0;
        return myrequestModleArrayList == null ? 0 : myrequestModleArrayList.size();
    }

    public interface MyRequestAppOnClickListener {
        void myRequestTabClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatTextView tvtextmyrequest;
        private RelativeLayout rlMyRequestRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtextmyrequest = itemView.findViewById(R.id.tvtextmyrequestid);
            rlMyRequestRow = itemView.findViewById(R.id.rlMyRequestRowId);
            rlMyRequestRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (myRequestAppOnClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            myRequestAppOnClickListener.myRequestTabClick(rlMyRequestRow, position);
                            notifyItemChanged(selectedPos);
                            selectedPos = getAdapterPosition();
                            notifyItemChanged(selectedPos);
                        }
                    }

                }
            });
        }

        @Override
        public void onClick(View v) {
            myRequestAppOnClickListener.myRequestTabClick(v, getAdapterPosition());
        }

        public void changeToSelect(int colorBackground) {
            tvtextmyrequest.setTextColor(colorBackground);
        }

    }


}
