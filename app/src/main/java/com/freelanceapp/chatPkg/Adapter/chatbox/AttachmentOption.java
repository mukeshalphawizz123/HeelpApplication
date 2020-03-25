package com.freelanceapp.chatPkg.Adapter.chatbox;

import com.freelanceapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Varun John on 12 Feb, 2020
 * Github : https://github.com/varunjohn
 */
public class AttachmentOption implements Serializable {

    private int id;
    private String title;
    private int resourceImage;

    public static final byte GALLERY_ID = 103;
    public static List<AttachmentOption> getDefaultList() {
        List<AttachmentOption> attachmentOptions = new ArrayList<>();
        attachmentOptions.add(new AttachmentOption(GALLERY_ID, "Gallery", R.drawable.ic_attachment_gallery));
        return attachmentOptions;
    }

    public AttachmentOption(int id, String title, int resourceImage) {
        this.id = id;
        this.title = title;
        this.resourceImage = resourceImage;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getResourceImage() {
        return resourceImage;
    }
}
