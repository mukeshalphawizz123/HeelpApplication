package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.YourMission;
import com.frelance.utility.Constants;
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
        try {
            if (mymissionModelArrayList.get(position).getOffer_budget().isEmpty()) {
                holder.tvNoteBudgetsRow.setText(" : " + mymissionModelArrayList.get(position).getMissionBudget() + "€");
            } else {
                holder.tvNoteBudgetsRow.setText(" : " + mymissionModelArrayList.get(position).getOffer_budget() + "€");
            }
            holder.tvDesBudgetsRow.setText(mymissionModelArrayList.get(position).getMissionTitle());
            holder.tvDaysNotesRow.setText(mymissionModelArrayList.get(position).getDuration());
            if (mymissionModelArrayList.get(position).getPicture_url().isEmpty()) {
            } else {
                Picasso.with(context)
                        .load(RetrofitClient.MISSION_USER_IMAGE_URL + mymissionModelArrayList.get(0).getPicture_url())
                        .into(holder.ivmymission);

            }
            holder.ratingpubished.setRating(Float.parseFloat(mymissionModelArrayList.get(position).getProfile_Rate()));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        public RelativeLayout rlaccept, rldiscuteridd, rluserprofile;
        public AppCompatImageView ivmymission;
        private AppCompatTextView tvNoteBudgetsRow, tvDesBudgetsRow, tvDaysNotesRow;
        private RatingBar ratingpubished;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rluserprofile = itemView.findViewById(R.id.rluserprofileid);
            rlaccept = itemView.findViewById(R.id.rlacceptid);
            rldiscuteridd = itemView.findViewById(R.id.rldiscuteridd);
            ivmymission = itemView.findViewById(R.id.ivmymissionid);
            tvNoteBudgetsRow = itemView.findViewById(R.id.tvNoteBudgetsRowId);
            tvDesBudgetsRow = itemView.findViewById(R.id.tvDesBudgetsRowId);
            tvDaysNotesRow = itemView.findViewById(R.id.tvDaysNotesRowId);
            ratingpubished = itemView.findViewById(R.id.ratingpubishedidd);
            rlaccept.setOnClickListener(this);
            ivmymission.setOnClickListener(this);
            rldiscuteridd.setOnClickListener(this);
            rluserprofile.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myRequestPublishedNoteAppOnClickListener.myReqPublishedNoteTabClick(v, getAdapterPosition(), mymissionModelArrayList.get(getAdapterPosition()));
        }
    }
}
