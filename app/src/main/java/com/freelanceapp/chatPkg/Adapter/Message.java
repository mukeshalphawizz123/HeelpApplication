package com.freelanceapp.chatPkg.Adapter;

import android.net.Uri;

import java.util.List;

/**
 * Created by Varun John on 4 Dec, 2018
 * Github : https://github.com/varunjohn
 */
public class Message {

    public static int TYPE_TEXT = 1;
    public static int TYPE_AUDIO = 21;
    public static int TYPE_IMAGE = 31;

    public String text;
    public int type;
    public int time;
    private List<Uri> uris;


    public Message(String text) {
        this.text = text;
        this.type = TYPE_TEXT;
    }

    public Message(int time) {
        this.time = time;
        this.type = TYPE_AUDIO;
    }
    public Message(List<Uri> uriList){
        this.uris = uriList;
        this.type = TYPE_IMAGE;
    }

    public List<Uri> getUris() {
        return uris;
    }

    public void setUris(List<Uri> uris) {
        this.uris = uris;
    }
}
