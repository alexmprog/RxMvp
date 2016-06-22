package com.renovavision.rxmvp.presentation.ui.user.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.renovavision.rxmvp.R;
import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.domain.UseCaseExecutor;
import com.renovavision.rxmvp.domain.usecase.github.GetUserUseCase;
import com.renovavision.rxmvp.presentation.common.presenter.BasePresenter;
import com.renovavision.rxmvp.presentation.ui.user.view.UserView;
import com.renovavision.rxmvp.presentation.ui.user.view.UserViewError;
import com.renovavision.rxmvp.presentation.ui.user.view.UserViewModel;

import rx.Subscription;

public class UserPresenter extends BasePresenter<UserView> {

    private static final String MODEL_KEY = "model_key";

    private UseCaseExecutor mUseCaseExecutor;

    private GetUserUseCase mGetUserUseCase;

    private UserModelTransformer mUserModelTransformer;

    private Subscription mSubscription;

    private UserViewModel mUserViewModel;

    public UserPresenter(@NonNull GetUserUseCase getUserUseCase,
                         @NonNull UseCaseExecutor useCaseExecutor,
                         @NonNull UserModelTransformer userModelTransformer) {
        this.mGetUserUseCase = getUserUseCase;
        this.mUseCaseExecutor = useCaseExecutor;
        this.mUserModelTransformer = userModelTransformer;
    }

    public void loadUser(@NonNull Repository repository) {
        if (mUserViewModel != null) {
            getMvpView().hideLoading();
            getMvpView().showContent();
            getMvpView().showData(mUserViewModel);
        } else {
            loadUserDetails(repository);
        }
    }

    private void loadUserDetails(@NonNull final Repository repository) {
        String userUrl = repository.owner.url.trim();
        if (TextUtils.isEmpty(userUrl)) {
            return;
        }

        getMvpView().hideContent();
        getMvpView().showLoading();

        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        mUserModelTransformer.setRepository(repository);

        mSubscription = mGetUserUseCase
                .getUser(userUrl)
                .map(mUserModelTransformer)
                .compose(mUseCaseExecutor.applySchedulers())
                .subscribe(userViewModel -> {
                    mUserViewModel = userViewModel;
                    if (isViewAttached()) {
                        getMvpView().hideLoading();
                        getMvpView().showContent();
                        getMvpView().showData(userViewModel);
                    }
                }, throwable -> {
                    if (isViewAttached()) {
                        getMvpView().hideLoading();
                        getMvpView().hideContent();
                        getMvpView().showError(new UserViewError(R.string.error_username_not_found));
                    }
                });
    }

    @Override
    public void saveState(Bundle outBundle) {
        if (mUserViewModel != null) {
            outBundle.putSerializable(MODEL_KEY, mUserViewModel);
        }
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mUserViewModel = (UserViewModel) savedInstanceState.getSerializable(MODEL_KEY);
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

}
