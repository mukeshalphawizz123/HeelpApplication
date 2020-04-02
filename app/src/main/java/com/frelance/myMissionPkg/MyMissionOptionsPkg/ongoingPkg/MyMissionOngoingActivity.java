package com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomToast;
import com.frelance.HelpActivity;
import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.homeTablayout.publishPkg.SelectImageAdapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg.Adapter.CompleteeFileUploadAdapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.Adapter.OngoingAdapter;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.SendProjectProgDetailModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.viewProgressModle.Datum;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.viewProgressModle.MissionInProgressModle;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.frelance.utility.ImagePicker;
import com.squareup.picasso.Picasso;

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

public class MyMissionOngoingActivity extends Fragment implements OngoingAdapter.OngoingAppOnClickListener, View.OnClickListener, SelectImageAdapter.SelectImageOnClickListener {

    private CompleteeFileUploadAdapter completeeFileUploadAdapter;
    private RecyclerView rvongoingfileupload;
    private ImageView ivmissionongoingdashboardback, ivnotification;
    private RelativeLayout rlmissongoingviewdetails;
    private TextView tvviewprofile, tvmymissionongoingtext;
    private ApiServices apiServices;
    private ProgressBar pbMymissionProgress;
    private RelativeLayout rlmymissionongoingSendbrtn, rlproblem, rlmyMissProgressFile, rlmyMissProgressImag;
    private File fileForImage, fileForDocs;
    private String filePath = null, profilImgPath, docPath, missionId, userId;
    private static final int SELECT_PICTURE = 101;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 100;
    private static final int FILE_SELECT_CODE = 0;
    private AppCompatEditText etMsgBoxInprogress;
    private static Animation shakeAnimation;
    private List<Datum> yourMissionList;
    private AppCompatTextView tvUserNameInProgMission, tvCommentInProgMission;
    private CircleImageView ivUserInprogMission;

    private RecyclerView rvselectimageId;
    private SelectImageAdapter selectImageAdapter;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private List<Uri> files = new ArrayList<>();
    private ArrayList<Uri> uriArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_mission_ongoing, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        missionId = this.getArguments().getString("missionId");
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            myMissionInProgress(missionId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void init(View view) {
        rvselectimageId = view.findViewById(R.id.rvselectimageId);
        ivUserInprogMission = view.findViewById(R.id.ivUserInprogMissionId);
        tvUserNameInProgMission = view.findViewById(R.id.tvUserNameInProgMissionId);
        tvCommentInProgMission = view.findViewById(R.id.tvCommentInProgMissionId);
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
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rvongoingfileupload.setLayoutManager(layoutManager);
        OngoingAdapter ongoingAdapter = new OngoingAdapter(getActivity(), this);
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
                chooseFromGallery();
              /*  BSImagePicker pickerDialog = new BSImagePicker.Builder("com.asksira.imagepickersheetdemo")
                        .setMaximumDisplayingImages(Integer.MAX_VALUE)
                        .isMultiSelect()
                        .setMinimumMultiSelectCount(1)
                        .setMaximumMultiSelectCount(6)
                        .build();
                pickerDialog.show(getFragmentManager(), "picker");*/
                break;
            case R.id.rlmyMissProgressFileId:
                showFileChooser();
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
                sendProjectPorgress("12");
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
        MediaType mediaType = MediaType.parse("");//Based on the Postman logs,it's not specifying Content-Type, this is why I've made this empty content/mediaType
        MultipartBody.Part[] fileParts = new MultipartBody.Part[files.size()];
        for (int i = 0; i < files.size(); i++) {
            File file = new File(files.get(i).getPath());
            RequestBody fileBody = RequestBody.create(mediaType, file);
            //Setting the file name as an empty string here causes the same issue, which is sending the request successfully without saving the files in the backend, so don't neglect the file name parameter.
            fileParts[i] = MultipartBody.Part.createFormData("project_files[]", file.getPath(), fileBody);
            // fileParts[i] = MultipartBody.Part.createFormData(String.format(Locale.ENGLISH, "files[%d]", i), file.getName(), fileBody);
        }
       /* MultipartBody.Part imgFileStation = null;
        MultipartBody.Part imgFileStationDoc = null;
        if (profilImgPath == null) {
        } else {
            fileForImage = new File(profilImgPath);
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("multipart/form-data"), fileForImage);
            imgFileStation = MultipartBody.Part.createFormData("project_image", fileForImage.getName(), requestFileOne);
        }

        if (docPath == null) {
        } else {
            fileForDocs = new File(docPath);
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("multipart/form-data"), docPath);
            imgFileStationDoc = MultipartBody.Part.createFormData("project_file", fileForDocs.getName(), requestFileOne);
        }*/

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
                    if (sendProjectProgDetailModle.getStatus() == true) {
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
                    chooseFromGallery();
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


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
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
            // docPath = getRealPathFromURI(getActivity(), content_describer);
            // Log.v("images", docPath);
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
        pbMymissionProgress.setVisibility(View.VISIBLE);
        apiServices.projectPorgress(myMissionId).enqueue(new Callback<MissionInProgressModle>() {
            @Override
            public void onResponse(Call<MissionInProgressModle> call, Response<MissionInProgressModle> response) {
                if (response.isSuccessful()) {
                    pbMymissionProgress.setVisibility(View.GONE);
                    MissionInProgressModle myMissionProposedModle = response.body();
                    if (myMissionProposedModle.getStatus() == true) {
                        yourMissionList = myMissionProposedModle.getData();
                        tvUserNameInProgMission.setText(yourMissionList.get(0).getFirstName());
                        tvCommentInProgMission.setText(yourMissionList.get(0).getYourComments());
                        Picasso.with(getActivity())
                                .load(RetrofitClient.MYMISSIONANDMYDEMANDE_IMAGE_URL + yourMissionList
                                        .get(0).getPictureUrl())
                                .into(ivUserInprogMission);

                    } else {

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
            public void onFailure(Call<MissionInProgressModle> call, Throwable t) {
                pbMymissionProgress.setVisibility(View.GONE);
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
}
