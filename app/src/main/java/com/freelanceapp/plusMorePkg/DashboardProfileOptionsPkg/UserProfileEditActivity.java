package com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.CustomToast;
import com.freelanceapp.R;
import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg.GetProfileModle;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg.YourMission;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.updateProfileModlePkg.UpdateProfileModle;
import com.freelanceapp.utility.AppSession;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;
import com.freelanceapp.utility.ImagePicker;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class UserProfileEditActivity extends Fragment implements View.OnClickListener {

    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 100;
    private static final int SELECT_PICTURE = 101;
    final Calendar myCalendar = Calendar.getInstance();
    private ImageView ivnotificationeditprofile, ivdashboardcreditcardback;
    private TextView Tvdob;
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    private CircleImageView ivuserprofileimage;
    private ApiServices apiServices;
    private ProgressBar pbUserEditProfile;
    private List<YourMission> yourMissionList;
    private AppCompatTextView tvname, tvdesinationedit;
    private AppCompatEditText EtName, EtUsername, EtStatus, EtEmail, EtPassword, Etcountry;
    private AppCompatEditText tvpresentation, tvlevelofstudyy, tvfiledofstudyy, tvunivercityy,
            tvcategoriess, tvcompetencesss;
    private RelativeLayout rlnextpager;
    private static Animation shakeAnimation;
    private AppCompatImageView iveditprofilepin;
    private File fileForImage;
    private String profilImgPath, userId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_profile_edit, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            getProfileApi(userId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private void init(View view) {
        iveditprofilepin = view.findViewById(R.id.iveditprofilepinId);
        rlnextpager = view.findViewById(R.id.rlnextpagerid);
        tvlevelofstudyy = view.findViewById(R.id.tvlevelofstudyyid);
        tvfiledofstudyy = view.findViewById(R.id.tvfiledofstudyyid);
        tvunivercityy = view.findViewById(R.id.tvunivercityyid);
        tvcategoriess = view.findViewById(R.id.tvcategoriessid);
        tvcompetencesss = view.findViewById(R.id.tvcompetencesssid);

        EtName = view.findViewById(R.id.EtNameid);
        EtUsername = view.findViewById(R.id.EtUsernameId);
        EtStatus = view.findViewById(R.id.EtStatusId);
        EtEmail = view.findViewById(R.id.EtEmailID);
        EtPassword = view.findViewById(R.id.EtPasswordID);
        Etcountry = view.findViewById(R.id.EtcountryID);
        tvname = view.findViewById(R.id.tvnameid);
        tvdesinationedit = view.findViewById(R.id.tvdesinationeditid);
        tvpresentation = view.findViewById(R.id.tvpresentationidd);


        pbUserEditProfile = view.findViewById(R.id.pbUserEditProfileId);
        ivuserprofileimage = view.findViewById(R.id.ivuserprofileimageId);
        ivuserprofileimage.setOnClickListener(this);
        Tvdob = view.findViewById(R.id.TvdobId);
        Tvdob.setOnClickListener(this);
        ivnotificationeditprofile = view.findViewById(R.id.ivnotificationeditprofileId);
        ivnotificationeditprofile.setOnClickListener(this);

        ivdashboardcreditcardback = view.findViewById(R.id.ivdashboardcreditcardbackId);
        ivdashboardcreditcardback.setOnClickListener(this);

        rlnextpager.setOnClickListener(this);
        iveditprofilepin.setOnClickListener(this);
        ivuserprofileimage.setOnClickListener(this);

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlnextpagerid:
                validation(v);
                break;
            case R.id.ivnotificationeditprofileId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.ivdashboardcreditcardbackId:
                removeThisFragment();
                //replaceFragement(new PlusMoreFragment());
                break;
            case R.id.TvdobId:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
            case R.id.ivuserprofileimageId:
                askStoragePermission();
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
            chooseFromGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseFromGallery();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
                    ivuserprofileimage.setImageBitmap(bitmap);
                    profilImgPath = getRealPathFromURI(getActivity(), imageUri);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Tvdob.setText(sdf.format(myCalendar.getTime()));

    }

    public void removeThisFragment() {
        final FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();
    }

    private void getProfileApi(String user_id) {
        pbUserEditProfile.setVisibility(View.VISIBLE);
        apiServices.getMyProfile(user_id).enqueue(new Callback<GetProfileModle>() {
            @Override
            public void onResponse(Call<GetProfileModle> call, Response<GetProfileModle> response) {
                if (response.isSuccessful()) {
                    pbUserEditProfile.setVisibility(View.GONE);
                    GetProfileModle missionlist = response.body();
                    if (missionlist.getStatus() == true) {
                        yourMissionList = missionlist.getYourMissions();
                        EtName.setText(yourMissionList.get(0).getName());
                        EtUsername.setText(yourMissionList.get(0).getUsername());
                        EtStatus.setText(yourMissionList.get(0).getStatus());
                        EtEmail.setText(yourMissionList.get(0).getEmail());
                        EtPassword.setText(yourMissionList.get(0).getPassword());
                        Etcountry.setText(yourMissionList.get(0).getCountry());
                        Tvdob.setText(yourMissionList.get(0).getDob());

                        if (yourMissionList.get(0).getPictureUrl().isEmpty()) {
                        } else {
                            Picasso.with(getActivity()).load(RetrofitClient.MISSION_USER_IMAGE_URL + yourMissionList
                                    .get(0).getPictureUrl())
                                    .into(ivuserprofileimage);
                        }

                        tvpresentation.setText(yourMissionList.get(0).getPresentation());
                        tvlevelofstudyy.setText(yourMissionList.get(0).getLevelOfStudy());
                        tvfiledofstudyy.setText(yourMissionList.get(0).getFieldOfStudy());
                        tvunivercityy.setText(yourMissionList.get(0).getSchoolAddress() + "," + yourMissionList.get(0).getUniversity());
                        tvcategoriess.setText(yourMissionList.get(0).getIntrestedCategory());
                        tvcompetencesss.setText(yourMissionList.get(0).getSkills());
                    }

                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbUserEditProfile.setVisibility(View.GONE);
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
            public void onFailure(Call<GetProfileModle> call, Throwable t) {
                pbUserEditProfile.setVisibility(View.GONE);
            }
        });

    }

    private void validation(View v) {
        if (EtName.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(getActivity(), v, "Name Can't Empty");
            EtName.startAnimation(shakeAnimation);
            EtName.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (EtUsername.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(getActivity(), v, "User Name Can't Empty");
            EtUsername.startAnimation(shakeAnimation);
            EtUsername.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (!Constants.emailValidator(EtEmail.getText().toString())) {
            new CustomToast().Show_Toast(getActivity(), v, "Invalid Email Id");
            EtEmail.startAnimation(shakeAnimation);
            EtEmail.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (EtPassword.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(getActivity(), v, "Can't Empty");
            EtPassword.startAnimation(shakeAnimation);
            EtPassword.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            EtPassword.getText().toString().trim();
        } else {

            if (CheckNetwork.isNetAvailable(getActivity())) {
                updateProfile();
            } else {
                new CustomToast().Show_Toast(getActivity(), v, "Check Network Connection");
            }
        }
    }


    private void updateProfile() {
        pbUserEditProfile.setVisibility(View.VISIBLE);
        MultipartBody.Part imgFileStation = null;
        MultipartBody.Part imgFileStationDoc = null;
        if (profilImgPath == null) {
        } else {
            fileForImage = new File(profilImgPath);
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("multipart/form-data"), fileForImage);
            imgFileStation = MultipartBody.Part.createFormData("picture_url", fileForImage.getName(), requestFileOne);
        }

        MultipartBody.Part profile_id_ = MultipartBody.Part.createFormData("profile_id", String.valueOf(userId));
        MultipartBody.Part name_ = MultipartBody.Part.createFormData("name", EtName.getText().toString());
        MultipartBody.Part email_ = MultipartBody.Part.createFormData("email", EtEmail.getText().toString());
        MultipartBody.Part username_ = MultipartBody.Part.createFormData("username", EtUsername.getText().toString());
        MultipartBody.Part password_ = MultipartBody.Part.createFormData("password", EtPassword.getText().toString());
        MultipartBody.Part dob_ = MultipartBody.Part.createFormData("dob", Tvdob.getText().toString());
        MultipartBody.Part country = MultipartBody.Part.createFormData("country", Etcountry.getText().toString());
        MultipartBody.Part presentation = MultipartBody.Part.createFormData("presentation", tvpresentation.getText().toString());
        MultipartBody.Part level_of_study = MultipartBody.Part.createFormData("level_of_study", tvlevelofstudyy.getText().toString());
        MultipartBody.Part school_college = MultipartBody.Part.createFormData("school_address", tvunivercityy.getText().toString());
        MultipartBody.Part skill = MultipartBody.Part.createFormData("skills", tvcompetencesss.getText().toString());
        MultipartBody.Part field_of_study = MultipartBody.Part.createFormData("Field_of_study", tvfiledofstudyy.getText().toString());
        MultipartBody.Part categroy_of_interest = MultipartBody.Part.createFormData("intrested_category", tvcategoriess.getText().toString());


        apiServices.updateProfile(profile_id_, name_, email_, username_, password_, dob_,
                country, imgFileStation, presentation, level_of_study, school_college, skill, field_of_study, categroy_of_interest).enqueue(new Callback<UpdateProfileModle>() {
            @Override
            public void onResponse(Call<UpdateProfileModle> call, Response<UpdateProfileModle> response) {
                if (response.isSuccessful()) {
                    pbUserEditProfile.setVisibility(View.GONE);
                    UpdateProfileModle updateProfileModle = response.body();
                    if (updateProfileModle.getStatus()) {
                        Toast.makeText(getActivity(), updateProfileModle.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                pbUserEditProfile.setVisibility(View.GONE);
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
            public void onFailure(Call<UpdateProfileModle> call, Throwable t) {
                pbUserEditProfile.setVisibility(View.GONE);
            }
        });

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
