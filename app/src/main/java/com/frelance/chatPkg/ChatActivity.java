package com.frelance.chatPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.R;
import com.frelance.chatPkg.Adapter.ChatAdapter;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.userProfileRatingPkg.UserProfileActivity;
import com.frelance.utility.CheckNetwork;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.ChatAppOnClickListener, View.OnClickListener {
    private ChatAdapter chatAdapter;
    private RelativeLayout rlmessageuserprofile;
    private RecyclerView rvmsglist;
    private ImageView ivbackmsg, ivnotificationHome;
    private ApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();


/*
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat, container, false);
        init(view);
        return view;*/


    }

    private void init() {
        ivnotificationHome = findViewById(R.id.ivnotificationHomeId);
        ivnotificationHome.setOnClickListener(this);

        ivbackmsg = findViewById(R.id.ivbackmsgId);
        ivbackmsg.setOnClickListener(this);


        rlmessageuserprofile = findViewById(R.id.rlmessageuserprofileid);
        rlmessageuserprofile.setOnClickListener(this);

        rvmsglist = findViewById(R.id.rvChatId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvmsglist.setLayoutManager(layoutManager);
       // ChatAdapter chatAdapter = new ChatAdapter(getApplicationContext(), this);
       // rvmsglist.setAdapter(chatAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlmessageuserprofileid:
                CheckNetwork.nextScreenWithoutFinish(ChatActivity.this, UserProfileActivity.class);
                break;
            case R.id.ivbackmsgId:
                onBackPressed();
                break;
            case R.id.ivnotificationHomeId:
                CheckNetwork.nextScreenWithoutFinish(ChatActivity.this, NotificationActivity.class);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }
}
