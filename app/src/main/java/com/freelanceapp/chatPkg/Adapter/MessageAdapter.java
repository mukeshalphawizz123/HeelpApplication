package com.freelanceapp.chatPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.freelanceapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Varun John on 4 Dec, 2018
 * Github : https://github.com/varunjohn
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messages = new ArrayList<>();
    private static SimpleDateFormat timeFormatter;
    Context context;

    public MessageAdapter() {
        timeFormatter = new SimpleDateFormat("m:ss", Locale.getDefault());
    }

    public void add(Message message,Context context) {
        messages.add(message);
        this.context = context;
        notifyItemInserted(messages.lastIndexOf(message));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_message, null);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessageViewHolder) holder).bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static String getAudioTime(long time) {
        time *= 1000;
        timeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return timeFormatter.format(new Date(time));
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        private ImageView ivImage1, ivImage2, ivImage3, ivImage4;
        private CardView cardview2;

        public MessageViewHolder(View view) {
            super(view);
            text = itemView.findViewById(R.id.textView);
            ivImage1 = itemView.findViewById(R.id.iv_image1);
            ivImage2 = itemView.findViewById(R.id.iv_image2);
            ivImage3 = itemView.findViewById(R.id.iv_image3);
            ivImage4 = itemView.findViewById(R.id.iv_image4);
            cardview2 = itemView.findViewById(R.id.cardview2);
        }

        public void bind(final Message message) {
            if (message.type == Message.TYPE_AUDIO) {
                cardview2.setVisibility(View.GONE);
                text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mic_small, 0, 0, 0);
                text.setText(String.valueOf(getAudioTime(message.time)));
            }else if (message.type ==Message.TYPE_IMAGE){
                text.setVisibility(View.GONE);
                cardview2.setVisibility(View.GONE);
                for (int i=0; i <message.getUris().size(); i++) {
                    if (i >= 4) return;
                    ImageView iv;
                    switch (i) {
                        case 0:
                            iv = ivImage1;
                            break;
                        case 1:
                            iv = ivImage2;
                            break;
                        case 2:
                            iv = ivImage3;
                            break;
                        case 3:
                        default:
                            iv = ivImage4;
                    }
                    Glide.with(context).load(message.getUris().get(i)).into(iv);
                }

            } else if (message.type == Message.TYPE_TEXT) {
                cardview2.setVisibility(View.GONE);
                text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                text.setText(message.text);
            } else {
                cardview2.setVisibility(View.GONE);
                text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                text.setText("");
            }
        }
    }
}