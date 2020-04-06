package com.frelance.chatPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.R;
import com.frelance.chatPkg.Adapter.ChatAdapter;
import com.frelance.chatPkg.chatModlePkg.ChatModle;
import com.frelance.chatPkg.chatModlePkg.Consersation;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.clientProfilePkg.ClinetProfileActivity;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.ChatAppOnClickListener
        , View.OnClickListener {
    private ChatAdapter chatAdapter;
    private RelativeLayout rlmessageuserprofile;
    private RecyclerView rvmsglist;
    private ImageView ivbackmsg, ivnotificationHome, ivgifbutton;
    private ApiServices apiServices;
    private String clientId, fName, lName, clientImg;
    private AppCompatEditText ettypemsg;
    private String userid;
    private LinearLayoutManager layoutManager;
    private Consersation consersation;
    private DatabaseReference mRootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        userid = getIntent().getStringExtra(Constants.USERID);
        clientId = getIntent().getStringExtra("client_id");
        fName = getIntent().getStringExtra("firstName");
        lName = getIntent().getStringExtra("lastName");
        clientImg = getIntent().getStringExtra("clientImg");
        consersation = new Consersation();
        init();


    }

    private void init() {
        ettypemsg = findViewById(R.id.ettypemsgid);
        ivnotificationHome = findViewById(R.id.ivnotificationHomeId);
        ivnotificationHome.setOnClickListener(this);
        ivbackmsg = findViewById(R.id.ivbackmsgId);
        ivgifbutton = findViewById(R.id.ivgifbuttonid);
        ivbackmsg.setOnClickListener(this);
        ivgifbutton.setOnClickListener(this);
        rlmessageuserprofile = findViewById(R.id.rlmessageuserprofileid);
        rlmessageuserprofile.setOnClickListener(this);
        rvmsglist = findViewById(R.id.rvChatId);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvmsglist.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(getApplicationContext(), this);
      //  chatDataSanpchat();
    }


    private void chatDataSanpchat() {

        ///  adapter = new ListMessageAdapter(this, consersation, bitmapAvataFriend, bitmapAvataUser);
        FirebaseDatabase.getInstance().getReference().child("message/" +userid + "_" + clientId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getValue() != null) {
//                        postnotification("Alert", "You received message");
                            HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                            ChatModle chatModle = new ChatModle((String) mapMessage.get("userId"),
                                    (String) mapMessage.get("client"),
                                    (String) mapMessage.get("dateTime"),
                                    (String) mapMessage.get("message"));

                            consersation.getListMessageData().add(chatModle);
                            chatAdapter.notifyDataSetChanged();
                            layoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);

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

                    }
                });

        rvmsglist.setAdapter(chatAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlmessageuserprofileid:
                CheckNetwork.nextScreenWithoutFinish(ChatActivity.this, ClinetProfileActivity.class);
                break;
            case R.id.ivbackmsgId:
                onBackPressed();
                break;
            case R.id.ivnotificationHomeId:
                CheckNetwork.nextScreenWithoutFinish(ChatActivity.this, NotificationActivity.class);
                break;
            case R.id.ivgifbuttonid:
                String content = ettypemsg.getText().toString().trim();
                final String message = ettypemsg.getText().toString();
                if (content.length() > 0) {
                    ettypemsg.setText("");
                    ChatModle newMessage = new ChatModle(userid, clientId, Constants.currentDateAndTime(), message);
                    FirebaseDatabase.getInstance().getReference().child("message/" +userid + "_" + clientId).push().setValue(newMessage);
                    break;

                }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }
}
