package com.freelanceapp.homefilterPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;
import com.freelanceapp.homeTablayout.adapter.HomeCategoryFilterAdapter;
import com.freelanceapp.homeTablayout.homeModel.ListOfProjectModel;
import com.freelanceapp.homeTablayout.homeModel.Project;
import com.freelanceapp.homefilterPkg.AdapterPkg.FilterAdapter;
import com.freelanceapp.utility.CheckNetwork;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCategoryFilterActivity extends AppCompatActivity implements FilterAdapter.FilterAppOnClickListener,HomeCategoryFilterAdapter.HomePublisherRequest{

    private AppCompatImageView iveditCloseid;
    private Context context;
    private List<Project> projectlist;
    private ProgressBar pbHomepublisherlist;
    private ApiServices apiServices;
    private HomeCategoryFilterAdapter homeCategoryFilterAdapter;
    private RecyclerView rvSearch;
    private EditText Etsearch;
    private ProgressBar Pbsearch;
    public FilterAdapter filterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__category__filter);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            searchfilter();
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        Etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                homeCategoryFilterAdapter.getFilter().filter(s);
               Toast.makeText(HomeCategoryFilterActivity.this, ""+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                  //  homeCategoryFilterAdapter.getFilter().filter(s);
                    Toast.makeText(HomeCategoryFilterActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                    //ivLogoHome.setVisibility(View.VISIBLE);
                } else {
                   // ivLogoHome.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    private void searchfilter() {
        Pbsearch.setVisibility(View.VISIBLE);
        apiServices.publisherlist().enqueue(new Callback<ListOfProjectModel>() {
            @Override
            public void onResponse(Call<ListOfProjectModel> call, Response<ListOfProjectModel> response) {
                if (response.isSuccessful()) {
                    Pbsearch.setVisibility(View.GONE);
                    ListOfProjectModel listOfProjectModel = response.body();
                    projectlist = listOfProjectModel.getProjects();
                    filterAdapter.projectlist(projectlist);
                }
            }

            @Override
            public void onFailure(Call<ListOfProjectModel> call, Throwable t) {
                Pbsearch.setVisibility(View.GONE);
            }
        });
    }

    private void init() {
        Etsearch=findViewById(R.id.EtsearchId);
        Pbsearch=findViewById(R.id.PbsearchId);
        rvSearch = findViewById(R.id.rvphyFragId);
        homeCategoryFilterAdapter =new HomeCategoryFilterAdapter(context,this);
    }


    @Override
    public void publishOnClick(View view, int position) {

    }
}

