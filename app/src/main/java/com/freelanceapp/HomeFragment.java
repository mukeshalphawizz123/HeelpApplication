package com.freelanceapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.freelanceapp.homeTablayout.HomeTablayoutFragment;
import com.freelanceapp.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private HomeFragmentBinding homeFragmentBinding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        View view = homeFragmentBinding.getRoot();
        init(view);
        return view;
    }

    private void init(View view) {
        replaceFragement(new HomeTablayoutFragment());
    }


    private void replaceFragement(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.flrecentFragHomeId, fragment)
                .commit();
    }



    @Override
    public void onClick(View v) {

    }
}

