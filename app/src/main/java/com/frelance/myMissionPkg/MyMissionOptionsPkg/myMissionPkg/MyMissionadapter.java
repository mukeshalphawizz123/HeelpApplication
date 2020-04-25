package com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.Datum;
import com.frelance.utility.Constants;

import java.util.ArrayList;
import java.util.List;


public class MyMissionadapter extends RecyclerView.Adapter<MyMissionadapter.ViewHolder> {
    private Context context;
    private MyMissionDisputeOnClickListener myMissionDisputeOnClickListener;
    private List<Datum> datumList;


    public MyMissionadapter(Context context, MyMissionDisputeOnClickListener myMissionDisputeOnClickListener) {
        this.context = context;
        this.myMissionDisputeOnClickListener = myMissionDisputeOnClickListener;
    }

    @NonNull
    @Override
    public MyMissionadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.my_mission_on_dispute_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyMissionadapter.ViewHolder holder, int position) {
        // String sm = "<b>" + "Support message:" + "</b> ";
        if (datumList.get(position).getMessageType().equalsIgnoreCase("admin_message")) {
            if (datumList.get(position).getMessage().isEmpty()) {
                holder.rlSenderBox.setVisibility(View.GONE);
                holder.rltime.setVisibility(View.GONE);
                holder.tvSupportMsg.setVisibility(View.GONE);
                holder.rlsupportmsgbox.setVisibility(View.GONE);

            } else {
                // holder.tvSupportMsg.setText(Html.fromHtml(sm) + "\n" + datumList.get(position).getMessage());
                holder.tvSupportMsg.setText(datumList.get(position).getMessage());
                holder.rlSenderBox.setVisibility(View.GONE);
                holder.rltime.setVisibility(View.GONE);
                holder.tvSupportMsg.setVisibility(View.VISIBLE);
                holder.rlsupportmsgbox.setVisibility(View.VISIBLE);
            }
        } else {
            holder.tvSenderMessage.setText(datumList.get(position).getMessage());
            holder.tvtime.setText(Constants.missionChatDate(datumList.get(position).getMessageDateTime()));
            holder.rlSenderBox.setVisibility(View.VISIBLE);
            holder.rltime.setVisibility(View.VISIBLE);
            holder.tvSupportMsg.setVisibility(View.GONE);
            holder.rlsupportmsgbox.setVisibility(View.GONE);
        }

    }

    public void addDisputeList(List<Datum> datumList) {
        this.datumList = datumList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        // return 10;
        return datumList == null ? 0 : datumList.size();
    }

    public interface MyMissionDisputeOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvSupportMsg, tvSenderMessage, tvtime;
        private RelativeLayout rlsupportmsgbox, rlSenderBox, rltime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlSenderBox = itemView.findViewById(R.id.rlSenderBoxId);
            rltime = itemView.findViewById(R.id.rltimeid);
            rlsupportmsgbox = itemView.findViewById(R.id.rlsupportmsgboxid);
            tvSupportMsg = itemView.findViewById(R.id.tvSupportMsgId);
            tvSenderMessage = itemView.findViewById(R.id.tvSenderMessageId);
            tvtime = itemView.findViewById(R.id.tvtimeId);
        }
    }
}
