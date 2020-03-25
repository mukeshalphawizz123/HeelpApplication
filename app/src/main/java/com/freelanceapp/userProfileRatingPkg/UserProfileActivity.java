package com.freelanceapp.userProfileRatingPkg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.chatPkg.Adapter.ChatActivityMain;

import com.freelanceapp.utility.CheckNetwork;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlcircleprrogressbar, rldiscuss, rlmessageuserprofile;
    private ImageView ivbackproffilemsg, ivnotificationuserprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
    }
/*    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);
        init(view);
        return view;*/


    private void init() {
        ivnotificationuserprofile = findViewById(R.id.ivnotificationuserprofileId);
        ivnotificationuserprofile.setOnClickListener(this);

        ivbackproffilemsg = findViewById(R.id.ivbackproffilemsgId);
        ivbackproffilemsg.setOnClickListener(this);

        rlcircleprrogressbar = findViewById(R.id.rlcircleprrogressbarid);
        rldiscuss = findViewById(R.id.rldiscussid);
        rldiscuss.setOnClickListener(this);
        rlcircleprrogressbar.setOnClickListener(this);

        CircularProgressIndicator donutprogress = findViewById(R.id.donutprogressid);

// you can set max and current progress values individually
        donutprogress.setMaxProgress(5);
        donutprogress.setCurrentProgress(4.3);
// or all at once
        donutprogress.setProgress(4.3, 5);

// you can get progress values using following getters
        donutprogress.getProgress(); // returns 4
        donutprogress.getMaxProgress();// returns 5
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlcircleprrogressbarid:
                Intent intent = new Intent(getApplicationContext(), ProfileRatingDescriptionActivity.class);
                startActivity(intent);
                break;
            case R.id.rldiscussid:
                CheckNetwork.nextScreenWithoutFinish(UserProfileActivity.this, ChatActivityMain.class);
                break;
            case R.id.ivbackproffilemsgId:
                CheckNetwork.backScreenWithouFinish(UserProfileActivity.this);
                break;
            case R.id.ivnotificationuserprofileId:
                CheckNetwork.nextScreenWithoutFinish(UserProfileActivity.this, NotificationActivity.class);
                break;

        }
    }


}
