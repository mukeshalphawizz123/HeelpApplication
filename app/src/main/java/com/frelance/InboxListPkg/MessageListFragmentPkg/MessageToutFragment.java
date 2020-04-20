package com.frelance.InboxListPkg.MessageListFragmentPkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;

import com.frelance.InboxListPkg.MessageListAdapterPkg.MessageToutAdapter;
import com.frelance.InboxListPkg.msgModlePkg.ChatUserResponseModle;
import com.frelance.InboxListPkg.msgModlePkg.Datum;
import com.frelance.chatPkg.ChatActivity;
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

public class MessageToutFragment extends Fragment implements MessageToutAdapter.MessageToutAppOnClickListener {
    private RecyclerView rvmsglist;
    private ApiServices apiServices;
    private MessageToutAdapter messageToutAdapter;
    private List<Datum> datumList;
    private String userId;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_tout, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
       // Toast.makeText(getActivity(), userId, Toast.LENGTH_LONG).show();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            chatUserlist(userId);
        } else {

        }
        return view;
    }

    private void init(View view) {
        //  rlmsguserid=view.findViewById(R.id.rlmsguserid);
        // rlmsguserid.setOnClickListener(this);
        rvmsglist = view.findViewById(R.id.rvmsglistId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvmsglist.setLayoutManager(layoutManager);
        messageToutAdapter = new MessageToutAdapter(getActivity(), this);
        rvmsglist.setAdapter(messageToutAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public void onClick(View view, int position) {
    }

    @Override
    public void msgOnClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlmsguserid:
               // Toast.makeText(getActivity(), datumList.get(position).getClientId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("client_id", datumList.get(position).getClientId());
                intent.putExtra("firstName", datumList.get(position).getFirstName());
                intent.putExtra("lastName", datumList.get(position).getLastName());
                intent.putExtra("clientImg", datumList.get(position).getPictureUrl());
                startActivity(intent);
                (getActivity()).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }

    private void chatUserlist(String userId) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.getuserslist(userId).enqueue(new Callback<ChatUserResponseModle>() {
            @Override
            public void onResponse(Call<ChatUserResponseModle> call, Response<ChatUserResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    ChatUserResponseModle chatUserResponseModle = response.body();
                    if (chatUserResponseModle.getStatus()) {
                        datumList = chatUserResponseModle.getData();
                        messageToutAdapter.addmymissionData(datumList);
                    } else {
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
            public void onFailure(Call<ChatUserResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });


    }
}
