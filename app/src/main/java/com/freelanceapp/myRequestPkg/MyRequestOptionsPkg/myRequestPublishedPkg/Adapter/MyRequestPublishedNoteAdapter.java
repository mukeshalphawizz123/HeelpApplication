package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestPublishedPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestPublishedPkg.Fragment.MyRequestPublishedNoteFragment;
import com.makeramen.roundedimageview.RoundedImageView;


public class MyRequestPublishedNoteAdapter extends RecyclerView.Adapter<MyRequestPublishedNoteAdapter.ViewHolder> {

    private Context context;
    private MyRequestPublishedNoteAppOnClickListener myRequestPublishedNoteAppOnClickListener;

    public MyRequestPublishedNoteAdapter(Context context, MyRequestPublishedNoteFragment myRequestPublishedNoteAppOnClickListener) {
        this.context = context;
        this.myRequestPublishedNoteAppOnClickListener = myRequestPublishedNoteAppOnClickListener;
    }

    @NonNull
    @Override
    public MyRequestPublishedNoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_my_request_published_note_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestPublishedNoteAdapter.ViewHolder holder, int position) {

    }

    @Override

    public int getItemCount() {
        return 6;
    }

    public interface MyRequestPublishedNoteAppOnClickListener {

        void myReqPublishedNoteTabClick(View view, int position);


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RelativeLayout rlaccept,rldiscuteridd;
        public AppCompatImageView ivmymission;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlaccept = itemView.findViewById(R.id.rlacceptid);
            rldiscuteridd = itemView.findViewById(R.id.rldiscuteridd);
            ivmymission = itemView.findViewById(R.id.ivmymissionid);
            rlaccept.setOnClickListener(this);
            ivmymission.setOnClickListener(this);
            rldiscuteridd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myRequestPublishedNoteAppOnClickListener.myReqPublishedNoteTabClick(v, getAdapterPosition());
        }
    }
}
