package com.renovavision.rxmvp.presentation.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.renovavision.rxmvp.presentation.common.di.component.ActivityComponent;
import com.renovavision.rxmvp.presentation.common.presenter.MvpPresenter;
import com.renovavision.rxmvp.presentation.common.view.MvpView;

import javax.inject.Inject;

public abstract class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>, T extends ActivityComponent> extends AppCompatActivity implements ActivityComponent.Injector<T> {

    @Inject
    protected P presenter;

    private T activityComponent;

    protected P getMvpPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();

        // restore state if needed
        if (savedInstanceState != null) {
            presenter.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public T getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = createActivityComponent();
        }
        return activityComponent;
    }
}


