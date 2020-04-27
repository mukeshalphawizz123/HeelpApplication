package com.frelance.notificationPkg;

public class NotificatinModel {
    public interface OnCustomCartListener {
        void changeCartState();
    }
    private static NotificatinModel mInstance;
    private OnCustomCartListener mListener;
    private String mCurrentNotificatinCount;

    private NotificatinModel() {
    }

    public static NotificatinModel getInstance() {
        if (mInstance == null) {
            mInstance = new NotificatinModel();
        }
        return mInstance;
    }

    public void setListener(OnCustomCartListener listener) {
        mListener = listener;
    }

    public void setNotificationCount(String state) {
        if (mListener != null) {
            mCurrentNotificatinCount = state;
            notifyStateChange();
        }
    }

    public String getNotificationCount() {
        return mCurrentNotificatinCount;
    }

    private void notifyStateChange() {
        mListener.changeCartState();
    }
}
