package com.frelance.makeAnOfferPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.makeAnOfferPkg.MakeAnOfferActivity;

public class MakeanOfferAdapter extends RecyclerView.Adapter<MakeanOfferAdapter.ViewHolder> {
    private Context context;
    private MakeanOfferAdapter.MakeanOfferAppOnClickListener makeanOfferAppOnClickListener;


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

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public interface MakeanOfferAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
