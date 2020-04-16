package com.frelance.chatPkg.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
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

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private ChatAppOnClickListener chatAppOnClickListener;
    private ArrayList<ChatModle> chatModleArrayList;
    private String userId;
    private MediaPlayer mediaPlayer;


    public ChatAdapter(Context context, ChatActivity chatAdapter) {
        this.context = context;
        this.chatAppOnClickListener = chatAppOnClickListener;
        userId = AppSession.getStringPreferences(context, Constants.USERID);
        mediaPlayer = new MediaPlayer();
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

        if (userId.equalsIgnoreCase(chatModleArrayList.get(position).getUserId())) {
            if (chatModleArrayList.get(position).getUserVoice().isEmpty()) {
                holder.rlChatuUserVoiceLeft.setVisibility(View.GONE);
                holder.rlChatuUserVoiceRight.setVisibility(View.GONE);
            } else {
                holder.rlChatuUserVoiceLeft.setVisibility(View.GONE);
                holder.rlChatuUserVoiceRight.setVisibility(View.VISIBLE);
                holder.tvvoicetimePlayRight.setText(Constants.missionChatDate(chatModleArrayList.get(position).getDateTime()));
                holder.rlChatuUserLeft.setVisibility(View.GONE);
                holder.rlChatuUserRight.setVisibility(View.GONE);
            }

        } else {
            if (chatModleArrayList.get(position).getUserVoice().isEmpty()) {
                holder.rlChatuUserVoiceLeft.setVisibility(View.GONE);
                holder.rlChatuUserVoiceRight.setVisibility(View.GONE);

            } else {

                holder.rlChatuUserVoiceLeft.setVisibility(View.VISIBLE);
                holder.rlChatuUserVoiceRight.setVisibility(View.GONE);
                holder.tvSenderImagetimeVoice.setText(Constants.missionChatDate(chatModleArrayList.get(position).getDateTime()));
                holder.rlChatuUserLeft.setVisibility(View.GONE);
                holder.rlChatuUserRight.setVisibility(View.GONE);

            }
        }


        holder.ivchaRecordingPlayImageleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivchaRecordingPlayImageleft.setVisibility(View.GONE);
                holder.ivchaRecordingStopImageleft.setVisibility(View.VISIBLE);
                mediaPlayer.stop();
                notifyDataSetChanged();
            }
        });


        holder.ivchaRecordingPlayImageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivchaRecordingStopImageRight.setVisibility(View.VISIBLE);
                holder.ivchaRecordingPlayImageRight.setVisibility(View.GONE);
                mediaPlayer.stop();
            }
        });


        holder.ivchaRecordingStopImageleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivchaRecordingPlayImageleft.setVisibility(View.VISIBLE);
                holder.ivchaRecordingStopImageleft.setVisibility(View.GONE);
                mediaPlayer = new MediaPlayer();
                mediaPlayer.stop();
                try {
                    mediaPlayer.setDataSource(chatModleArrayList.get(position).getUserVoice());
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
            }
        });

        holder.ivchaRecordingStopImageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivchaRecordingPlayImageRight.setVisibility(View.VISIBLE);
                holder.ivchaRecordingStopImageRight.setVisibility(View.GONE);
                mediaPlayer = new MediaPlayer();
                mediaPlayer.stop();
                try {
                    mediaPlayer.setDataSource(chatModleArrayList.get(position).getUserVoice());
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
            }
        });
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
        private AppCompatTextView tvusertime, tvSenderChat, tvSenderImagetimeVoice,
                tvSendertime, tvUserChat;
        private CircleImageView ivchatprofile,ivchatImageprofile;
        private RelativeLayout rlChatuUserLeft, rlChatuUserRight, rlChatuUserImgLeft, rlChatuUserImgRight, rlChatuUserVoiceRight, rlChatuUserVoiceLeft;
        private AppCompatImageView ivchatSharedImage, ivchaRecordingPlayImageleft, ivchaRecordingStopImageleft,
                ivchatSharedImageRight, ivchaRecordingStopImageRight, ivchaRecordingPlayImageRight;
        private AppCompatTextView tvSenderImagetime, tvSendSharetimeRight;
        private AppCompatTextView tvvoicetimePlayRight, tvvoicetimeRight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlChatuUserVoiceLeft = itemView.findViewById(R.id.rlChatuUserVoiceLeftId);
            rlChatuUserVoiceRight = itemView.findViewById(R.id.rlChatuUserVoiceRightId);
            rlChatuUserImgRight = itemView.findViewById(R.id.rlChatuUserImgRightId);
            rlChatuUserImgLeft = itemView.findViewById(R.id.rlChatuUserImgLeftId);
            rlChatuUserLeft = itemView.findViewById(R.id.rlChatuUserLeftId);
            rlChatuUserRight = itemView.findViewById(R.id.rlChatuUserRightId);
            ivchatprofile = itemView.findViewById(R.id.ivchatprofileId);
            ivchaRecordingStopImageRight = itemView.findViewById(R.id.ivchaRecordingStopImageRightId);
            ivchaRecordingPlayImageRight = itemView.findViewById(R.id.ivchaRecordingPlayImageRightId);
            ivchatSharedImage = itemView.findViewById(R.id.ivchatSharedImageId);
            ivchaRecordingPlayImageleft = itemView.findViewById(R.id.ivchaRecordingPlayImageleftId);
            ivchaRecordingStopImageleft = itemView.findViewById(R.id.ivchaRecordingStopImageleftId);
            ivchatImageprofile = itemView.findViewById(R.id.ivchatImageprofileId);
            ivchatSharedImageRight = itemView.findViewById(R.id.ivchatSharedImageRightId);
            tvusertime = itemView.findViewById(R.id.tvusertimeId);
            tvSenderChat = itemView.findViewById(R.id.tvSenderChatId);
            tvSenderImagetimeVoice = itemView.findViewById(R.id.tvSenderImagetimeVoiceId);
            tvSendertime = itemView.findViewById(R.id.tvSendertimeId);
            tvUserChat = itemView.findViewById(R.id.tvUserChatId);
            tvSenderImagetime = itemView.findViewById(R.id.tvSenderImagetimeId);
            tvSendSharetimeRight = itemView.findViewById(R.id.tvSendSharetimeRightId);
            tvvoicetimePlayRight = itemView.findViewById(R.id.tvvoicetimePlayRightId);
          //  tvvoicetimeRight = itemView.findViewById(R.id.tvvoicetimeRightId);

        }
    }
}
