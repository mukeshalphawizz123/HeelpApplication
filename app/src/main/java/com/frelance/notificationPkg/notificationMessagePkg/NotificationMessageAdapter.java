package com.frelance.notificationPkg.notificationMessagePkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.notificationPkg.NotificationModlePkg.Datum;
import com.frelance.utility.Constants;

import java.util.List;

public class NotificationMessageAdapter extends RecyclerView.Adapter<NotificationMessageAdapter.ViewHolder> {

    private Context context;
    private List<Datum> mymissionModelArrayList;
    private NotificationMessageAppOnClickListener notificationMessageAppOnClickListener;
    private int selectedPos = 0;
    private String filterTag;

    public interface NotificationMessageAppOnClickListener {
        void notMsgTabClick(View view, int position);
    }

    public NotificationMessageAdapter(Context context, NotificationMessageAppOnClickListener notificationMessageAppOnClickListener) {
        this.context = context;
        this.notificationMessageAppOnClickListener = notificationMessageAppOnClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.message_notfication_row, parent, false);
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
        private RelativeLayout rlmission;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtime = itemView.findViewById(R.id.tvtimeId);
            tvUserNameChat = itemView.findViewById(R.id.tvUserNameChatId);
        }

        @Override
        public void onClick(View v) {
            notificationMessageAppOnClickListener.notMsgTabClick(v, getAdapterPosition());

        }

        public void changeToSelect(int colorBackground) {
            //  tvtextmission.setTextColor(colorBackground);
        }


    }


}
