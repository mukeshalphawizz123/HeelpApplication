package com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg;

import android.content.Context;
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
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMissionadapter.ViewHolder holder, int position) {
        try {
            if (datumList.get(position).getMessageType().equalsIgnoreCase("admin_message")) {
                if (datumList.get(position).getMessage().isEmpty()) {
                    holder.rlSenderBox.setVisibility(View.GONE);
                    holder.rltime.setVisibility(View.GONE);
                    holder.tvSupportMsg.setVisibility(View.GONE);
                    holder.rlsupportmsgbox.setVisibility(View.GONE);

                } else {
                    holder.tvSupportMsg.setText(datumList.get(position).getMessage());
                    holder.rlSenderBox.setVisibility(View.GONE);
                    holder.rltime.setVisibility(View.GONE);
                    holder.tvSupportMsg.setVisibility(View.VISIBLE);
                    holder.rlsupportmsgbox.setVisibility(View.VISIBLE);
                    if (position == 0) {
                        holder.rlsupportmsgbox.setBackground(context.getResources().getDrawable(R.drawable.blue_support_chat_box));
                        holder.tvSupportMsg.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    else {
                        holder.rlsupportmsgbox.setBackground(context.getResources().getDrawable(R.drawable.white_chat_box));
                        holder.tvSupportMsg.setTextColor(context.getResources().getColor(R.color.Black));
                    }
                }
            } else {
                if (datumList.get(position).getMessage().isEmpty()) {
                    holder.rlSenderBox.setVisibility(View.GONE);
                    holder.rltime.setVisibility(View.GONE);
                    holder.tvSupportMsg.setVisibility(View.GONE);
                    holder.rlsupportmsgbox.setVisibility(View.GONE);

                } else {
                    holder.rlSenderBox.setVisibility(View.VISIBLE);
                    holder.tvSenderMessage.setVisibility(View.VISIBLE);
                    holder.rltime.setVisibility(View.VISIBLE);
                    holder.tvSenderMessage.setText(datumList.get(position).getMessage());
                    holder.tvtime.setText(Constants.missionChatDate(datumList.get(position).getMessageDateTime()));
                    holder.tvSupportMsg.setVisibility(View.GONE);
                    holder.rlsupportmsgbox.setVisibility(View.GONE);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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
