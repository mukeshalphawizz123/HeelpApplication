package com.frelance.makeAnOfferPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.makeAnOfferPkg.MakeAnOfferActivity;

import java.util.ArrayList;

public class MakeanOfferAdapter extends RecyclerView.Adapter<MakeanOfferAdapter.ViewHolder> {
    private Context context;
    private MakeanOfferAppOnClickListener makeanOfferAppOnClickListener;
    private ArrayList<String> filesList;


    public MakeanOfferAdapter(Context context, MakeAnOfferActivity makeanOfferAppOnClickListener) {
        this.context = context;
        this.makeanOfferAppOnClickListener = makeanOfferAppOnClickListener;
    }

    @NonNull
    @Override
    public MakeanOfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_make_an_offer_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MakeanOfferAdapter.ViewHolder holder, int position) {
        holder.tvfilename.setText(filesList.get(position));
    }

    public void addDetailFiles(ArrayList<String> filesList) {
        this.filesList = filesList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filesList == null ? 0 : filesList.size();
    }



    public interface MakeanOfferAppOnClickListener {
        void myMakeAnOfferDetailTabClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout rlFileFolder;
        AppCompatTextView tvfilename;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlFileFolder = itemView.findViewById(R.id.rlFileFolderId);
            tvfilename = itemView.findViewById(R.id.tvfilenameid);
            rlFileFolder.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            makeanOfferAppOnClickListener.myMakeAnOfferDetailTabClick(v,getAdapterPosition());
        }
    }
}
