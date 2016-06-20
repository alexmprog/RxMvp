package com.renovavision.rxmvp.presentation.common.presenter;

import android.os.Bundle;

import com.renovavision.rxmvp.presentation.common.view.MvpView;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

    void saveState(Bundle outBundle);

    void restoreState(Bundle savedInstanceState);
}
