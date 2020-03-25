package com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestPublishedPkg.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.R;
import com.freelanceapp.chatPkg.Adapter.ChatActivityMain;

import com.freelanceapp.databinding.FragmentMyRequestPublishedNoteBinding;
import com.freelanceapp.myRequestPkg.MyRequestOptionsPkg.myRequestPublishedPkg.Adapter.MyRequestPublishedNoteAdapter;
import com.freelanceapp.paymentPkg.CreditCardPayment;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.CreditCardActivity;
import com.freelanceapp.userProfileRatingPkg.UserProfileActivity;
import com.freelanceapp.utility.CheckNetwork;


public class MyRequestPublishedNoteFragment extends Fragment implements MyRequestPublishedNoteAdapter.MyRequestPublishedNoteAppOnClickListener {
    private FragmentMyRequestPublishedNoteBinding fragmentMyRequestPublishedNoteBinding;
    private MyRequestPublishedNoteAdapter myRequestPublishedNoteAdapter;
    private RecyclerView rvnote;
    private ApiServices apiServices;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_request_published_note, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);
        return view;
    }

    private void init(View view) {
        rvnote = view.findViewById(R.id.rvnoteId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvnote.setLayoutManager(layoutManager);
        MyRequestPublishedNoteAdapter myRequestPublishedNoteAdapter = new MyRequestPublishedNoteAdapter(getActivity(), this);
        rvnote.setAdapter(myRequestPublishedNoteAdapter);
    }

    @Override
    public void myReqPublishedNoteTabClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlacceptid:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), CreditCardPayment.class);
              //  Intent intent = new Intent(getActivity(), CreditCardPayment.class);
              //  startActivity(intent);
               // getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.rldiscuteridd:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), ChatActivityMain.class);
               // Intent intent1 = new Intent(getActivity(), ChatActivity.class);
              //  startActivity(intent1);
              //  getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.ivmymissionid:
                CheckNetwork.nextScreenWithoutFinish(getActivity(), UserProfileActivity.class);

                //Intent profile = new Intent(getActivity(), UserProfileActivity.class);
              //  startActivity(profile);
               // getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;



        }

    }
}
