package com.frelance.plusMorePkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.EditProfileConfirmation;
import com.frelance.R;
import com.frelance.SelectLanguageDailoge;
import com.frelance.databinding.ActivityPlusMoreBinding;
import com.frelance.homePkg.HomeActivity;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.paymentPkg.DashboardPaymentActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardHelpActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg.GetProfileModle;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg.YourMission;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardParametersActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.DashboardSupportActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DeshboardSponsorshipActivity;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.UserProfileEditActivity;
import com.frelance.clientProfilePkg.ProfileRatingDescriptionActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.frelance.utility.PrefData;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlusMoreFragment extends Fragment implements PlusMoreAdapter.PlusMoreAppOnClickListener, View.OnClickListener {
    private RelativeLayout rlsponsorship, rlpayment, rlparameters, rlsupport,
            rlhelp, rleditprofile, rlcircleprrogressbaro, rlratingnameid;
    private ActivityPlusMoreBinding activityPlusMoreBinding;
    private ImageView IvUserProfileNotifi, IvUserProfilesettings;
    private RadioButton radiofrench, radioenglish;
    private boolean flagmale = false;
    private boolean flagfemale = false;
    private ApiServices apiServices;

    private CircularProgressIndicator donutprogress;
    // private ImageView IvUserProfileNotifi;
    private EditProfileConfirmation editProfileConfirmation;
    private SelectLanguageDailoge selectLanguageDailoge;
    Context con;
    PrefData prefData;
    private String language;
    private ProgressBar pbUserEditProfile;
    private List<YourMission> yourMissionList;
    private CircleImageView ivuserprofileimage;
    private AppCompatTextView tvname, tvdesination, tvRatingCountPlusMore;
    private String userImg, userName, userId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityPlusMoreBinding = DataBindingUtil.inflate(inflater, R.layout.activity_plus_more, container, false);
        View view = activityPlusMoreBinding.getRoot();
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        Toast.makeText(getActivity(), userId, Toast.LENGTH_LONG).show();
        init(view);
        prefData = new PrefData(getActivity());

        if (CheckNetwork.isNetAvailable(getActivity())) {
            getProfileApi(userId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }


    private void init(View view) {
        tvRatingCountPlusMore = view.findViewById(R.id.tvRatingCountPlusMoreId);
        pbUserEditProfile = view.findViewById(R.id.pbUserEditProfileId);
        IvUserProfilesettings = view.findViewById(R.id.IvUserProfilesettingsId);
        tvdesination = view.findViewById(R.id.tvdesinationid);
        tvname = view.findViewById(R.id.tvnameid);
        ivuserprofileimage = view.findViewById(R.id.ivuserprofileimageId);
        IvUserProfilesettings.setOnClickListener(this);
        IvUserProfilesettings.setVisibility(View.INVISIBLE);
        IvUserProfileNotifi = view.findViewById(R.id.IvUserProfileNotifiId);
        IvUserProfileNotifi.setOnClickListener(this);

        rleditprofile = view.findViewById(R.id.rleditprofileId);
        rleditprofile.setOnClickListener(this);

        rlratingnameid = view.findViewById(R.id.rlratingnameid);
        rlratingnameid.setOnClickListener(this);

        rlcircleprrogressbaro = view.findViewById(R.id.rlcircleprrogressbaroid);
        rlcircleprrogressbaro.setOnClickListener(this);

        donutprogress = view.findViewById(R.id.donutprogressid);

// you can set max and current progress values individually
        donutprogress.setMaxProgress(5);
// you can get progress values using following getters
        donutprogress.getProgress(); // returns 4
        donutprogress.getMaxProgress();// returns 5

     /*   IvUserProfileNotifi = view.findViewById(R.id.IvUserProfileNotifiId);
        IvUserProfileNotifi.setOnClickListener(this);*/

        rlsponsorship = view.findViewById(R.id.rlsponsorshipid);
        rlsponsorship.setOnClickListener(this);

        rlpayment = view.findViewById(R.id.rlpaymentid);
        rlpayment.setOnClickListener(this);

        rlparameters = view.findViewById(R.id.rlparametersid);
        rlparameters.setOnClickListener(this);

        rlsupport = view.findViewById(R.id.rlsupportid);
        rlsupport.setOnClickListener(this);

        rlhelp = view.findViewById(R.id.rlhelpid);
        rlhelp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlsponsorshipid:
                addFragment(new DeshboardSponsorshipActivity(), true, Constants.DESHBOARD_SPONSOR_SHIP);
                break;
            case R.id.rlpaymentid:
                addFragment(new DashboardPaymentActivity(), true, Constants.DASHBOARD_PAYMENT_ACTIVITY);
                break;
            case R.id.rlparametersid:
                addFragment(new DashboardParametersActivity(), true, Constants.DASHBOARD_PARAMETERS_ACTIVITY);
                break;
            case R.id.rlsupportid:
                addFragment(new DashboardSupportActivity(), true, Constants.DASHBOARD_SUPPORT_ACTIVITY);
                break;
            case R.id.rlhelpid:
                addFragment(new DashboardHelpActivity(), true, Constants.DASHBOARD_HELP_ACTIVITY);
                break;
            case R.id.rleditprofileId:
                editConfirmationDialoge();
                break;
            case R.id.rlcircleprrogressbaroid:
                break;
            case R.id.IvUserProfileNotifiId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.IvUserProfilesettingsId:
                selectLanguageDailoge();
                break;
            case R.id.rlratingnameid:
                AppSession.setStringPreferences(getActivity(), "clientEntry", "user");
                Intent intent = new Intent(getActivity(), ProfileRatingDescriptionActivity.class);
                intent.putExtra("userImg", userImg);
                intent.putExtra("userName", userName);
                getActivity().startActivity(intent);
                ((Activity) (getActivity())).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;


        }


    }

    private void selectLanguageDailoge() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.activity_select_language_dailoge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        final AppCompatCheckBox cbfrenchiId = dialog.findViewById(R.id.cbfrenchiId);
        final AppCompatCheckBox cbEnglishid = dialog.findViewById(R.id.cbEnglishid);
        ImageView ivlogoutClose = dialog.findViewById(R.id.ivlogoutCloseId);
        RelativeLayout rlOkId = dialog.findViewById(R.id.rlOkId);
        if (prefData.getCurrentLanguage().equals("fr")) {
            cbfrenchiId.setChecked(true);
        } else if (prefData.getCurrentLanguage().equals("eng")) {
            cbEnglishid.setChecked(true);
        }
        ivlogoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        cbfrenchiId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbEnglishid.setChecked(false);
                    language = "fr";

                }
            }
        });
        cbEnglishid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    language = "eng";
                    cbfrenchiId.setChecked(false);
                }
            }
        });

        rlOkId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (language == null) {
                    Toast.makeText(getActivity(), "select Language", Toast.LENGTH_SHORT).show();
                } else {
                    Locale myLocale = new Locale(language);
                    Resources res = getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = myLocale;
                    res.updateConfiguration(conf, dm);
                    prefData.setCurrentLanguage(language);
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.flHomeId, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    private void replaceFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void editConfirmationDialoge() {
        final View dialogView = View.inflate(getActivity(), R.layout.activity_edit_profile_confirmation, null);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(false);
        AppCompatImageView iveditClose = (AppCompatImageView) dialog.findViewById(R.id.iveditCloseId);
        RelativeLayout rlYesEdit = dialog.findViewById(R.id.rlYesEditId);
        RelativeLayout rlNoedit = dialog.findViewById(R.id.rlNoeditId);

        iveditClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });
        rlYesEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                replaceFragement(new UserProfileEditActivity());
            }

        });

        rlNoedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

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
                        tvname.setText(yourMissionList.get(0).getUsername());
                        tvdesination.setText(yourMissionList.get(0).getSkills());
                        if (yourMissionList.get(0).getPictureUrl().isEmpty()) {

                        } else {
                            Picasso.with(getActivity()).load(RetrofitClient.MISSION_USER_IMAGE_URL + yourMissionList
                                    .get(0).getPictureUrl()).into(ivuserprofileimage);
                        }
                        userImg = yourMissionList.get(0).getPictureUrl();
                        userName = yourMissionList.get(0).getFirstName();

                        donutprogress.setCurrentProgress(Double.parseDouble(yourMissionList.get(0).getProfileRate()));
                        donutprogress.setProgress(Double.parseDouble(yourMissionList.get(0).getProfileRate()), 5);
                        //tvRatingCountPlusMore.setText(yourMissionList.get(0).getProfileRate());
                        if (missionlist.getRating().isEmpty()) {
                            tvRatingCountPlusMore.setText("0");
                        } else {
                            tvRatingCountPlusMore.setText(missionlist.getRating());
                        }

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

}
