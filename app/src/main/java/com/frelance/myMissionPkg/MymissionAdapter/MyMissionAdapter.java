package com.frelance.myMissionPkg.MymissionAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;

import com.frelance.myMissionPkg.Mymissionmodle.mymissionModle;

import java.util.ArrayList;

public class MyMissionAdapter extends RecyclerView.Adapter<MyMissionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<mymissionModle> mymissionModelArrayList;
    private MyMissionAppOnClickListener myMissionAppOnClickListener;
    private int selectedPos = 0;
    private String filterTag;

    public interface MyMissionAppOnClickListener {
        void myMissionTabClick(View view, int position);
    }
    public MyMissionAdapter(Context context, MyMissionAppOnClickListener myMissionAppOnClickListener) {
        this.context = context;
        this.myMissionAppOnClickListener = myMissionAppOnClickListener;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_mymission_tab_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      /*  if (filterTag.equalsIgnoreCase(mymissionModelArrayList.get(position).getName())){

            holder.changeToSelect(selectedPos == position ? Color.parseColor("#000000") : Color.GRAY);
        }else {
            holder.tvtextmission.setText(mymissionModelArrayList.get(position).getName());
            holder.changeToSelect(selectedPos == position ? Color.parseColor("#000000") : Color.GRAY);

        }
*/
       holder.tvtextmission.setText(mymissionModelArrayList.get(position).getName());
       holder.changeToSelect(selectedPos == position ? Color.parseColor("#000000") : Color.GRAY);

    }

    public void addmymissionData(ArrayList<mymissionModle> mymissionModelArrayList) {
        this.mymissionModelArrayList = mymissionModelArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mymissionModelArrayList == null ? 0 : mymissionModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatTextView tvtextmission;
        private RelativeLayout rlmission;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtextmission = itemView.findViewById(R.id.tvtextmissionid);
            rlmission = itemView.findViewById(R.id.rlmissionid);
            rlmission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (myMissionAppOnClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            myMissionAppOnClickListener.myMissionTabClick(rlmission, position);
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
            myMissionAppOnClickListener.myMissionTabClick(v, getAdapterPosition());

        }

        public void changeToSelect(int colorBackground) {
            tvtextmission.setTextColor(colorBackground);
        }


    }


}
