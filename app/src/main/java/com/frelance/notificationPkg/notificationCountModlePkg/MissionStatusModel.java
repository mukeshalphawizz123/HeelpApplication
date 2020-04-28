package com.frelance.notificationPkg.notificationCountModlePkg;

public class MissionStatusModel {
    public interface OnMissListener {
        void changeMissState();
    }

    private static MissionStatusModel mInstance;
    private OnMissListener mListener;
    private String mCurrentNotificatinMissCount;

    private MissionStatusModel() {
    }

    public static MissionStatusModel getInstance() {
        if (mInstance == null) {
            mInstance = new MissionStatusModel();
        }
        return mInstance;
    }

    public void setListener(OnMissListener listener) {
        mListener = listener;
    }

    public void setNotificationMissCount(String state) {
        if (mListener != null) {
            mCurrentNotificatinMissCount = state;
            notifyStateChange();
        }
    }
    public String getNotificationMissCount() {
        return mCurrentNotificatinMissCount;
    }
    private void notifyStateChange() {
        mListener.changeMissState();
    }
}
