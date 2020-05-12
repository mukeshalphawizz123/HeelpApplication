package com.frelance.homeTablayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.chatPkg.chatModlePkg.UnReadMessageUserModle;
import com.frelance.chatPkg.chatModlePkg.UnReadMsgConsersation;
import com.frelance.homeTablayout.adapter.HomeCategoryFilterAdapter;
import com.frelance.homeTablayout.fragment.HomeCategoryFrament;
import com.frelance.homeTablayout.fragment.HomeMissionFragment;
import com.frelance.homeTablayout.homeModel.ListOfProjectModel;
import com.frelance.homeTablayout.homeModel.Project;
import com.frelance.homeTablayout.publishPkg.PostADemandActivity;
import com.frelance.notificationPkg.NotificatinModel;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.notificationPkg.NotificationCountResponseModle;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeTablayoutFragment extends Fragment implements
        View.OnClickListener,
        HomeCategoryFilterAdapter.HomePublisherRequest
        , NotificatinModel.OnCustomCartListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivnotificationHome, ivSerachHome;
    private ProgressBar PbsearchId;
    private HomeCategoryFilterAdapter homeCategoryFilterAdapter;
    private ApiServices apiServices;
    private List<Project> projectlist;
    private List<Project> projectlist1;
    Dialog dialog;
    private String userId;
    private UnReadMsgConsersation consersation;
    private ArrayList<UnReadMessageUserModle> datumList;
    private AppCompatTextView tvHomeNotificationCount;
    private Handler handler = new Handler();
    private int apiDelayed = 5 * 1000; //1 second=1000 milisecond, 5*1000=5seconds
    private Runnable runnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tablayout_home, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        datumList = new ArrayList<>();
        consersation = new UnReadMsgConsersation();

        addTabs(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            notification(userId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private void addTabs(View view) {
        tvHomeNotificationCount = view.findViewById(R.id.tvHomeNotificationCountId);
        ivSerachHome = view.findViewById(R.id.ivSerachHomeId);
        ivnotificationHome = view.findViewById(R.id.ivnotificationHomeId);
        tabLayout = view.findViewById(R.id.tabLayoutId);
        viewPager = view.findViewById(R.id.viewPagerId);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new HomeCategoryFrament(), getResources().getString(R.string.post_a_demand));
        adapter.addFrag(new HomeMissionFragment(), getResources().getString(R.string.find_a_mission));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1) {

                }
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        clickListenerSetup();
    }

    private void clickListenerSetup() {
        ivnotificationHome.setOnClickListener(this);
        ivSerachHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivnotificationHomeId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.ivSerachHomeId:
                Homecategoryfilter();
                break;
        }
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
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
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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

    @Override
    public void publishOnClick(View view, int position, Project project) {
        switch (view.getId()) {
            case R.id.rlhomeitemsRowid:
                //  Toast.makeText(getActivity(), ""+position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), PostADemandActivity.class);
                intent.putExtra("imagetitle", project.getTitle());
                intent.putExtra("title", project.getTitle());
                intent.putExtra("description", project.getDescription());
                intent.putExtra("budget", project.getBudget());
                intent.putExtra("imageUrl", project.getPictureUrl());
                getActivity().startActivity(intent);
                dialog.dismiss();
                break;
        }

    }


    private void notification(String userId) {
        //    CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.getnotificationcount(userId).enqueue(new Callback<NotificationCountResponseModle>() {
            @Override
            public void onResponse(Call<NotificationCountResponseModle> call, Response<NotificationCountResponseModle> response) {
                if (response.isSuccessful()) {
                    // CustomProgressbar.hideProgressBar();
                    NotificationCountResponseModle notificationResponseModle = response.body();
                    if (notificationResponseModle.getStatus()) {
                        int messageCount = notificationResponseModle.getCountMessages();
                        int messageDemands = notificationResponseModle.getCountMissionanddemands();
                        int messageOffers = notificationResponseModle.getCountOffers();
                        int messageCountPayment = notificationResponseModle.getCountPayment();
                        int messageCountReveiews = notificationResponseModle.getCountReviews();

                        String totalNotification = String.valueOf(messageCount
                                + messageOffers
                                + messageDemands
                                + messageCountPayment
                                + messageCountReveiews);

                        if (totalNotification == null || totalNotification.isEmpty()) {
                            tvHomeNotificationCount.setVisibility(View.GONE);
                        } else {
                            tvHomeNotificationCount.setText(totalNotification);
                            tvHomeNotificationCount.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvHomeNotificationCount.setVisibility(View.GONE);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!false) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationCountResponseModle> call, Throwable t) {
                // CustomProgressbar.hideProgressBar();
                tvHomeNotificationCount.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do your function;
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    //  Log.v("test","t");
                    notification(userId);
                } else {
                }
                handler.postDelayed(runnable, apiDelayed);
            }
        }, apiDelayed); // so basically after your getHeroes(), from next time it will be 5 sec repeated
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible
    }


    @Override
    public void changeCartState() {
        if (NotificatinModel.getInstance().getNotificationCount().isEmpty()) {
            tvHomeNotificationCount.setVisibility(View.GONE);
        } else {
            tvHomeNotificationCount.setText(NotificatinModel.getInstance().getNotificationCount());
            tvHomeNotificationCount.setVisibility(View.VISIBLE);

        }

    }
}
