package com.frelance.notificationPkg.notificationCountModlePkg;

public class PaymentInterfaceModel {
    public interface OnPayemntListener {
        void changePaymentState();
    }

    private static PaymentInterfaceModel mInstance;
    private OnPayemntListener mListener;
    private String mCurrentNotificatinPaymentCount;

    private PaymentInterfaceModel() {
    }

    public static PaymentInterfaceModel getInstance() {
        if (mInstance == null) {
            mInstance = new PaymentInterfaceModel();
        }
        return mInstance;
    }

    public void setListener(OnPayemntListener listener) {
        mListener = listener;
    }

    public void setNotificationPaymentCount(String state) {
        if (mListener != null) {
            mCurrentNotificatinPaymentCount = state;
            notifyStateChange();
        }
    }
    public String getNotificationPaymentCount() {
        return mCurrentNotificatinPaymentCount;
    }
    private void notifyStateChange() {
        mListener.changePaymentState();
    }
}
