package com.frelance.chatPkg;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.R;
import com.frelance.chatPkg.Adapter.ChatAdapter;
import com.frelance.chatPkg.chatModlePkg.ChatModle;
import com.frelance.chatPkg.chatModlePkg.Consersation;
import com.frelance.chatPkg.chatModlePkg.UnReadMessageUserModle;
import com.frelance.chatPkg.chatModlePkg.chatResponseModlePkg.ChatImageResponseModle;
import com.frelance.chatPkg.chatModlePkg.voiceRecordingModle.RecordingResponseModle;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.clientProfilePkg.ClinetProfileActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg.GetProfileModle;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.frelance.utility.ImagePicker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ChatActivity extends AppCompatActivity implements
        ChatAdapter.ChatAppOnClickListener
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
    private AppCompatTextView tvUserNameMsg, tvUserProffesion;
    private CircleImageView ivUserMsg;
    private AppCompatImageView ivsharefilebutton, ivrecordedbutton;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 100;
    private static final int SELECT_PICTURE = 101;
    private String profilImgPath;
    private File fileForImage;

    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer;
    private RelativeLayout rlVoiceRecordingStop;
    private Chronometer tvTimer;
    private GetProfileModle missionlist;

    private String entryFlag = "0", firstname, lastName, user_picturUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userid = AppSession.getStringPreferences(getApplicationContext(), Constants.USERID);
        firstname = AppSession.getStringPreferences(getApplicationContext(), Constants.FIRST_NAME);
        user_picturUrl = AppSession.getStringPreferences(getApplicationContext(), Constants.PICTURE_URL);

        clientId = getIntent().getStringExtra("client_id");
        fName = getIntent().getStringExtra("firstName");
        lName = getIntent().getStringExtra("lastName");
        clientImg = getIntent().getStringExtra("clientImg");
        consersation = new Consersation();
        random = new Random();
        init();
        if (CheckNetwork.isNetAvailable(getApplicationContext())) {
            getProfileApi(clientId);
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        tvTimer = findViewById(R.id.tvTimerId);
        rlVoiceRecordingStop = findViewById(R.id.rlVoiceRecordingStopId);
        ivrecordedbutton = findViewById(R.id.ivrecordedbuttonid);
        ivsharefilebutton = findViewById(R.id.ivsharefilebuttonid);
        ivUserMsg = findViewById(R.id.ivUserMsgId);
        tvUserProffesion = findViewById(R.id.tvUserProffesionId);
        tvUserNameMsg = findViewById(R.id.tvUserNameMsgId);
        ettypemsg = findViewById(R.id.ettypemsgid);
        ivnotificationHome = findViewById(R.id.ivnotificationHomeId);
        ivnotificationHome.setOnClickListener(this);
        ivbackmsg = findViewById(R.id.ivbackmsgId);
        ivgifbutton = findViewById(R.id.ivgifbuttonid);
        ivbackmsg.setOnClickListener(this);
        ivgifbutton.setOnClickListener(this);
        rlVoiceRecordingStop.setOnClickListener(this);
        ivrecordedbutton.setOnClickListener(this);
        ivsharefilebutton.setOnClickListener(this);
        rlmessageuserprofile = findViewById(R.id.rlmessageuserprofileid);
        rlmessageuserprofile.setOnClickListener(this);
        rvmsglist = findViewById(R.id.rvChatId);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvmsglist.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(getApplicationContext(), this);
        chatDataSanpchat();
    }

    private void chatDataSanpchat() {
        String userRecordinsertFormat = "user_" + userid + "_" + "client_" + clientId;
        FirebaseDatabase.getInstance().getReference().child("message/" + userRecordinsertFormat).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    //CustomProgressbar.hideProgressBar();
//                        postnotification("Alert", "You received message");
                    HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                    ChatModle chatModle = new ChatModle((String) mapMessage.get("userId"),
                            (String) mapMessage.get("client"),
                            (String) mapMessage.get("dateTime"),
                            (String) mapMessage.get("message"),
                            (String) mapMessage.get("userImgPath"),
                            (String) mapMessage.get("userVoice"));

                    consersation.getListMessageData().add(chatModle);
                    chatAdapter.addChatList(consersation.getListMessageData());
                    chatAdapter.notifyDataSetChanged();
                    layoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);

                    if (entryFlag.equalsIgnoreCase("0")) {
                        entryFlag = "1";
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
        //  CustomProgressbar.hideProgressBar();
        rvmsglist.setAdapter(chatAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlVoiceRecordingStopId:
                mediaRecorder.stop();
                tvTimer.stop();
                rlVoiceRecordingStop.setVisibility(View.GONE);
                ivrecordedbutton.setVisibility(View.VISIBLE);
                uploadSoundForChat(AudioSavePathInDevice);
                break;
            case R.id.ivrecordedbuttonid:
                if (checkPermission()) {
                    AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CreateRandomAudioFileName(5) + "AudioRecording.3gp";
                    MediaRecorderReady();
                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    tvTimer.setVisibility(View.VISIBLE);
                    rlVoiceRecordingStop.setVisibility(View.VISIBLE);
                    ivrecordedbutton.setVisibility(View.GONE);
                    tvTimer.start();
                    Toast.makeText(ChatActivity.this, getResources().getString(R.string.RecordingStarted), Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }
                break;
            case R.id.ivsharefilebuttonid:
                askStoragePermission();
                break;
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
                    String userRecordinsertFormat = "user_" + userid + "_" + "client_" + clientId;
                    String clientRecordinsertFormat = "user_" + clientId + "_" + "client_" + userid;
                    ChatModle newMessage = new ChatModle(userid, clientId, Constants.currentDateAndTime(), message, "", "");
                    FirebaseDatabase.getInstance().getReference().child("message/" + userRecordinsertFormat).push().setValue(newMessage);
                    FirebaseDatabase.getInstance().getReference().child("message/" + clientRecordinsertFormat).push().setValue(newMessage);

                    UnReadMessageUserModle unReadMessageUserModle = new UnReadMessageUserModle(clientId,
                            firstname ,
                            user_picturUrl,
                            Constants.currentDateAndTime(),
                            userid);

                    if (entryFlag.equalsIgnoreCase("1")) {
                        entryFlag = "2";
                        // FirebaseDatabase.getInstance().getReference().child("userList/" + "user_" + userid + "_").push().setValue(unReadMessageUserModle);
                        FirebaseDatabase.getInstance().getReference().child("userList/" + "user_" + clientId + "_").push().setValue(unReadMessageUserModle);
                    }

                    break;

                }
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(ChatActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    private void getProfileApi(String user_id) {
        CustomProgressbar.showProgressBar(this, false);
        apiServices.getMyProfile(user_id).enqueue(new Callback<GetProfileModle>() {
            @Override
            public void onResponse(Call<GetProfileModle> call, Response<GetProfileModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    missionlist = response.body();
                    if (missionlist.getStatus() == true) {
                        tvUserNameMsg.setText(missionlist.getYourMissions().get(0).getUsername());
                        tvUserProffesion.setText(missionlist.getYourMissions().get(0).getSkills());
                        if (missionlist.getYourMissions().get(0).getPictureUrl().isEmpty()) {

                        } else {
                            Picasso.with(getApplicationContext())
                                    .load(RetrofitClient.MISSION_USER_IMAGE_URL + missionlist.getYourMissions()
                                            .get(0).getPictureUrl())
                                    .into(ivUserMsg);
                        }

                    }

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<GetProfileModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    private void uploadImagForChat(String profilImgPath) {
        CustomProgressbar.showProgressBar(this, false);
        MultipartBody.Part imgFileStation = null;
        if (profilImgPath == null) {
        } else {
            fileForImage = new File(profilImgPath);
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("multipart/form-data"), fileForImage);
            imgFileStation = MultipartBody.Part.createFormData("picture_url", fileForImage.getName(), requestFileOne);
        }
        apiServices.addchatimage(imgFileStation).enqueue(new Callback<ChatImageResponseModle>() {
            @Override
            public void onResponse(Call<ChatImageResponseModle> call, Response<ChatImageResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    ChatImageResponseModle chatImageResponseModle = response.body();
                    if (chatImageResponseModle.getStatus() == true) {
                        String imgurl = chatImageResponseModle.getData().get(0).getImageName();
                        String userRecordinsertFormat = "user_" + userid + "_" + "client_" + clientId;
                        String clientRecordinsertFormat = "user_" + clientId + "_" + "client_" + userid;
                        ChatModle newMessage = new ChatModle(userid, clientId, Constants.currentDateAndTime(), "", imgurl, "");
                        FirebaseDatabase.getInstance().getReference().child("message/" + userRecordinsertFormat).push().setValue(newMessage);
                        FirebaseDatabase.getInstance().getReference().child("message/" + clientRecordinsertFormat).push().setValue(newMessage);
                    }

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ChatImageResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    private void uploadSoundForChat(String profilImgPath) {
        CustomProgressbar.showProgressBar(this, false);
        MultipartBody.Part imgFileStation = null;
        if (profilImgPath == null) {

        } else {
            fileForImage = new File(profilImgPath);
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("multipart/form-data"), fileForImage);
            imgFileStation = MultipartBody.Part.createFormData("voice_url", fileForImage.getName(), requestFileOne);
        }
        apiServices.voice_url(imgFileStation).enqueue(new Callback<RecordingResponseModle>() {
            @Override
            public void onResponse(Call<RecordingResponseModle> call, Response<RecordingResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    RecordingResponseModle recordingResponseModle = response.body();
                    if (recordingResponseModle.getStatus() == true) {
                        String voiceUrl = recordingResponseModle.getData().get(0).getVoicefileName();
                        String userRecordinsertFormat = "user_" + userid + "_" + "client_" + clientId;
                        String clientRecordinsertFormat = "user_" + clientId + "_" + "client_" + userid;
                        ChatModle newMessage = new ChatModle(userid, clientId, Constants.currentDateAndTime(), "", "", voiceUrl);
                        FirebaseDatabase.getInstance().getReference().child("message/" + userRecordinsertFormat).push().setValue(newMessage);
                        FirebaseDatabase.getInstance().getReference().child("message/" + clientRecordinsertFormat).push().setValue(newMessage);
                    }

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<RecordingResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }


    private void askStoragePermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_READ_EXTERNAL_STORAGE);
            }
        } else {
            chooseFromGallery();
        }
    }


    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseFromGallery();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;

            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(ChatActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChatActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

    }

    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0;
        while (i < string) {
            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));
            i++;
        }
        return stringBuilder.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = data.getData();
                    // Bitmap bitmap = ImagePicker.getImageFromResult(getApplicationContext(), resultCode, data);
                    profilImgPath = getRealPathFromURI(getApplicationContext(), imageUri);
                    if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                        uploadImagForChat(profilImgPath);
                    } else {
                        Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
