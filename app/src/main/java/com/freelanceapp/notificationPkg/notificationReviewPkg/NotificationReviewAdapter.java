package com.freelanceapp.notificationPkg.notificationReviewPkg;

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
import com.freelanceapp.notificationPkg.NotificationModlePkg.Datum;
import com.freelanceapp.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class NotificationReviewAdapter extends RecyclerView.Adapter<NotificationReviewAdapter.ViewHolder> {

    private Context context;
    private List<Datum> mymissionModelArrayList;
    private NotReviewAppOnClickListener notReviewAppOnClickListener;
    private int selectedPos = 0;
    private String filterTag;

    public interface NotReviewAppOnClickListener {
        void myReviewTabClick(View view, int position);
    }

    public NotificationReviewAdapter(Context context, NotReviewAppOnClickListener notReviewAppOnClickListener) {
        this.context = context;
        this.notReviewAppOnClickListener = notReviewAppOnClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.review_notfication_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvtime.setText(Constants.parseDateToddMMyyyy(mymissionModelArrayList.get(position).getCreated()));
        holder.tvUserNameChat.setText(mymissionModelArrayList.get(position).getNotification());
    }

    public void addmymissionData(List<Datum> mymissionModelArrayList) {
        this.mymissionModelArrayList = mymissionModelArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // return 10;
        return mymissionModelArrayList == null ? 0 : mymissionModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatTextView tvtime, tvUserNameChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtime = itemView.findViewById(R.id.tvtimeId);
            tvUserNameChat = itemView.findViewById(R.id.tvUserNameChatId);

        }

        @Override
        public void onClick(View v) {
            notReviewAppOnClickListener.myReviewTabClick(v, getAdapterPosition());

        }

        public void changeToSelect(int colorBackground) {
            // tvtextmission.setTextColor(colorBackground);
        }


    }


}
