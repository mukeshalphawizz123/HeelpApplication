package com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.CustomProgressbar;
import com.frelance.CustomToast;
import com.frelance.HelpActivity;
import com.frelance.R;
import com.frelance.detailsPkg.DetailsActivity;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.Adapter.MyDemandsLiveryAdapter;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg.AskToModifyResponseModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg.Datum;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg.DemandDeliveredModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg.SubmitReviewModle;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.paymentPkg.CreditCardPayment;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.frelance.utility.FileDownloading;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDemandsDeliveryActivity extends Fragment
        implements MyDemandsLiveryAdapter.MyRequestLiveryAppOnClickListener,
        View.OnClickListener {

    private MyDemandsLiveryAdapter myRequestLiveryAdapter;
    private RecyclerView rvmyreqliveryfileupload;
    private ImageView ivlivreedashboardback, ivnotification;
    private RelativeLayout rlreqlivreeviewdetail;
    private TextView tvviewprofile, tvliverycontact;
    private AppCompatCheckBox radioid, radiograyid;
    private ApiServices apiServices;
    private ProgressBar pbDemandDelivery;
    private List<Datum> datumList;
    private AppCompatTextView tvUserDemandDely, tvCommentDemandDely, tvDemandTitleRequest;
    private CircleImageView ivUserDemandDely;
    private String projectId, userid, clientId, mission_demand_title, firstName;
    private static Animation shakeAnimation;
    private CheckBox radiogray;
    private RelativeLayout rlliverymodificationbtnbtn, rlpaymentbtn;
    private ArrayList<String> filesList;
    FileDownloading fileDownloading;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_request_livery, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        fileDownloading = new FileDownloading(getActivity());
        projectId = this.getArguments().getString("projectId");
        clientId = AppSession.getStringPreferences(getActivity(), "clientId");
        userid = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        firstName = AppSession.getStringPreferences(getActivity(), Constants.FIRST_NAME);
        mission_demand_title = AppSession.getStringPreferences(getActivity(), "mission_demand_title");
        filesList = new ArrayList<>();
        // Toast.makeText(getActivity(), "" + clientId, Toast.LENGTH_LONG).show();
        init(view);
        if (CheckNetwork.isNetAvailable(getActivity())) {
            // Toast.makeText(getActivity(), "" + projectId, Toast.LENGTH_LONG).show();
            myOnDeliveryApi(projectId);
        } else {
            Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
        }
        tvDemandTitleRequest.setText(mission_demand_title);
        return view;
    }

    private void init(View view) {
        tvDemandTitleRequest = view.findViewById(R.id.tvDemandTitleRequestId);
        rlpaymentbtn = view.findViewById(R.id.rlpaymentbtnid);
        rlliverymodificationbtnbtn = view.findViewById(R.id.rlliverymodificationbtnbtnid);
        radiogray = view.findViewById(R.id.radiograyid);
        ivUserDemandDely = view.findViewById(R.id.ivUserDemandDelyId);
        tvCommentDemandDely = view.findViewById(R.id.tvCommentDemandDelyId);
        tvUserDemandDely = view.findViewById(R.id.tvUserDemandDelyId);
        pbDemandDelivery = view.findViewById(R.id.pbDemandDeliveryId);
        tvliverycontact = view.findViewById(R.id.tvliverycontactid);
        tvliverycontact.setOnClickListener(this);
        radioid = view.findViewById(R.id.radioid);
        radiograyid = view.findViewById(R.id.radiograyid);
        ivnotification = view.findViewById(R.id.ivnotificationId);
        ivnotification.setOnClickListener(this);
        tvviewprofile = view.findViewById(R.id.tvviewprofileid);
        ivlivreedashboardback = view.findViewById(R.id.ivlivreedashboardbackId);
        ivlivreedashboardback.setOnClickListener(this);
        rlreqlivreeviewdetail = view.findViewById(R.id.rlreqlivreeviewdetailid);
        rlreqlivreeviewdetail.setOnClickListener(this);
        rvmyreqliveryfileupload = view.findViewById(R.id.rvmyreqliveryfileuploadid);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvmyreqliveryfileupload.setLayoutManager(layoutManager);
        myRequestLiveryAdapter = new MyDemandsLiveryAdapter(getActivity(), this);
        rvmyreqliveryfileupload.setAdapter(myRequestLiveryAdapter);
        SpannableString content = new SpannableString(getResources().getString(R.string.view_details));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvviewprofile.setText(content);
        SpannableString content1 = new SpannableString(getResources().getString(R.string.report_a_problem));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvliverycontact.setText(content1);
        rlliverymodificationbtnbtn.setOnClickListener(this);
        rlpaymentbtn.setOnClickListener(this);
        rlliverymodificationbtnbtn.setEnabled(false);
        radioid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    reveiwDialoge();
                    radiograyid.setChecked(false);
                    //  rlpaymentbtn.setEnabled(true);
                } else {
                    //  rlpaymentbtn.setEnabled(false);
                }
            }
        });
        radiograyid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioid.setChecked(false);
                }
            }
        });

        radiogray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rlliverymodificationbtnbtn.setEnabled(true);

                } else {
                    rlliverymodificationbtnbtn.setEnabled(false);

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlpaymentbtnid:
                if (radioid.isChecked()) {
                    CheckNetwork.nextScreenWithoutFinish(getActivity(), CreditCardPayment.class);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.kindlyreviews), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.ivlivreedashboardbackId:
                removeThisFragment();
                // replaceFragementWithoutStack(new MyDemandFragment());
                break;
            case R.id.rlreqlivreeviewdetailid:
                replaceFragement(new DetailsActivity());
                break;
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.tvliverycontactid:
                replaceFragement(new HelpActivity());
                break;
            case R.id.rlliverymodificationbtnbtnid:
                if (CheckNetwork.isNetAvailable(getActivity())) {
                    askToModify(projectId);
                } else {
                    Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    private void replaceFragement(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("missionId", projectId);
        fragment.setArguments(bundle);
        AppSession.setStringPreferences(getActivity(), "OnGoing", "MyReqLivree");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void replaceFragementWithoutStack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flHomeId, fragment);
        fragmentTransaction.commit();
    }


    public void removeThisFragment() {
        final FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();
    }

    private void myOnDeliveryApi(String status) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.demandDelivered(status).enqueue(new Callback<DemandDeliveredModle>() {
            @Override
            public void onResponse(Call<DemandDeliveredModle> call, Response<DemandDeliveredModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    DemandDeliveredModle requestlist = response.body();
                    if (requestlist.getStatus()) {
                        datumList = requestlist.getData();
                        tvUserDemandDely.setText(datumList.get(0).getFirstName());
                        tvCommentDemandDely.setText(datumList.get(0).getYourComments());

                        if (datumList.get(0).getPictureUrl().isEmpty()) {

                        } else {
                            Picasso.with(getActivity())
                                    .load(RetrofitClient.MISSION_USER_IMAGE_URL + datumList.get(0)
                                            .getPictureUrl())
                                    .into(ivUserDemandDely);
                        }


                        if (datumList.get(0).getProjectImage().isEmpty()) {

                        } else {
                            String[] imgesArray = datumList.get(0).getProjectImage().split(",");
                            for (int i = 0; i < imgesArray.length; i++) {
                                filesList.add(imgesArray[i]);
                            }
                        }

                        if (datumList.get(0).getProjectFiles().isEmpty()) {

                        } else {
                            String[] filesArray = datumList.get(0).getProjectFiles().split(",");
                            for (int i = 0; i < filesArray.length; i++) {
                                filesList.add(filesArray[i]);
                            }
                        }
                        myRequestLiveryAdapter.addDeliveryDemandsFiles(filesList);

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
            public void onFailure(Call<DemandDeliveredModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }


    private void askToModify(String misionid) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.askToModify(misionid).enqueue(new Callback<AskToModifyResponseModle>() {
            @Override
            public void onResponse(Call<AskToModifyResponseModle> call, Response<AskToModifyResponseModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    AskToModifyResponseModle requestlist = response.body();
                    if (requestlist.getStatus()) {
                        Toast.makeText(getActivity(), requestlist.getMessage(), Toast.LENGTH_LONG).show();
                        removeThisFragment();
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
            public void onFailure(Call<AskToModifyResponseModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }


    private void reveiwDialoge() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.review_dialoge);
        AppCompatImageView ivReviewClose = (AppCompatImageView) dialog.findViewById(R.id.ivReviewCloseId);
        final AppCompatTextView tvUserNameReview = (AppCompatTextView) dialog.findViewById(R.id.tvUserNameReviewId);
        RelativeLayout rlLogin = (RelativeLayout) dialog.findViewById(R.id.rlLoginId);
        final AppCompatEditText tvReviewBoxId = (AppCompatEditText) dialog.findViewById(R.id.tvReviewBoxId);
        final RatingBar rbRevewId = dialog.findViewById(R.id.rbRevewId);
        if (firstName == null) {
            tvUserNameReview.setText("None");
        } else {
            tvUserNameReview.setText(firstName);
        }
        ivReviewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                radioid.setChecked(false);
            }
        });
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvReviewBoxId.getText().toString().isEmpty()) {
                    new CustomToast().Show_Toast(getActivity(), v, "Can't Empty");
                    tvReviewBoxId.startAnimation(shakeAnimation);
                    tvReviewBoxId.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                } else {
                    if (CheckNetwork.isNetAvailable(getActivity())) {
                        submitReviewApi(tvReviewBoxId.getText().toString(), String.valueOf(rbRevewId.getNumStars()), dialog);
                    } else {
                        Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        dialog.show();
    }

    private void submitReviewApi(String reveriewMsg, String numberOfStar, final Dialog dialog) {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.addReviewToUser(clientId, numberOfStar, reveriewMsg, userid).enqueue(new Callback<SubmitReviewModle>() {
            @Override
            public void onResponse(Call<SubmitReviewModle> call, Response<SubmitReviewModle> response) {
                if (response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        SubmitReviewModle submitReviewModle = response.body();
                        if (submitReviewModle.getStatus()) {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), submitReviewModle.getMessage(), Toast.LENGTH_LONG).show();
                            radioid.setChecked(true);
                            radioid.setEnabled(false);
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
            public void onFailure(Call<SubmitReviewModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }


    @Override
    public void myDemandDeliveryOnClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlFileFolderId:
                fileDownloading.DownloadImage(RetrofitClient.DOWNLOAD_URL + filesList.get(position));
                break;
        }
    }
}
