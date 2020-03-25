package com.freelanceapp.utility;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Constants {


    public static String PREF_NAME = "preference_data";
    public static String PREF_LANG = "current_language";
    public static final String Initiate_Fragment = "InitiateFragment";
    public static String DESHBOARD_SPONSOR_SHIP = "DeshboardSponsorshipActivity";
    public static String DESHBOARD_SUPPORT = "DashboardSupportActivity";
    public static String PLUS_MORE_FRAGMENT = "PlusMoreFragment";
    public static String DASHBOARD_PAYMENT_ACTIVITY = "DashboardPaymentActivity";
    public static String TRANSACTIONS_TAB_LAYOUT="TransactionsTablayoutFragment";
    public static String DASHBOARD_PARAMETERS_ACTIVITY="DashboardParametersActivity";
    public static String DASHBOARD_PARAMETERS_TERMS_ACTIVITY="DashboardParametersTermsActivity";
    public static String DASHBOARD_SUPPORT_ACTIVITY="DashboardSupportActivity";
    public static String DASHBOARD_HELP_ACTIVITY="DashboardHelpActivity";
    public static String HOME_TABLAYOUT_FRAGMENT="HomeTablayoutFragment";
    public static String MY_MISSION_FRAGMENT="MyMissionFragment";
    public static String MY_REQUEST_FRAGMENT="MyRequestFragment";
    public static String MESSAGE_LIST_TAB_LAYOUT_FRAGMENT="MessageListTablayoutFragment";
    public static String MY_MISSION_PROPOSEE_ACTIVITY="MyMissionProposeeActivity";
    public static String MY_MISSION_ONGOING_ACTIVITY="MyMissionOngoingActivity";
    public static String MY_MISSION_LIVERY_ACTIVITY="MyMissionDeliveryActivity";
    public static String MY_MISSION_COMPLETE_ACTIVITY="MyMissionCompleteActivity";
    public static String MY_MISSION_DISPUTE_ACTIVITY="MyMissionInDisputeActivity";
    public static String MY_REQUEST_PUBLISHED_TABLAYOUT_FRAGMENT="MyRequestPublishedTablayoutFragment";
    public static String MY_REQUEST_ONGOING_FRAGMENT="MyDemandsOngoingActivity";
    public static String MY_REQUEST_LIVERY_FRAGMENT="MyRequestLiveryActivity";
    public static String MY_REQUEST_COMPLETE_FRAGMENT="MyRequestCompleteeActivity";
    public static String MY_REQUEST_OPENLITIGATION_FRAGMENT="MyRequestOpenlitigationActivity";
    public static String DETAILS_ACTIVITY="DetailsActivity";

    public static final void customToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

 /*   public static void customSnackbar(String msg, Context context, View view) {
        TSnackbar snackbar = TSnackbar.make(view, msg, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.BLACK);
        View snackbarView = snackbar.getView();
        ViewGroup.LayoutParams params = snackbarView.getLayoutParams();
        params.height = 100;
        snackbarView.setLayoutParams(params);
        snackbarView.setBackgroundColor(context.getResources().getColor(R.color.snackColor));
        TextView mTextView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        mTextView.setGravity(Gravity.CENTER);
        snackbar.show();
    }*/


    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidEmail(String target) {
        boolean flag;
        if (TextUtils.isEmpty(target)) {
            return true;
        } else {
            flag = emailValidator(target);
            if (flag) {
                // return flag;
            }
            return flag;
        }
        // return (!TextUtils.isEmpty(target) || Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
