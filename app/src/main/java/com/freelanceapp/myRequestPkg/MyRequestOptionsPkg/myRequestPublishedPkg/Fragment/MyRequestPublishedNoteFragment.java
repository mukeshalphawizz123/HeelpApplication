package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestPublishedPkg.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;
import com.freelanceapp.chatPkg.Adapter.ChatActivityMain;

import com.freelanceapp.databinding.FragmentMyRequestPublishedNoteBinding;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestPublishedPkg.Adapter.MyRequestPublishedNoteAdapter;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.DemandInProgressModle;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.YourMission;
import com.freelanceapp.myRequestPkg.myRequestModlePkg.MyDemandeModel;
import com.freelanceapp.paymentPkg.CreditCardPayment;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.CreditCardActivity;
import com.freelanceapp.userProfileRatingPkg.UserProfileActivity;
import com.freelanceapp.utility.CheckNetwork;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyRequestPublishedNoteFragment extends Fragment implements MyRequestPublishedNoteAdapter.MyRequestPublishedNoteAppOnClickListener {
    private FragmentMyRequestPublishedNoteBinding fragmentMyRequestPublishedNoteBinding;
    private MyRequestPublishedNoteAdapter myRequestPublishedNoteAdapter;
    private RecyclerView rvnote;
    private ApiServices apiServices;
    private ProgressBar pbNoteMyDemands;
    private List<YourMission> yourMissionList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_request_published_note, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myDemandsNotes("12");
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private void init(View view) {
        pbNoteMyDemands = view.findViewById(R.id.pbNoteMyDemandsId);
        rvnote = view.findViewById(R.id.rvnoteId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvnote.setLayoutManager(layoutManager);
         myRequestPublishedNoteAdapter = new MyRequestPublishedNoteAdapter(getActivity(), this);
        rvnote.setAdapter(myRequestPublishedNoteAdapter);
    }

    @Override
    public void myReqPublishedNoteTabClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlacceptid:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), CreditCardPayment.class);
                //  Intent intent = new Intent(getActivity(), CreditCardPayment.class);
                //  startActivity(intent);
                // getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.rldiscuteridd:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), ChatActivityMain.class);
                // Intent intent1 = new Intent(getActivity(), ChatActivity.class);
                //  startActivity(intent1);
                //  getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.ivmymissionid:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), UserProfileActivity.class);

                //Intent profile = new Intent(getActivity(), UserProfileActivity.class);
                //  startActivity(profile);
                // getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;


        }

    }

    private void myDemandsNotes(String id) {
        pbNoteMyDemands.setVisibility(View.VISIBLE);
        apiServices.myDemandbidbyid(id).enqueue(new Callback<DemandInProgressModle>() {
            @Override
            public void onResponse(Call<DemandInProgressModle> call, Response<DemandInProgressModle> response) {
                if (response.isSuccessful()) {
                    pbNoteMyDemands.setVisibility(View.GONE);
                    DemandInProgressModle requestlist = response.body();
                    if (requestlist.getStatus() == true) {
                        yourMissionList = requestlist.getYourMissions();
                        myRequestPublishedNoteAdapter.addmyDemandsData(yourMissionList);
                    } else {

                    }


                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbNoteMyDemands.setVisibility(View.GONE);
                                String message = jsonObject.getString("message");
                                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DemandInProgressModle> call, Throwable t) {
                pbNoteMyDemands.setVisibility(View.GONE);
            }
        });
    }


}
