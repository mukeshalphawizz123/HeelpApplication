package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelanceapp.BuildConfig;
import com.freelanceapp.R;
import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.DashboardSupportActivity;
import com.freelanceapp.plusMorePkg.PlusMoreFragment;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;

public class DeshboardSponsorshipActivity extends Fragment implements View.OnClickListener {
    private ImageView ivsponsorshipdashboardback, ivnotification, ivdashboardsett;
    private TextView tvtextici;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_deshboard_sponsorship, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ivdashboardsett = view.findViewById(R.id.ivdashboardsettId);
        ivdashboardsett.setOnClickListener(this);
        tvtextici = view.findViewById(R.id.tvtexticiid);
        tvtextici.setOnClickListener(this);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        ivsponsorshipdashboardback = view.findViewById(R.id.ivsponsorshipdashboardbackId);
        ivsponsorshipdashboardback.setOnClickListener(this);
        SpannableString content = new SpannableString("ici");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvtextici.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivsponsorshipdashboardbackId:
                // CheckNetwork.goTobackScreen(getActivity(), PlusMoreFragment.class);
                // replaceFragement(new PlusMoreFragment());
                addFragment(new PlusMoreFragment(), false, Constants.PLUS_MORE_FRAGMENT);
                break;

            case R.id.ivnotificationId:
                // replaceFragement(new NotificationActivity());
                CheckNetwork.goTobackScreen(getActivity(), NotificationActivity.class);
                break;
            case R.id.tvtexticiid:
                addFragment12(new DashboardSupportActivity(), true, Constants.DESHBOARD_SUPPORT);
               // replaceFragement(new DashboardSupportActivity());
                //  CheckNetwork.nextScreen(new (), DashboardSupportActivity.class);
                break;
            case R.id.ivdashboardsettId:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
                break;


        }

    }

    public void removeAllFragment(Fragment replaceFragment,
                                  boolean addToBackStack, String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);
        manager.popBackStackImmediate(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.flHomeId, replaceFragment);
        ft.commit();
    }
    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void addFragment(Fragment fragment, boolean addToBackStack,
                            String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }else {
           manager =  getActivity().getSupportFragmentManager();
           manager.popBackStackImmediate();
        }
        ft.replace(R.id.flHomeId, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public void addFragment12(Fragment fragment, boolean addToBackStack,
                            String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_left,R.anim.slide_in_right,R.anim.slide_out_right);
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }else {
            manager =  getActivity().getSupportFragmentManager();
            manager.popBackStackImmediate();
        }
        ft.replace(R.id.flHomeId, fragment, tag);
        ft.commitAllowingStateLoss();
    }
}
