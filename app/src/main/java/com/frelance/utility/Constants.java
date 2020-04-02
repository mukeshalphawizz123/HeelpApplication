package com.frelance.utility;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public static String MY_REQUEST_FRAGMENT="MyDemandFragment";
    public static String MESSAGE_LIST_TAB_LAYOUT_FRAGMENT="MessageListTablayoutFragment";
    public static String MY_MISSION_PROPOSEE_ACTIVITY="MyMissionProposeeActivity";
    public static String MY_MISSION_ONGOING_ACTIVITY="MyMissionOngoingActivity";
    public static String MY_MISSION_LIVERY_ACTIVITY="MyMissionDeliveryActivity";
    public static String MY_MISSION_COMPLETE_ACTIVITY="MyMissionCompleteActivity";
    public static String MY_MISSION_DISPUTE_ACTIVITY="MyMissionInDisputeActivity";
    public static String MY_REQUEST_PUBLISHED_TABLAYOUT_FRAGMENT="MyDemandsPublishedTablayoutFragment";
    public static String MY_REQUEST_ONGOING_FRAGMENT="MyDemandsOngoingActivity";
    public static String MY_REQUEST_LIVERY_FRAGMENT="MyDemandsDeliveryActivity";
    public static String MY_REQUEST_COMPLETE_FRAGMENT="MyDemandsCompleteeActivity";
    public static String MY_REQUEST_OPENLITIGATION_FRAGMENT="MyRequestOpenlitigationActivity";
    public static String DETAILS_ACTIVITY="DetailsActivity";
    public static String USERID="user_id";
    public static String USERNAME="user_name";
    public static String ROLE="user_role";
    public static String NAME="name";
    public static String FIRST_NAME="first_name";
    public static String LAST_NAME="last_name";
    public static String EMAIL="email";
    public static String MOBILE_NO="mobile_no";
    public static String PROFILE_URL="profile_url";
    public static String PICTURE_URL="picture_url";




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

    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
