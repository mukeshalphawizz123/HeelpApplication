package com.frelance.InboxListPkg.MessageListFragmentPkg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.frelance.InboxListPkg.MessageListAdapterPkg.UnReadMsgAdapter;
import com.frelance.R;
import com.frelance.InboxListPkg.MessageListAdapterPkg.MessageToutAdapter;
import com.frelance.chatPkg.ChatActivity;
import com.frelance.chatPkg.chatModlePkg.UnReadMessageUserModle;
import com.frelance.chatPkg.chatModlePkg.UnReadMsgConsersation;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageNonLusFragment extends Fragment implements UnReadMsgAdapter.MessageToutAppOnClickListener {
    private RecyclerView rvmsglist;
    private ApiServices apiServices;
    private MessageToutAdapter messageToutAdapter;
    private ArrayList<UnReadMessageUserModle> datumList;
    private String userId;
    private UnReadMsgConsersation consersation;
    LinearLayoutManager layoutManager;
    private UnReadMsgAdapter unReadMsgAdapter;
    private String entryFlag = "1";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  fragmentMessageToutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_tout, container, false);
        View view = inflater.inflate(R.layout.fragment_message_tout, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        datumList = new ArrayList<>();
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        consersation = new UnReadMsgConsersation();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            chatDataSanpchat();
        } else {

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


    private void init(View view) {
        rvmsglist = view.findViewById(R.id.rvmsglistId);
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvmsglist.setLayoutManager(layoutManager);
        unReadMsgAdapter = new UnReadMsgAdapter(getActivity(), this);
        rvmsglist.setAdapter(messageToutAdapter);


    }

    private void chatDataSanpchat() {
        datumList.clear();
        String userRecordinsertFormat = "user_" + userId + "_";
        FirebaseDatabase.getInstance().getReference().child("userList/" + userRecordinsertFormat).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.v("te",s);
              //  String str = s;

                if (dataSnapshot.getValue() != null) {
                    HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                    UnReadMessageUserModle chatModle = new UnReadMessageUserModle((String) mapMessage.get("userId"),
                            (String) mapMessage.get("name"),
                            (String) mapMessage.get("imgUrl"),
                            (String) mapMessage.get("dateAndTime"),
                            (String) mapMessage.get("senderId"));
                    try {
                        datumList.add(chatModle);
                        unReadMsgAdapter.addmymissionData(datumList);
                        unReadMsgAdapter.notifyDataSetChanged();
                        AppSession.setStringPreferences(getActivity(), "count", "" + datumList.size());

                        // layoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }


              /*  getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        datumList.clear();
                    }
                });*/

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                datumList.clear();
            }


            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                datumList.clear();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                datumList.clear();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                CustomProgressbar.hideProgressBar();
                datumList.clear();
            }
        });

        rvmsglist.setAdapter(unReadMsgAdapter);

    }

    @Override
    public void msgOnClick(View view, int position, UnReadMessageUserModle unReadMessageUserModle) {
        switch (view.getId()) {
            case R.id.rlmsguserid:
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("client_id", unReadMessageUserModle.getSenderId());
                intent.putExtra("firstName", unReadMessageUserModle.getName());
                intent.putExtra("lastName", unReadMessageUserModle.getName());
                intent.putExtra("clientImg", unReadMessageUserModle.getImgUrl());
                startActivity(intent);
                (getActivity()).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                // CheckNetwork.nextScreenWithoutFinish(getActivity(), ChatActivityMain.class);
                break;
        }
    }

}
