package com.frelance.homeTablayout.publishPkg;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.R;

import java.util.List;

public class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.ViewHolder> {
    private Context context;
    private SelectImageOnClickListener appointmentOnClickListener;
    private List<Uri> photos;


    public SelectImageAdapter(Context context, SelectImageOnClickListener appointmentOnClickListener) {
        this.appointmentOnClickListener = appointmentOnClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_select_images, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public void scheduleappoinList(List<Uri> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Uri photo = photos.get(position);
        // String path = photo.getPath();
        // File sd = Environment.getExternalStorageDirectory();
        // File image = new File(path);
        // BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        // Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
        //  //bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
        holder.ivselectimageId.setImageURI(photo);
       /* holder.ivdeletCloseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photos.remove(position);
                notifyDataSetChanged();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }


    public interface SelectImageOnClickListener {
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View viewconformation;
        private AppCompatImageView ivselectimageId, ivdeletCloseId, tvestimettimenid, tvyourtoken;

        public ViewHolder(View itemView) {
            super(itemView);
            ivselectimageId = itemView.findViewById(R.id.ivselectimageId);
            ivdeletCloseId = itemView.findViewById(R.id.ivdeletCloseId);
            ivdeletCloseId.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            appointmentOnClickListener.onClick(v,getAdapterPosition());
        }
    }
}
