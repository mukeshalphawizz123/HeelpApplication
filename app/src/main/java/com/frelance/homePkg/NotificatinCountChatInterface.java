package com.frelance.homePkg;

public class NotificatinCountChatInterface {
    public interface OnCustomCartListener {
        void changeChatState();
    }

    private static NotificatinCountChatInterface mInstance;

    private OnCustomCartListener mListener;

    private String mCurrentNotificatinChatCount;

    private NotificatinCountChatInterface() {
    }

    public static NotificatinCountChatInterface getInstance() {
        if (mInstance == null) {
            mInstance = new NotificatinCountChatInterface();
        }
        return mInstance;
    }

    public void setListener(OnCustomCartListener listener) {
        mListener = listener;
    }

    public void setNotificationChatCount(String state) {
        if (mListener != null) {
            mCurrentNotificatinChatCount = state;
            notifyStateChange();
        }
    }

    public String getNotificationChatCount() {
        return mCurrentNotificatinChatCount;
    }

    private void notifyStateChange() {
        mListener.changeChatState();
    }
}
