package com.renovavision.rxmvp.presenters;

import android.support.annotation.NonNull;

import com.renovavision.rxmvp.ui.views.BaseView;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<T extends BaseView> {

    protected WeakReference<T> mViewRef;

    public BasePresenter(@NonNull T baseView) {
        mViewRef = new WeakReference<>(baseView);
    }

    protected T getView() {
        return mViewRef.get();
    }

    public void onViewCreated() {
        // do nothing here - will implements in children classes
    }

    public void onViewResumed() {
       // do nothing here - will implement in children class
    }

    public void onViewPaused() {
        // do nothing here - will implement in children class
    }

    public void onViewDestroyed() {
        // do nothing here - will implements in children classes
    }

}
