package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.chatPkg.ChatActivity;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Adapter.MyRequestPublishedNoteAdapter;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.DemandInProgressModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.YourMission;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.notesModlePkg.AcceptOfferModle;
import com.frelance.paymentPkg.CreditCardPayment;
import com.frelance.clientProfilePkg.ClinetProfileActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRequestPublishedBudgetFragment extends Fragment implements MyRequestPublishedNoteAdapter.MyRequestPublishedNoteAppOnClickListener {

    private ApiServices apiServices;
    private MyRequestPublishedNoteAdapter myRequestPublishedNoteAdapter;
    private RecyclerView rvnote;
    private ProgressBar pbNoteMyDemands;
    private List<YourMission> yourMissionList;
    private String userId, projectId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_request_published_note, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        projectId = AppSession.getStringPreferences(getActivity(), "projectid");
      //   Toast.makeText(getActivity(),projectId,Toast.LENGTH_LONG).show();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myDemandsNotes(projectId);

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
    public void myReqPublishedNoteTabClick(View view, int position, YourMission yourMission) {
        switch (view.getId()) {
            case R.id.rlacceptid:
                AppSession.setStringPreferences(getActivity(), "clientId", yourMission.getUserId());
                acceptOffer(yourMission.getOfferId(), userId, yourMission.getMissionId(), "1");
                break;
            case R.id.rldiscuteridd:
                AppSession.setStringPreferences(getActivity(),"chatEntrty","");
                Intent intent1 = new Intent(getActivity(), ChatActivity.class);
                intent1.putExtra("client_id", yourMission.getUserId());
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.ivmymissionid:
                AppSession.setStringPreferences(getActivity(), "clientId", yourMission.getUserId());
                CheckNetwork.nextScreenWithoutFinish(getActivity(), ClinetProfileActivity.class);
                break;

        }

    }

    private void myDemandsNotes(String id) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.myDemandbidbybudget(id).enqueue(new Callback<DemandInProgressModle>() {
            @Override
            public void onResponse(Call<DemandInProgressModle> call, Response<DemandInProgressModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    DemandInProgressModle requestlist = response.body();
                    if (requestlist.getStatus()) {
                        yourMissionList = requestlist.getYourMissions();
                        Log.v("testing", yourMissionList.get(0).getPicture_url());
                        myRequestPublishedNoteAdapter.addmyDemandsData(yourMissionList);
                    } else {
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
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
                CustomProgressbar.hideProgressBar();
            }
        });
    }


    private void acceptOffer(String offerid, String userId, String missionId, String status) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.acceptOffer(offerid, userId, missionId, status).enqueue(new Callback<AcceptOfferModle>() {
            @Override
            public void onResponse(Call<AcceptOfferModle> call, Response<AcceptOfferModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    AcceptOfferModle requestlist = response.body();
                    if (requestlist.getStatus()) {
                        Toast.makeText(getActivity(), requestlist.getMessage(), Toast.LENGTH_LONG).show();
                        AppSession.setStringPreferences(getActivity(),"pay_mission_id",missionId);
                        CheckNetwork.nextScreenWithoutFinish(getActivity(), CreditCardPayment.class);
                    } else {
                        Toast.makeText(getActivity(), requestlist.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
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
            public void onFailure(Call<AcceptOfferModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }
}