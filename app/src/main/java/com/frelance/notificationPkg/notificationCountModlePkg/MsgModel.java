package com.frelance.notificationPkg.notificationCountModlePkg;

public class MsgModel {
    public interface OnMsgListener {
        void changeMsgState();
    }

    private static MsgModel mInstance;
    private OnMsgListener mListener;
    private String mCurrentNotificatinMsgCount;

    private MsgModel() {
    }

    public static MsgModel getInstance() {
        if (mInstance == null) {
            mInstance = new MsgModel();
        }
        return mInstance;
    }

    public void setListener(OnMsgListener listener) {
        mListener = listener;
    }

    public void setNotificationMsgCount(String state) {
        if (mListener != null) {
            mCurrentNotificatinMsgCount = state;
            notifyStateChange();
        }
    }

    public String getNotificationMsgCount() {
        return mCurrentNotificatinMsgCount;
    }

    private void notifyStateChange() {
        mListener.changeMsgState();
    }
}
