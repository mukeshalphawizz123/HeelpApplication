package com.frelance.InboxListPkg.MessageListAdapterPkg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.InboxListPkg.msgModlePkg.Datum;
import com.frelance.R;
import com.frelance.chatPkg.chatModlePkg.ChatModle;
import com.frelance.chatPkg.chatModlePkg.UnReadMessageUserModle;
import com.frelance.utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class UnReadMsgAdapter extends RecyclerView.Adapter<UnReadMsgAdapter.ViewHolder> {
    private Context context;
    private MessageToutAppOnClickListener messageToutAppOnClickListener;
    private ArrayList<UnReadMessageUserModle> chatModleArrayList;
    private ArrayList<String> sendIdList;
    private ArrayList<UnReadMessageUserModle> chatModleArrayListDemo;
    private String userId, senderId;


    public UnReadMsgAdapter(Context context, MessageToutAppOnClickListener messageToutAppOnClickListener) {
        this.context = context;
        this.messageToutAppOnClickListener = messageToutAppOnClickListener;
        sendIdList = new ArrayList<>();
    }

    @NonNull
    @Override
    public UnReadMsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_messagelist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            userId = chatModleArrayList.get(position).getSenderId();
            senderId = chatModleArrayList.get(position).getSenderId();
            holder.tvUserNameMsg.setText(chatModleArrayList.get(position).getName());
            holder.tvtime.setText(Constants.missionDemandDate(chatModleArrayList.get(position).getDateAndTime()));
            if (chatModleArrayList.get(position).getImgUrl().isEmpty()) {
            } else {
                Picasso.with(context)
                        .load(RetrofitClient.MISSION_USER_IMAGE_URL + chatModleArrayList.get(position).getImgUrl())
                        .resize(100, 100)
                        .into(holder.ivUserMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addmymissionData(ArrayList<UnReadMessageUserModle> chatModleArrayList_) {
        this.chatModleArrayList = chatModleArrayList_;
        notifyDataSetChanged();
       /* for (int i = 0; i < chatModleArrayList_.size(); i++) {
            String senderId = chatModleArrayList_.get(i).getSenderId();
            sendIdList.add(senderId);
        }
        Set<String> primesWithoutDuplicates = new LinkedHashSet<String>(sendIdList);
        // now let's clear the ArrayList so that we can copy all elements from LinkedHashSet
        sendIdList.clear(); // copying elements but without any duplicates
        sendIdList.addAll(primesWithoutDuplicates);
        Log.v("senderid", sendIdList.toString());
        chatModleArrayList.clear();
        for (int j = 0; j < sendIdList.size(); j++) {
            for (int i = 0; i < chatModleArrayList_.size(); i++) {
                String senderId = chatModleArrayList_.get(i).getSenderId();
                if (sendIdList.get(j).equalsIgnoreCase(senderId)) {
                    chatModleArrayList.add(chatModleArrayList_.get(i));
                    break;
                }
            }
        }
        Log.v("chat", chatModleArrayList.toString());
        notifyDataSetChanged();
   */ }

    @Override
    public int getItemCount() {
        //  return 15;
        return chatModleArrayList == null ? 0 : chatModleArrayList.size();
    }


    public interface MessageToutAppOnClickListener {
        void msgOnClick(View view, int position, UnReadMessageUserModle unReadMessageUserModle);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RelativeLayout rlmsguser;
        CircleImageView ivUserMsg;
        AppCompatTextView tvUserNameMsg, tvtime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtime = itemView.findViewById(R.id.tvtimeId);
            rlmsguser = itemView.findViewById(R.id.rlmsguserid);
            tvUserNameMsg = itemView.findViewById(R.id.tvUserNameMsgId);
            ivUserMsg = itemView.findViewById(R.id.ivUserMsgId);
            rlmsguser.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            messageToutAppOnClickListener.msgOnClick(v, getAdapterPosition(), chatModleArrayList.get(getAdapterPosition()));

        }
    }


}
