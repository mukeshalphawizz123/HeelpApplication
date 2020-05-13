package com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.MyMissionOngoingActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.viewProgressModle.Datum;
import com.squareup.picasso.Picasso;


public class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.ViewHolder> {
    private Context context;
    private OngoingAppOnClickListener ongoingAppOnClickListener;
    private List<Datum> filesList;
    private ArrayList<String> fileImagesUrl;
    private MyMisionOngoingChildAdapter myDemandOngoingChildAdapter;
  //  LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);


    public OngoingAdapter(Context context, MyMissionOngoingActivity ongoingAppOnClickListener) {
        this.context = context;
        this.ongoingAppOnClickListener = ongoingAppOnClickListener;
    }

    @NonNull
    @Override
    public OngoingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_ongonig_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingAdapter.ViewHolder holder, int position) {
        fileImagesUrl = new ArrayList<>();
        fileImagesUrl.clear();
        holder.tvUserNameInProgMission.setText(filesList.get(position).getFirstName());
        holder.tvCommentInProgMission.setText(filesList.get(position).getYourComments());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rvOnGoingRow.setLayoutManager(layoutManager);
        myDemandOngoingChildAdapter = new MyMisionOngoingChildAdapter(context);
        holder.rvOnGoingRow.setAdapter(myDemandOngoingChildAdapter);

        if (filesList.get(position).getPictureUrl().isEmpty()) {
        } else {
            Picasso.with(context).load(RetrofitClient.MISSION_USER_IMAGE_URL + filesList.get(position).getPictureUrl()).into(holder.ivUserInprogMission);
        }

        if (filesList.get(position).getProjectImage().isEmpty()) {

        } else {
            String[] imgesArray = filesList.get(position).getProjectImage().split(",");
            for (int i = 0; i < imgesArray.length; i++) {
                fileImagesUrl.add(imgesArray[i]);
            }
        }
        if (filesList.get(position).getProjectFiles().isEmpty()) {

        } else {
            String[] filesArray = filesList.get(position).getProjectFiles().split(",");
            for (int i = 0; i < filesArray.length; i++) {
                fileImagesUrl.add(filesArray[i]);
            }
        }

        myDemandOngoingChildAdapter.addOnGoingDemandsFiles(fileImagesUrl);
    }

    public void addOnGoingFiles(List<Datum> filesList) {
        this.filesList = filesList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return filesList == null ? 0 : filesList.size();
    }


    public interface OngoingAppOnClickListener {
        void myMissOnGoingOnClick(View view, int position,Datum datum);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView ivUserInprogMission;
        private RecyclerView rvOnGoingRow;
        private AppCompatTextView tvUserNameInProgMission, tvCommentInProgMission;
        private RelativeLayout rldummyimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvOnGoingRow = itemView.findViewById(R.id.rvOnGoingRowId);
            ivUserInprogMission = itemView.findViewById(R.id.ivUserInprogMissionId);
            tvUserNameInProgMission = itemView.findViewById(R.id.tvUserNameInProgMissionId);
            tvCommentInProgMission = itemView.findViewById(R.id.tvCommentInProgMissionId);
            rldummyimg = itemView.findViewById(R.id.rldummyimgid);
            rldummyimg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ongoingAppOnClickListener.myMissOnGoingOnClick(v, getAdapterPosition(),filesList.get(getAdapterPosition()));
        }
    }
}
