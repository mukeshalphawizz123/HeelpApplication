package com.freelanceapp.messageListPkg.MessageListFragmentPkg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.R;
import com.freelanceapp.chatPkg.ChatActivity;
import com.freelanceapp.messageListPkg.MessageListAdapterPkg.MessageToutAdapter;
import com.freelanceapp.utility.CheckNetwork;

public class MessageToutFragment extends Fragment implements MessageToutAdapter.MessageToutAppOnClickListener {
    private MessageToutAdapter messageToutAdapter;
    private RecyclerView rvmsglist;

    // private RelativeLayout rlmsguserid;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  fragmentMessageToutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_tout, container, false);
        View view = inflater.inflate(R.layout.fragment_message_tout, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        //  rlmsguserid=view.findViewById(R.id.rlmsguserid);
        // rlmsguserid.setOnClickListener(this);
        rvmsglist = view.findViewById(R.id.rvmsglistId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvmsglist.setLayoutManager(layoutManager);
        MessageToutAdapter messageToutAdapter = new MessageToutAdapter(getActivity(), this);
        rvmsglist.setAdapter(messageToutAdapter);
    }

    public void onClick(View view, int position) {

    }

    @Override
    public void msgOnClick(View view, int position) {
        switch (view.getId()) {
            case R.id.rlmsguserid:
                CheckNetwork.nextScreenWithoutFinish(getActivity(),ChatActivity.class);
                break;
        }
    }
}
