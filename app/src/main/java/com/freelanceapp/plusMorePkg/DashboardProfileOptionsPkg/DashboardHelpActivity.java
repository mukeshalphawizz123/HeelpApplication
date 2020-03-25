package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
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

import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.plusMorePkg.PlusMoreFragment;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;

public class DashboardHelpActivity extends Fragment implements View.OnClickListener {
    private ImageView ivdashboardbackhelp,ivnotification;
    private AppCompatTextView tvmail;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_help);*/

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard_help, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ivnotification=view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);

        tvmail = view.findViewById(R.id.tvmailidid);
        tvmail.setOnClickListener(this);

        ivdashboardbackhelp = view.findViewById(R.id.ivdashboardbackhelpId);
        ivdashboardbackhelp.setOnClickListener(this);

        SpannableString content = new SpannableString("contact@heelp.com");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvmail.setText(content);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivdashboardbackhelpId:
                // CheckNetwork.goTobackScreen(getActivity(), PlusMoreFragment.class);
                removeAllFragment(new PlusMoreFragment(),false, Constants.PLUS_MORE_FRAGMENT);
                break;
            case R.id.ivnotificationId:
                // replaceFragement(new NotificationActivity());
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;


        }
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
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
