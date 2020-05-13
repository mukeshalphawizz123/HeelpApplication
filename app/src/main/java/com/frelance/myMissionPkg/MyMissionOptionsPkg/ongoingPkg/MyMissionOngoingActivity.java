package com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.CustomToast;
import com.frelance.HelpActivity;
import com.frelance.R;
import com.frelance.clientProfilePkg.ClinetProfileActivity;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.homeTablayout.publishPkg.SelectImageAdapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg.Adapter.CompleteeFileUploadAdapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.Adapter.OngoingAdapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.SendProjectProgDetailModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.viewProgressModle.Datum;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.viewProgressModle.MissionInProgressModle;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.notificationPkg.NotificationCountResponseModle;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.frelance.utility.FileDownloading;
import com.frelance.utility.FileUtil;
import com.frelance.utility.ImagePicker;

import org.bouncycastle.util.io.TeeOutputStream;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class MyMissionOngoingActivity extends Fragment implements
        OngoingAdapter.OngoingAppOnClickListener,
        View.OnClickListener,
        SelectImageAdapter.SelectImageOnClickListener,
        BSImagePicker.OnMultiImageSelectedListener,
        BSImagePicker.ImageLoaderDelegate {

    private CompleteeFileUploadAdapter completeeFileUploadAdapter;
    private RecyclerView rvongoingfileupload;
    private ImageView ivmissionongoingdashboardback, ivnotification;
    private RelativeLayout rlmissongoingviewdetails;
    private TextView tvviewprofile, tvmymissionongoingtext;
    private ApiServices apiServices;
    private ProgressBar pbMymissionProgress;
    private RelativeLayout rlmymissionongoingSendbrtn, rlproblem, rlmyMissProgressFile, rlmyMissProgressImag;
    private File fileForImage, fileForDocs;
    private String filePath = null, profilImgPath, docPath, missionId, userId, mission_mission_title;
    private static final int SELECT_PICTURE = 101;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 100;
    private static final int FILE_SELECT_CODE = 0;
    private AppCompatEditText etMsgBoxInprogress;
    private static Animation shakeAnimation;
    private List<Datum> yourMissionList;
    private CircleImageView ivUserInprogMission;
    private RecyclerView rvselectimageId;
    private SelectImageAdapter selectImageAdapter;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private List<Uri> files = new ArrayList<>();
    private ArrayList<Uri> uriArrayList = new ArrayList<>();
    private ArrayList<String> filesList;
    private OngoingAdapter ongoingAdapter;
    private FileDownloading fileDownloading;
    private AppCompatTextView tvMyMissTitle, tvMyMissionAvailbale, tvHomeNotificationCount, tvFileNameOngoing;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mission_ongoing, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        fileDownloading = new FileDownloading(getActivity());
        missionId = this.getArguments().getString("missionId");
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        mission_mission_title = AppSession.getStringPreferences(getActivity(), "mission_mission_title");
        filesList = new ArrayList<>();

        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            //Toast.makeText(getActivity(),missionId, Toast.LENGTH_LONG).show();
            myMissionInProgress(missionId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

        if (CheckNetwork.isNetAvailable(getActivity())) {
            notification(userId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        tvMyMissTitle.setText(mission_mission_title);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void init(View view) {
        tvFileNameOngoing = view.findViewById(R.id.tvFileNameOngoingId);
        tvHomeNotificationCount = view.findViewById(R.id.tvHomeNotificationCountId);
        tvMyMissionAvailbale = view.findViewById(R.id.tvMyMissionAvailbaleId);
        tvMyMissTitle = view.findViewById(R.id.tvMyMissTitleId);
        rvselectimageId = view.findViewById(R.id.rvselectimageId);
        etMsgBoxInprogress = view.findViewById(R.id.etMsgBoxInprogressId);
        rlmyMissProgressImag = view.findViewById(R.id.rlmyMissProgressImagId);
        rlmyMissProgressFile = view.findViewById(R.id.rlmyMissProgressFileId);
        rlproblem = view.findViewById(R.id.rlproblemid);
        rlmymissionongoingSendbrtn = view.findViewById(R.id.rlmymissionongoingSendbrtnId);
        pbMymissionProgress = view.findViewById(R.id.pbMymissionProgressId);
        tvmymissionongoingtext = view.findViewById(R.id.tvmymissionongoingtextid);
        tvmymissionongoingtext.setOnClickListener(this);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);
        ivmissionongoingdashboardback = view.findViewById(R.id.ivmissionongoingdashboardbackId);
        ivmissionongoingdashboardback.setOnClickListener(this);
        rlmissongoingviewdetails = view.findViewById(R.id.rlmissongoingviewdetailsid);
        rlmissongoingviewdetails.setOnClickListener(this);
        rvongoingfileupload = view.findViewById(R.id.rvongoingfileuploadid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvongoingfileupload.setLayoutManager(layoutManager);
        ongoingAdapter = new OngoingAdapter(getActivity(), this);
        rvongoingfileupload.setAdapter(ongoingAdapter);
        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
        SpannableString content1 = new SpannableString(getResources().getString(R.string.report_a_problem));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvmymissionongoingtext.setText(content1);

        rlmymissionongoingSendbrtn.setOnClickListener(this);
        rlproblem.setOnClickListener(this);
        rlmyMissProgressImag.setOnClickListener(this);
        rlmyMissProgressFile.setOnClickListener(this);

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rlmyMissProgressImagId:
                // chooseFromGallery();
                //  FragmentManager fragmentManager=getFragmentManager()
                BSImagePicker pickerDialog = new BSImagePicker.Builder("com.asksira.imagepickersheetdemo")
                        .setMaximumDisplayingImages(Integer.MAX_VALUE)
                        .isMultiSelect()
                        .setMinimumMultiSelectCount(1)
                        .setMaximumMultiSelectCount(6)
                        .build();
                pickerDialog.show(getChildFragmentManager(), "picker");
                break;
            case R.id.rlmyMissProgressFileId:
                askStoragePermission();
                break;
            case R.id.rlproblemid:
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    //  sendProjectDispute("12");
                } else {
                    Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.rlmymissionongoingSendbrtnId:
                validation(v);
                break;

            case R.id.ivmissionongoingdashboardbackId:
                removeThisFragment();
                // backReplaceFragement(new MyMissionFragment());
                break;
            case R.id.rlmissongoingviewdetailsid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.tvmymissionongoingtextid:
                AppSession.setStringPreferences(getActivity(), "MyDemand_MyMission", "MyMission");
                AppSession.setStringPreferences(getActivity(), "dispute_mission_id", missionId);
                replaceFragement(new HelpActivity());
                break;
        }

    }

    public void removeThisFragment() {
        final FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();

        // Toast.makeText(this, ""+manager, Toast.LENGTH_SHORT).show();
    }

    private void replaceFragement(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("missionId", missionId);
        fragment.setArguments(bundle);
        AppSession.setStringPreferences(getActivity(), "OnGoing", "OnGoing");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void backReplaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
    }

    private void validation(View v) {
        if (etMsgBoxInprogress.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(getActivity(), v, "Can't Empty");
            etMsgBoxInprogress.startAnimation(shakeAnimation);
            etMsgBoxInprogress.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else {
            if (CheckNetwork.isNetAvailable(getActivity())) {
                sendProjectPorgress(missionId);
            } else {
                Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void sendProjectPorgress(String myMissionId) {
        pbMymissionProgress.setVisibility(View.VISIBLE);
        MultipartBody.Part imgFileStation = null;
        MultipartBody.Part imgFileStationDoc = null;
        MultipartBody.Part[] parts = new MultipartBody.Part[stringArrayList.size()];
        try {
            if (stringArrayList.size() == 0) {
            } else {
                for (int index = 0; index < stringArrayList.size(); index++) {
                    File file1 = new File(stringArrayList.get(index));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file1);
                    parts[index] = MultipartBody.Part.createFormData("project_image[]", file1.getPath(), requestBody);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        // List<Uri> files; //These are the uris for the files to be uploaded
        MediaType mediaType = MediaType.parse("*/*");//Based on the Postman logs,it's not specifying Content-Type, this is why I've made this empty content/mediaType
        MultipartBody.Part[] fileParts = new MultipartBody.Part[files.size()];
        for (int i = 0; i < files.size(); i++) {
            File file = new File(FileUtil.getPath(getActivity(), files.get(i)));
            RequestBody fileBody = RequestBody.create(mediaType, file);
            //Setting the file name as an empty string here causes the same issue, which is sending the request successfully without saving the files in the backend, so don't neglect the file name parameter.
            fileParts[i] = MultipartBody.Part.createFormData("project_files[]", file.getPath(), fileBody);
            // fileParts[i] = MultipartBody.Part.createFormData(String.format(Locale.ENGLISH, "files[%d]", i), file.getName(), fileBody);
        }

        MultipartBody.Part project_id_ = MultipartBody.Part.createFormData("project_id", String.valueOf(missionId));
        MultipartBody.Part user_id_ = MultipartBody.Part.createFormData("user_id", String.valueOf(userId));
        MultipartBody.Part your_comments_ = MultipartBody.Part.createFormData("your_comments", etMsgBoxInprogress.getText().toString());
        MultipartBody.Part project_status_ = MultipartBody.Part.createFormData("project_status", "1");

        apiServices.sendProjectPorgress(project_id_, user_id_, your_comments_, project_status_, fileParts, parts).enqueue(new Callback<SendProjectProgDetailModle>() {
            @Override
            public void onResponse(Call<SendProjectProgDetailModle> call, Response<SendProjectProgDetailModle> response) {
                if (response.isSuccessful()) {
                    pbMymissionProgress.setVisibility(View.GONE);
                    SendProjectProgDetailModle sendProjectProgDetailModle = response.body();
                    if (sendProjectProgDetailModle.getStatus()) {
                        Toast.makeText(getActivity(), sendProjectProgDetailModle.getMessage(), Toast.LENGTH_LONG).show();
                        //   yourMissionList = sendProjectProgDetailModle.getYourMissions();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbMymissionProgress.setVisibility(View.GONE);
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
            public void onFailure(Call<SendProjectProgDetailModle> call, Throwable t) {
                Log.v("going", t.toString());
                pbMymissionProgress.setVisibility(View.GONE);
            }
        });

    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showFileChooser();
                } else {
                    //Constants.customToast(getApplicationContext(), "Permission Denied");
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void askStoragePermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_READ_EXTERNAL_STORAGE);
            }
        } else {
            showFileChooser();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Copy"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
            Uri content_describer = data.getData();
            // Uri content_describer = data.getData();
            files.add(content_describer);


            String uriString = content_describer.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(content_describer, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        tvFileNameOngoing.setText(displayName);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                tvFileNameOngoing.setText(displayName);

            }


            BufferedReader reader = null;
            try {
                // open the user-picked file for reading:
                InputStream in = getActivity().getContentResolver().openInputStream(content_describer);
                // now read the content:
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                // Do something with the content in
                //  some_view.setText(builder.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {

            try {
                Uri imageUri = data.getData();
                Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
                profilImgPath = getRealPathFromURI(getActivity(), imageUri);
                Log.v("iamges", profilImgPath.toString());

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
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


    private void myMissionInProgress(String myMissionId) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.projectPorgress(myMissionId).enqueue(new Callback<MissionInProgressModle>() {
            @Override
            public void onResponse(Call<MissionInProgressModle> call, Response<MissionInProgressModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    MissionInProgressModle myMissionProposedModle = response.body();
                    if (myMissionProposedModle.getStatus() == true) {
                        yourMissionList = myMissionProposedModle.getData();
                        ongoingAdapter.addOnGoingFiles(yourMissionList);
                        if (yourMissionList.size() > 1) {
                            tvMyMissionAvailbale.setText(yourMissionList.size() + "Availables");
                        } else {
                            tvMyMissionAvailbale.setText(yourMissionList.size() + "Available");
                        }
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
            public void onFailure(Call<MissionInProgressModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    private void setupImagePath() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvselectimageId.setLayoutManager(layoutManager);
        selectImageAdapter = new SelectImageAdapter(getActivity(), this);
        rvselectimageId.setAdapter(selectImageAdapter);
        selectImageAdapter.scheduleappoinList(uriArrayList);
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.ivdeletCloseId:
                stringArrayList.clear();
                uriArrayList.remove(position);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                rvselectimageId.setLayoutManager(layoutManager);
                selectImageAdapter = new SelectImageAdapter(getActivity(), this);
                rvselectimageId.setAdapter(selectImageAdapter);
                selectImageAdapter.scheduleappoinList(uriArrayList);

                for (int i = 0; i < uriArrayList.size(); i++) {
                    profilImgPath = getRealPathFromURI(getActivity(), uriArrayList.get(i));
                    stringArrayList.add(profilImgPath);
                }

                break;
        }
    }

    @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {
        Glide.with(getActivity()).load(imageUri).into(ivImage);
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        uriArrayList.addAll(uriList);
        setupImagePath();
        for (int i = 0; i < uriList.size(); i++) {
            profilImgPath = getRealPathFromURI(getActivity(), uriList.get(i));
            stringArrayList.add(profilImgPath);
        }
    }

    @Override
    public void myMissOnGoingOnClick(View view, int position, Datum datum) {
        switch (view.getId()) {
            case R.id.rldummyimgid:
                AppSession.setStringPreferences(getActivity(), "chatEntry", "other");
                AppSession.setStringPreferences(getActivity(), "clientId", datum.getUserId());
                CheckNetwork.nextScreenWithoutFinish(getActivity(), ClinetProfileActivity.class);
                break;
            //  fileDownloading.DownloadImage(RetrofitClient.DOWNLOAD_URL + filesList.get(position));

        }
    }


    private void notification(String userId) {
        //    CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.getnotificationcount(userId).enqueue(new Callback<NotificationCountResponseModle>() {
            @Override
            public void onResponse(Call<NotificationCountResponseModle> call, Response<NotificationCountResponseModle> response) {
                if (response.isSuccessful()) {
                    // CustomProgressbar.hideProgressBar();
                    NotificationCountResponseModle notificationResponseModle = response.body();
                    if (notificationResponseModle.getStatus()) {
                        int messageCount = notificationResponseModle.getCountMessages();
                        int messageDemands = notificationResponseModle.getCountMissionanddemands();
                        int messageOffers = notificationResponseModle.getCountOffers();
                        int messageCountPayment = notificationResponseModle.getCountPayment();
                        int messageCountReveiews = notificationResponseModle.getCountReviews();

                        String totalNotification = String.valueOf(messageCount
                                + messageOffers
                                + messageDemands
                                + messageCountPayment
                                + messageCountReveiews);

                        if (totalNotification == null || totalNotification.isEmpty()) {
                            tvHomeNotificationCount.setVisibility(View.GONE);
                        } else {
                            tvHomeNotificationCount.setText(totalNotification);
                            tvHomeNotificationCount.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvHomeNotificationCount.setVisibility(View.GONE);
                    }
                } else {
                    if (response.code() == 400) {
                        if (!false) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationCountResponseModle> call, Throwable t) {
                // CustomProgressbar.hideProgressBar();
                tvHomeNotificationCount.setVisibility(View.GONE);
            }
        });

    }
}
