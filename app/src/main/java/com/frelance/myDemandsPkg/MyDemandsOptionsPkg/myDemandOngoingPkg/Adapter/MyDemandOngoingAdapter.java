package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.R;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.MyDemandsOngoingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.demandProgressModlePkg.Datum;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyDemandOngoingAdapter extends RecyclerView.Adapter<MyDemandOngoingAdapter.ViewHolder> {
    private Context context;
    private MyRequestOngoingAppOnClickListener myRequestOngoingAppOnClickListener;
    private List<Datum> filesList;
    private MyDemandOngoingChildAdapter myDemandOngoingChildAdapter;
    private HashMap<String, ArrayList<String>> fileImageList;
    private ArrayList<String> fileImagesUrl;


    public MyDemandOngoingAdapter(Context context, MyDemandsOngoingActivity myRequestOngoingAppOnClickListener) {
        this.context = context;
        this.myRequestOngoingAppOnClickListener = myRequestOngoingAppOnClickListener;
        fileImagesUrl = new ArrayList<>();

      //  fileImageList = new HashMap<>();
    }


    @NonNull
    @Override
    public MyDemandOngoingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_my_request_ongoing_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDemandOngoingAdapter.ViewHolder holder, int position) {
        fileImagesUrl = new ArrayList<>();
        fileImagesUrl.clear();
        holder.tvUserNameDemandProg.setText(filesList.get(position).getFirstName());
        holder.tvCommentDemandProg.setText(filesList.get(position).getYourComments());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rvOnGoingChild.setLayoutManager(layoutManager);
        myDemandOngoingChildAdapter = new MyDemandOngoingChildAdapter(context);
        holder.rvOnGoingChild.setAdapter(myDemandOngoingChildAdapter);
        if (filesList.get(position).getPictureUrl().isEmpty()) {
        } else {
            Picasso.with(context).load(RetrofitClient.MISSION_USER_IMAGE_URL + filesList.get(position).getPictureUrl()).into(holder.ivUserProgDemain);
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

        //  fileImageList.put(filesList.get(position).getId(),fileImagesUrl);
        myDemandOngoingChildAdapter.addOnGoingDemandsFiles(fileImagesUrl);
    }


    public void addOnGoingDemandsFiles(List<Datum> filesList) {
        this.filesList = filesList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return filesList == null ? 0 : filesList.size();
    }


    public interface MyRequestOngoingAppOnClickListener {
        void myDemandOnGoingOnClick(View view, int position,Datum datum);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerView rvOnGoingChild;
        private AppCompatTextView tvCommentDemandProg, tvUserNameDemandProg;
        private CircleImageView ivUserProgDemain;
        private RelativeLayout rldummyimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserNameDemandProg = itemView.findViewById(R.id.tvUserNameDemandProgId);
            ivUserProgDemain = itemView.findViewById(R.id.ivUserProgDemainId);
            tvCommentDemandProg = itemView.findViewById(R.id.tvCommentDemandProgId);
            rvOnGoingChild = itemView.findViewById(R.id.rvOnGoingChildId);
            rldummyimg = itemView.findViewById(R.id.rldummyimgid);
            rldummyimg.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            myRequestOngoingAppOnClickListener.myDemandOnGoingOnClick(v, getAdapterPosition(),filesList.get(getAdapterPosition()));
        }
    }
}
