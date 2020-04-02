package com.frelance.userProfileRatingPkg.AdapterPkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;
import com.frelance.userProfileRatingPkg.ProfileRatingDescriptionActivity;
import com.frelance.userProfileRatingPkg.getuserreviewsModulePkg.Review;

import java.util.List;


public class ProfileRatingAdapter extends RecyclerView.Adapter<ProfileRatingAdapter.ViewHolder> {
    private Context context;
    private ProfileRatingAdapter.ProfileRatingAppOnClickListener profileRatingAppOnClickListener;
    private List<Review> getuserreview;

    public ProfileRatingAdapter(Context context, ProfileRatingDescriptionActivity profileRatingAdapter) {
        this.context = context;
        this.profileRatingAppOnClickListener = profileRatingAppOnClickListener;
    }

    @NonNull
    @Override
    public ProfileRatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_profile_rating_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvratingname.setText(getuserreview.get(position).getReviewBy());
            holder.tvdate.setText(getuserreview.get(position).getDate());
            try {
                holder.TvReviews.setText(getuserreview.get(position).getComment());
                holder.tvratingpersentage.setText(getuserreview.get(position).getRating());
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.rbhelperprofile.setNumStars(Integer.parseInt(getuserreview.get(position).getRating()));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }


    }

    public void getuserreview(List<Review> getuserreview) {
        this.getuserreview = getuserreview;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        // return 5;
        return getuserreview == null ? 0 : getuserreview.size();

    }

    public interface ProfileRatingAppOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvratingname, tvdate, TvReviews, tvratingpersentage;
        private RatingBar rbhelperprofile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvratingname = itemView.findViewById(R.id.tvratingnameid);
            tvdate = itemView.findViewById(R.id.tvdateid);
            TvReviews = itemView.findViewById(R.id.tvProfileCommentId);
            tvratingpersentage = itemView.findViewById(R.id.tvratingpersentageid);
            rbhelperprofile = itemView.findViewById(R.id.rbhelperprofileId);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
