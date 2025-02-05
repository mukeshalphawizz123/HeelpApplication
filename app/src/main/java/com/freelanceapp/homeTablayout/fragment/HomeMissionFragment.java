package com.freelanceapp.homeTablayout.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;
import com.freelanceapp.chatPkg.ChatActivity;
import com.freelanceapp.databinding.FragmentHomeRespondttoarerequestBinding;
import com.freelanceapp.homeTablayout.adapter.HomeCategoryFilterAdapter;
import com.freelanceapp.homeTablayout.adapter.FindMisionAdapter;
import com.freelanceapp.homeTablayout.homeModel.ListOfProjectModel;
import com.freelanceapp.homeTablayout.homeModel.Project;
import com.freelanceapp.homeTablayout.homeModel.RepondreUneDemandeModelPkg.RepondreunedemandeModel;
import com.freelanceapp.homeTablayout.homeModel.RepondreUneDemandeModelPkg.YourMission;
import com.freelanceapp.homefilterPkg.HomeCategoryFilterActivity;
import com.freelanceapp.makeAnOfferPkg.MakeAnOfferActivity;
import com.freelanceapp.utility.CheckNetwork;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeMissionFragment extends Fragment implements HomeCategoryFilterAdapter.HomePublisherRequest,
        FindMisionAdapter.HomerespondtoarequestAppOnClickListener, View.OnClickListener, Filterable {
    private FragmentHomeRespondttoarerequestBinding fragmentHomeRespondttoarerequestBinding;
    private FindMisionAdapter findMisionAdapter;
    private RecyclerView rvcategoriesFrag;
    private ProgressBar pbHomeRespondlist;
    private ApiServices apiServices;
    private Context context;
    private List<YourMission> findmission;
    private RelativeLayout Rlcategories;
    private List<Project> projectlist;
    private List<Project> projectlist1;
    private HomeCategoryFilterAdapter homeCategoryFilterAdapter;
    private HomeCategoryFilterActivity homeCategoryFilterActivity;
    private ProgressBar PbsearchId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_respondttoarerequest, container, false);
        //apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            homeRespondeList();
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private void homeRespondeList() {
        pbHomeRespondlist.setVisibility(View.VISIBLE);
        apiServices.repondrelist().enqueue(new Callback<RepondreunedemandeModel>() {
            @Override
            public void onResponse(Call<RepondreunedemandeModel> call, Response<RepondreunedemandeModel> response) {
                if (response.isSuccessful()) {
                    pbHomeRespondlist.setVisibility(View.GONE);
                    RepondreunedemandeModel repondreunedemandeModel = response.body();
                    findmission = repondreunedemandeModel.getYourMissions();
                    findMisionAdapter.findmission(findmission);
                    //seearchDoctorAdapter.doctorList(doctorList);
                }
            }
            @Override
            public void onFailure(Call<RepondreunedemandeModel> call, Throwable t) {
                pbHomeRespondlist.setVisibility(View.GONE);
            }
        });
    }

    private void init(View view) {
        rvcategoriesFrag = view.findViewById(R.id.rvcategoriesFragId);
        Rlcategories = view.findViewById(R.id.rlcategoriesId);
        Rlcategories.setOnClickListener(this);
        pbHomeRespondlist = view.findViewById(R.id.pbHomeRespondlistId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvcategoriesFrag.setLayoutManager(layoutManager);
        findMisionAdapter = new FindMisionAdapter(getActivity(), this);
        rvcategoriesFrag.setAdapter(findMisionAdapter);
        // homepublisharequestAdapter.addHomeData(homeModelArrayList);
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction home = getFragmentManager().beginTransaction();
        home.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        home.replace(R.id.flHomeId, fragment);
        home.commit();
    }


    @Override
    public void findmissionTabClick(View view, int position) {
        switch (view.getId()) {
            case R.id.RlacceptofferId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), MakeAnOfferActivity.class);
                break;
            case R.id.RlDiscussId:
                Intent intent1 = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlcategoriesId:
                Homecategoryfilter();
                break;

        }
    }

    private void Homecategoryfilter() {
        final View dialogView = View.inflate(getActivity(), R.layout.activity_home__category__filter, null);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(dialogView);
        AppCompatImageView iveditClose = (AppCompatImageView) dialog.findViewById(R.id.iveditCloseId);
        RecyclerView rvSearchId = (RecyclerView) dialog.findViewById(R.id.rvSearchId);
        PbsearchId = (ProgressBar) dialog.findViewById(R.id.PbsearchId);
        AppCompatEditText Etsearch = dialog.findViewById(R.id.EtsearchId);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dialogView.getWindowToken(), 0);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rvSearchId.setLayoutManager(layoutManager);
        homeCategoryFilterAdapter = new HomeCategoryFilterAdapter(getActivity(), this);
        rvSearchId.setAdapter(homeCategoryFilterAdapter);
        iveditClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (CheckNetwork.isNetAvailable(getActivity())) {
            homePublisherList();
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        Etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                homeCategoryFilterAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                homeCategoryFilterAdapter.getFilter().filter(s);
            }
        });


        dialog.show();
    }

    private void homePublisherList() {
        PbsearchId.setVisibility(View.VISIBLE);
        apiServices.publisherlist().enqueue(new Callback<ListOfProjectModel>() {
            @Override
            public void onResponse(Call<ListOfProjectModel> call, Response<ListOfProjectModel> response) {
                if (response.isSuccessful()) {
                    PbsearchId.setVisibility(View.GONE);
                    ListOfProjectModel listOfProjectModel = response.body();
                    projectlist = listOfProjectModel.getProjects();
                    homeCategoryFilterAdapter.projectlist(projectlist);
                }
            }
            @Override
            public void onFailure(Call<ListOfProjectModel> call, Throwable t) {
                PbsearchId.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void publishOnClick(View view, int position) {

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    projectlist = projectlist1;
                } else {
                    List<Project> filteredList = new ArrayList<>();
                    for (Project row : projectlist1) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    projectlist = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = projectlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                projectlist = (ArrayList<Project>) filterResults.values;
               // notifyDataSetChanged();
            }
        };
    }
}

