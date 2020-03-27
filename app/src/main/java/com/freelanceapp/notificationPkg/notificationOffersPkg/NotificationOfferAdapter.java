package com.freelanceapp.notificationPkg.notificationOffersPkg;

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
import com.freelanceapp.myMissionPkg.Mymissionmodle.mymissionModle;

import java.util.ArrayList;

public class NotificationOfferAdapter extends RecyclerView.Adapter<NotificationOfferAdapter.ViewHolder> {

    private Context context;
    private ArrayList<mymissionModle> mymissionModelArrayList;
    private NotificationOfferOnClickListener notificationOfferOnClickListener;
    private int selectedPos = 0;
    private String filterTag;

    public interface NotificationOfferOnClickListener {
        void offerTabClick(View view, int position);
    }
    public NotificationOfferAdapter(Context context, NotificationOfferOnClickListener notificationOfferOnClickListener) {
        this.context = context;
        this.notificationOfferOnClickListener = notificationOfferOnClickListener;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.offer_notfication_row, parent, false);
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

    }

    public void addmymissionData(ArrayList<mymissionModle> mymissionModelArrayList) {
        this.mymissionModelArrayList = mymissionModelArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 10;
      //  return mymissionModelArrayList == null ? 0 : mymissionModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatTextView tvtextmission;
        private RelativeLayout rlmission;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        @Override
        public void onClick(View v) {
            notificationOfferOnClickListener.offerTabClick(v, getAdapterPosition());

        }

        public void changeToSelect(int colorBackground) {
            tvtextmission.setTextColor(colorBackground);
        }


    }


}
