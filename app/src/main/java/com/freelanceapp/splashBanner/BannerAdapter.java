package com.freelanceapp.splashBanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.freelanceapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Integer> integerArrayList;

    public BannerAdapter(Context context, ArrayList<Integer> integerArrayList) {
        mContext = context;
        this.integerArrayList = integerArrayList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.banner_adapter, collection, false);
        RoundedImageView ivSlideId = layout.findViewById(R.id.ivSlideId);
        ivSlideId.setBackgroundResource(integerArrayList.get(position));
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return integerArrayList == null ? 0 : integerArrayList.size();

    }


/*
    public void addBanner(List<Datum> datumList) {
        this.datumList = datumList;
        notifyDataSetChanged();
    }
*/

   /* @Override
    public int getCount() {
        //  return 3;
        return datumList == null ? 0 : datumList.size();
    }*/

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
