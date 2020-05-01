package com.frelance.InboxListPkg.MessageTablayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.frelance.ApiPkg.ApiServices;
import com.frelance.ApiPkg.RetrofitClient;
import com.frelance.InboxListPkg.MessageListAdapterPkg.DialogeMessageUserAdapter;
import com.frelance.InboxListPkg.msgModlePkg.ChatUserResponseModle;
import com.frelance.InboxListPkg.msgModlePkg.Datum;
import com.frelance.R;
import com.frelance.InboxListPkg.MessageListFragmentPkg.MessageNonLusFragment;
import com.frelance.InboxListPkg.MessageListFragmentPkg.MessageToutFragment;
import com.frelance.chatPkg.ChatActivity;
import com.frelance.notificationPkg.NotificationActivity;
import com.frelance.utility.AppSession;
import com.frelance.utility.CheckNetwork;
import com.frelance.utility.Constants;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageListTablayoutFragment extends Fragment implements View.OnClickListener, DialogeMessageUserAdapter.MessageToutAppOnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivnotificationmsg;
    private ProgressBar Pbsearch;
    private String userId;
    private ApiServices apiServices;
    private DialogeMessageUserAdapter dialogeMessageUserAdapter;
    private List<Datum> datumList;
    private List<Datum> datumList1;
    private RelativeLayout rlUserSearchBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tablayout_messagelist, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USERID);
        addTabs(view);
        return view;
    }

    private void addTabs(View view) {
        rlUserSearchBox = view.findViewById(R.id.rlUserSearchBoxId);
        ivnotificationmsg = view.findViewById(R.id.ivnotificationmsgId);
        ivnotificationmsg.setOnClickListener(this);
        rlUserSearchBox.setOnClickListener(this);
        tabLayout = view.findViewById(R.id.tabLayoutId);
        viewPager = view.findViewById(R.id.viewPagerId);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new MessageToutFragment(), getResources().getString(R.string.All));
        adapter.addFrag(new MessageNonLusFragment(), getResources().getString(R.string.Unread));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivnotificationmsgId:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), NotificationActivity.class);
                break;
            case R.id.rlUserSearchBoxId:
                inboxChatUser();
                break;

        }

    }

    private void inboxChatUser() {
        final View dialogView = View.inflate(getActivity(), R.layout.fragment_message_userlist, null);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(dialogView);
        AppCompatImageView iveditClose = (AppCompatImageView) dialog.findViewById(R.id.iveditCloseId);
        RecyclerView rvSearchUserId = (RecyclerView) dialog.findViewById(R.id.rvSearchUserId);
        Pbsearch = (ProgressBar) dialog.findViewById(R.id.PbsearchId);
        AppCompatEditText Etsearch = dialog.findViewById(R.id.EtsearchId);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dialogView.getWindowToken(), 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvSearchUserId.setLayoutManager(layoutManager);
        dialogeMessageUserAdapter = new DialogeMessageUserAdapter(getActivity(), this);
        rvSearchUserId.setAdapter(dialogeMessageUserAdapter);
        iveditClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (CheckNetwork.isNetAvailable(getActivity())) {
            //chatUserlist(userId);
            chatUserlist(userId);
        } else {

        }
        Etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogeMessageUserAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                dialogeMessageUserAdapter.getFilter().filter(s);
            }
        });


        dialog.show();
    }

    private void chatUserlist(String userId) {
        Pbsearch.setVisibility(View.VISIBLE);
        apiServices.getuserslist(userId).enqueue(new Callback<ChatUserResponseModle>() {
            @Override
            public void onResponse(Call<ChatUserResponseModle> call, Response<ChatUserResponseModle> response)
            {
                Pbsearch.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    ChatUserResponseModle chatUserResponseModle = response.body();
                    if (chatUserResponseModle.getStatus()) {
                        datumList = chatUserResponseModle.getData();
                        dialogeMessageUserAdapter.addmymissionData(datumList);
                    } else {
                    }
                } else {
                    if (response.code() == 400) {
                        if (!false) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                Pbsearch.setVisibility(View.GONE);
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
            public void onFailure(Call<ChatUserResponseModle> call, Throwable t) {
                Pbsearch.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void msgOnClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlmsguserid:
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("client_id", datumList.get(position).getClientId());
                intent.putExtra("firstName", datumList.get(position).getFirstName());
                intent.putExtra("lastName", datumList.get(position).getLastName());
                intent.putExtra("clientImg", datumList.get(position).getPictureUrl());
                startActivity(intent);
                (getActivity()).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }

/*    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    datumList =datumList1;
                } else {
                    List<Project> filteredList = new ArrayList<>();
                    for (Project row : datumList1) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    datumList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = datumList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                datumList = (ArrayList<Project>) filterResults.values;
                // notifyDataSetChanged();
            }
        };
    }*/
}
