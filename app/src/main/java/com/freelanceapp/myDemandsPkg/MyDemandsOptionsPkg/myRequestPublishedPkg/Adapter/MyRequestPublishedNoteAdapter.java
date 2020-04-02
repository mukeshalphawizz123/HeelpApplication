package com.freelanceapp.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;
import com.freelanceapp.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.YourMission;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyRequestPublishedNoteAdapter extends RecyclerView.Adapter<MyRequestPublishedNoteAdapter.ViewHolder> {
    private Context context;
    private MyRequestPublishedNoteAppOnClickListener myRequestPublishedNoteAppOnClickListener;
    private List<YourMission> mymissionModelArrayList;

    public MyRequestPublishedNoteAdapter(Context context, MyRequestPublishedNoteAppOnClickListener myRequestPublishedNoteAppOnClickListener) {
        this.context = context;
        this.myRequestPublishedNoteAppOnClickListener = myRequestPublishedNoteAppOnClickListener;
    }


    @NonNull
    @Override
    public MyRequestPublishedNoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_my_request_published_note_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestPublishedNoteAdapter.ViewHolder holder, int position) {
        holder.tvNoteBudgetsRow.setText(mymissionModelArrayList.get(position).getMissionBudget());
        // holder.tvDesBudgetsRow.setText(mymissionModelArrayList.get(position).getMessage());
        holder.tvDaysNotesRow.setText(mymissionModelArrayList.get(position).getCreatedDate());
        Picasso.with(context).load(RetrofitClient.MYMISSIONANDMYDEMANDE_IMAGE_URL + mymissionModelArrayList.get(position).getMissionImage()).into(holder.ivmymission);


    }

    public void addmyDemandsData(List<YourMission> mymissionModelArrayList) {
        this.mymissionModelArrayList = mymissionModelArrayList;
        notifyDataSetChanged();
    }


    @Override

    public int getItemCount() {
        return mymissionModelArrayList == null ? 0 : mymissionModelArrayList.size();
    }


    public interface MyRequestPublishedNoteAppOnClickListener {
        void myReqPublishedNoteTabClick(View view, int position, YourMission yourMission);


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RelativeLayout rlaccept, rldiscuteridd;
        public AppCompatImageView ivmymission;
        private AppCompatTextView tvNoteBudgetsRow, tvDesBudgetsRow, tvDaysNotesRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlaccept = itemView.findViewById(R.id.rlacceptid);
            rldiscuteridd = itemView.findViewById(R.id.rldiscuteridd);
            ivmymission = itemView.findViewById(R.id.ivmymissionid);
            tvNoteBudgetsRow = itemView.findViewById(R.id.tvNoteBudgetsRowId);
            tvDesBudgetsRow = itemView.findViewById(R.id.tvDesBudgetsRowId);
            tvDaysNotesRow = itemView.findViewById(R.id.tvDaysNotesRowId);
            rlaccept.setOnClickListener(this);
            ivmymission.setOnClickListener(this);
            rldiscuteridd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myRequestPublishedNoteAppOnClickListener.myReqPublishedNoteTabClick(v, getAdapterPosition(), mymissionModelArrayList.get(getAdapterPosition()));
        }
    }
}
