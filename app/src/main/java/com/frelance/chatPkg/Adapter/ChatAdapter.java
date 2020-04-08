package com.frelance.chatPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.chatPkg.ChatActivity;
import com.frelance.chatPkg.chatModlePkg.ChatModle;
import com.frelance.utility.AppSession;
import com.frelance.utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private ChatAppOnClickListener chatAppOnClickListener;
    private ArrayList<ChatModle> chatModleArrayList;
    private String userId;


    public ChatAdapter(Context context, ChatActivity chatAdapter) {
        this.context = context;
        this.chatAppOnClickListener = chatAppOnClickListener;
        userId = AppSession.getStringPreferences(context, Constants.USERID);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_chat_text_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (userId.equalsIgnoreCase(chatModleArrayList.get(position).getUserId())) {
            holder.tvusertime.setText(Constants.missionChatDate(chatModleArrayList.get(position).getDateTime()));
            holder.tvUserChat.setText(chatModleArrayList.get(position).getMessage());
            holder.rlChatuUserLeft.setVisibility(View.GONE);
            holder.rlChatuUserRight.setVisibility(View.VISIBLE);
        } else {
            holder.rlChatuUserLeft.setVisibility(View.VISIBLE);
            holder.rlChatuUserRight.setVisibility(View.GONE);
            holder.tvSenderChat.setText(chatModleArrayList.get(position).getMessage());
            holder.tvSendertime.setText(Constants.missionChatDate(chatModleArrayList.get(position).getDateTime()));
        }

        if (userId.equalsIgnoreCase(chatModleArrayList.get(position).getUserId())) {
            if (chatModleArrayList.get(position).getUserImgPath().isEmpty()) {
                holder.rlChatuUserImgRight.setVisibility(View.GONE);
                holder.rlChatuUserImgLeft.setVisibility(View.GONE);
                holder.rlChatuUserLeft.setVisibility(View.GONE);
                holder.rlChatuUserRight.setVisibility(View.VISIBLE);
            } else {
                holder.rlChatuUserLeft.setVisibility(View.GONE);
                holder.rlChatuUserRight.setVisibility(View.GONE);
                holder.rlChatuUserImgRight.setVisibility(View.VISIBLE);
                holder.rlChatuUserImgLeft.setVisibility(View.GONE);
                Picasso.with(context).load(chatModleArrayList
                        .get(position).getUserImgPath())
                        .resize(300, 300)
                        .placeholder(R.drawable.progress_animation)
                        .into(holder.ivchatSharedImageRight);
                holder.tvSendSharetimeRight.setText(Constants.missionChatDate(chatModleArrayList.get(position).getDateTime()));
            }
            //ivchatSharedImageRight
        } else {
            if (chatModleArrayList.get(position).getUserImgPath().isEmpty()) {
                holder.rlChatuUserImgRight.setVisibility(View.GONE);
                holder.rlChatuUserImgLeft.setVisibility(View.GONE);

                holder.rlChatuUserLeft.setVisibility(View.VISIBLE);
                holder.rlChatuUserRight.setVisibility(View.GONE);

            } else {
                holder.rlChatuUserLeft.setVisibility(View.GONE);
                holder.rlChatuUserRight.setVisibility(View.GONE);
                holder.rlChatuUserImgRight.setVisibility(View.GONE);
                holder.rlChatuUserImgLeft.setVisibility(View.VISIBLE);
                Picasso.with(context).load(chatModleArrayList
                        .get(position).getUserImgPath())
                        .resize(300, 300)
                        .placeholder(R.drawable.progress_animation)
                        .into(holder.ivchatSharedImage);
                holder.tvSenderImagetime.setText(Constants.missionChatDate(chatModleArrayList.get(position).getDateTime()));
            }

        }
    }

    @Override
    public int getItemCount() {
        // return 10;
        return chatModleArrayList == null ? 0 : chatModleArrayList.size();
    }

    public void addChatList(ArrayList<ChatModle> chatModleArrayList) {
        this.chatModleArrayList = chatModleArrayList;
        notifyDataSetChanged();
    }

    public interface ChatAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvusertime, tvSenderChat,
                tvSendertime, tvUserChat;
        private AppCompatImageView ivchatprofile;
        private RelativeLayout rlChatuUserLeft, rlChatuUserRight, rlChatuUserImgLeft, rlChatuUserImgRight;
        private AppCompatImageView ivchatSharedImage, ivchatImageprofile, ivchatSharedImageRight;
        private AppCompatTextView tvSenderImagetime, tvSendSharetimeRight;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlChatuUserImgRight = itemView.findViewById(R.id.rlChatuUserImgRightId);
            rlChatuUserImgLeft = itemView.findViewById(R.id.rlChatuUserImgLeftId);
            rlChatuUserLeft = itemView.findViewById(R.id.rlChatuUserLeftId);
            rlChatuUserRight = itemView.findViewById(R.id.rlChatuUserRightId);
            ivchatprofile = itemView.findViewById(R.id.ivchatprofileId);
            ivchatSharedImage = itemView.findViewById(R.id.ivchatSharedImageId);
            ivchatImageprofile = itemView.findViewById(R.id.ivchatImageprofileId);
            ivchatSharedImageRight = itemView.findViewById(R.id.ivchatSharedImageRightId);
            tvusertime = itemView.findViewById(R.id.tvusertimeId);
            tvSenderChat = itemView.findViewById(R.id.tvSenderChatId);
            tvSendertime = itemView.findViewById(R.id.tvSendertimeId);
            tvUserChat = itemView.findViewById(R.id.tvUserChatId);
            tvSenderImagetime = itemView.findViewById(R.id.tvSenderImagetimeId);
            tvSendSharetimeRight = itemView.findViewById(R.id.tvSendSharetimeRightId);

        }
    }
}
