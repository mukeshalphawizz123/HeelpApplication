package com.frelance.homeTablayout.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.homeTablayout.adapter.HomeCategoryAdapter;
import com.frelance.homeTablayout.homeModel.ListOfProjectModel;
import com.frelance.homeTablayout.homeModel.Project;
import com.frelance.homeTablayout.publishPkg.PostADemandActivity;
import com.frelance.R;
import com.frelance.utility.CheckNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCategoryFrament extends Fragment implements
        HomeCategoryAdapter.HomePublisherRequest {
    //  private ArrayList<HomeModel> homeModelArrayList;
    private HomeCategoryAdapter homeCategoryAdapter;
    private RecyclerView rvphyFrag;
    private RelativeLayout rlhomeitems;
    private ProgressBar pbHomepublisherlist;
    private ApiServices apiServices;
    private List<Project> projectlist;
    private Context context;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_publisharequest, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            homePublisherList();
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    private void homePublisherList() {
        // pbHomepublisherlist.setVisibility(View.VISIBLE);
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.publisherlist().enqueue(new Callback<ListOfProjectModel>() {
            @Override
            public void onResponse(Call<ListOfProjectModel> call, Response<ListOfProjectModel> response) {
                CustomProgressbar.hideProgressBar();
                if (response.isSuccessful()) {
                    try {
                        ListOfProjectModel listOfProjectModel = response.body();
                        projectlist = listOfProjectModel.getProjects();
                        homeCategoryAdapter.projectlist(projectlist);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // seearchDoctorAdapter.doctorList(doctorList);
                }
            }

            @Override
            public void onFailure(Call<ListOfProjectModel> call, Throwable t) {
                //  pbHomepublisherlist.setVisibility(View.GONE);
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    private void init(View view) {
        pbHomepublisherlist = view.findViewById(R.id.pbHomepublisherlistId);
        rvphyFrag = view.findViewById(R.id.rvphyFragId);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rvphyFrag.setLayoutManager(layoutManager);
        homeCategoryAdapter = new HomeCategoryAdapter(getActivity(), this);
       // homeCategoryAdapter.setHasStableIds(true);
        rvphyFrag.setAdapter(homeCategoryAdapter);
        rvphyFrag.setHasFixedSize(true);
        rvphyFrag.setNestedScrollingEnabled(false);

       // rvphyFrag.setNestedScrollingEnabled(false);

    }


    @Override
    public void publishOnClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlhomeitemsRowid:
                Intent intent = new Intent(getActivity(), PostADemandActivity.class);
                intent.putExtra("imagetitle", projectlist.get(position).getTitle());
                intent.putExtra("title", projectlist.get(position).getTitle());
                intent.putExtra("description", projectlist.get(position).getDescription());
                intent.putExtra("budget", projectlist.get(position).getBudget());
                intent.putExtra("imageUrl", projectlist.get(position).getPictureUrl());
                intent.putExtra("projectId", projectlist.get(position).getProjectId());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }
}
