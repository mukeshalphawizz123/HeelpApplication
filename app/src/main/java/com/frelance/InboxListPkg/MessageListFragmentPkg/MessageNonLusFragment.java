package com.frelance.InboxListPkg.MessageListFragmentPkg;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.database.FirebaseDatabase;

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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  fragmentMessageToutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_tout, container, false);
        View view = inflater.inflate(R.layout.fragment_message_tout, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        datumList = new ArrayList<>();
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        consersation = new UnReadMsgConsersation();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            // chatUserlist("1");
            // chatUserlist(userId);
            chatDataSanpchat();
        } else {

        }

        return view;
    }

    private void init(View view) {
        rvmsglist = view.findViewById(R.id.rvmsglistId);
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvmsglist.setLayoutManager(layoutManager);
        unReadMsgAdapter = new UnReadMsgAdapter(getActivity(), this);
        rvmsglist.setAdapter(messageToutAdapter);
        chatDataSanpchat();

    }

    private void chatDataSanpchat() {
        datumList.clear();
        String userRecordinsertFormat = "user_" + userId + "_";

        FirebaseDatabase.getInstance().getReference().child("userList/" + userRecordinsertFormat).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    //CustomProgressbar.hideProgressBar();
//                        postnotification("Alert", "You received message");
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
                        // layoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);


                       /* if (datumList.contains(chatModle.getSenderId())) {
                        } else {
                            datumList.add(chatModle);
                            unReadMsgAdapter.addmymissionData(datumList);
                            unReadMsgAdapter.notifyDataSetChanged();
                            layoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);
                        }*/
                        //consersation.getListMessageData().add(chatModle);

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }


            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                CustomProgressbar.hideProgressBar();

            }
        });

    /*    FirebaseDatabase.getInstance().getReference().child("userList/" + userRecordinsertFormat).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    //CustomProgressbar.hideProgressBar();
//                        postnotification("Alert", "You received message");
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
                       // layoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);


                       *//* if (datumList.contains(chatModle.getSenderId())) {
                        } else {
                            datumList.add(chatModle);
                            unReadMsgAdapter.addmymissionData(datumList);
                            unReadMsgAdapter.notifyDataSetChanged();
                            layoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);
                        }*//*
                        //consersation.getListMessageData().add(chatModle);

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }


            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                CustomProgressbar.hideProgressBar();

            }
        });*/

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
