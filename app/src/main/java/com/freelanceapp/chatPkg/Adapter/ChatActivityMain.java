package com.freelanceapp.chatPkg.Adapter;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.R;
import com.freelanceapp.chatPkg.Adapter.chatbox.AttachmentOption;
import com.freelanceapp.chatPkg.Adapter.chatbox.AttachmentOptionsListener;
import com.freelanceapp.chatPkg.Adapter.chatbox.AudioRecordView;
import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.userProfileRatingPkg.UserProfileActivity;
import com.freelanceapp.utility.CheckNetwork;

import java.util.List;

public class ChatActivityMain extends AppCompatActivity implements ChatAdapter.ChatAppOnClickListener, View.OnClickListener, BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener, BSImagePicker.ImageLoaderDelegate, AudioRecordView.RecordingListener, AttachmentOptionsListener {
    private ChatAdapter chatAdapter;
    private RelativeLayout rlmessageuserprofile;
    private RecyclerView rvmsglist;
    private ImageView ivbackmsg, ivnotificationHome;
    private ApiServices apiServices;
    private ImageView ivImage1, ivImage2, ivImage3, ivImage4, ivImage5, ivImage6;
    private MessageAdapter messageAdapter;
    private AudioRecordView audioRecordView;
    private RecyclerView recyclerViewMessages;
    private long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        audioRecordView = new AudioRecordView();
        audioRecordView.initView((FrameLayout) findViewById(R.id.layoutMain));
        View containerView = audioRecordView.setContainerView(R.layout.layout_chatting);
        audioRecordView.setRecordingListener(this);
        recyclerViewMessages = containerView.findViewById(R.id.recyclerViewMessages);
        messageAdapter = new MessageAdapter();
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewMessages.setHasFixedSize(false);
        recyclerViewMessages.setAdapter(messageAdapter);
        recyclerViewMessages.setItemAnimator(new DefaultItemAnimator());
        setListener();
        audioRecordView.getMessageView().requestFocus();
        audioRecordView.setAttachmentOptions(AttachmentOption.getDefaultList(), this);
        audioRecordView.removeAttachmentOptionAnimation(false);
        init();
/*
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat, container, false);
        init(view);
        return view;*/


    }


    private void setListener() {
        audioRecordView.getEmojiView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioRecordView.hideAttachmentOptionView();
                showToast("Emoji Icon Clicked");
            }
        });

        audioRecordView.getCameraView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioRecordView.hideAttachmentOptionView();
                showToast("Camera Icon Clicked");
            }
        });

        audioRecordView.getSendView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = audioRecordView.getMessageView().getText().toString().trim();
                audioRecordView.getMessageView().setText("");
                messageAdapter.add(new Message(msg), ChatActivityMain.this);
            }
        });
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private void debug(String log) {
        Log.d("VarunJohn", log);
    }
    private void init() {
        ivnotificationHome = findViewById(R.id.ivnotificationHomeId);
        ivnotificationHome.setOnClickListener(this);
        ivbackmsg = findViewById(R.id.ivbackmsgId);
        ivbackmsg.setOnClickListener(this);
        rlmessageuserprofile = findViewById(R.id.rlmessageuserprofileid);
        rlmessageuserprofile.setOnClickListener(this);
        ivnotificationHome.setOnClickListener(this);
        //rvmsglist = findViewById(R.id.rvChatId);
      //  LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
       // rvmsglist.setLayoutManager(layoutManager);
      //  ChatAdapter chatAdapter = new ChatAdapter(ChatActivityMain.this, this);
      //  rvmsglist.setAdapter(chatAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlmessageuserprofileid:
                CheckNetwork.nextScreenWithoutFinish(ChatActivityMain.this, UserProfileActivity.class);
                break;
            case R.id.ivbackmsgId:
                onBackPressed();
                break;
            case R.id.ivnotificationHomeId:
                CheckNetwork.nextScreenWithoutFinish(ChatActivityMain.this, NotificationActivity.class);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        Glide.with(ChatActivityMain.this).load(uri);
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        messageAdapter.add(new Message(uriList), ChatActivityMain.this);
    }

    @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {
        Glide.with(ChatActivityMain.this).load(imageUri).into(ivImage);
    }

    @Override
    public void onRecordingStarted() {
        showToast("started");
        debug("started");
        time = System.currentTimeMillis() / (1000);
    }

    @Override
    public void onRecordingLocked() {
        showToast("locked");
        debug("locked");
    }

    @Override
    public void onRecordingCompleted() {
        showToast("completed");
        debug("completed");
        int recordTime = (int) ((System.currentTimeMillis() / (1000)) - time);
        if (recordTime > 1) {
            messageAdapter.add(new Message(recordTime), ChatActivityMain.this);
        }
    }

    @Override
    public void onRecordingCanceled() {
        showToast("canceled");
        debug("canceled");
    }

    @Override
    public void onClick(AttachmentOption attachmentOption) {
        switch (attachmentOption.getId()) {
            case AttachmentOption.GALLERY_ID:
                BSImagePicker pickerDialog = new BSImagePicker.Builder("com.asksira.imagepickersheetdemo")
                        .setMaximumDisplayingImages(Integer.MAX_VALUE)
                        .isMultiSelect()
                        .setMinimumMultiSelectCount(3)
                        .setMaximumMultiSelectCount(6)
                        .build();
                pickerDialog.show(getSupportFragmentManager(), "picker");
                break;

        }
    }
}
