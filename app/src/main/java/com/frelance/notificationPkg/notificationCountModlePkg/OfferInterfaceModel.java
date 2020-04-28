package com.frelance.notificationPkg.notificationCountModlePkg;

public class OfferInterfaceModel {
    public interface OnOfferListener {
        void changeOfferState();
    }

    private static OfferInterfaceModel mInstance;
    private OnOfferListener mListener;
    private String mCurrentNotificatinOfferCount;

    private OfferInterfaceModel() {
    }

    public static OfferInterfaceModel getInstance() {
        if (mInstance == null) {
            mInstance = new OfferInterfaceModel();
        }
        return mInstance;
    }

    public void setListener(OnOfferListener listener) {
        mListener = listener;
    }

    public void setNotificationOfferCount(String state) {
        if (mListener != null) {
            mCurrentNotificatinOfferCount = state;
            notifyStateChange();
        }
    }
    public String getNotificationOfferCount() {
        return mCurrentNotificatinOfferCount;
    }
    private void notifyStateChange() {
        mListener.changeOfferState();
    }
}
