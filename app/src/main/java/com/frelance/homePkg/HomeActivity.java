package com.frelance.homePkg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.frelance.CustomProgressbar;
import com.frelance.FirebaseUnreadUserCount;
import com.frelance.chatPkg.chatModlePkg.UnReadMessageUserModle;
import com.frelance.chatPkg.chatModlePkg.UnReadMsgConsersation;
import com.frelance.homeTablayout.HomeTablayoutFragment;
import com.frelance.R;
import com.frelance.InboxListPkg.MessageTablayout.MessageListTablayoutFragment;
import com.frelance.myMissionPkg.FragmentPkg.MyMissionFragment;
import com.frelance.myDemandsPkg.FragmentPkg.MyDemandFragment;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.plusMorePkg.PlusMoreFragment;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static FragmentManager fragmentManager;
    private RelativeLayout rlHome, rlHomemymission, rlHomemyrequest, rlHomechat, rlHomemenu;
    private ImageView ivHomeDd, ivmymissionHome, ivMyrequestHome, ivchatHome, ivhomemenu;
    private AppCompatTextView tvaccounthome, tvmymissionhome, tvmyrequesthome, tvchathome, tvhomeplus, tvHomeNotificationCount;
    private String userId;
    private UnReadMsgConsersation consersation;
    private ArrayList<UnReadMessageUserModle> datumList;
    private FirebaseUnreadUserCount firebaseUnreadUserCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userId = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        datumList = new ArrayList<>();
        consersation = new UnReadMsgConsersation();
        String data = getIntent().getStringExtra("data");
        fragmentManager = getSupportFragmentManager();
        firebaseUnreadUserCount = new FirebaseUnreadUserCount(getApplicationContext());
        init();
        try {
            if (data != null) {
                if (data.equalsIgnoreCase("Chat")) {
                    chat();
                    addFragment(new MessageListTablayoutFragment(), false, Constants.HOME_TABLAYOUT_FRAGMENT);
                } else {
                    addFragment(new HomeTablayoutFragment(), false, Constants.HOME_TABLAYOUT_FRAGMENT);
                    CheckNetwork.nextScreenWithoutFinish(HomeActivity.this, NotificationActivity.class);
                }
            } else {
                addFragment(new HomeTablayoutFragment(), false, Constants.HOME_TABLAYOUT_FRAGMENT);
            }
        } catch (Exception e) {
           // addFragment(new HomeTablayoutFragment(), false, Constants.HOME_TABLAYOUT_FRAGMENT);
        }

       /* if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            chatDataSanpchat();
        } else {

        }*/
        String count = firebaseUnreadUserCount.chatDataSanpchat();
        if (count == null || count.isEmpty()) {
            tvHomeNotificationCount.setVisibility(View.GONE);
        } else {
            tvHomeNotificationCount.setVisibility(View.VISIBLE);
            tvHomeNotificationCount.setText(count);
        }
        ///
    }

    private void init() {
        tvHomeNotificationCount = findViewById(R.id.tvHomeNotificationCountId);
        rlHome = findViewById(R.id.rlHomeId);
        rlHomemymission = findViewById(R.id.rlHomemymissionId);
        rlHomemyrequest = findViewById(R.id.rlHomemyrequestId);
        rlHomechat = findViewById(R.id.rlHomechatId);
        rlHomemenu = findViewById(R.id.rlHomemenuId);
        ivHomeDd = findViewById(R.id.ivHomeDdId);
        tvaccounthome = findViewById(R.id.tvaccounthomeid);
        ivmymissionHome = findViewById(R.id.ivmymissionHomeId);
        tvmymissionhome = findViewById(R.id.tvmymissionhomeid);
        ivMyrequestHome = findViewById(R.id.ivMyrequestHomeId);
        tvmyrequesthome = findViewById(R.id.tvmyrequesthomeid);
        ivchatHome = findViewById(R.id.ivchatHomeId);
        tvchathome = findViewById(R.id.tvchathomeid);
        ivhomemenu = findViewById(R.id.ivhomemenuId);
        tvhomeplus = findViewById(R.id.tvhomeplusid);
        rlHome.setOnClickListener(this);
        rlHomemymission.setOnClickListener(this);
        rlHomemyrequest.setOnClickListener(this);
        rlHomechat.setOnClickListener(this);
        rlHomemenu.setOnClickListener(this);
    }


    private void replaceFragement(Fragment fragment) {
        FragmentTransaction home = getSupportFragmentManager().beginTransaction();
        // home.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        home.replace(R.id.flHomeId, fragment);
        home.commit();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.flHomeId, fragment, tag);
        ft.commitAllowingStateLoss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlHomeId:
                account();
                addFragment(new HomeTablayoutFragment(), false, Constants.HOME_TABLAYOUT_FRAGMENT);
                //replaceFragement(new HomeTablayoutFragment());
                break;
            case R.id.rlHomemymissionId:
                mymission();
                removeThisFragment();
                addFragment(new MyMissionFragment(), false, Constants.MY_MISSION_FRAGMENT);
                // replaceFragement(new MyMissionFragment());
                break;
            case R.id.rlHomemyrequestId:
                myrequest();
                removeThisFragment();
                addFragment(new MyDemandFragment(), false, Constants.MY_REQUEST_FRAGMENT);
                break;
            case R.id.rlHomechatId:
                chat();
                removeThisFragment();
                addFragment(new MessageListTablayoutFragment(), false, Constants.MESSAGE_LIST_TAB_LAYOUT_FRAGMENT);
                // replaceFragement(new MessageListTablayoutFragment());
                break;
            case R.id.rlHomemenuId:
                plus();
                removeThisFragment();
                addFragment(new PlusMoreFragment(), false, Constants.PLUS_MORE_FRAGMENT);//replaceFragement(new PlusMoreFragment());
                break;
        }
    }

    private void account() {
        ivHomeDd.setImageResource(R.drawable.blackhome);
        ivmymissionHome.setImageResource(R.drawable.linechart);
        ivMyrequestHome.setImageResource(R.drawable.myrequest);
        ivchatHome.setImageResource(R.drawable.chat);
        ivhomemenu.setImageResource(R.drawable.menu);
        tvaccounthome.setTextColor(getResources().getColor(R.color.Black));
        tvmymissionhome.setTextColor(getResources().getColor(R.color.gray));
        tvmyrequesthome.setTextColor(getResources().getColor(R.color.gray));
        tvchathome.setTextColor(getResources().getColor(R.color.gray));
        tvhomeplus.setTextColor(getResources().getColor(R.color.gray));


    }

    private void mymission() {
        ivHomeDd.setImageResource(R.drawable.grayhome);
        ivmymissionHome.setImageResource(R.drawable.linechartblack);
        ivMyrequestHome.setImageResource(R.drawable.myrequest);
        ivchatHome.setImageResource(R.drawable.chat);
        ivhomemenu.setImageResource(R.drawable.menu);
        tvaccounthome.setTextColor(getResources().getColor(R.color.gray));
        tvmymissionhome.setTextColor(getResources().getColor(R.color.Black));
        tvmyrequesthome.setTextColor(getResources().getColor(R.color.gray));
        tvchathome.setTextColor(getResources().getColor(R.color.gray));
        tvhomeplus.setTextColor(getResources().getColor(R.color.gray));


    }

    private void myrequest() {
        ivHomeDd.setImageResource(R.drawable.grayhome);
        ivmymissionHome.setImageResource(R.drawable.linechart);
        ivMyrequestHome.setImageResource(R.drawable.requestblack);
        ivchatHome.setImageResource(R.drawable.chat);
        ivhomemenu.setImageResource(R.drawable.menu);
        tvaccounthome.setTextColor(getResources().getColor(R.color.gray));
        tvmymissionhome.setTextColor(getResources().getColor(R.color.gray));
        tvmyrequesthome.setTextColor(getResources().getColor(R.color.Black));
        tvchathome.setTextColor(getResources().getColor(R.color.gray));
        tvhomeplus.setTextColor(getResources().getColor(R.color.gray));


    }

    private void chat() {
        ivHomeDd.setImageResource(R.drawable.grayhome);
        ivmymissionHome.setImageResource(R.drawable.linechart);
        ivMyrequestHome.setImageResource(R.drawable.myrequest);
        ivchatHome.setImageResource(R.drawable.chatblack);
        ivhomemenu.setImageResource(R.drawable.menu);
        tvaccounthome.setTextColor(getResources().getColor(R.color.gray));
        tvmymissionhome.setTextColor(getResources().getColor(R.color.gray));
        tvmyrequesthome.setTextColor(getResources().getColor(R.color.gray));
        tvchathome.setTextColor(getResources().getColor(R.color.Black));
        tvhomeplus.setTextColor(getResources().getColor(R.color.gray));


    }

    private void plus() {
        ivHomeDd.setImageResource(R.drawable.grayhome);
        ivmymissionHome.setImageResource(R.drawable.linechart);
        ivMyrequestHome.setImageResource(R.drawable.myrequest);
        ivchatHome.setImageResource(R.drawable.chat);
        ivhomemenu.setImageResource(R.drawable.menublack);
        tvaccounthome.setTextColor(getResources().getColor(R.color.gray));
        tvmymissionhome.setTextColor(getResources().getColor(R.color.gray));
        tvmyrequesthome.setTextColor(getResources().getColor(R.color.gray));
        tvchathome.setTextColor(getResources().getColor(R.color.gray));
        tvhomeplus.setTextColor(getResources().getColor(R.color.Black));

    }

    @Override
    public void onBackPressed() {
        Fragment HOME_TABLAYOUT_FRAGMENT = fragmentManager.findFragmentByTag(Constants.HOME_TABLAYOUT_FRAGMENT);
        Fragment MY_MISSION_FRAGMENT = fragmentManager.findFragmentByTag(Constants.MY_MISSION_FRAGMENT);
        Fragment MY_REQUEST_FRAGMENT = fragmentManager.findFragmentByTag(Constants.MY_REQUEST_FRAGMENT);
        Fragment MESSAGE_LIST_TAB_LAYOUT_FRAGMENT = fragmentManager.findFragmentByTag(Constants.MESSAGE_LIST_TAB_LAYOUT_FRAGMENT);
        Fragment PLUS_MORE_FRAGMENT = fragmentManager.findFragmentByTag(Constants.PLUS_MORE_FRAGMENT);
        Fragment MY_MISSION_PROPOSEE_ACTIVITY = fragmentManager.findFragmentByTag(Constants.MY_MISSION_PROPOSEE_ACTIVITY);
        Fragment MY_MISSION_ONGOING_ACTIVITY = fragmentManager.findFragmentByTag(Constants.MY_MISSION_ONGOING_ACTIVITY);
        Fragment MY_MISSION_LIVERY_ACTIVITY = fragmentManager.findFragmentByTag(Constants.MY_MISSION_LIVERY_ACTIVITY);
        Fragment MY_MISSION_COMPLETE_ACTIVITY = fragmentManager.findFragmentByTag(Constants.MY_MISSION_COMPLETE_ACTIVITY);
        Fragment MY_MISSION_DISPUTE_ACTIVITY = fragmentManager.findFragmentByTag(Constants.MY_MISSION_DISPUTE_ACTIVITY);
        Fragment MY_REQUEST_PUBLISHED_TABLAYOUT_FRAGMENT = fragmentManager.findFragmentByTag(Constants.MY_REQUEST_PUBLISHED_TABLAYOUT_FRAGMENT);
        Fragment MY_REQUEST_ONGOING_FRAGMENT = fragmentManager.findFragmentByTag(Constants.MY_REQUEST_ONGOING_FRAGMENT);
        Fragment MY_REQUEST_LIVERY_FRAGMENT = fragmentManager.findFragmentByTag(Constants.MY_REQUEST_LIVERY_FRAGMENT);
        Fragment MY_REQUEST_COMPLETE_FRAGMENT = fragmentManager.findFragmentByTag(Constants.MY_REQUEST_COMPLETE_FRAGMENT);
        Fragment MY_REQUEST_OPENLITIGATION_FRAGMENT = fragmentManager.findFragmentByTag(Constants.MY_REQUEST_OPENLITIGATION_FRAGMENT);
        Fragment DESHBOARD_SPONSOR_SHIP = fragmentManager.findFragmentByTag(Constants.DESHBOARD_SPONSOR_SHIP);
        Fragment DASHBOARD_PAYMENT_ACTIVITY = fragmentManager.findFragmentByTag(Constants.DASHBOARD_PAYMENT_ACTIVITY);
        Fragment DASHBOARD_PARAMETERS_ACTIVITY = fragmentManager.findFragmentByTag(Constants.DASHBOARD_PARAMETERS_ACTIVITY);
        Fragment DASHBOARD_SUPPORT_ACTIVITY = fragmentManager.findFragmentByTag(Constants.DASHBOARD_SUPPORT_ACTIVITY);
        Fragment DASHBOARD_HELP_ACTIVITY = fragmentManager.findFragmentByTag(Constants.DASHBOARD_HELP_ACTIVITY);

        if (HOME_TABLAYOUT_FRAGMENT != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit the activity?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else if (MY_MISSION_FRAGMENT != null) {
            if (MY_MISSION_PROPOSEE_ACTIVITY != null) {
                super.onBackPressed();
            } else if (MY_MISSION_ONGOING_ACTIVITY != null) {
                super.onBackPressed();
            } else if (MY_MISSION_LIVERY_ACTIVITY != null) {
                super.onBackPressed();
            } else if (MY_MISSION_COMPLETE_ACTIVITY != null) {
                super.onBackPressed();
            } else if (MY_MISSION_DISPUTE_ACTIVITY != null) {
                super.onBackPressed();
            } else {
                account();
                addFragment(new HomeTablayoutFragment(), false, Constants.HOME_TABLAYOUT_FRAGMENT);
            }
        } else if (MY_REQUEST_FRAGMENT != null) {
            if (MY_REQUEST_PUBLISHED_TABLAYOUT_FRAGMENT != null) {
                super.onBackPressed();
            } else if (MY_REQUEST_ONGOING_FRAGMENT != null) {
                super.onBackPressed();
            } else if (MY_REQUEST_LIVERY_FRAGMENT != null) {
                super.onBackPressed();
            } else if (MY_REQUEST_COMPLETE_FRAGMENT != null) {
                super.onBackPressed();
            } else if (MY_REQUEST_OPENLITIGATION_FRAGMENT != null) {
                super.onBackPressed();
            } else {
                account();
                addFragment(new HomeTablayoutFragment(), false, Constants.HOME_TABLAYOUT_FRAGMENT);
            }
        } else if (MESSAGE_LIST_TAB_LAYOUT_FRAGMENT != null) {
            account();
            addFragment(new HomeTablayoutFragment(), false, Constants.HOME_TABLAYOUT_FRAGMENT);
        } else if (PLUS_MORE_FRAGMENT != null) {
            // super.onBackPressed();
            if (DESHBOARD_SPONSOR_SHIP != null) {
                super.onBackPressed();
            } else if (DASHBOARD_PAYMENT_ACTIVITY != null) {
                super.onBackPressed();
            } else if (DASHBOARD_PARAMETERS_ACTIVITY != null) {
                super.onBackPressed();
            } else if (DASHBOARD_SUPPORT_ACTIVITY != null) {
                super.onBackPressed();
            } else if (DASHBOARD_HELP_ACTIVITY != null) {
                super.onBackPressed();
            } else {
                account();
                addFragment(new HomeTablayoutFragment(), false, Constants.HOME_TABLAYOUT_FRAGMENT);
            }
        } else
            super.onBackPressed();
    }


    public void removeThisFragment() {
        final FragmentManager manager = getSupportFragmentManager();
        manager.popBackStackImmediate();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String data = intent.getStringExtra("data");
            if (data != null) {
                Fragment fragment = new MessageListTablayoutFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flHomeId, fragment).addToBackStack(null).commit();
            }
        }
    }


    private void chatDataSanpchat() {
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

        String count = AppSession.getStringPreferences(getApplicationContext(), "count");
        if (count == null || count.isEmpty()) {
            tvHomeNotificationCount.setVisibility(View.GONE);
        } else {
            tvHomeNotificationCount.setVisibility(View.VISIBLE);
            tvHomeNotificationCount.setText(count);
        }
        //tvHomeNotificationCount.setText();
        //Toast.makeText(getActivity(), AppSession.getStringPreferences(getActivity(), "count"), Toast.LENGTH_LONG).show();
        // AppSession.getStringPreferences(getActivity(),"count");
        // Log.println(Log.VERBOSE, "count", AppSession.getStringPreferences(getActivity(), "count"));

       /* DatabaseReference fbDb = null;
        if (fbDb == null) {
            fbDb = FirebaseDatabase.getInstance().getReference();
        }*/
/*
        fbDb.child("ques/Technology")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                        int size = (int) dataSnapshot.getChildrenCount();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/


    }
}
