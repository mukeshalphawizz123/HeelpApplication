package com.frelance;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.frelance.chatPkg.chatModlePkg.UnReadMessageUserModle;
import com.frelance.chatPkg.chatModlePkg.UnReadMsgConsersation;
import com.frelance.utility.AppSession;
import com.frelance.utility.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseUnreadUserCount extends Application {
    private String userId;
    private UnReadMsgConsersation consersation;
    private ArrayList<UnReadMessageUserModle> datumList;
    private Context context;


    public FirebaseUnreadUserCount(Context context) {
        userId = AppSession.getStringPreferences(context, Constants.USERID);
        datumList = new ArrayList<>();
        consersation = new UnReadMsgConsersation();
        this.context = context;
    }
    
    public String chatDataSanpchat() {
        datumList.clear();
        String userRecordinsertFormat = "user_" + userId + "_";
        FirebaseDatabase.getInstance().getReference().child("userList/" + userRecordinsertFormat).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                    UnReadMessageUserModle chatModle = new UnReadMessageUserModle((String) mapMessage.get("userId"),
                            (String) mapMessage.get("name"),
                            (String) mapMessage.get("imgUrl"),
                            (String) mapMessage.get("dateAndTime"),
                            (String) mapMessage.get("senderId"));
                    try {
                        // datumList.clear();
                        datumList.add(chatModle);
                        AppSession.setStringPreferences(getApplicationContext(), "count", "" + datumList.size());
                        // layoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);
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
        String count = AppSession.getStringPreferences(context, "count");
        return count;

    }
}
