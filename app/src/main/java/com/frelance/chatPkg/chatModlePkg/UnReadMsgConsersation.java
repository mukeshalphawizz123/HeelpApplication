package com.frelance.chatPkg.chatModlePkg;

import java.util.ArrayList;


public class UnReadMsgConsersation {
    private ArrayList<UnReadMessageUserModle> listMessageData;
    public UnReadMsgConsersation(){
        listMessageData = new ArrayList<>();
    }

    public ArrayList<UnReadMessageUserModle> getListMessageData() {
        return listMessageData;
    }
}
