package com.freelanceapp.messageListPkg.MessageListFragmentPkg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.CustomProgressbar;
import com.freelanceapp.R;
import com.freelanceapp.chatPkg.Adapter.ChatActivityMain;

import com.freelanceapp.messageListPkg.MessageListAdapterPkg.MessageToutAdapter;
import com.freelanceapp.messageListPkg.msgModlePkg.ChatUserResponseModle;
import com.freelanceapp.messageListPkg.msgModlePkg.Datum;
import com.freelanceapp.utility.CheckNetwork;

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



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  fragmentMessageToutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_tout, container, false);
        View view = inflater.inflate(R.layout.fragment_message_tout, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            chatUserlist("1");
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

    public void onClick(View view, int position) {

    }

    @Override
    public void msgOnClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlmsguserid:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), ChatActivityMain.class);
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
            public void onFailure(Call<ChatUserResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });


    }
}
