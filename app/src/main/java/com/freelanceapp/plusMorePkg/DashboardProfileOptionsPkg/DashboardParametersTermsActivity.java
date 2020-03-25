package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.plusMorePkg.PlusMoreFragment;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;

public class DashboardParametersTermsActivity extends Fragment implements View.OnClickListener {
    private ImageView ivdashboardtermsback,ivnotificationterms;
    private TextView tvtext,tvtextthree,tvtextfive,tvtextseven,tvtexteghit;

public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_dashboard_parameters_terms, container, false);
    init(view);
    return view;

    }

    private void init(View view) {
        tvtexteghit=view.findViewById(R.id.tvtexteghitid);
        tvtext=view.findViewById(R.id.tvtextid);
        tvtextthree=view.findViewById(R.id.tvtextthreeid);
        tvtextfive=view.findViewById(R.id.tvtextfiveid);
        tvtextseven=view.findViewById(R.id.tvtextsevenid);


        ivnotificationterms=view.findViewById(R.id.ivnotificationtermsId);
        ivnotificationterms.setOnClickListener(this);
        ivdashboardtermsback=view.findViewById(R.id.ivdashboardtermsbackId);
        ivdashboardtermsback.setOnClickListener(this);

        SpannableString content = new SpannableString("Vos engagements :");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvtext.setText(content);

        SpannableString content1 = new SpannableString("Données personnelles :");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvtextthree.setText(content1);

        SpannableString content2 = new SpannableString("Terminologie :");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        tvtextthree.setText(content2);

        SpannableString content3 = new SpannableString("Statuts des missions et demandes :");
        content3.setSpan(new UnderlineSpan(), 0, content3.length(), 0);
        tvtextseven.setText(content3);

        SpannableString content4 = new SpannableString("Parrainage:");
        content4.setSpan(new UnderlineSpan(), 0, content4.length(), 0);
        tvtexteghit.setText(content4);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivdashboardtermsbackId:
                // CheckNetwork.goTobackScreen(getActivity(), PlusMoreFragment.class);
                removeAllFragment(new PlusMoreFragment(),false, Constants.PLUS_MORE_FRAGMENT);
                break;
            case R.id.ivnotificationtermsId:
                // replaceFragement(new NotificationActivity());
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;

        }
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
}
