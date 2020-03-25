package com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.proposePkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.R;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.completeePkg.Adapter.CompleteeFileUploadAdapter;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.proposePkg.MyMissionProposeeActivity;

public class ProposeAdapter extends RecyclerView.Adapter<ProposeAdapter.ViewHolder> {
    private Context context;
    private ProposeAdapter.ProposeAppOnClickListener proposeAppOnClickListener;


    public ProposeAdapter(Context context, MyMissionProposeeActivity proposeAppOnClickListener) {
        this.context = context;
        this.proposeAppOnClickListener = proposeAppOnClickListener;
    }


    @NonNull
    @Override
    public ProposeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_proposee_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProposeAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public interface ProposeAppOnClickListener {
        void mymissionpropose(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RelativeLayout Rlmissionpropose;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Rlmissionpropose=itemView.findViewById(R.id.RlmissionproposeId);
            Rlmissionpropose.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            proposeAppOnClickListener.mymissionpropose(v, getAdapterPosition());

        }
    }
}
