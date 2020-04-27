package com.frelance.homeTablayout.fragment;

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

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.chatPkg.ChatActivity;
import com.frelance.chatPkg.chatModlePkg.ChatEntryModel;
import com.frelance.homeTablayout.adapter.HomeCategoryFilterAdapter;
import com.frelance.homeTablayout.adapter.FindMisionAdapter;
import com.frelance.homeTablayout.homeModel.ListOfProjectModel;
import com.frelance.homeTablayout.homeModel.Project;
import com.frelance.homeTablayout.homeModel.RepondreUneDemandeModelPkg.RepondreunedemandeModel;
import com.frelance.homeTablayout.homeModel.RepondreUneDemandeModelPkg.YourMission;
import com.frelance.homefilterPkg.HomeCategoryFilterActivity;
import com.frelance.makeAnOfferPkg.MakeAnOfferActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeMissionFragment extends Fragment implements HomeCategoryFilterAdapter.HomePublisherRequest,
        FindMisionAdapter.HomerespondtoarequestAppOnClickListener, View.OnClickListener, Filterable {

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
    private String userId;
    private Dialog dialog;
    private boolean flag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_respondttoarerequest, container, false);
        //apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            flag = false;
            homeRespondeList(userId, "0");
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

    private void homeRespondeList(String userId, String category_id) {
        //   pbHomeRespondlist.setVisibility(View.VISIBLE);
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.repondrelist(userId, category_id).enqueue(new Callback<RepondreunedemandeModel>() {
            @Override
            public void onResponse(Call<RepondreunedemandeModel> call, Response<RepondreunedemandeModel> response) {
                if (response.isSuccessful()) {

                    try {
                        CustomProgressbar.hideProgressBar();
                        // pbHomeRespondlist.setVisibility(View.GONE);
                        RepondreunedemandeModel repondreunedemandeModel = response.body();
                        findmission = repondreunedemandeModel.getYourMissions();
                        findMisionAdapter.findmission(findmission);
                        if (flag == false) {
                        } else {
                            dialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //seearchDoctorAdapter.doctorList(doctorList);
                }
            }

            @Override
            public void onFailure(Call<RepondreunedemandeModel> call, Throwable t) {
                //  pbHomeRespondlist.setVisibility(View.GONE);
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    private void chatEntryApi(String userId, String client) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.chatEntryApi(userId, client).enqueue(new Callback<ChatEntryModel>() {
            @Override
            public void onResponse(Call<ChatEntryModel> call, Response<ChatEntryModel> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        ChatEntryModel chatEntryModel = response.body();
                        if (chatEntryModel.getStatus()) {
                            Intent intent1 = new Intent(getActivity(), ChatActivity.class);
                            intent1.putExtra("client_id", client);
                            startActivity(intent1);
                            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        } else {
                            // dialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //seearchDoctorAdapter.doctorList(doctorList);
                }
            }

            @Override
            public void onFailure(Call<ChatEntryModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
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
    public void findmissionTabClick(View view, int position, YourMission yourMission) {
        switch (view.getId()) {
            case R.id.RlacceptofferId:
                AppSession.setStringPreferences(getActivity(), "mission_Id", yourMission.getMissionId());
                CheckNetwork.nextScreenWithoutFinish(getActivity(), MakeAnOfferActivity.class);
                break;
            case R.id.RlDiscussId:
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    chatEntryApi(userId, yourMission.getClient_id());
                } else {
                    Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
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
        dialog = new Dialog(getActivity());
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
    public void publishOnClick(View view, int position, Project project) {
        switch (view.getId()) {
            case R.id.rlhomeitemsRowid:
                flag = true;
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    homeRespondeList(userId, project.getProjectId());
                } else {
                    Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
                break;
        }
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

