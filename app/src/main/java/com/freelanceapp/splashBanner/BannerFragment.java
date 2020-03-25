package com.freelanceapp.splashBanner;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import com.freelanceapp.OptionActivity;
import com.freelanceapp.R;

import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.StatusBarManagment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BannerFragment extends AppCompatActivity implements View.OnClickListener {
    private ViewPager vpFirstBanner;
    //private SplashFragmentBinding splashFragmentBinding;
    private TabLayout tlFirstBanner;
    private BannerAdapter bannerAdapter;
    private Handler handler;
    private int currentPage = 0;
    private Timer timer;
    private long DELAY_MS = 500;
    private long PERIOD_MS = 5000;
    private RelativeLayout ivnextpasser, rlCestParti;
    private AppCompatImageView ivbackarrow;
    private ArrayList<Integer> sliderImg;
    private TextView tvsplashtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_fragment);
        sliderImg = new ArrayList<>();
        sliderImg.add(R.drawable.banner);
        sliderImg.add(R.drawable.banner_second);
        sliderImg.add(R.drawable.banner_third);
        Window window = getWindow();
        StatusBarManagment.hideShowStatusBar(getApplicationContext(), window);
        render();
    }

    private void render() {
        tvsplashtext = findViewById(R.id.tvsplashtextid);
        rlCestParti = findViewById(R.id.rlCestPartiId);
        vpFirstBanner = findViewById(R.id.vpFirstBannerId);
        tlFirstBanner = findViewById(R.id.tlFirstBannerId);
        ivnextpasser = findViewById(R.id.ivnextpasserid);
        ivbackarrow = findViewById(R.id.ivbackarrowid);
        ivnextpasser.setOnClickListener(this);
        rlCestParti.setOnClickListener(this);
        ivbackarrow.setOnClickListener(this);
        bannerAdapter = new BannerAdapter(getApplicationContext(), sliderImg);
        vpFirstBanner.setAdapter(bannerAdapter);
        tlFirstBanner.setupWithViewPager(vpFirstBanner);
        //----------Slider Default timer method------------------//
        scrooling(3);
        vpFirstBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2) {
                    hideBtnOnSlider();
                } else if (position == 1) {
                    showBtnOnSlider();
                } else {
                    showBtnOnSlider();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void scrooling(final int length) {
        /*After setting the adapter use the timer */
        handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == length) {
                    if (currentPage == 0) {
                        // Toast.makeText(BannerFragment.this, "adsds", Toast.LENGTH_SHORT).show();
                    }
                    currentPage = 0;
                }
                vpFirstBanner.setCurrentItem(currentPage++, true);
                if (vpFirstBanner.getCurrentItem() == 2) {
                    // Toast.makeText(BannerFragment.this, "adas", Toast.LENGTH_SHORT).show();
                    hideBtnOnSlider();
                } else if (vpFirstBanner.getCurrentItem() == 1) {
                    showBtnOnSlider();
                } else if (vpFirstBanner.getCurrentItem() == 0) {
                    showBtnOnSlider();
                }
            }
        };

        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        handler.post(runnable);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timertask, DELAY_MS, PERIOD_MS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivbackarrowid:
                if (vpFirstBanner.getCurrentItem() == 0) {
                    vpFirstBanner.setCurrentItem(2);
                    //setup to show /hide bottom click btn--------------//
                    // hideBtnOnSlider();
                } else if (vpFirstBanner.getCurrentItem() == 1) {
                    vpFirstBanner.setCurrentItem(0);
                    showBtnOnSlider();
                } else {
                    vpFirstBanner.setCurrentItem(1);
                    // showBtnOnSlider();
                }
                break;
            case R.id.ivnextpasserid:
                CheckNetwork.nextScreen(BannerFragment.this, OptionActivity.class);
                break;
            case R.id.rlCestPartiId:
                CheckNetwork.nextScreen(BannerFragment.this, OptionActivity.class);
                break;
        }
    }

    private void hideBtnOnSlider() {
        ivbackarrow.setVisibility(View.GONE);
        tvsplashtext.setVisibility(View.GONE);
        rlCestParti.setVisibility(View.VISIBLE);
    }

    private void showBtnOnSlider() {
        tvsplashtext.setVisibility(View.VISIBLE);
        ivbackarrow.setVisibility(View.VISIBLE);
        rlCestParti.setVisibility(View.GONE);

    }
}
