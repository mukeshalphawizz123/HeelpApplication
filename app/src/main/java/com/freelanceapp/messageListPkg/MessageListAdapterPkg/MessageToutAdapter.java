package com.freelanceapp.messageListPkg.MessageListAdapterPkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.messageListPkg.MessageListFragmentPkg.MessageToutFragment;

public class MessageToutAdapter extends RecyclerView.Adapter<MessageToutAdapter.ViewHolder> {
    private Context context;
    private MessageToutAdapter.MessageToutAppOnClickListener messageToutAppOnClickListener;


    public MessageToutAdapter(Context context, MessageToutAppOnClickListener messageToutAppOnClickListener) {
        this.context = context;
        this.messageToutAppOnClickListener = messageToutAppOnClickListener;
    }

    @NonNull
    @Override
    public MessageToutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_messagelist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageToutAdapter.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 15;
    }

    public interface MessageToutAppOnClickListener {
        void msgOnClick(View view,int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public RelativeLayout rlmsguser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlmsguser=itemView.findViewById(R.id.rlmsguserid);
            rlmsguser.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            messageToutAppOnClickListener.msgOnClick(v,getAdapterPosition());

        }
    }
}
