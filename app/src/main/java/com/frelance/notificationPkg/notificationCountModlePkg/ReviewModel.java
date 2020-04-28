package com.frelance.notificationPkg.notificationCountModlePkg;

public class ReviewModel {
    public interface OnReviewListener {
        void changeReviewState();
    }

    private static ReviewModel mInstance;
    private OnReviewListener mListener;
    private String mCurrentNotificatinReviewCount;

    private ReviewModel() {
    }

    public static ReviewModel getInstance() {
        if (mInstance == null) {
            mInstance = new ReviewModel();
        }
        return mInstance;
    }

    public void setListener(OnReviewListener listener) {
        mListener = listener;
    }

    public void setNotificationReviewCount(String state) {
        if (mListener != null) {
            mCurrentNotificatinReviewCount = state;
            notifyStateChange();
        }
    }
    public String getNotificationReviewCount() {
        return mCurrentNotificatinReviewCount;
    }
    private void notifyStateChange() {
        mListener.changeReviewState();
    }
}
