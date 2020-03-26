package com.freelanceapp.plusMorePkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.freelanceapp.EditProfileConfirmation;
import com.freelanceapp.NotificationActivity;
import com.freelanceapp.R;
import com.freelanceapp.SelectLanguageDailoge;
import com.freelanceapp.databinding.ActivityPlusMoreBinding;
import com.freelanceapp.homePkg.HomeActivity;
import com.freelanceapp.paymentPkg.DashboardPaymentActivity;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardHelpActivity;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardParametersActivity;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.DashboardSupportActivity;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DeshboardSponsorshipActivity;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.UserProfileEditActivity;
import com.freelanceapp.userProfileRatingPkg.ProfileRatingDescriptionActivity;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.Constants;
import com.freelanceapp.utility.PrefData;

import java.util.Locale;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class PlusMoreFragment extends Fragment implements PlusMoreAdapter.PlusMoreAppOnClickListener, View.OnClickListener {
    private RelativeLayout rlsponsorship, rlpayment, rlparameters, rlsupport,
            rlhelp, rleditprofile, rlcircleprrogressbaro, rlratingnameid;
    private ActivityPlusMoreBinding activityPlusMoreBinding;
    private ImageView IvUserProfileNotifi, IvUserProfilesettings;
    private RadioButton radiofrench, radioenglish;
    private boolean flagmale = false;
    private boolean flagfemale = false;

    private CircularProgressIndicator donutprogress;
    // private ImageView IvUserProfileNotifi;
    private EditProfileConfirmation editProfileConfirmation;
    private SelectLanguageDailoge selectLanguageDailoge;
    Context con;
    PrefData prefData;
    private String language;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityPlusMoreBinding = DataBindingUtil.inflate(inflater, R.layout.activity_plus_more, container, false);
        View view = activityPlusMoreBinding.getRoot();
        init(view);
        prefData = new PrefData(getActivity());
        return view;
    }


    private void init(View view) {
        IvUserProfilesettings = view.findViewById(R.id.IvUserProfilesettingsId);
        IvUserProfilesettings.setOnClickListener(this);

        IvUserProfileNotifi = view.findViewById(R.id.IvUserProfileNotifiId);
        IvUserProfileNotifi.setOnClickListener(this);

        rleditprofile = view.findViewById(R.id.rleditprofileId);
        rleditprofile.setOnClickListener(this);

        rlratingnameid = view.findViewById(R.id.rlratingnameid);
        rlratingnameid.setOnClickListener(this);

        rlcircleprrogressbaro = view.findViewById(R.id.rlcircleprrogressbaroid);
        rlcircleprrogressbaro.setOnClickListener(this);

        CircularProgressIndicator donutprogress = view.findViewById(R.id.donutprogressid);

// you can set max and current progress values individually
        donutprogress.setMaxProgress(5);
        donutprogress.setCurrentProgress(4.3);
// or all at once
        donutprogress.setProgress(4.3, 5);

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
                // replaceFragement(new DeshboardSponsorshipActivity());
                addFragment(new DeshboardSponsorshipActivity(), true, Constants.DESHBOARD_SPONSOR_SHIP);
                break;
            case R.id.rlpaymentid:
                //CheckNetwork.nextScreenWithoutFinish(getActivity(), DashboardPaymentActivity.class);
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
            /*case R.id.IvUserProfileNotifiId:
                // replaceFragement(new NotificationActivity());
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;*/
            case R.id.rleditprofileId:
                // replaceFragement(new UserProfileEditActivity());
                editConfirmationDialoge();
                // editProfileConfirmation.EditProfile(getActivity());
                //CheckNetwork.goTobackScreen(getActivity(), EditProfileConfirmation.class);
                break;
            case R.id.rlcircleprrogressbaroid:
                //replaceFragement(new ProfileRatingDescriptionActivity());
                //  CheckNetwork.nextScreenWithoutFinish(getActivity(), ProfileRatingDescriptionActivity.class);
                break;
            case R.id.IvUserProfileNotifiId:
                //replaceFragement(new ProfileRatingDescriptionActivity());
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.IvUserProfilesettingsId:
                selectLanguageDailoge();
                break;
            case R.id.rlratingnameid:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), ProfileRatingDescriptionActivity.class);
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
        if (prefData.getCurrentLanguage().equals("sp")) {
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
                    language = "sp";

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
}
